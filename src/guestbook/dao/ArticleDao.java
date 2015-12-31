package guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import guestbook.vo.Article;

public class ArticleDao {
	Connection connection;
	
	public void setConnection(Connection connection){
		this.connection = connection;
	}
	
	public List<Article> getArticles() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT idx,email,body,mod_date FROM guestbook ORDER BY idx ASC");
			
			ArrayList<Article> articles = new ArrayList<Article>();
			
			while( rs.next() ){
				articles.add(new Article().setIdx(rs.getInt("idx"))
						.setEmail(rs.getString("email"))
						.setBody(rs.getString("body"))
						.setModDate(rs.getDate("mod_date")));
			}
			return articles;
		} catch(Exception e){
			throw e;
		} finally {
			try {if (rs!=null) rs.close();} catch(Exception e){}
			try {if (stmt!=null) stmt.close();} catch(Exception e){}
		}
	}
	public int addArticle(Article article) throws Exception{
		PreparedStatement stmt = null;
		try{
			stmt = connection.prepareStatement("INSERT INTO guestbook(email,pwd,body,cre_date,mod_date)"
						+" VALUES (?,?,?,NOW(),NOW())",Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, article.getEmail());
			stmt.setString(2, article.getPassword());
			stmt.setString(3, article.getBody());
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch(Exception e){
			throw e;
		} finally {
			try {if (stmt!=null) stmt.close();} catch(Exception e){}
		}
	}
	public int modifyArticle(Article article) throws Exception{
		PreparedStatement stmt = null;
		try{
			stmt = connection.prepareStatement("UPDATE guestbook SET body=?,mod_date=NOW() WHERE idx=?");
			stmt.setString(1, article.getBody());
			stmt.setInt(2, article.getIdx());
			
			return stmt.executeUpdate();
		} catch(Exception e){
			throw e;
		} finally {
			try {if (stmt!=null) stmt.close();} catch(Exception e){}
		}
	}
	public boolean isValidPassword(Article article) throws Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = connection.prepareStatement("SELECT idx FROM guestbook WHERE idx=? and pwd=?");
			stmt.setInt(1, article.getIdx());
			stmt.setString(2, article.getPassword());
			rs = stmt.executeQuery();
			if(rs != null){
				return true;
			} else {
				return false;
			}
		} catch(Exception e){
			throw e;
		} finally{
			try {if (rs!=null) rs.close();} catch(Exception e){}
			try {if (stmt!=null) stmt.close();} catch(Exception e){}
		}
	}
}
