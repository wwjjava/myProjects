package ztzb.com.tongtech.gtp;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.Logger;

public class GtpMove implements Runnable {

	private static Logger log = Logger.getLogger(GtpMove.class);
	
	private static Properties pt;
	private static String gtpDir;
	private static String hdpDir;
	
	static{
		try {
			FileInputStream fis = new FileInputStream("gtp.conf");
			pt = new Properties();
			pt.load(fis);
			fis.close();
			
			gtpDir = pt.getProperty("gtpDir");
			hdpDir = pt.getProperty("hdpDir");
			
			System.out.println(gtpDir+"-"+hdpDir);
			if(!hdpDir.endsWith("/")){
				hdpDir += "/";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String dispatchProcesser(File file) {
		
		String filePath = file.getPath();
		String fileName = file.getName();
		
		System.out.println("开始处理处理文件："+fileName);
		
		String fileUUID = fileName.substring(fileName.lastIndexOf(".")-32,fileName.lastIndexOf("."));
		char fileType=fileName.charAt(0);
		
		if(fileType=='L'){
			//调用元数据清单警戒程序
		}
			
		else if(fileType=='U'){
			//调用非结构文件警戒程序1.存入Hadoop2.写入非结构化文件状态标识表
			
			//1.存入hadoop,存入成功则删除文件
			
//			if(fileName.endsWith(".ok")){
//				continue;
//			}
			
			System.out.println(filePath);
			
			InputStream in = null;
			OutputStream out = null;
			boolean delFlag = true;
			try {
				in = new BufferedInputStream(new FileInputStream(filePath));
				Configuration conf = new Configuration();
				String hdpFile = hdpDir + fileName;
				FileSystem fs = FileSystem.get(URI.create(hdpFile),conf);	//获得hadoop系统的连接  
				out = fs.create(new Path(hdpFile));
				IOUtils.copyBytes(in, out, 4096,true);
				System.out.println("非结构化数据入库Hadoop成功："+fileName);
			} catch (Exception e) {
				/*if (e instanceof FileNotFoundException) {
					System.out.println(e.getCause());
				}*/
				delFlag = false;
//				e.printStackTrace();
				System.out.println(e.getCause());
				System.out.println("文件入Hadoop遇到异常："+fileName+",将异常退出该文件处理过程。");
				return "fail";
			} finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(delFlag){
				file.delete();
				/*if(!fileName.endsWith(".ok")){
					File newFile = new File(filePath + ".ok");
					try {
						newFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/
			}
		
			//2.写入数据库
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement statement = null;
			try {
				String sql ="insert into UNSTRUCTUREDFILESTATUS "
					+ "(ID, FILEUUID, ISGTPFILEARRIVED, ISWSMETADATAARRIVED)"
					+ " values (?,?,?,?)";
				statement = conn
						.prepareStatement(sql);
				statement.setString(1, UUIDUtil.getUUID());
				statement.setString(2, fileUUID);
				statement.setInt(3, 1);
				statement.setInt(4, 0);
				System.out.println(sql);
				statement.executeUpdate();
				System.out.println("非结构化文件到达数据库标识更新成功："+fileName);
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("非结构化文件到达数据库标识表示更新失败："+fileName);
				return "fails";
			} finally {
				DatabaseConnection.releaseConnection(conn, statement, null);
			}
			System.out.println("该文件处理完成："+fileName);
			return "success";
		}
		System.out.println(fileUUID+fileType);
		return null;
	}
	public void run(){
		System.out.println(gtpDir);
		System.out.println(hdpDir);
		
		if(gtpDir == null || gtpDir.equals("") || hdpDir == null || hdpDir.equals("")){
			return ;
		}
		
		while (true){
			File dir = new File(gtpDir);
			File[] files = dir.listFiles();
			if(files == null || files.length < 1){
				continue ;
			}
			
			for(File file : files){
				//调用警戒函数入口
				this.dispatchProcesser(file);
			}
			
			try {
				//每隔30s扫描一下目录
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
