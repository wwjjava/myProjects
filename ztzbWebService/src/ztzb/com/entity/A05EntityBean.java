package ztzb.com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_A05")
public class A05EntityBean {
	@Id
	@GeneratedValue
	private String id;
	private String A1314;
	private String A1315;
	
	public String getA1314() {
		return A1314;
	}
	public void setA1314(String a1314) {
		A1314 = a1314;
	}
	public String getA1315() {
		return A1315;
	}
	public void setA1315(String a1315) {
		A1315 = a1315;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
