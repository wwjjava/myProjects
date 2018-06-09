package ztzb.com.tongtech.gtp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import org.apache.log4j.Logger;

public class GtpMain {
	
	private static Logger log = Logger.getLogger(GtpMain.class);
	
	private static String className = GtpMain.class.getName();
	
	public static void main(String[] args){
//		boolean flag = checkProcess(className);
		boolean flag = false;
		if (flag){
			System.out.println(className + "进程已经启动，程序结束运行！");
		    log.error(className + "进程已经启动，程序结束运行！");
		    return;
		}
		
		log.warn("********开始启动处理程序*********");
		Thread gtpMove = new Thread(new GtpMove());
	    gtpMove.start();
	    log.warn("********启动处理程序成功*********");
	}

	public static boolean checkProcess(String patcher){
		Process process = null;
	    InputStream in = null;
	    String result = null;
	    String shell = "ps -fu $USER |grep " + patcher + "|grep -v grep|wc -l";
	    
	    try{
	    	process = Runtime.getRuntime().exec(new String[] { 
	    	        "/bin/sh", "-c", shell });
	    }catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    if (process != null){
	    	in = process.getInputStream();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        String line = null;
	        try {
				while ((line = reader.readLine()) != null){
					result = line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
	    }else{
	    	return true;
	    }
	    

        if(result != null && !result.equals("1")){
        	return true;
        }
        
        return false;
	}
	
}