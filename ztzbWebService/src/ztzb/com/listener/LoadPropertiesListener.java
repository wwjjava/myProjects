package ztzb.com.listener;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import ztzb.com.util.Utils;

public class LoadPropertiesListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(LoadPropertiesListener.class);
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("Application CLOSE---------------------");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Application START---------------------Begin to Load Properties Files");
		//将配置文件sysProperties.properties加载，并将其中各个配置文件的相对路径赋值给Utils类的变量
		Properties sysProperties = Utils.getProperties("sysProperties.properties");
		Utils.attributeConfigName = sysProperties.getProperty("attributeConfig");
		Utils.entityConfigName = sysProperties.getProperty("entityConfig");
		Utils.schemaConfigName = sysProperties.getProperty("schemaConfig");
		Utils.serviceConfigName = sysProperties.getProperty("serviceConfig");
		Utils.webserviceConfigName = sysProperties.getProperty("webserviceConfig");
		//这里根据实际的配置逻辑 加载配置文件里的，需要缓存至内存中的配置文件 及类对象，避免数据的冗余。
        Properties wsProperties = Utils.getWebserviceConfigProperties();
        Iterator<Entry<Object, Object>> itr = wsProperties.entrySet().iterator();
        while(itr.hasNext()){
        	Entry<Object, Object> wsets = itr.next();
        	String[] configParam = wsets.getValue().toString().split(",");
        	//以A02为例，这里获取到第一个参数，曁A02,对应了其他各个properties配置文件的KEY.
        	String wsRootName = configParam[0];
        	//此步是将配置文件里的方法的参数名，拼装为一个数组，A02为例，数组为[org.dom4j.Element]
        	String[] params = new String[configParam.length - 3];
        	for(int i = 3; i<configParam.length;i++){
        		params[i-3] = configParam[i];
			}
			try {
				//将这个参数数组 存在classesMap中，A02为例，KEY为A02,[org.dom4j.Element]为VALUE
				Utils.putClassesToMap(wsRootName,params);
			} catch (Exception e) {
				logger.error(e);
			}
			//根据实际的配置逻辑，将各个配置文件加载至内存中
        	Utils.getSchemaNameByRootMapping(wsRootName);
        	String scmName = Utils.getSchemaConfigProperties().getProperty(wsRootName);
        	Utils.getSchema(scmName);
        	String attbm = Utils.getAttributeConfigProperties().getProperty(wsRootName);
        	Utils.getProperties(attbm);
        	String entityName = Utils.getEntityConfigProperties().getProperty(wsRootName);
        	try {
				Utils.putEntityToMap(entityName);
			} catch (Exception e) {
				logger.error(e);
			}
        	//此步为加载实际业务处理类的配置文件
        	Properties serviceConfig = Utils.getServiceConfigProperties();
        	String[] serviceParam = serviceConfig.getProperty(wsRootName).split(",");
        	//此步是将配置文件里的方法的参数名，拼装为一个数组，A02为例，数组为[ztzb.com.entity.A02EntityBean]
        	String[] sparams = new String[serviceParam.length - 2]; 
        	for(int i = 2; i<serviceParam.length;i++){
        		sparams[i-2] = serviceParam[i];
			}
			try {
				//将这个参数数组 存至entitiesMap中，A02为例，KEY为A02,[ztzb.com.entity.A02EntityBean]为VALUE
				Utils.putEntitiesToMap(wsRootName,sparams);
			} catch (Exception e) {
				logger.error(e);
			}
        }
        logger.info("Load Properties Files is complete!");
	}

}
