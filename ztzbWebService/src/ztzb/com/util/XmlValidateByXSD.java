package ztzb.com.util;

import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

public class XmlValidateByXSD {
	private static Logger logger = Logger.getLogger(XmlValidateByXSD.class);
	/**
	 * 根据传递的 schemaName获取并初始化一个Schema对象，用于验证传递的xmlMessage的合法性，包括数据类型以及数据长度等属性。
	 * @param schemaName 这里为schema文件的相对路径
	 * @param xmlMessage XML格式的字符串
	 * @throws Throwable
	 */
    public static void validateByXSD(String schemaName,String xmlMessage) throws Throwable {
    	Schema schema = Utils.getSchema(schemaName);
			
		Validator validator = schema.newValidator();
		InputSource inputSource = new InputSource(new StringReader(xmlMessage));
		Source source =new SAXSource(inputSource);

		validator.validate(source);
		logger.info(xmlMessage);
		logger.info("Vallidate is OK");

    }
}
