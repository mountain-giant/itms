<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ITMS</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#include "base/base_css.ftl"/>
</head>
<body class="skin-blue sidebar-mini sidebar-collapse">
<div class="wrapper">
    <input type="hidden" id="sessionUserId" value="${user.id}">
    <!--提示是否执行窗口开始-->
    <div class="modal fade" id="prompt_modal" tabindex="1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width: 300px">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="prompt_title"></h4>
                </div>
                <div class="modal-body" id="prompt_body">
                </div>
                <div class="modal-footer">
                    <button type="button" onclick="no()" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="yes()" class="btn btn-primary">确定</button>
                </div>
            </div>
        </div>
    </div>
    <!--提示是否执行窗口结束-->
    <header class="main-header" style="position: fixed;top:0;width: 100%;">
        <a href="#" url="/project/projectSurvey" class="logo menu_link">
            <span class="logo-mini" style="font-size:30px;margin-top:10px;"><b><i class="fa fa-bug"></i></b></span>
            <span class="logo-lg"><b><i class="fa fa-bug"></i> ITMS</b></span>
        </a>
        <nav class="navbar navbar-static-top">
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button" style="height:50px;line-height:20px;"></a>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="${user.headImage}" class="user-image" alt="User Image">
                            <span class="hidden-xs">${user.name}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- UserDO image -->
                            <li class="user-header">
                                <img src="${user.headImage}" class="img-circle" alt="User Image" onclick="openUserInfo();">
                                <p>
                                </p>
                            </li>
                            <!-- ResourceDO Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a id="update_password_btn" href="javascript:void(0);" class="btn btn-default btn-flat">修改密码</a>
                                </div>
                                <div class="pull-right">
                                    <a href="/logout" class="btn btn-default btn-flat">退出系统</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <!-- Control Sidebar Toggle Button -->
                    <li>
                        <a href="#" data-toggle="control-sidebar" style="height:50px;line-height:22px;"><i class="fa fa-gears"></i></a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar" style="height: 1000000px;">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
        <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
                ${menus}
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>
    <div class="content-wrapper" id="child_page_panel" style="background: #FFFFFF;margin-top:50px;">
    </div>
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>    
        <strong>
            <!--<%&#45;&#45;<img src="/static/custom/images/MyLogo.png" width="60.4px" height="24.6px">&#45;&#45;%>-->
            Copyright &copy; 2015 - 2016 <a href="#"> China Co., Ltd.. </a>.</strong> All rights
        reserved.
    </footer>
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Tab panes -->
        <div class="tab-content">
            <!-- Home tab content -->
            <div class="tab-pane" id="control-sidebar-home-tab">
            </div>
            <!-- /.tab-pane -->
        </div>
    </aside>
    <!--<%&#45;&#45;右侧边栏结束&#45;&#45;%>-->
    <div class="control-sidebar-bg"></div>
</div>
<!--<%&#45;&#45;main div end&#45;&#45;%>-->
<!-- 修改密码窗口 -->
<div class="modal fade" id="update_password_model" tabindex="1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header" style="background: #FAFAFA;">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <span class="modal-title" id="myModalLabel">修改密码</span>
            </div>
            <div class="modal-body">
                <form role="form" id="update_password_form">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="hidden" name="loginName" value="${user.loginName}">
                    <div class="form-group">
                        <label for="password_old">旧密码</label>
                        <input type="password" class="form-control" id="password_old" name="password" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="password_new">新密码</label>
                        <input type="password" class="form-control" id="password_new" name="newPassword" placeholder="">
                    </div>
                </form>
            </div>
            <div class="modal-footer" style="background: #FAFAFA;">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="savefunctional" class="btn bg-light-blue">保存</button>
            </div>
        </div>
    </div>
</div>
<!-- 修改密码窗口结束 -->
<div class="modal fade" id="userinfo_modal" tabindex="1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header" style="background: #FAFAFA;">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <span class="modal-title">详细资料</span>
            </div>
            <div class="modal-body">
                <form role="form" id="userinfo_form">
                    <div style='text-align:center;'>
                        <a href="javascript:;" class="file">
                            <img src="${user.headImage}" class='img-circle' style='width: 50px;height: 50px;'>
                        <input type="file" name="imageFile" style="width: 60px;height: 60px;">
                        </a>
                        <br>
                        <span style="font-size: 11px;color: #5e5e5e;">为了正常显示图片，请尽量上传正方形照片</span>
                        <hr style="height: 1px;width: 250px; color:#e0e0e0;margin-top: 0px;">
                    </div>
                    <input type="hidden" name="id" id="userId" value="${user.id}">
                    <div class="form-group">
                        <label for="user_loginName">登录账号</label>
                        <input type="text" class="form-control" id="user_loginName" name="loginName" placeholder="用于登录的账号" readonly="true" value="${user.loginName}">
                    </div>
                    <div class="form-group">
                        <label for="userinfo_name">用户名</label>
                        <input type="text" class="form-control" value="${user.name}" id="userinfo_name" name="name"  placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="userinfo_phonenumber">联系电话</label>
                        <input type="text" class="form-control" value="${user.phone}" id="userinfo_phonenumber" name="phone" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="userinfo_email">邮箱</label>
                        <input type="text" class="form-control" value="${user.email}" id="userinfo_email" name="email" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="userinfo_address">地址</label>
                        <input type="text" class="form-control" value="${user.address}" id="userinfo_address" name="address" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="userinfo_remarks">备注</label>
                        <input type="text" class="form-control" value="${user.remarks}" id="userinfo_remarks" name="remarks" placeholder="">
                    </div>
                </form>
            </div>
            <div class="modal-footer" style="background: #FAFAFA;">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="saveuserinfo" class="btn bg-light-blue">保存</button>
            </div>
        </div>
    </div>
