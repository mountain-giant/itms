<head>
    <style>
        .bs-glyphicons {
            padding-left: 0;
            padding-bottom: 1px;
            margin-bottom: 20px;
            list-style: none;
            overflow: hidden;
        }

        .bs-glyphicons li {
            float: left;
            width: 25%;
            height: 115px;
            padding: 10px;
            margin: 0 -1px -1px 0;
            font-size: 12px;
            line-height: 1.4;
            text-align: center;
            border: 1px solid #ddd;
        }

        .bs-glyphicons .glyphicon {
            margin-top: 5px;
            margin-bottom: 10px;
            font-size: 24px;
        }

        .bs-glyphicons .glyphicon-class {
            display: block;
            text-align: center;
            word-wrap: break-word; /* Help out IE10+ with class names */
        }

        .bs-glyphicons li:hover {
            background-color: rgba(86, 61, 124, .1);
        }

        @media (min-width: 768px) {
            .bs-glyphicons li {
                width: 12.5%;
            }
        }
    </style>
</head>
<div id="formView">
    <section class="content-header my-content-header" style="background: #F4F4F4;">
        <h1>
            <span class="my-view-title2" id="windowTitle"> 资源管理 <i class="glyphicon glyphicon-arrow-right"></i> <label id="viewTitle">${title}</label></span>
            <span class="pull-right">  
                <a class="btn btn-default" style="margin-top: -6px" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
                <a class="btn btn-success save" style="margin-top: -6px" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </h1>
    </section>
    <section class="content" id="detail">
        <form class="form-horizontal" id="resourceForm">
            <blockquote><small>基本信息</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <input type="hidden" name="id" value="${resource.id!}">
                        <label class="col-sm-2 control-label">资源名称</label>
                        <div class="col-sm-6">
                            <input type="text" name="name" value="${resource.name!}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">资源类型</label>
                        <div class="col-sm-6" style="height:30px;line-height:30px;">
                            <label>
                                <input type="radio" name="type" class="minimal" ${(resource.type! == 'M')?string('checked','')} value="M"> 资源
                            </label>
                            <label>
                                <input type="radio" name="type" class="minimal" ${(resource.type! == 'F')?string('checked','')} value="F"> 操作
                            </label>
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">父级编号</label>
                        <div class="col-sm-6">
                            <input type="text" name="parentId" value="${resource.parentId!}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">链接地址</label>
                        <div class="col-sm-6">
                            <input type="text" name="href" value="${resource.href!}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">显示图标</label>
                        <div class="col-sm-6">
                            <input type="text" name="icon" value="${resource.icon!'glyphicon glyphicon-cog'}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">图标颜色</label>
                        <div class="col-sm-6">
                            <div class="input-group my-colorpicker2 colorpicker-element">
                                <input type="text" class="form-control" value="#B8C7CD" value="${resource.iconColor!}" name="iconColor">
                                <div class="input-group-addon">
                                    <i id="iconBtn" style="background-color: #B8C7CD;"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">排序号</label>
                        <div class="col-sm-6">
                            <input type="text" name="sort" value="${resource.sort!}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row" style="margin-left: 5px;margin-right: 5px;">
                <#include "../viewblock/iconpage.ftl"/>
            </div>
        </form>
        <div class="box-footer">
            <span class="pull-right">  
                <a class="btn btn-default" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
                <a class="btn btn-success save" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </div>
        <br/>
        <%-- 执行记录 --%>
    </section>
</div>
<script type="text/javascript" src="/custom/js/base.js"></script>
<script>
    $(function () {
        $('.my-colorpicker2').colorpicker();
        $("#formView .save").click(function () {
            var name = $("#resourceForm input[name=name]").val();
            var parentId = $("#resourceForm input[name=parentId]").val();
            var href = $("#resourceForm input[name=href]").val();
            var icon = $("#resourceForm input[name=icon]").val();
            var sort = $("#resourceForm input[name=sort]").val();
            if (name == null || name == "") {
                toastrwarning('请输入名称');
                return;
            }
            if (parentId == null || parentId == "") {
                toastrwarning('请输入父级编号');
                return;
            }
            if (href == null || href == "") {
                toastrwarning('请输入链接地址');
                return;
            }
            if (icon == null || icon == "") {
                toastrwarning('请输入图标');
                return;
            }
            if (sort == null || sort == "") {
                toastrwarning('请输入排序号');
                return;
            }
            var data = $("#resourceForm").serialize();
            var title = $("#viewTitle").text();
            $("#formView .save").text("正在保存").attr({"disabled": "disabled"});
            if (title == "添加资源") {
                $.post("/resource/addMenu", data, function (data) {
                    if (data.code == "0000") {
                        history.back();
                    } else {
                        toastrerror(data.msg);
                    }
                    $("#formView .save").text("保存").removeAttr("disabled");
                }, "json");
            } else {
                $.post("/resource/update", data, function (data) {
                    if (data.code == "0000") {
                        history.back();
                    } else {
                        toastrerror(data.msg);
                    }
                    $("#formView .save").text("保存").removeAttr("disabled");
                }, "json");
            }
        });

    })
</script>
