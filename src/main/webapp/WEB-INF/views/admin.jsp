<%--
  Created by IntelliJ IDEA.
  User: iMliu
  Date: 2019/9/11
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>系统管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/2.3.1/css_default/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/layui/layui.all.js" type="text/javascript"></script>

    <style type="text/css">
        body {
            background-color: #e7e7e7;
        }

        input:-webkit-autofill {
            -webkit-box-shadow: inset 0 0 0 1000px #fff;
            background-color: transparent;

        }

        .admin-login-background {
            width: 300px;
            height: 300px;
            position: absolute;
            left: 50%;
            top: 50%;
            margin-left: -150px;
            margin-top: -100px;
        }

        .admin-header {
            margin-top: -100px;
            margin-bottom: 20px;
        }
        .admin-logo {
            width: 280px;
        }

        .admin-button {
            margin-top: 20px;
        }

        .admin-input {

            border-top-style: none;
            border-right-style: solid;
            border-bottom-style: solid;
            border-left-style: solid;
            height: 150px;
            width: 200px;
            padding-bottom: 0px;
        }

        .admin-button {
            width: 300px;
            height: 50px;
            border-radius: 4px;
            background-color: #2d8cf0;
        }

        .admin-icon {
            margin-left: 260px;
            margin-top: 2px;
            font-size: 30px;
        }

        i {
            position: absolute;
        }

    </style>

</head>
<body>
<div id="container">
    <div></div>
    <div class="admin-login-background">
            <h1 class="form-signin-heading" style="text-align: center">不动产系统管理</h1>
            <div>
                <i class="layui-icon layui-icon-username admin-icon admin-icon-username"></i>
                <input type="text" id="username"  style="padding: 18px 6px" autocomplete="off" placeholder="请输入用户名" class="layui-input">
            </div>
            <div>
                <i class="layui-icon layui-icon-password admin-icon admin-icon-password"></i>
                <input type="password" id="password" placeholder="请输入密码" autocomplete="off" style="padding: 18px 6px" class="layui-input ">
            </div>

            <button class="layui-btn admin-button" onclick="login()" lay-filter="formDemo">登陆</button>


    </div>
</div>
<script>

    //登录
    function login() {
            var username  = $("#username").val();
            var password = $("#password").val();
            $.ajax({
                type: 'post',
                url: "/callnum/GetCallnumber/login",
                data:{username:username,password:password},
                success: function (data) {
                    if (data == '1') {
                        window.location.href = "/callnum/view/logins";
                    }else {
                        layer.msg('请输入正确的用户名和密码',{time:3000});
                    }
                },
                error: function () {
                    layer.msg('登录请求异常！');
                }
            });
    }

</script>
</body>
</html>
