package ztzb.com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.xml.sax.SAXException;

/**
 * Webservice 相关工具类
 * @author chenxi
 * @version 2015-04-07
 */
public class Utils{
	private static Logger logger = Logger.getLogger(Utils.class);
	//用于初始化SchemaFactory对象
	private final static String schemaLanguage = XMLConstants.W3C_XML_SCHEMA_NS_URI;
	//attributeConfig.properties的相对路径
	public static String attributeConfigName;
	//schemaConfig.properties的相对路径
	public static String schemaConfigName;
	//webserviceConfig.properties的相对路径
	public static String webserviceConfigName;
	//serviceConfig.properties的相对路径
	public static String serviceConfigName;
	//entityConfig.properties的相对路径
	public static String entityConfigName;
	//用于存放properties配置文件，KEY为文件的相对路径，value为Properties对象
	private static Map<String,Properties> propertiesMap = new HashMap<String,Properties>();
	//用于存放XSD文件，KEY为文件的相对路径，value为Schema对象
	private static Map<String,Schema> schemaMap = new HashMap<String,Schema>();
	//用于存放entity，KEY为entity的name，包含包路径，value为此类的Class对象。
	private static Map<String,Class> entityMap = new HashMap<String,Class>();
	//用于存放webservice调用的解析XML类的方法的参数数组，KEY为XML根节点的name，value为的参数数组Class[]。
	private static Map<String,Class[]> classesMap = new HashMap<String,Class[]>();
	//用于存放解析XML类调用的实际业务类的方法的参数数组，KEY为XML根节点的name，value为的参数数组Class[]。
	private static Map<String,Class[]> entitiesMap = new HashMap<String,Class[]>();
	private static SchemaFactory schemaFactory;
	//webserviceConfig.properties的对象名
	private static Properties wsc;
	//schemaConfig.properties的对象名
	private static Properties shc;
	//attributeConfig.properties的对象名
	private static Properties abc;
	//serviceConfig.properties的对象名
	private static Properties dcn;
	//entityConfig.properties的对象名
	private static Properties ecn;
	private synchronized static SchemaFactory getSchemaFactory(){
		if(schemaFactory == null){
			schemaFactory = SchemaFactory.newInstance(schemaLanguage);
		}
		return schemaFactory;
	}
	public static Properties getEntityConfigProperties(){
		if(ecn == null){
			ecn = Utils.getProperties(entityConfigName);
		}
		return ecn;
	}
	public static Properties getServiceConfigProperties(){
		if(dcn == null){
			dcn = Utils.getProperties(serviceConfigName);
		}
		return dcn;
	}
	
	public static Properties getAttributeConfigProperties(){
		if(abc == null){
			abc = Utils.getProperties(attributeConfigName);
		}
		return abc;
	}
	
