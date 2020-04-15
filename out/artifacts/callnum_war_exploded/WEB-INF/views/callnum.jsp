<%--
  Created by IntelliJ IDEA.
  User: iMliu
  Date: 2019/8/19
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>叫号系统</title>
    <meta name="decorator" content="blank"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/2.3.1/css_default/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery-1.9.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/layui/layui.all.js" type="text/javascript"></script>

    <style type="text/css">
        html,body,table{background-color:#f5f5f5;width:100%;height:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
        .form-signin{position:relative;text-align:left;width:600px;height:130px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
            -webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
        .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
        .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
        .form-signin .btn.btn-large{font-size:35px;height:90px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
        .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
        .header{height:880px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
        label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
        .jsz_yw{height:100px;width:100px;text-align:center;}
    </style>

    <style type="text/css">
        body{
            background-image: url(${pageContext.request.contextPath}/static/images/cccc.bmp);
            position:fixed;
            width:100%;
            height:100%;
            min-width: 500px;
            z-index:-10;
            zoom: 1;
            background-color: #fff;
            background-repeat: no-repeat;
            background-size: 100% 100%;
        }
        #canvas{
            background-image: url(${pageContext.request.contextPath}/static/images/asd.jpg);
            background-size:cover;
            height: 100%;
        }

    </style>

   <%-- <script language="javascript">
        var scrollFunc=function(e){
            e=e || window.event;
            if(e.wheelDelta && event.ctrlKey){//IE/Opera/Chrome
                event.returnValue=false;
            }else if(e.detail){//Firefox
                event.returnValue=false;
            }
        }
        /*注册事件*/
        if(document.addEventListener){
            document.addEventListener('DOMMouseScroll',scrollFunc,false);
        }//W3C
        window.onmousewheel=document.onmousewheel=scrollFunc;//IE/Opera/Chrome/Safari
    </script>--%>
    <script>
        var zoom=function(e){
            var e = e || window.event;
            if(e.wheelDelta && event.ctrlKey){
                event.returnValue = false
            }else if(e.detail){
                event.returnValue = false;
            }
        }
        if(document.addEventListener){
            document.addEventListener('DOMMouseScroll' , zoom , false);
        }
        window.onmousewheel = document.onmousewheel = zoom;
    </script>
    <script type="text/javascript">

        //取号人进行取号（获取身份证信息）
        function person() {
           $('#myModal').modal('show');
            layer.msg('<span style="font-size: 40px;">请正确放置身份证☝</span>',{
                area: ['35%','110px'],
            });
            var checkIdcard = setInterval(function () {
                $.ajax({
                    type: 'post',
                    url: "/callnum/view/idcard",
                    success:function (data) {
                        console.log(data);
                        if(data==''){
                            layer.msg('<span style="font-size: 40px;">请正确放置身份证☝</span>',{
                                area: ['35%','110px'],
                            });
                        }else{
                            layer.msg('读取成功！');
                            clearInterval(checkIdcard);
                            if(data[0].option==1){//
                                dlr_yw(data);//获取到身份证信息
                            }else{
                                getNumber(data);//取号
                            }
                        }
                    },
                    error:function () {
                        layer.msg('<span style="font-size: 40px;">读卡数据请求异常！</span>',{
                            area: ['35%','110px'],
                        });
                    }
                });
            },3000);

        }

        //打卡摄像头采集人脸
        function dlr_yw (photo) {
            function getUserMedia(constraints, success, error) {
                if (navigator.mediaDevices.getUserMedia) {
                    //最新的标准API
                    navigator.mediaDevices.getUserMedia(constraints).then(success).catch(error);
                } else if (navigator.webkitGetUserMedia) {
                    //webkit核心浏览器
                    navigator.webkitGetUserMedia(constraints,success, error)
                } else if (navigator.mozGetUserMedia) {
                    //firfox浏览器
                    navigator.mozGetUserMedia(constraints, success, error);
                } else if (navigator.getUserMedia) {
                    //旧版API
                    navigator.getUserMedia(constraints, success, error);
                }
            }

            let video = document.getElementById('video');
            video.controls=false;
            let canvas = document.getElementById('canvas');
            let context = canvas.getContext('2d');


            function success(stream) {
                //兼容webkit核心浏览器
                let CompatibleURL = window.URL || window.webkitURL;
                //将视频流设置为video元素的源
                video.srcObject = stream;
                video.play();
            }

            function error(error) {
                console.log(`访问用户媒体设备失败${error.name}, ${error.message}`);
            }

            if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
                //调用用户媒体设备, 访问摄像头
                getUserMedia({video : {width: 480, height: 320}}, success, error);
            } else {
                alert('不支持访问用户媒体');
            }
            getGZL(photo);
        }


        //读取到人脸 进行人脸比对
        function getGZL(photo){
            var drawCanvas = setInterval(function () {
                let video_draw = document.getElementById('video');
                video_draw.controls=false;
                let canvas_draw= document.getElementById('canvas');
                let contexts_draw = canvas_draw.getContext('2d');
                contexts_draw.drawImage(video_draw, 0, 0, 480, 320);
            },20);

            let canvass = document.getElementById('canvas');
            let contexts = canvass.getContext('2d');
            var photos_data = photo[0].photodata;
            var checkRes = setInterval(function(){
                var dataURL = canvass.toDataURL("image/png",1.0);
                $.ajax({
                    type: 'post',
                    url: "/callnum/view/face",
                    data:{imageURL:dataURL,photoData:photos_data},
                    dataType:"json",
                    success:function (data) {
                        if(data == '1'){
                            var c=document.getElementById("canvas");
                            var cxt=c.getContext("2d");
                            c.height=c.height;

                            layer.msg('<span style="font-size: 40px;">认证成功！,正在打印排队号</span>'
                                ,{area: ['35%','110px'],
                                time:4000}
                                ,function(){
                                $('#myModal').modal('hide');
                            });
                            clearInterval(checkRes);
                            clearInterval(drawCanvas);
                            getNumber(photo);//取号
                        }else{
                            layer.msg('<span style="font-size: 40px;">---请正视摄像头---</span>',{
                                area: ['35%','110px'],
                            })
                        }
                    },
                    error:function () {
                        console.log("认证失败，数据异常");
                    }
                });
            },2000);

        }

        //取号
        function getNumber(data) {
            var sfzmhm = data[0].sfzmhm;
            var qhrxm = data[0].qhrxm;
            var ywlx = '1';
            $.ajax({
                type: 'post',
                url: "/callnum/GetCallnumber/getNumber",
                data:{sfzmhm:sfzmhm,qhrxm:qhrxm,ywlx:ywlx},
                dataType:"json",
                success: function (data) {
                    if (data == '-3') {
                        layer.msg('当天取号已取完',{time:3000});
                    }else if(data=='-2'){
                        layer.msg('身份证取号次数超出限制',{time:3000});
                    }else if(data=='-1'){
                        layer.msg('打印失败');
                    } else {
                        layer.msg('<span style="font-size: 40px;">打印成功,请取走打印纸</span>',{area: ['35%','110px'],time:3000});
                        setTimeout(function () {
                            window.location.reload();
                        },3000)

                    }
                },
                error: function () {
                    layer.msg('取号数据请求异常！');
                }
            });
        }

        function diminish() {
            window.location.reload();
        }
        setInterval("getWaitNum()",4000);//界面轮询
        function getWaitNum(){
            $.ajax({
                url : "/callnum/GetCallnumber/GetWaitNum",
                type : 'post',
                success : function(data) {
                    if(data>=0){
                        $('#jsz_wait').text('等待人数:'+data);
                    }
                }
            });
        }

    </script>
</head>
<body>
<%--<div style="width: 100%;height: 380px;background-image: url(${pageContext.request.contextPath}/static/images/tops.png);background-size:100% 100%;" ></div>--%>
<div id="myModal" class="modal hide fade"  tabindex="1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-header">
        <h3 id="myModalLabel">人脸识别</h3>
    </div>
    <div  id = "id_card" class="modal-body" style="height:350px;text-align:center;">
        <h4 id="wait"></h4>
        <video id="video" style="display: none" width="480" height="320" controls>
        </video>
        <div>
        </div>
        <canvas id="canvas"  width="480" height="320"></canvas>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" onclick="diminish()">取消</button>
    </div>
</div>
<div id="myModal_dlr" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

    </div>
    <div class="modal-body" style="height:150px;text-align:center;padding: 40px">
        <div class="input-append">
            <div class="modal-header">
                <h3 id="wait_dlr">身份证号码/统一社会信用代码</h3>
            </div>
            <input class="span2" style="width:320px;font-size:26px;height:50px;" id="dlr_value" maxlength="18" type="text" placeholder="请输入">
            <button class="btn btn-primary" onclick="dlr_qh()" style="height:60px;font-size:26px;" type="button">确定</button>
            <div class="input-append">
            </div>
            <div class="form-group">
                <div style="margin-top: 10px">
                    <label class="radio-inline" >
                        <input  style="vertical-align: middle" type="radio" name="dlr_type" value="01"  checked="checked">个人业务
                    </label>
                    <label class="radio-inline">
                        <input  style="vertical-align: middle" type="radio" value="02" name="dlr_type">公司业务
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
    </div>
</div>
<div class="header">
    <div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
        <label id="loginError" class="error">${message}</label>
    </div>
</div>
<h1 class="form-signin-heading"></h1>
<div class="form-signin">
    <button type="button" onclick="person()" class="btn btn-large btn-success btn-block">不动产登记</button>
    <span id="jsz_wait" style="text-align: center;display:block;"></span>
    <label></label>
</div>
<div class="footer">

</div>
</body>
</html>
