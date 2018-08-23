<div id="projectFormView">
    <section class="content-header my-content-header" style="background: #F4F4F4;">
        <h1>
            <span class="my-view-title2" id="windowTitle"> 项目管理 <i class="glyphicon glyphicon-arrow-right"></i> <label id="viewTitle">${title}</label></span>
            <span class="pull-right">  
                <a class="btn btn-default" style="margin-top: -6px" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
                <a class="btn btn-success save" style="margin-top: -6px" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </h1>
    </section>

    <section class="content" id="detail">
        <form class="form-horizontal" id="projectForm">
            <blockquote><small>基本信息</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <input type="hidden" name="id" value="${project.id!}">
                        <label class="col-sm-2 control-label">项目名称</label>
                        <div class="col-sm-6">
                            <input type="text" name="projectName" value="${project.projectName!}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">项目简称</label>
                        <div class="col-sm-6">
                            <input type="text" name="projectShortName" value="${project.projectShortName!}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">产品</label>
                        <div class="col-sm-8" style="height:30px;line-height:30px;">
                            <#list (products)! as product>
                                <label style="margin-right: 20px;">
                                     <#if (product.isHave)!>
                                        <input type="checkbox" name="productId" class="minimal" checked value="${product.id}">
                                     <#else>
                                        <input type="checkbox" name="productId" class="minimal" value="${product.id}">
                                     </#if>
                                    ${product.productName}
                                </label>
                            </#list>
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">项目描述</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" style="resize:none;" name="projectDesc" rows="3" placeholder="最多输入500个字符...">${project.projectDesc!}</textarea>
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
        $("#projectFormView .save").click(function () {
            $("#projectForm").submit();
        });
        
        $("#projectForm").validate({
            errorClass:'myerror',
            errorElement:'span',
            rules:{
                projectName:{
                    required:true
                },
                projectShortName:{
                    required:true
                },
                projectDesc:{
                    required:true
                }
            },
            messages:{
                projectName:'请输入项目名称',
                projectShortName:'请输入项目简称',
                projectDesc:'请输入项目描述'
            },
            submitHandler:function(){
                var data = $("#projectForm").serialize();
                var title = $("#viewTitle").text();
                $("#projectFormView .save").text("正在保存").attr({"disabled": "disabled"});
                if (title == "添加项目") {
                    $.post("/project/create", data, function (data) {
                        if (data.code == "0000") {
                            toastrsuccess('添加项目成功...');
                            history.back();
                        } else {
                            toastrerror(data.msg);
                        }
                        $("#projectFormView .save").text("保存").removeAttr("disabled");
                    }, "json");
                } else {
                    $.post("/project/update", data, function (data) {
                        if (data.code == "0000") {
                            toastrsuccess('修改项目成功...');
                            history.back();
                        } else {
                            toastrerror(data.msg);
                        }
                        $("#projectFormView .save").text("保存").removeAttr("disabled");
                    }, "json");
                }
            }
        });
    })
</script>
