package guestbook.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {
	protected int idx;
	protected String email;
	protected String password;
	protected String body;
	protected Date creDate;
	protected Date modDate;
	
	public int getIdx(){
		return idx;
	}
	public Article setIdx(int idx){
		this.idx = idx;
		return this;
	}
	public String getEmail(){
		return email;
	}
	public Article setEmail(String email){
		this.email = email;
		return this;
	}
	public String getPassword(){
		return password;
	}
	public Article setPassword(String password){
		this.password = password;
		return this;
	}
	public String getBody(){
		return body;
	}
	public Article setBody(String body){
		this.body = body;
		return this;
	}
	public Date getCreDate(){
		return creDate;
	}
	public String getCreDateString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a"); 
		return sdf.format(creDate).toString();
	}
	public Article setCreDate(Date creDate){
		this.creDate = creDate;
		return this;
	}
	public Date getModDate(){
		return modDate;
	}
	public String getModDateString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a"); 
		return sdf.format(modDate).toString();
	}
	public Article setModDate(Date modDate){
		this.modDate = modDate;
		return this;
	}
}
