package ztzb.ws.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="response")   
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7040136954665869105L;
	
	@XmlElement(required = true)
	private String code;
	@XmlElement(required = true)
	private String message;
	@XmlElement(required = true)
	private String responseTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public ResponseMessage() {
		// TODO Auto-generated constructor stub
	}

}
