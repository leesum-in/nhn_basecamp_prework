package guestbook.controller;

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
				article_json.put("mod_date", article.getModDate());
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
		doGet(request, response);
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
