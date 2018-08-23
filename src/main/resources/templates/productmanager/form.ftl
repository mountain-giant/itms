<div id="productFormView">
    <section class="content-header my-content-header" style="background: #F4F4F4;">
        <h1>
            <span class="my-view-title2" id="windowTitle"> 产品管理 <i class="glyphicon glyphicon-arrow-right"></i> <label
                    id="viewTitle">${title}</label></span>
            <span class="pull-right">  
                <a class="btn btn-default" style="margin-top: -6px" href="javascript:void(0)" role="button"
                   onclick="history.back()">返回</a> 
                <a class="btn btn-success save" style="margin-top: -6px" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </h1>
    </section>
    <section class="content" id="detail">
        <form class="form-horizontal" id="productForm">
            <blockquote><small>基本信息</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <input type="hidden" name="id" value="${product.id!}">
                        <label class="col-sm-2 control-label">产品名称</label>
                        <div class="col-sm-6">
                            <input type="text" name="productName" value="${product.productName!}"class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">产品描述</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" style="resize:none;" name="productDesc" rows="3"
                                      placeholder="最多输入500个字符...">${product.productDesc!}</textarea>
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
        $("#productFormView .save").click(function () {
            $("#productForm").submit();
        });
        $("#productForm").validate({
            errorClass:'myerror',
            errorElement:'div',
            rules:{
                productName:{
                    required:true
                },
                productDesc:{
                    required:true
                }
            },
            messages:{
                productName:'请输入产品名称',
                productDesc:'请输入产品描述'
            },
            submitHandler:function(){
                var data = $("#productForm").serialize();
                var title = $("#viewTitle").text();
                $("#productFormView .save").text("正在保存").attr({"disabled": "disabled"});
                if (title == "添加产品") {
                    $.post("/product/create", data, function (data) {
                        if (data.code == "0000") {
                            toastrsuccess('添加产品成功...');
                            history.back();
                        } else {
                            toastrerror(data.msg);
                        }
                        $("#productFormView .save").text("保存").removeAttr("disabled");
                    }, "json");
                } else {
                    $.post("/product/update", data, function (data) {
                        if (data.code == "0000") {
                            toastrsuccess('修改产品成功...');
                            history.back();
                        } else {
                            toastrerror(data.msg);
                        }
                        $("#productFormView .save").text("保存").removeAttr("disabled");
                    }, "json");
                }
            }
        });
    })
</script>
