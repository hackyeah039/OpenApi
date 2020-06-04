<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String key=request.getParameter("key");
String type=request.getParameter("type");

StringBuffer sb=new StringBuffer();
String surl="https://openapi.gg.go.kr/PlaceThatDoATasteyFoodSt?key=366259b4781645a3af9b4fcd6a8838b2&type=json&pIndex=1&pSize=100";

URL url=new URL(surl);
HttpURLConnection conn=(HttpURLConnection)url.openConnection();
if(conn!=null){
	conn.setConnectTimeout(10000);//접속대기시간 10초 설정
	conn.setUseCaches(false);//캐쉬사용안하기
	if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){//서버로부터 응답이 성공적으로 왔으면
		BufferedReader br=
		 new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		String line="";
		while((line=br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		conn.disconnect();
	}
}
System.out.println(sb);
response.setContentType("text/plain;charset=utf-8");
PrintWriter pw=response.getWriter();
pw.print(sb);
pw.close();
%>