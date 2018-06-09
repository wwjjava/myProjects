package ztzb.com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_A20")
public class A20EntityBean {
	private String A030001Attr;
	private String A200001Attr;
	private String A200002Attr;
	private String E010003Attr;
	private String A200003Attr;
	@Id
	@GeneratedValue
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getA030001Attr() {
		return A030001Attr;
	}
	public void setA030001Attr(String a030001Attr) {
		A030001Attr = a030001Attr;
	}
	public String getA200001Attr() {
		return A200001Attr;
	}
	public void setA200001Attr(String a200001Attr) {
		A200001Attr = a200001Attr;
	}
	public String getA200002Attr() {
		return A200002Attr;
	}
	public void setA200002Attr(String a200002Attr) {
		A200002Attr = a200002Attr;
	}
	public String getE010003Attr() {
		return E010003Attr;
	}
	public void setE010003Attr(String e010003Attr) {
		E010003Attr = e010003Attr;
	}
	public String getA200003Attr() {
		return A200003Attr;
	}
	public void setA200003Attr(String a200003Attr) {
		A200003Attr = a200003Attr;
	}
	
}
