package guestbook.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import guestbook.dao.ArticleDao;
import guestbook.vo.Article;

/**
 * Servlet implementation class ArticleController
 */
@WebServlet("/article")
public class ArticleController extends HttpServlet {
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext sc = this.getServletContext();
		ArticleDao articleDao = (ArticleDao)sc.getAttribute("articleDao");
		JSONArray articlesArr = new JSONArray();
		
		response.setContentType("application/json; charset=UTF-8");
		try{
			ArrayList<Article> articles = (ArrayList<Article>) articleDao.getArticles();
			for (Article article : articles){
				JSONObject article_json = new JSONObject();
				article_json.put("idx", article.getIdx());
				article_json.put("email", article.getEmail());
				article_json.put("mod_date", article.getModDateString());
				article_json.put("body", article.getBody());
				articlesArr.add(article_json);
			}
			String jsonResult = articlesArr.toString();
			response.getWriter().print(jsonResult);
		} catch(Exception e) {
			response.sendError(404);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		ServletContext sc = this.getServletContext();
		ArticleDao articleDao = (ArticleDao)sc.getAttribute("articleDao");
		JSONObject article_json;
		try{
			article_json = getJSONRequest(request,response);
			Article article = new Article().setEmail(article_json.get("email").toString())
					.setBody(article_json.get("body").toString())
					.setPassword(article_json.get("pwd").toString());
			int idx = articleDao.addArticle(article);
			
			JSONObject return_json = new JSONObject();
			return_json.put("idx", idx);
			response.getWriter().print(return_json.toString());
		} catch(IOException e){
			response.sendError(404,"Request read Error");
		} catch(ParseException e){
			response.sendError(404,"JSON parse Error");
		} catch(Exception e){
			response.sendError(404,"DB insert Error");
		}
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		ServletContext sc = this.getServletContext();
		ArticleDao articleDao = (ArticleDao)sc.getAttribute("articleDao");
		JSONObject article_json;
		JSONObject return_json = new JSONObject();
		try{
			article_json = getJSONRequest(request,response);
			Article article = new Article().setIdx(Integer.parseInt(article_json.get("idx").toString()))
					.setBody(article_json.get("body").toString())
					.setPassword(article_json.get("pwd").toString());
			if(articleDao.isValidPassword(article)){
				int sql_res = articleDao.modifyArticle(article);
				return_json.put("status", "success");
				return_json.put("message", sql_res);
			} else {
				return_json.put("status", "fail");
				return_json.put("message", "Incorrect Password");
			}
			response.getWriter().print(return_json.toString());
		} catch(IOException e){
			response.sendError(404,"Request read Error");
		} catch(ParseException e){
			response.sendError(404,"JSON parse Error");
		} catch(Exception e){
			response.sendError(404,"DB insert Error");
		}
	}
	
	protected JSONObject getJSONRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException{
		StringBuffer jb = new StringBuffer();
		String line = null;
		try{
			BufferedReader reader = request.getReader();
			while ( (line = reader.readLine()) != null){
				jb.append(line);
			}
			JSONParser jsonParser = new JSONParser();
			return (JSONObject) jsonParser.parse(jb.toString());
		} catch(IOException ie){
			throw ie;
		} catch(ParseException pe){
			throw pe;
		}
	}
}
