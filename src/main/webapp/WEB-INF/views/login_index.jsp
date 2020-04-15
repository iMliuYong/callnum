<%--
  Created by IntelliJ IDEA.
  User: iMliu
  Date: 2019/9/12
  Time: 10:17
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
    <script src="${pageContext.request.contextPath}/static/layui/layui.js" type="text/javascript"></script>
</head>
<body>
<script>

    window.onload = function(){
        $.ajax({
            type: 'post',
            url: "/callnum/GetCallnumber/getFaceOption",
            data:{},
            success:function (data) {
                if(data==0){
                    $("#faces").removeAttr("checked");
                    var x = document.getElementsByClassName("layui-unselect layui-form-switch layui-form-onswitch");
                    x[0].setAttribute("class", "layui-unselect layui-form-switch");
                    var d = document.getElementsByTagName('em')[0];
                    d.firstChild.nodeValue = '关闭';
                }
            },
            error:function () {
                layer.msg('数据异常！');
            }
        });
    }

    //退出
    function quit(){
        window.location.href="/callnum/view/admin";
    }

    layui.use(['form'], function(){
        var form = layui.form
            ,layer = layui.layer;

        //监听指定开关
        form.on('switch(switchTest)', function(data){
            var face_op = this.checked?'1':'0';
                $.ajax({
                    type: 'post',
                    url: "/callnum/GetCallnumber/FaceOption",
                    data:{face_op:face_op},
                    success:function (data) {

                    },
                    error:function () {
                        layer.msg('数据异常！');
                    }
                });
            layer.msg('人脸组件：'+ (this.checked ? '开' : '关'), {
                offset: '6px'
            });
            layer.tips('温馨提示：该开关为人脸组件是否启用', data.othis)
        });
    });

    layui.use('element', function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function(elem){
            //console.log(elem)
            layer.msg(elem.text());
        });
    });

</script>
<ul class="layui-nav">
    <li class="layui-nav-item">
        <a href="">系统参数配置</a>
    </li>
    <li class="layui-nav-item" lay-unselect="" style="float: right">
        <a href="javascript:;"><img src="//t.cn/RCzsdCq" class="layui-nav-img">管理员</a>
        <dl class="layui-nav-child">
            <dd><a href="javascript:;">修改信息</a></dd>
            <dd><a href="javascript:;" onclick="quit()">退出</a></dd>
        </dl>
    </li>
</ul>
<form class="layui-form" action="">
    <div class="layui-form-item" pane="">
        <label class="layui-form-label">人脸组件</label>
        <div class="layui-input-block">
            <input type="checkbox" checked="" id = "faces" name="switch" lay-skin="switch" lay-text="启用|关闭" lay-filter="switchTest" title="开关">
        </div>
    </div>
</form>
</body>
</html>
