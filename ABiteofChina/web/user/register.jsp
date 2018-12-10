<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link href="${pageContext.request.contextPath }/user/css/style.css" rel='stylesheet' type='text/css' />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!--webfonts-->
		<!--//webfonts-->
		<script src="js/setDate.js" type="text/javascript"></script>
		<script type="text/javascript">
            //必须是字母数字 下划线
            function  checkUsername() {
                var elementById = document.getElementById("username");
                var username =elementById.value;
                //var uPattern = /^[a-zA-Z0-9_]{4,16}$/;
                var uPattern = /^\w{3,8}$/;

                var valid = uPattern.test(username);

                if (!valid){
                    document.getElementById("messageUsername").innerText="invalid";
                }else{
                    document.getElementById("messageUsername").innerText="valid";
                    //发送异步请求去验证看看是否可用
                    isUserUsernameAvailable();
                }
            }
            function isUserUsernameAvailable() {
                var username = document.getElementsByName("username")[0];
                var messageUsername = document.getElementById("messageUsername");
                if (username.value === "") {
                    messageUsername.innerText = "";
                    return;
                }
                var request = new XMLHttpRequest();
                request.onreadystatechange = function () {
                    if (request.readyState == 4 && request.status == 200) {
                        var responseText = request.responseText;
                        if (responseText === "true") {
                            messageUsername.innerText = "用户名可用";
                            messageUsername.style.color = 'blue';
                        } else {
                            messageUsername.innerText = "用户名重复";
                            messageUsername.style.color = 'red';
                        }
                    }
                };
                var url = "${pageContext.request.contextPath}/admin/AjaxServlet?ajax=isUserUsernameAvailable&username=" + username.value;
                request.open("GET",url, true);
                request.send(null);
            }

            function  checkNickname() {
                var elementById = document.getElementById("nickname");
                var nickname =elementById.value;
                var nPattern = /^[a-zA-Z0-9_\u4e00-\u9fa5]{1,16}$/;
                //var nPattern = /^\w{3,8}$/;

                var valid = nPattern.test(nickname);

                if (!valid){
                    document.getElementById("messageNickname").innerText="invalid";
                    document.getElementById("messageNickname").style.color = 'red';
                }else{
                    document.getElementById("messageNickname").innerText="valid";
                    document.getElementById("messageNickname").style.color = 'blue';
                }
            }

            function  checkPassword() {
                var elementById = document.getElementsByName("password")[0];
                var password =elementById.value;
                var pPattern = /^[a-zA-Z0-9_-]{4,16}$/;
                //var pPattern = /^\w{3,8}$/;

                var valid = pPattern.test(password);

                if (!valid){
                    document.getElementById("messagePassword").innerText="invalid";
                    document.getElementById("messagePassword").style.color = 'red';
                }else{
                    document.getElementById("messagePassword").innerText="valid";
                    document.getElementById("messagePassword").style.color = 'blue';
                }
            }

            function  checkBirthday() {
                var elementById = document.getElementsByName("birthday")[0];
                var birthday =elementById.value;
                var bPattern = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
                //var bPattern = /^\w{3,8}$/;

                var valid = bPattern.test(birthday);

                if (!valid){
                    document.getElementById("messageBirthday").innerText="invalid";
                    document.getElementById("messageBirthday").style.color = 'red';
                }else{
                    document.getElementById("messageBirthday").innerText="valid";
                    document.getElementById("messageBirthday").style.color = 'blue';
                }
            }


            function  checkEmail() {
                var email = document.getElementsByName("email")[0];
                //var uPattern = /^[a-zA-Z0-9_]{4,16}$/;

                var ePattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

                var valid = ePattern.test(email.value);

                if (!valid){
                    document.getElementById("messageEmail").innerText="invalid";
                }else{
                    document.getElementById("messageEmail").innerText="valid";
                    //发送异步请求去验证看看是否可用
                    isUserEmailAvailable();
                }
            }

            function isUserEmailAvailable() {
                var email = document.getElementsByName("email")[0];
                var messageEmail = document.getElementById("messageEmail");
                if (email.value === "") {
                    messageEmail.innerText = "";
                    return;
                }
                var request = new XMLHttpRequest();
                request.onreadystatechange = function () {
                    if (request.readyState == 4 && request.status == 200) {
                        var responseText = request.responseText;
                        if (responseText === "true") {
                            messageEmail.innerText = "邮箱可用";
                            messageEmail.style.color = 'blue';
                        } else {
                            messageEmail.innerText = "邮箱重复";
                            messageEmail.style.color = 'red';
                        }
                    }
                };
                var url = "${pageContext.request.contextPath}/admin/AjaxServlet?ajax=isUserEmailAvailable&email=" + email.value;
                request.open("GET",url, true);
                request.send(null);
            }
		</script>
	</head>

	<body>
		<div class="main" align="center">
			<div class="header">
				<h1>创建一个免费的新帐户！</h1>
			</div>
			<p></p>
			<form method="post" action="${pageContext.request.contextPath }/user/UserServlet">
				<input type="hidden" name="op" value="register" />
				<ul class="left-form">
					<li>
						<span id="messageUsername">${msg.error.username }</span><br/>
						<input type="text" id="username" name="username" placeholder="用户名" value="${msg.username}" required="required" onblur="checkUsername()"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						<span id="messageNickname">${msg.error.nickname }</span><br/>
						<input type="text" id ="nickname" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"  onblur="checkNickname()"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						<span id="messageEmail">${msg.error.email }</span><br/>
						<input type="text" name="email" placeholder="邮箱" value="${msg.email}" required="required" onblur="checkEmail()"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						<span id="messagePassword">${msg.error.password }</span><br/>
						<input type="password" name="password" placeholder="密码" value="${msg.password}" required="required" onblur="checkPassword()"/>
						<a href="#" class="icon into"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						<span id="messageBirthday">${msg.error.birthday }</span><br/>
						<input type="text" placeholder="出生日期 2018-3-16" name="birthday" value="${msg.birthday}" size="15" onblur="checkBirthday()"/>
						<div class="clear"> </div>
					</li>
					<li style="height: 10px;"></li>
					<li>
						<input type="submit" value="创建账户">
						<div class="clear"> </div>
					</li>
			</ul>
				<p class="submit">
					<a href="${pageContext.request.contextPath }/user/login.jsp">登录</a>
					<a href="${pageContext.request.contextPath }/index.jsp">返回首页</a>
				</p>

			<div class="clear"> </div>

			</form>

		</div>
		<!-----start-copyright---->

		<!-----//end-copyright---->

	</body>

</html>