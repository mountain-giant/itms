<div id="environmentsFormView">
    <section class="content-header my-content-header" style="background: #F4F4F4;">
        <h1>
            <span class="my-view-title2" id="windowTitle"> 环境管理 <i class="glyphicon glyphicon-arrow-right"></i> <label id="viewTitle">${title}</label></span>
            <span class="pull-right">  
                <a class="btn btn-default" style="margin-top: -6px" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
                <a class="btn btn-success save" style="margin-top: -6px" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </h1>
    </section>

    <section class="content" id="detail">
        <form class="form-horizontal" id="environmentsForm">
            <blockquote><small>基本信息</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <input type="hidden" name="id" value="${environments.id!}">
                        <label class="col-sm-2 control-label">环境名称</label>
                        <div class="col-sm-6">
                            <input type="text" name="environment" value="${environments.environment!}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">IP</label>
                        <div class="col-sm-6">
                            <input type="text" name="ip" value="${environments.ip!}" class="form-control" placeholder="">
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
        $("#environmentsFormView .save").click(function () {
            $("#environmentsForm").submit();   
        });
        
        $("#environmentsForm").validate({
            errorClass:'myerror',
            errorElement:'div',
            rules:{
                environment:{
                    required:true
                },
                ip:{
                    required:true
                }
            },
            messages:{
                environment:'请输入环境名称',
                ip:'请输入环境IP'
            },
            submitHandler:function(){
                var data = $("#environmentsForm").serialize();
                var title = $("#viewTitle").text();
                $("#environmentsFormView .save").text("正在保存").attr({"disabled": "disabled"});
                if (title == "添加环境") {
                    $.post("/environments/create", data, function (data) {
                        if (data.code == "0000") {
                            toastrsuccess('添加环境成功...');
                            history.back();
                        } else {
                            toastrerror(data.msg);
                        }
                        $("#environmentsFormView .save").text("保存").removeAttr("disabled");
                    }, "json");
                } else {
                    $.post("/environments/update", data, function (data) {
                        if (data.code == "0000") {
                            toastrsuccess('修改环境成功...');
                            history.back();
                        } else {
                            toastrerror(data.msg);
                        }
                        $("#environmentsFormView .save").text("保存").removeAttr("disabled");
                    }, "json");
                }
            }
        });
    })
</script>
