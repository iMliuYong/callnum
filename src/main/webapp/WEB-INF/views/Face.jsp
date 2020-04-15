<%--
  Created by IntelliJ IDEA.
  User: iMliu
  Date: 2019/8/21
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>呼叫界面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/2.3.1/css_default/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/layui/layui.all.js" type="text/javascript"></script>
</head>
<fieldset class="layui-elem-field site-demo-button" style="margin-top: 30px;">
    <legend>呼叫器</legend>
    <div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 100px">请输入窗口编号</label>
            <div class="layui-input-inline">
                <input type="text" id="winnum" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
            </div>
        </div>
        <button type="button" id="callout" class="layui-btn layui-btn-normal">叫号</button>
        <button type="button" id ="recall" class="layui-btn">重呼</button>
        <button type="button" id="skipnum" class="layui-btn layui-btn-danger">过号</button>
        <button type="button" class="layui-btn layui-btn-primary">暂停业务</button>
        <button type="button" class="layui-btn layui-btn-warm">恢复叫号</button>
        <button type="button" class="layui-btn layui-btn-disabled">开始受理</button>
    </div>
</fieldset>
<script type="text/javascript">

        //叫号
        $("#callout").click(function () {
            var winnum = $("#winnum").val().trim();
            if(winnum=='' ||winnum==undefined||isNaN(winnum)){
                layer.msg('请输入窗正确窗口号',{time:3000});
                return;
            }
            $.ajax({
                type: 'post',
                url: "/callnum/GetCallnumber/callout",
                data:{winNum:winnum},
                success:function (data) {
                    console.log(data)
                        layer.msg(data[0].respMsg);
                },
                error:function () {
                    layer.msg('呼叫请求异常！');
                }
            });
        })

        //过号
        $("#skipnum").click(function () {
            var winnum = $("#winnum").val().trim();
            if(winnum=='' ||winnum==undefined||isNaN(winnum)){
                layer.msg('请输入窗正确窗口号',{time:3000});
                return;
            }
            $.ajax({
                type: 'post',
                url: "/callnum/GetCallnumber/callout",
                data:{winNum:winnum},
                success:function (data) {
                    console.log(data)
                    layer.msg(data[0].respMsg);
                },
                error:function () {
                    layer.msg('过号请求异常！');
                }
            });
        })


        //重呼
        $("#recall").click(function () {
            var winnum = $("#winnum").val().trim();
            if(winnum=='' ||winnum==undefined||isNaN(winnum)){
                layer.msg('请输入窗正确窗口号',{time:3000});
                return;
            }
            $.ajax({
                type: 'post',
                url: "/callnum/GetCallnumber/recall",
                data:{winNum:winnum},
                success:function (data) {
                    console.log(data)
                    layer.msg(data[0].respMsg);
                },
                error:function () {
                    layer.msg('呼叫请求异常！');
                }
            });
        })

</script>
<body>
</body>
</html>
