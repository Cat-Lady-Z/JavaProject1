<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html lang="en">
<head>
  <meta charset="utf-8">
      <base href="<%=basePath%>">

  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Login Form</title>
  <link rel="stylesheet" href="user/css/style.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
  <script type="text/javascript">
      function  checkVerifyCode() {
          var verifyCodeID = document.getElementsByName("verifyCode")[0];
          var verifyCode = verifyCodeID.value;
          var checkcode_sessionID = document.getElementsByName("checkcode_session")[0];
          var checkcode_session = checkcode_sessionID.value;


          if ( verifyCode == null || ""=== verifyCode || verifyCode !== checkcode_session){
              document.getElementById("messageVerifyCode").innerText="验证码输入错误";
              document.getElementById("messageVerifyCode").style.color = 'red';

          }else{
              document.getElementById("messageVerifyCode").innerText="验证码输入正确";
              document.getElementById("messageVerifyCode").style.color = 'blue';
          }
      }
  </script>
</head>
<body>

  <section class="container">
    <div class="login">
      <h1>用户登录
        <span style="text-align:center;padding-top:2px;"><font color="#ff0000">${message}</font></span></h1>
      <form method="post" action="${pageContext.request.contextPath }/user/UserServlet">
      
      	<input type="hidden" name="op" value="login"/>
        <p><input type="text" name="username" value="${cookie.username.value}" placeholder="用户名"></p>
        <p><input type="password" name="password" value="${cookie.password.value}" placeholder="密码"></p>
        <p><input type="text" name="verifyCode" placeholder="VerifyCode" style="width: 150px;"  onblur="checkVerifyCode()">
          <%--<input type="hidden" name="checkcode_session" value="${checkcode_session}">--%>
            <img src="${pageContext.request.contextPath}/verifyCode.jpg" alt="verifyCode" width="120px" height="30px" style="vertical-align: middle;"></p>
        <%--<span id="messageVerifyCode">${msg.error.verifyCode}</span>--%>
          <p class="remember_me">
          <label>
            <input type="checkbox" name="remember_me" id="remember_me"
                   <c:if test="${!empty cookie.password}">checked</c:if>>
            记住密码
          </label>
        </p>
        <p class="submit">
          <a href="${pageContext.request.contextPath }/user/register.jsp">注册</a>
          <a href="${pageContext.request.contextPath }/index.jsp">返回首页</a>
        </p>
        <p class="submit"><input type="submit" name="commit" value="登录"></p>
      </form>
    </div>
  </section>

</body>
</html>
