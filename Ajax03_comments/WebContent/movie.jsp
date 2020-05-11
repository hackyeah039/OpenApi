<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>movie.jsp</title>
<style>
	.comm{width:400px;height:100px;border:1px solid #aaa;margin-bottom: 5px;}
</style>
<script>
	var xhrList = null;
	function getList(){
		xhrList = new XMLHttpRequest();
		xhrList.onreadystatechange=callback;
		xhrList.open('get','comments.do?mnum=${vo.mnum}',true);
		xhrList.send();
	}
	function callback(){
		if(xhrList.readyState==4 && xhrList.status==200 ){
			deAll();//기존댓글 다 지우고 새로운 댓글 새롭게
			var xml=xhrList.responseXML;
			var comm=xml.getElementsByTagName("comm");
			var commList=document.getElementById("commList");
			for(var i=0;i<comm.length;i++){
				var id=comm[i].getElementsByTagName("id")[0].firstChild.nodeValue;
				var comments=comm[i].getElementsByTagName("comments")[0].firstChild.nodeValue;
				var num=comm[i].getElementsByTagName("num")[0].firstChild.nodeValue;
				var div=document.createElement("div");
				div.innerHTML="아이디 : " +id +"<br>" +
							"내용 : " + comments +"<br>" +
							"<input type='button' value='삭제' onclick='delete(${num})'>";
				div.className="comm";
				commList.appendChild(div);
			}
		}
	}
	var xhh = null;
	function insertComm(){
		var id=document.getElementById("id").value;
		var comments=document.getElementById("comments").value;
		xhh = new XMLHttpRequest();
		xhh.onreadystatechange=callback3;
		xhh.open('post','insert.do',true);//댓글은 크기가 크므로 -?이건 get방식으로 보내는것임
				//그래서 send에다 보내고,그다음 post방식인경우 콘텐츠 타입을 지정해야되서 44번째줄생성 
				//무조건 open한 다음에 설정해야 함 
		xhh.setRequestHeader('Content-Type','application/x-www-form-urlencoded');		
		xhh.send('id='+ id +"&comments=" + comments + "&mnum=${vo.mnum}");
	}
	function callback3(){
		if(xhh.status==200 && xhh.readyState==4){
				
			var xml= xhh.responseXML;
			var code=xml.getElementsByTagName("code");
			var msg=code[0].firstChild.nodeValue;
			if(msg=='fail'){
				alert("댓글 등록에 "+ msg +"했습니다.");//메시지					
			}else{
				alert("댓글 등록에 "+ msg +"했습니다.");//메시지
				getList();
			}		
			
		}
	}
	//전체데이터를 다 받아와서 자식데이터를 지움
	function delAll(){//전체댓글 지우기
		var commlist=document.getElementById("commList");
		var childs=commList.childNodes;//전체 자식노드(모든댓글 ) 얻어오기 //childs는 ArrayList같은 배열임
		var len=childs.length;
		for (var i = len-1; i >= 0; i--) {//뒤에서부터 지우기(앞에서부터 지우면 seq가 2번째것->1로 바뀌기때문에)
			var comments =childs.item(i);
			commList.removeChild(comments);
		}
	}
	var dele=null;
	
	function delete(int num){
		dele = new XMLHttpRequest();
		dele.onreadystatechange()=deletecall;
		dele.open('get' , 'delete.do?num='+num , true);
		dele.send();
	}
	
	function deletecall(){
		if( dele.status == 200 && dele.readyState ==4 )
			var delexml= dele.responseXML;
	}
</script>
</head>
<body onload="getList()">
<h1>영화 상세페이지</h1>
<div>
	<h1>${vo.title }</h1>
	<p>
		내용 : ${vo.content }<br>
		감독 : ${vo.director }
	</p>
</div>
<div><!-- 댓글이 보여질 div -->
	<div id="commList"></div> <!-- 댓글 목록보여질 div-->
	<div> <!-- 입력할 div -->
		아이디 <input type="text" id="id"><br>
		댓글 <textarea rows="3" cols="30" id="comments"></textarea><br>
		<input type="button" value="등록" onclick="insertComm()">
		<!-- AJAX로 댓글등록하고 성공/실패메시지 alert로 보이기 -->
	</div>
</div>
</body>
</html>