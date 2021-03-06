package ztzb.com.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import ztzb.com.service.IZtzbService;
import ztzb.com.util.Utils;
import ztzb.com.util.XmlValidateByXSD;
import ztzb.ws.entity.ResponseMessage;

@Service("ZtzbAServiceImpl")
public class ZtzbAServiceImpl implements IZtzbService {
	private static Logger logger = Logger.getLogger(ZtzbAServiceImpl.class);
	public ResponseMessage excute(Element element) {
		ResponseMessage response = new ResponseMessage();
		//获取到传递过来的参数 element的节点名，A02为例，曁最外围的<A02></A02>的节点名为A02
		String rootName = element.getName();
		String schemaName = Utils.getSchemaNameByRootMapping(rootName);
		try {
			//用XSD规范，验证XML的合法性
			XmlValidateByXSD.validateByXSD(schemaName, element.asXML());
		} catch (Throwable e1) {
			e1.printStackTrace();
			logger.error(e1);
			response.setCode("FALSE");
			response.setMessage(e1.getMessage());
			response.setResponseTime(Calendar.getInstance().getTime().toString());
			return response;
		}
		//获取到XML节点对应实体类的映射配置文件
		String abm = Utils.getAttributeMappingByRootMapping(rootName);
		Properties attrMappingPies = Utils.getProperties(abm);
		Map<String, String> map = new HashMap<String, String>();
		//循环所以子节点，将映射配置文件中节点名对应的变量名作为KEY,节点值作为VALUE,MAP用于提高效率，避免多层循环
		for(@SuppressWarnings("unchecked")
		Iterator<Element> i = element.elementIterator(); i.hasNext();){
			Element e = i.next();
			map.put(attrMappingPies.getProperty(e.getName()), e.getText());
		}
		String entityName = Utils.getEntityByRootName(rootName);
		Object entity = null;
		try {
			//根据映射，将节点值赋值给对应的变量
			entity = Utils.prepareEntity(entityName, map);

		} catch (Throwable e) {
			e.printStackTrace();
			response.setCode("FALSE");
			response.setMessage(e.getMessage());
			response.setResponseTime(Calendar.getInstance().getTime().toString());
			return response;
		}
		//用节点名，获取到serviceConfig.properties中value,例A02,获取到A02ServiceImpl,save,ztzb.com.entity.A02EntityBean
		String serviceParam = Utils.getServiceByRootName(rootName);
		String[] serviceParams = serviceParam.split(",");
		//用节点名，获取项目启动自加载至entitiesMap中的参数数组，例A02,获取到[ztzb.com.entity.A02EntityBean]
		Class[] classes = Utils.getEntitiesFromMap(rootName);
		//因自定义的第二个参数，曁方法save就一个参数，所以对应的值数组 length就为1位
		Object[] entities = new Object[1];
		try {
			entities[0] = entity;
			//此类的此方法的，根据实体类对象，执行实际的业务逻辑进行数据处理
			Utils.excuteService(serviceParams[0],serviceParams[1],classes, entities);
		} catch (Throwable e) {
			e.printStackTrace();
			response.setCode("FALSE");
			response.setMessage(e.getMessage());
			response.setResponseTime(Calendar.getInstance().getTime().toString());
			return response;
		}
		
		response.setCode("TRUE");
		response.setMessage("Message is saved");
		response.setResponseTime(Calendar.getInstance().getTime().toString());
		
		return response;
	}

}
