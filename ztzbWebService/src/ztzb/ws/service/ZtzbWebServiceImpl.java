package ztzb.ws.service;

import java.util.Calendar;
import java.util.Iterator;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import ztzb.com.util.Utils;
import ztzb.ws.IZtzbWebService;
import ztzb.ws.entity.RequestMessage;
import ztzb.ws.entity.ResponseMessage;


@Service("ztzbWebServiceImpl")
@WebService(endpointInterface = "ztzb.ws.IZtzbWebService",targetNamespace="https://www.zhaotouzhaobiao.com/interface/all/wsdl") 
@XmlAccessorType(XmlAccessType.FIELD)
public class ZtzbWebServiceImpl implements IZtzbWebService { 
    private static Logger logger = Logger.getLogger(ZtzbWebServiceImpl.class);

	@SuppressWarnings("rawtypes")
	public ResponseMessage saveZtzbService(RequestMessage request) {
		ResponseMessage response = null;
		logger.info(request.getMessage());
		//从request里获取到传递的上行报文（XML格式）
		String XMLMessage = request.getMessage();
		//因目前不知具体报文格式，暂定外层加根节点<ztzb>,用于解析报文，迭代循环
		String xmlData = "<ztzb>"+XMLMessage+"</ztzb>";
		Document document = null;
		try {
			//用dom4j 将xml格式的String转换为XML的document对象
			document = DocumentHelper.parseText(xmlData);
			//设置编码格式为UTF-8
			document.setXMLEncoding("UTF-8");
		} catch (Throwable e) {
			e.printStackTrace();
			response = new ResponseMessage();
			response.setCode("FALSE");
			response.setMessage(e.getMessage());
			response.setResponseTime(Calendar.getInstance().getTime().toString());
			return response;
		}
		//迭代循环上行报文，例<ztzb><A02>内容略</A02><E01>内容略</E01></ztzb>,将获取到A02 和E01
		Iterator root = document.getRootElement().elementIterator();
		while(root.hasNext()){
			//将获取到节点<A02> 和<E01>
			Element e = (Element) root.next();
			//将获取到A02 和E01
			String markName = e.getName().toString();
			//在webserviceConfig.properties配置文件中，用A02做为KEY获取到对应的配置信息
			String wsConfig = Utils.getWebserviceConfigProperties().getProperty(markName);
			if(wsConfig != null){
				String[] configParam = wsConfig.split(",");
				//根据配置文件里的第一个参数，获取到项目启动加载至classesMap里的类对象数组，作为要执行方法的参数类数组
				Class[] classes = Utils.getClassesFromMap(configParam[0]);
				//因自定义的第三个参数，曁方法excute就一个参数，所以对应的值数组 length就为1位
				Object[] entities = new Object[1];
				//第四位参数为方法的参数为Element，所以直接赋值即可
				entities[0] = e;
			    try {
			    	//此类的此方法的解析报文，拼装一个对应的实体类对象，返回处理结果
			    	response = (ResponseMessage) Utils.excuteService(configParam[1],configParam[2],classes, entities);
				} catch (Throwable e1) {
					e1.printStackTrace();
					logger.error(e1);
				}
			}
		}
		
		return response;
	}
} 
