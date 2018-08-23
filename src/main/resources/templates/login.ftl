<html>
<head>
    <title>系统登录</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/adminlte/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/adminlte/plugins/font-awesome-4.4.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="/adminlte/plugins/ionicons-2.0.1/css/ionicons.min.css">
    <!-- daterange picker -->
    <link rel="stylesheet" href="/adminlte/plugins/daterangepicker/daterangepicker.css">
    <!-- bootstrap datepicker -->
    <link rel="stylesheet" href="/adminlte/plugins/datepicker/datepicker3.css">
    <!-- iCheck for checkboxes and radio inputs -->
    <link rel="stylesheet" href="/adminlte/plugins/iCheck/all.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="/adminlte/plugins/datatables/dataTables.bootstrap.css">
    <!-- Bootstrap Color Picker -->
    <link rel="stylesheet" href="/adminlte/plugins/colorpicker/bootstrap-colorpicker.min.css">
    <!-- Bootstrap time Picker -->
    <link rel="stylesheet" href="/adminlte/plugins/timepicker/bootstrap-timepicker.min.css">
    <!-- Select2 -->
    <link rel="stylesheet" href="/adminlte/plugins/select2/select2.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/adminlte/dist/css/AdminLTE.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
    folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/adminlte/dist/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="/adminlte/plugins/pace/pace.css">
    <link rel="stylesheet" type="text/css" href="/adminlte/plugins/easyui/themes/metro/easyui.css">
    <link rel="stylesheet" type="text/css" href="/adminlte/plugins/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/adminlte/plugins/easyui/demo.css">
    <link rel="stylesheet" type="text/css" href="/adminlte/plugins/easyui/themes/metro/tree.css">
    <link rel="stylesheet" type="text/css" href="/custom/css/myapp.css?<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="/adminlte/plugins/morris/morris.css">
    <link rel="stylesheet" type="text/css" href="/adminlte/plugins/toastr/toastr.css">

    <link rel="stylesheet" type="text/css" href="/adminlte/plugins/bootstrap-table/bootstrap-table.min.css">
    <link rel="stylesheet" type="text/css" href="/adminlte/plugins/baiduwebuploader/webuploader.css">
    <link href="/adminlte/plugins/froala_editor/css/froala_editor.css" rel="stylesheet" type="text/css">
    <link href="/adminlte/plugins/newtoast/css/toast.css" rel="stylesheet" type="text/css">
    <link href="/adminlte/plugins/newtoast/css/animate.css" rel="stylesheet" type="text/css">

    <style>
        .logininput{
            border: none;
            background: #282D2F;
            border-bottom: solid 1px #7a7a7a;
            color: #7a7a7a;
            width: 100%;
            height: 40px;
            padding-left: 2px;
        }
    </style>
</head>
<body class="hold-transition" style="background-color: #282D2F;">
<div class="login-box">
    <div class="login-logo">
        <b style="color:#B6B6B6;font-size:28px;">ITMS LOGIN</b>            <br/>
    </div>
    <div class="login-box-body" style="background: #282D2F;">
        <div class="form-group has-feedback">
            <input type="text" class="logininput" placeholder="请输入账号" name="username" value="root">
        </div>
        <div class="form-group has-feedback">
            <input type="password" class="logininput" placeholder="请输入密码" name="password" value="123123">
        </div>
        <div class="form-group has-feedback">
            <br/>
            <button type="submit" class="btn btn-primary btn-block btn-flat" id="login_btn" style="border: solid 1px #7a7a7a;background-color: #282D2F;">登录</button>
        </div>
    </div>
    <!-- /.login-box-body -->
</div>
<!-- jQuery 2.2.3 -->
<script src="/adminlte/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/adminlte/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="/adminlte/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="/adminlte/plugins/newtoast/js/toast.js"></script>
<script>
    // 如果在框架中，则跳转刷新上级页面
    if(self.frameElement && self.frameElement.tagName=="IFRAME"){
        parent.location.reload();
    }
    $(function () {
        $("[name=password]").keydown(function () {
            if(event.keyCode == "13"){
                var username = $("[name=username]").val();
                var passwd = $("[name=password]").val();
                if($.trim(passwd) == ""){
                    toastrwarning('请输入密码');
                }

                $.post("/admin/login",{loginName:username,password:passwd},function(result){
                    if(result.code!="0000"){
                        toastrerror(result.msg);
                        $("#login_btn").text("登录").removeAttr("disabled");
                        return false;
                    } else {
                        location.href = "/sys/index";
                        return true;
                    }
                },"json");
            }
        });

        $("#login_btn").click(function(){
            $(this).text("正在登录").attr({"disabled":"disabled"});
            var username = $("[name=username]").val();
            var password = $("[name=password]").val();
            if ($.trim(username)==""){
                toastrwarning('请输入用户名');
                $("#login_btn").text("登录").removeAttr("disabled");
                return;
            } else if ($.trim(password)==""){
                toastrwarning('请输入密码');
                $("#login_btn").text("登录").removeAttr("disabled");
                return;
            }
            $.post("/admin/login",{loginName:username,password:password},function(result){
                if(result.code!="0000"){
                    toastrerror(result.msg);
                    $("#login_btn").text("登录").removeAttr("disabled");
                    return false;
                } else {
                    location.href = "/sys/index";
                    return true;
                }
            },"json");
        });
    });
</script>
</body>
</html>
