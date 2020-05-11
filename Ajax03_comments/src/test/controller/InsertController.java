package test.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.dao.CommentsDao;
import test.vo.CommentsVo;
@WebServlet("/insert.do")
public class InsertController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String id=req.getParameter("id");
		String comments=req.getParameter("comments");
		int mnum=Integer.parseInt(req.getParameter("mnum"));//받아오고
		CommentsVo vo = new CommentsVo(0, mnum, id, comments);//Vo에 저장하고
		CommentsDao dao=CommentsDao.getInstance();//Dao생성하고
		int n= dao.insert(vo);//메소드 호출하고
		resp.setContentType("text/xml;charset=utf-8"); //XML로 응답
		PrintWriter pw = resp.getWriter();
		pw.print("<?xml version='1.0' encoding='utf-8'?>");
		pw.print("<result>");
		if(n>0) {
			pw.print("<code>success</code>");
		}else {
			pw.print("<code>fail</code>");
		}
		pw.print("</result>");
	}
}
