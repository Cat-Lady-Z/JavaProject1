<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page buffer="108kb" autoFlush="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>舌尖上的中国 - Shopping Cart</title>
<meta name="keywords" content="舌尖上的中国e, shopping cart, free template, ecommerce, online shop, website templates, CSS, HTML" />
<meta name="description" content="舌尖上的中国, Shopping Cart, online store template " />
<link href="../css/templatemo_style.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="../css/ddsmoothmenu.css" />

<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/ddsmoothmenu.js">



</script>

<script type="text/javascript">

ddsmoothmenu.init({
	mainmenuid: "top_nav", //menu DIV id
	orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'ddsmoothmenu', //class added to menu's outer DIV
	//customtheme: ["#1c5a80", "#18374a"],
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
})

</script>
	<script type="text/javascript">
        function hint() {

            var nameElement = document.getElementById("keyword");
            var div = document.getElementById("hintContent");

            //获取输入的信息
            var keyword = nameElement.value;
            //如果文本框不没有数据时，把div隐藏，且不向服务器发送请求
            if (keyword === "") {
                div.style.display = "none";
                return;
            }

            //隐藏提示框
            div.innerHTML = "";

            //1.获取XMLHttpRequest对象
            var xmlhttp = new XMLHttpRequest();

            //2.绑定回调函数
            xmlhttp.onreadystatechange = function () {

                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

                    // 如果没有响应内容，隐藏 div
                    if (xmlhttp.responseText === "") {
                        div.style.display = "none";
                        return;
                    }

                    var response = xmlhttp.responseText.split(",");

                    var childdiv = "";

                    for (var i = 0; i < response.length; i++) {
                        childdiv = childdiv +
                            "<div  onmouseover='changeBackground_over(this)' " +
                            "onmouseout='changeBackground_out(this)' " +
                            "onclick='fillNameValue(this)'>" + response[i] + "</div>";
                    }

                    div.innerHTML = childdiv;
                    div.style.display = "block";
                }
            };
            //3.open
            xmlhttp.open("GET", "${pageContext.request.contextPath}/admin/AjaxServlet?ajax=findProductsHintByName&key=" + keyword);
            //4.send
            xmlhttp.send(null);
        }

        function changeBackground_over(div) {
            div.style.background = "white";
        }

        function changeBackground_out(div) {
            div.style.background = "#111";
        }

        function fillNameValue(subDiv) {
            document.getElementById("keyword").value = subDiv.innerText;
            document.getElementById("hintContent").style.display = "none";
        }

        function dispear(div) {
            div.style.display = "none";
        }

	</script>
</head>

<body>
<c:if test="${empty user }">
		<jsp:forward page="/user/login.jsp"/>
