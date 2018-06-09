package ztzb.com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_E01")
public class E01EntityBean {
	private String E010002Attr;
	private String E010003Attr;
	private String E010004Attr;
	private String E010005Attr;
	private String E010006Attr;
	private String E010007Attr;
	private String E010008Attr;
	
	@Id
	@GeneratedValue
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getE010002Attr() {
		return E010002Attr;
	}
	public void setE010002Attr(String e010002Attr) {
		E010002Attr = e010002Attr;
	}
	public String getE010003Attr() {
		return E010003Attr;
	}
	public void setE010003Attr(String e010003Attr) {
		E010003Attr = e010003Attr;
	}
	public String getE010004Attr() {
		return E010004Attr;
	}
	public void setE010004Attr(String e010004Attr) {
		E010004Attr = e010004Attr;
	}
	public String getE010005Attr() {
		return E010005Attr;
	}
	public void setE010005Attr(String e010005Attr) {
		E010005Attr = e010005Attr;
	}
	public String getE010006Attr() {
		return E010006Attr;
	}
	public void setE010006Attr(String e010006Attr) {
		E010006Attr = e010006Attr;
	}
	public String getE010007Attr() {
		return E010007Attr;
	}
	public void setE010007Attr(String e010007Attr) {
		E010007Attr = e010007Attr;
	}
	public String getE010008Attr() {
		return E010008Attr;
	}
	public void setE010008Attr(String e010008Attr) {
		E010008Attr = e010008Attr;
	}

}