	public static Properties getSchemaConfigProperties(){
		if(shc == null){
			shc = Utils.getProperties(schemaConfigName);
		}
		return shc;
	}
	public static Properties getWebserviceConfigProperties(){
		if(wsc == null){
			wsc = Utils.getProperties(webserviceConfigName);
		}
		return wsc;
	}
	/**
	 * propertiesName 是配置文件的相对路径，如果propertiesMap中没有此KEY或者此KEY对应的值为null,则根据相对路径从磁盘加载此配置文件
	 * @param propertiesName
	 * @return Properties
	 */
	public static Properties getProperties(String propertiesName){
		Properties properties = propertiesMap.get(propertiesName);
		if(properties != null){
			return properties;
		}
		properties = new Properties();
		String url = Utils.class.getClassLoader().getResource(propertiesName).toString().substring(6);
		logger.info(File.separatorChar+url);
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(File.separatorChar+url));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		propertiesMap.put(propertiesName, properties);
		return properties;
		
	}
	/**
	 * propertiesName 是Schema文件(曁XSD文件)的相对路径，如果schemaMap中没有此KEY或者此KEY对应的值为null,则根据相对路径从磁盘加载此Schema文件
	 * @param propertiesName
	 * @return Schema
	 */
	public static Schema getSchema(String propertiesName){
		Schema schema = schemaMap.get(propertiesName);
		if(schema != null){
			return schema;
		}
		String url = Utils.class.getClassLoader().getResource(propertiesName).toString().substring(6);
		logger.info(File.separatorChar+url);
		
		try {
			schema = Utils.getSchemaFactory().newSchema( new File(File.separatorChar+url));
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		schemaMap.put(propertiesName, schema);
		return schema;
		
	}
	
	public static String getSchemaNameByRootMapping(String rootName){
		Properties shc = Utils.getSchemaConfigProperties();
		String shcValue = shc.getProperty(rootName);
		return shcValue;
	}
	public static String getAttributeMappingByRootMapping(String rootName){
		Properties abc = Utils.getAttributeConfigProperties();
		String abcValue = abc.getProperty(rootName);
		return abcValue;
	}
	
	public static String getEntityByRootName(String rootName){
		Properties tmc = Utils.getEntityConfigProperties();
		String ecValue = tmc.getProperty(rootName);
		return ecValue;
	}
	public static String getServiceByRootName(String rootName){
		Properties tmc = Utils.getServiceConfigProperties();
		String dcValue = tmc.getProperty(rootName);
		return dcValue;
	}
	/**
	 * entityName 是实体类的类名，包含了包路径，如果entityMap中没有此KEY或者此KEY对应的值为null,则根据此类名从磁盘加载此Class文件
	 * 初始化此Class,得到Object，根据java反射机制，若map中的key与Object对象中的变量对应起来，则value赋值给变量。
	 * @param entityName
	 * @param map KEY为变量名，value为变量值
	 * @return Object
	 * @throws Throwable
	 */
	public static Object prepareEntity(String entityName,Map<String,String> map) throws Throwable{
		Class entity = entityMap.get(entityName);
		if(entity == null){
		   entity = Class.forName(entityName);
		   entityMap.put(entityName, entity);
		}
		Object object = entity.newInstance();
		
		Method method[] =entity.getDeclaredMethods();
		for(int i = 0;i<method.length;i++){
			if(method[i].getName().startsWith("set") && !method[i].getName().equals("setId")){
				String attributeName = method[i].getName().substring(3);
				String value = map.get(attributeName);
				Class[] parameters = method[i].getParameterTypes();
				Object formatValue = value;
				if(parameters[0].getName().equals("java.util.Date")){
					value= value.replace("T", " ");
					formatValue = DateFormat.getDateTimeInstance().parse(value);
				}
			    method[i].invoke(object, formatValue);
			}
		}
		return object;
		
	}
	
	public static void putEntityToMap(String entityName) throws Exception{
		Class entity = entityMap.get(entityName);
		if(entity == null){
		   entity = Class.forName(entityName);
		   entityMap.put(entityName, entity);
		}
	}
	public static Class[] getEntitiesFromMap(String rootName){
		Class[] entity = entitiesMap.get(rootName);
		return entity;
	}
	/**
	 * 将String[] 中的元素加载为Class,变为Class[]作为value,rootName作为Key,存至classesMap
	 * @param rootName 
	 * @param classes 此数组的元素，为包含包路径的类名
	 * @throws Exception
	 */
	public static void putClassesToMap(String rootName,String[] classes) throws Exception{
		Class[] entities = classesMap.get(classes);
		if(entities == null){
			entities = new Class[classes.length];
			for(int i = 0; i<classes.length;i++){
        		String className = classes[i];
        		Class entity = Class.forName(className);
        		entities[i] = entity;
			}
		   
		   classesMap.put(rootName, entities);
		}
	}
	/**
	 * 将String[] 中的元素加载为Class,变为Class[]作为value,rootName作为Key,存至entitiesMap
	 * @param rootName
	 * @param classes  此数组的元素，为包含包路径的类名
	 * @throws Exception
	 */
	public static void putEntitiesToMap(String rootName,String[] classes) throws Exception{
		Class[] entities = entitiesMap.get(classes);
		if(entities == null){
			entities = new Class[classes.length];
			for(int i = 0; i<classes.length;i++){
        		String className = classes[i];
        		Class entity = Class.forName(className);
        		entities[i] = entity;
			}
		   
			entitiesMap.put(rootName, entities);
		}
	}
	public static Class[] getClassesFromMap(String rootName){
		Class[] entities = classesMap.get(rootName);
		return entities;
	}

	/**
	 * java反射机制，调用serviceName的excuteMethod方法，此方法的参数数组为param
	 * @param serviceName  Spring类池的类对象名
	 * @param excuteMethod 此类的要执行的方法
	 * @param param 此方法的参数数组
	 * @param entity 传递给此方法的值数组
	 * @return back 此方法的返回值，可以为null
	 * @throws Throwable
	 */
	public static Object excuteService(String serviceName,String excuteMethod,Class[] param,Object[] entity) throws Throwable{
		Object object = Utils.getBean(serviceName);
		Method method =object.getClass().getDeclaredMethod(excuteMethod, param);
		Object back = method.invoke(object, entity);
		return back;
	}

	/**
	 * 根据beanName从spring类池里获取已托管给Spring的类对象
	 * @param beanName Spring类池里的名字
	 * @return
	 */
	public static Object getBean(String beanName){
		
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	
}
