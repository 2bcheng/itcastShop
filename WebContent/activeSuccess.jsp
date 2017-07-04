<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	window.onload = function() {
		var  time=3;
		function  change(){
			time--;
			document.getElementById("span").innerHTML=time;
			if(time==0){
				window.location="${pageContext.request.contextPath}/index.jsp"
			}
		}
		setInterval(change, 1000);
	}
</script>
</head>
<body>
	<h1>
		激活成功,<span id="span"  style="color: red;">3</span>秒后跳转到登录界面
	</h1>
</body>
</html>