</c:if>
<div id="templatemo_body_wrapper">
<div id="templatemo_wrapper">

	<div id="templatemo_header">
    	<div id="site_title"><h1><a href="${pageContext.request.contextPath }/index.jsp">舌尖上的中国</a></h1></div>
        <div id="header_right">
        	<p>
	        <c:if test="${!empty user }">
	        	<a href="${pageContext.request.contextPath }/user/personal.jsp">我的个人中心</a> |
	        </c:if>
	        <a href="${pageContext.request.contextPath }/user/CartServlet?op=findCart">购物车</a> |
	        <c:if test="${user == null }">
	        	<a href="${pageContext.request.contextPath }/user/login.jsp">登录</a> |
	        	<a href="${pageContext.request.contextPath }/user/register.jsp">注册</a>
	        </c:if>
	        <c:if test="${!empty sessionScope.user }">
	        	${user.nickname }
	        	<a href="${pageContext.request.contextPath }/user/UserServlet?op=logout">退出</a></p>
	        </c:if>
	        </p>
	        <p>
		        <c:if test="${!empty user }">
		        	<a href="${pageContext.request.contextPath }/user/OrderServlet?op=myoid">我的订单</a> |
		        </c:if>
	        </p>
		</div>
        <div class="cleaner"></div>
    </div> <!-- END of templatemo_header -->
    
    <div id="templatemo_menubar" style="position: relative;">
    	<div id="top_nav" class="ddsmoothmenu">
            <ul>
                <li><a href="${pageContext.request.contextPath }/MainServlet" class="selected">主页</a></li>
            </ul>
            <br style="clear: left" />
        </div> <!-- end of ddsmoothmenu -->
        <div id="templatemo_search" onmouseleave="dispear(document.getElementById('hintContent'))">
            <form action="${pageContext.request.contextPath }/MultipartUserProductServlet" method="get">
              <input type="hidden" name="op" value="findProductsByName"/>
              <input type="text" value="${pname }" name="pname" id="keyword" title="keyword"
					 onfocus="clearText(this)" onblur="clearText(this)" onkeypress="hint()" class="txt_field" />
              <input type="submit" name="Search" value=" " alt="Search" id="searchbutton" title="Search" class="sub_btn"  />
            </form>
			<div id="hintContent"
				 style="background-color:#111;
                 display: none;
                 padding: 4px;
                 font-size:14px;
                 width:212px;
                 position: absolute;
                 right:30px;top:44px;
                 z-index: 99;
                 cursor: pointer;"
				 onmouseleave="dispear(this)">
			</div>
        </div>
    </div> <!-- END of templatemo_menubar -->
    
    <div id="templatemo_main">
    	<div id="sidebar" class="float_l">
        	<div class="sidebar_box"><span class="bottom"></span>
            	<h3>分类</h3>
                <div class="content"> 
                	<ul class="sidebar_list">
                    	<c:forEach items="${categories }" var="category" varStatus="vs">
                			<c:if test="${vs.index !=0}">
                				<c:if test="${vs.index != fn:length(categories)-1 }">
                					<li>
                						<a href="${pageContext.request.contextPath }/MultipartUserProductServlet?op=findProductByCid&cid=${category.cid}">${category.cname}</a>
                					</li>
                				</c:if>
                			</c:if>
                			<c:if test="${vs.index==0 }">
                				<li class="first">
                					<a href="${pageContext.request.contextPath }/MultipartUserProductServlet?op=findProductByCid&cid=${category.cid}">${category.cname}</a>
                				</li>
                			</c:if>
                			<c:if test="${vs.index == fn:length(categories)-1 }">
                				<li class="last">
                					<a href="${pageContext.request.contextPath }/MultipartUserProductServlet?op=byfindProductByCidCid&cid=${category.cid}">${category.cname}</a>
                				</li>
                			</c:if>
                		</c:forEach>
                    </ul>
                </div>
            </div>
            
        </div>
        <div id="content" class="float_r">
            
                <h4>我的订单详情</h4>
	<table border="1" width="700" style="border-style: inherit; border-color: black;">
		<tr style="color: grey">
			<td colspan="3"><center>订单详情</center></td>
			<td>收货人</td>
			<td>商品金额</td>
			<td>订单状态</td>
			<td><center>操作</center></td>
		</tr>
		<c:forEach items="${orderitems}" var="item"  varStatus ="status">
			<c:if test="${status.count==1}">
				<tr <%--style="color: grey"--%>>
					<td>订单号：${item.order.oid}</td>
					<td colspan="2">下单时间:${item.order.ordertime}</td>
					<td>菜系：${item.product.category.cname}</td>
				</tr>
			</c:if>
			<tr style="color: grey">
				<td><img src="${pageContext.request.contextPath}/${item.product.imgurl}" width="100" height="150"/></td>
				<td>${item.product.pname}</td>
				<td>${item.buynum}</td>
				<td>${item.order.recipients}</td>
				<td id="price">${item.buynum * item.product.estoreprice}</td>
				<td>
					<c:if test="${item.order.state == 0}">订单已取消</c:if>
					<c:if test="${item.order.state == 1}">已下单</c:if>
					<c:if test="${item.order.state == 2}">已支付</c:if>
					<c:if test="${item.order.state == 3}">已发货</c:if>
				</td>
				<td >
					<c:if test="${item.order.state==1}"><a href="${pageContext.request.contextPath}/user/OrderServlet?op=cancelOrder&oid=${item.order.oid}&state=0">取消订单</a></c:if>
					|<a href="${pageContext.request.contextPath}/user/OrderServlet?op=placeOrderAgain">再次购买
						<input type="hidden" name="orderitems" value="${orderitems}"/>
					</a>

						<%--
                                            <c:if test="${order.state==1}"><a href="${pageContext.request.contextPath}/user/OrderServlet?op=cancelOrder&oid=${order.oid}&state=0">支付订单</a>
                        --%>


				</td>
			</tr>			
		</c:forEach>
	</table>
                
         
                     
        <p><a href="${pageContext.request.contextPath}/index.jsp">继续购物</a>|
			<a href="${pageContext.request.contextPath}/user/myOrders.jsp">返回</a></p>
                     	
                  
		</div>
        <div class="cleaner"></div>
    </div> 
    
    <div id="templatemo_footer">
		    Copyright (c) 2018 <a href="#">舌尖上的中国</a> | <a href="#">Web工作室</a>
    </div> 
    
</div> 
</div>

</body>
</html>