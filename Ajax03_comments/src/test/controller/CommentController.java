package test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.dao.CommentsDao;
import test.vo.CommentsVo;
@WebServlet("/comments.do")
public class CommentController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int mnum=Integer.parseInt(req.getParameter("mnum"));
		CommentsDao dao=CommentsDao.getInstance();
		ArrayList<CommentsVo> list= dao.listAll(mnum);
		resp.setContentType("text/xml;charset=utf-8");
		PrintWriter pw=resp.getWriter();
		pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<result>");
		for(CommentsVo vo:list) {
			pw.println("<comm>");
			pw.println("<num>"+vo.getNum()+"</num>");
			pw.println("<mnum>"+vo.getMnum()+"</mnum>");
			pw.println("<id>"+vo.getId()+"</id>");
			pw.println("<comments>"+vo.getComments()+"</comments>");
			pw.println("</comm>");
		}
		pw.println("</result>");
	}
}