</div>
<!-- 图片展示窗口 -->
<div class="modal fade" id="showImageBaseWindow" tabindex="1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header" style="background: #FAFAFA;">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <span class="modal-title">图片</span>
            </div>
            <div class="modal-body" style="padding: 0px;">
                <img src="" width="1000" id="showImageInfo">  
            </div>
        </div>
    </div>
</div>
<!-- 图片展示窗口结束 -->
<#include "base/base_js.ftl"/>
<!--百度文件上传插件-->
<script type="text/javascript" src="/adminlte/plugins/baiduwebuploader/webuploader.min.js"></script>
<script>
    // 如果在框架中，则跳转刷新上级页面
    /**
     * 打开用户详细资料窗口
     */
    function openUserInfo() {
        $("#userinfo_modal").modal('show');
    }
    $(function(){
        $("#child_page_panel").css("min-height",(document.body.clientHeight-48)+"px");
        // 将项目的编号和名称存放在localStorage中
        // TODO 这里写死了，到时候要改回来
        localStorage.setItem("stotage_project_id", 9);
        localStorage.setItem("stotage_project_name", 'TOP');
        if (localStorage.getItem("stotage_project_id") == null){
            location.href = "/sys/index";
            return;
        }
        
        // 如果地址栏的路由为主页面，则在子页面中加载默认子页面
        if (event.path[0].location.pathname == "/sys/index" || event.path[0].location.pathname == "/") {
            history.replaceState({}, '',event.path[0].location.pathname);
            // /project/projectSurvey 为默认子页面
            $.post("/project/projectSurvey","",function (page) {
                $("#child_page_panel").append(page);
            });
        } else {
            history.replaceState({}, '', event.path[0].location.href);
            $.post(event.path[0].location.href, "", function (page) {
                $("#child_page_panel").append(page);
            });
        }
        //url改变触发onpopstate事件
        window.onpopstate = function (event) {
            var path = event.path[0].location.href;
            if (event.path[0].location.pathname == "/sys/index" || event.path[0].location.pathname == "/") {
                path = "/project/projectSurvey";
            } 
            $("#child_page_panel").children().remove();
            $("#child_page_panel").append('<br><div class="col-xs-12 text-center"><i class="fa fa-spin fa-refresh"></i>&nbsp; 页面加载中...</div>');
            $.post(path,"",function (page) {
                $("#child_page_panel").children().remove();
                $("#child_page_panel").append(page);
                console.log(2)
            });
        };
        
        // 链接点击加载子页面     
        $(document).off("click",".menu_link");
        $(document).on("click",".menu_link",function(){
            loadChildView($(this).attr('url'));
        });
        
        $("#saveuserinfo").click(function () {
            $(this).text("保存中").attr({"disabled":"disabled"});
            toastrinfo('正在提交数据...');
            var formData = new FormData($("#userinfo_form")[0]);
            $.ajax({
                url: "/user/updateMe",
                type: "POST",
                data: formData,
                dataType : "json",
                contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理  
                processData: false, //必须false才会自动加上正确的Content-Type
                success: function (data) {
                    if (data.code == "0000"){
                        $("#userinfo_modal").modal('hide');
                        toastrsuccess('用户信息修改成功...');
                        myConfirm("提示","是否刷新页面",function(){
                            // 保存权限刷新页面
                            parent.location.reload();
                        });
                    } else if(data.code=="9999") {
                        toastrerror('用户信息修改失败...');
                    } else {
                        toastrerror("请重新登录");
                    }
                    $("#saveuserinfo").text("保存").removeAttr("disabled");
                }
            },"json");
        });
        
        /**
         * 打开修改密码窗口
         */
        $("#update_password_btn").click(function(){
            $("#update_password_model").modal('show');
        });
        /**
         * 修改密码
         */
        $("#savefunctional").click(function () {
            var oldPwd = $("#password_old").val();
            var newPwd = $("#password_new").val();
            if (oldPwd == null || oldPwd.trim() == "") {
                toastrwarning('请输入旧密码');
                return;
            }
            if (newPwd == null || newPwd.trim() == "") {
                toastrwarning('请输入新密码');
                return;
            }
            var data = $("#update_password_form").serialize();
            $.post("/user/updatePwd",data, function (data) {
                if (data.code=="0000"){
                    toastrsuccess('密码修改成功...');
                }else {
                    toastrerror(data.msg);
                }
            },"json");
        });
        $(".switchProject").click(function () {
            var id = $(this).attr("value");
            var name = $(this).text();
            location.href = "/project/work?flag=survey&p="+name+"&pid="+id;
        });
    });
</script>
</body>
</html>
