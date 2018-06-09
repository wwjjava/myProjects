package ztzb.com.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_A03")
public class A03EntityBean {
	private String A010001;
	private String A020001;
	private String A020002;
	private String A020003;
	private String E010003;
	private String C010001;
	private String C050001;
	private String A020004;
	private String A020005;
	private Date A020006;
	private String A020007;
	private String A020008;
	private String A020009;
	private String A020010;
	private String D010001;
	private String A020011;
	@Id
	@GeneratedValue
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getA010001() {
		return A010001;
	}
	public void setA010001(String a010001) {
		A010001 = a010001;
	}
	public String getA020001() {
		return A020001;
	}
	public void setA020001(String a020001) {
		A020001 = a020001;
	}
	public String getA020002() {
		return A020002;
	}
	public void setA020002(String a020002) {
		A020002 = a020002;
	}
	public String getA020003() {
		return A020003;
	}
	public void setA020003(String a020003) {
		A020003 = a020003;
	}
	public String getE010003() {
		return E010003;
	}
	public void setE010003(String e010003) {
		E010003 = e010003;
	}
	public String getC010001() {
		return C010001;
	}
	public void setC010001(String c010001) {
		C010001 = c010001;
	}
	public String getC050001() {
		return C050001;
	}
	public void setC050001(String c050001) {
		C050001 = c050001;
	}
	public String getA020004() {
		return A020004;
	}
	public void setA020004(String a020004) {
		A020004 = a020004;
	}
	public String getA020005() {
		return A020005;
	}
	public void setA020005(String a020005) {
		A020005 = a020005;
	}
	public Date getA020006() {
		return A020006;
	}
	public void setA020006(Date a020006) {
		A020006 = a020006;
	}
	public String getA020007() {
		return A020007;
	}
	public void setA020007(String a020007) {
		A020007 = a020007;
	}
	public String getA020008() {
		return A020008;
	}
	public void setA020008(String a020008) {
		A020008 = a020008;
	}
	public String getA020009() {
		return A020009;
	}
	public void setA020009(String a020009) {
		A020009 = a020009;
	}
	public String getA020010() {
		return A020010;
	}
	public void setA020010(String a020010) {
		A020010 = a020010;
	}
	public String getD010001() {
		return D010001;
	}
	public void setD010001(String d010001) {
		D010001 = d010001;
	}
	public String getA020011() {
		return A020011;
	}
	public void setA020011(String a020011) {
		A020011 = a020011;
	}
}
