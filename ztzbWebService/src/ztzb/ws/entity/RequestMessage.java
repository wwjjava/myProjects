package ztzb.ws.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="request")   
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestMessage  implements java.io.Serializable {   
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(required = true)
	private String message; 
  
  
    public RequestMessage(){
    	super();
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}   