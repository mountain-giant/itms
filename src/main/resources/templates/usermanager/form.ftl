<div id="userFormView">
    <section class="content-header my-content-header" style="background: #F4F4F4;">
        <h1>
            <span class="my-view-title2" id="windowTitle"> 用户管理 <i class="glyphicon glyphicon-arrow-right"></i> <label id="viewTitle">${title}</label></span>
            <span class="pull-right">  
                <a class="btn btn-default" style="margin-top: -6px" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
                <a class="btn btn-success save" style="margin-top: -6px" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </h1>
    </section>
    <section class="content">
        <form class="form-horizontal" id="userForm">
            <input type="hidden" name="id" value="${userInfo.id!}"/>
            <blockquote><small>基本信息</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-6">
                            <input type="text" name="loginName" ${(title=='编辑用户')?string('readonly','')} class="form-control" placeholder="" value="${userInfo.loginName!}">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" ${(title=='编辑用户')?string('readonly','')}  name="password" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">真实姓名</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" ${(title=='编辑用户')?string('readonly','')}  name="name" id="name" placeholder=""  value="${userInfo.name!}">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-6">
                            <input type="email" class="form-control" ${(title=='编辑用户')?string('readonly','')}  name="email" id="email" placeholder="" value="${userInfo.email!}">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">联系电话</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" ${(title=='编辑用户')?string('readonly','')}  name="phone" id="phone" placeholder="" value="${userInfo.phone!}">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label for="remarks" class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" style="resize:none;" ${(title=='编辑用户')?string('readonly','')}  value="${userInfo.remarks!}" id="remarks" name="remarks" rows="3" placeholder="最多输入500个字符..."></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <blockquote><small>系统角色</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label for="remarks" class="col-sm-1 control-label"></label>
                        <div class="col-sm-9">
                            <#list (roles)! as role>
                            <label style="margin-right: 20px;">
                                <#if (role.have)!>
                                 <input type="checkbox" name="productIds" class="minimal" checked value="${role.roleId}">
                                <#else>
                                  <input type="checkbox" name="productIds" class="minimal" value="${role.roleId}">
                                </#if>
                                ${role.roleName}
                            </label>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
            <blockquote><small>关联产品</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-1 control-label"></label>
                        <div class="col-sm-9">
                        <#list (products)! as product>
                            <label style="margin-right: 20px;">
                            <#if (product.isHave)!>
                                 <input type="checkbox" name="productIds" class="minimal" checked value="${product.id}">
                            <#else>
                                  <input type="checkbox" name="productIds" class="minimal" value="${product.id}">
                            </#if>
                            ${product.productName}
                            </label>
                        </#list>
                        </div>
                    </div>
                </div>
            </div>
            <blockquote><small>流程角色</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label for="remarks" class="col-sm-1 control-label"></label>
                        <div class="col-sm-9">
                            <#list (processRoles)! as process>
                            <label style="margin-right: 20px;">
                                <#if (process.have)!>
                                 <input type="checkbox" name="processRoles" class="minimal" checked value="${process.configKey}">
                                <#else>
                                  <input type="checkbox" name="processRoles" class="minimal" value="${process.configKey}">
                                </#if>
                                ${process.configValue}
                            </label>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="box-footer">
            <span class="pull-right">  
                <a class="btn btn-default" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
                <a class="btn btn-success save" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </div>
        <br/>
    </section>
</div>      
<script type="text/javascript" src="/custom/js/base.js"></script>
<script>
    $(function () {
        $("#userForm").validate({
            errorClass:'myerror',
            errorElement:'div',
            rules:{
                loginName:{
                    required:true
                },
                name:{
                    required:true
                },
                email:{
                    required:true,
                    email:true
                }
            },
            messages:{
                loginName:'请输入账号',
                name:'请输入用户姓名',
                email:{
                    required:'请输入邮箱',
                    email:'请输入一个正确的邮箱'
                }
            },
            submitHandler:function(){
                var data = $("#userForm").serialize();
                $("#userFormView .save").text("正在保存").attr({"disabled":"disabled"});
                $.post("/user/adduser",data, function (data) {
                    if (data.code=="0000"){
                        toastrsuccess('新增用户成功...');
                        history.back();
                    }else{
                        toastrerror(data.msg);
                    }
                    $("#userFormView .save").text("保存").removeAttr("disabled");
                },"json");
            }
        });
        $("#userFormView .save").click(function () {
            var title =  $("#viewTitle").text();                    
            if (title=="添加用户") {
                $("#userForm").submit();
            } else {
                var data = $("#userForm").serialize();
                $("#userFormView .save").text("正在保存").attr({"disabled":"disabled"});
                $.post("/user/update",data,function (data) {
                    if (data.code=="0000"){
                        toastrsuccess('修改用户成功...');
                        history.back();
                    }else{
                        toastrerror(data.msg);
                    }
                    $("#userFormView .save").text("保存").removeAttr("disabled");
                },"json");
            }
        });

    });
</script>
