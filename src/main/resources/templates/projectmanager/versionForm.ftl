<div id="versionFormView">
    <section class="content-header my-content-header" style="background: #F4F4F4;">
        <h1>
            <span class="my-view-title2" id="windowTitle"> 版本管理 <i class="glyphicon glyphicon-arrow-right"></i> <label id="viewTitle">${title}</label></span>
            <span class="pull-right">  
                <a class="btn btn-default" style="margin-top: -6px" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
                <a class="btn btn-success save" style="margin-top: -6px" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </h1>
    </section>

    <section class="content" id="detail">
        <form class="form-horizontal" id="versionForm">
            <blockquote><small>基本信息</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <input type="hidden" name="projectId" value="${projectId!}"/>
                        <input type="hidden" name="id" value="${projectVersion.id!}">
                        <label class="col-sm-2 control-label">版本名称</label>
                        <div class="col-sm-6">
                            <input type="text" name="versionName" value="${projectVersion.versionName!}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">上线日期</label>
                        <div class="col-sm-6">
                            <input type="text" name="publishDate" value="${(projectVersion.publishDate?string("yyyy-MM-dd"))!}" class="form-control pull-right" id="datepicker">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">版本描述</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" style="resize:none;" name="versionDesc" rows="3" placeholder="最多输入500个字符...">${projectVersion.versionDesc!}</textarea>
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

        $('#datepicker').datepicker({
            autoclose: true,
            format:"yyyy-mm-dd"
        });
        $("#versionFormView .save").click(function () {
            $("#versionForm").submit();
        });

        $("#versionForm").validate({
            errorClass:'myerror',
            errorElement:'div',
            rules:{
                versionName:{
                    required:true
                },
                publishDate:{
                    required:true
                },
                versionDesc:{
                    required:true
                }
            },
            messages:{
                versionName:'请输入模块名称',
                publishDate:'请输入上线日期',
                versionDesc:'请输入模块描述'
            },
            submitHandler:function(){
                var data = $("#versionForm").serialize();
                var title = $("#viewTitle").text();
                $("#versionFormView .save").text("正在保存").attr({"disabled": "disabled"});
                if (title == "添加版本") {
                    $.post("/projectVersion/create", data, function (data) {
                        if (data.code == "0000") {
                            toastrsuccess('添加版本成功...');
                            history.back();
                        } else {
                            toastrerror(data.msg);
                        }
                        $("#versionFormView .save").text("保存").removeAttr("disabled");
                    }, "json");
                } else {
                    $.post("/projectVersion/update", data, function (data) {
                        if (data.code == "0000") {
                            toastrsuccess('修改版本成功...');
                            history.back();
                        } else {
                            toastrerror(data.msg);
                        }
                        $("#versionFormView .save").text("保存").removeAttr("disabled");
                    }, "json");
                }
            }
        });
    })
</script>
