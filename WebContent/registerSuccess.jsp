<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册成功</title>
</head>
<script type="text/javascript">
	window.onload = function() {
		var time = 10;
		function skip() {
			time--;
			document.getElementById("span").innerHTML = time;
			if (time == 0) {
				window.location = "${pageContext.request.contextPath}/index.jsp";
			}
		}
		setInterval(skip, 1000);
	}
</script>
<body>
	<h2>恭喜您,注册成功!请赶快前往您的邮箱进行激活</h2>
	<div>
		<span><a href="http://mail.163.com">网易163邮箱</a></span> <span><a
			href="http://mail.126.com">网易126邮箱</a></span> <span><a
			href="https://mail.qq.com/cgi-bin/loginpage">腾讯QQ邮箱</a></span>
	</div>
	<h1>
		<span id="span" style="color: red;">10</span>秒钟后跳转到主页
	</h1>
</body>
</html>