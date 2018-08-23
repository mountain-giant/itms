<div id="projectFormView">
    <section class="content-header my-content-header" style="background: #F4F4F4;">
        <h1>
            <span class="my-view-title2" id="windowTitle"> 项目详情 <i class="glyphicon glyphicon-arrow-right"></i> <label id="viewTitle">${project.projectName!}</label></span>
            <span class="pull-right">  
                <a class="btn btn-default" style="margin-top: -6px" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
            </span>
        </h1>
    </section>

    <section class="content" id="detail">
        <input type="hidden" name="id" value="${project.id!}">
        <blockquote><small>项目信息</small></blockquote>
        <div class="box-body">
            <div class="row">
                <div class="col-sm-12">
                    <label class="col-sm-1 control-label ">项目名称</label>
                    <div class="col-sm-11">${project.projectName!}</div>
                </div>
                <div class="col-sm-12">
                    <label class="col-sm-1 control-label ">项目简称</label>
                    <div class="col-sm-11">${project.projectShortName!}</div>
                </div>
                <div class="col-sm-12">
                    <label class="col-sm-1 control-label ">描述</label>
                    <div class="col-sm-11">${project.projectDesc!}</div>
                </div>
            </div>
        </div>
        <br/>
        <blockquote>
            <small>
                项目版本
                <a class="btn-sm btn-success pull-right" href="javascript:void(0)" role="button" id="openVersionAdd"><i class="glyphicon glyphicon-plus"></i> 添加版本</a>
            </small>
        </blockquote>
        <div class="box-body">
            <table id="projectVersionTable"></table>
        </div>
        <br/>
        <blockquote>
            <small>
                项目模块
                <a class="btn-sm btn-success pull-right" href="javascript:void(0)" role="button" id="openModuleAdd"><i class="glyphicon glyphicon-plus"></i> 添加模块</a>
            </small>
        </blockquote>
        <div class="box-body">
            <table id="projectModuleTable"></table>
        </div>
    </section>
</div>
<script type="text/javascript" src="/custom/js/base.js"></script>
<script>
    function guidangFun(row) {
        myConfirm("系统提示","是否归档版本"+row.versionName,function(){
            $.post("/projectVersion/guidang",{id:row.id},function (data) {
                if (data.code=="0000"){
                    reloadVersionTable();
                    toastrsuccess('归档版本成功...');
                }else {
                    toastrerror(data.msg);
                }
            },"json");
            $("#prompt_modal").modal('hide');
        });
    }
    function updateModuleFun(row) {
        loadChildView("/module/updateView?id="+row.id)
    }
    
    function updateVersionFun(row) {
        loadChildView("/projectVersion/updateView?id="+row.id)
    }
    
    function deleteModuleFun(row) {
        myConfirm("系统提示","是否删除模块"+row.moduleName,function(){
            $.post("/module/del",{id:row.id},function (data) {
                if (data.code=="0000"){
                    reloadMoudleTable();
                    toastrsuccess('删除模块成功...');
                }else {
                    toastrerror(data.msg);
                }
            },"json");
            $("#prompt_modal").modal('hide');
        });
    }

    function deleteVersionFun(row) {
        myConfirm("系统提示","是否删除版本"+row.versionName,function(){
            $.post("/projectVersion/del",{id:row.id},function (data) {
                if (data.code=="0000"){
                    reloadVersionTable();
                    toastrsuccess('删除版本成功...');
                }else {
                    toastrerror(data.msg);
                }
            },"json");
            $("#prompt_modal").modal('hide');
        });
    }
    
    function reloadVersionTable(){
        $('#projectVersionTable').bootstrapTable('refresh',{});
    }
    function reloadMoudleTable(){
        $('#projectModuleTable').bootstrapTable('refresh',{});
    }
    
    $(function () {
        $('#projectVersionTable').bootstrapTable({
            url: '/projectVersion/page?projectId='+$("input[name=id]").val(),
            singleSelect: true,//只能单选
            clickToSelect: true,
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,     //是否显示分页（*）
            sortable: false,      //是否启用排序
            height:300,
            // striped:true,
            sortOrder: "asc",     //排序方式
            pageNumber: 1,      //初始化加载第一页，默认第一页
            pageSize: 10,
            contentType: "application/x-www-form-urlencoded",
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
            //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            columns: [[
                {field: 'id', title: '版本编号', width: 75, align: 'left'},
                {field: 'versionName', title: '版本名称', align: 'left'},
                {field: 'versionDesc', title: '版本描述', align: 'left'},
                {field: 'state', title: '状态', align: 'left'},
                {field: 'publishDate', title: '上线日期', align: 'left',
                    formatter: function (value, row, index) {
                        if (value == null)
                            return "";
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleDateString();
                    }
                },
                {field: 'createBy', title: '创建人', align: 'left'},
                {field: 'createTime', title: '创建时间', align: 'left',
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }
                },
                {field: 'updateBy', title: '修改人', align: 'left'},
                {field: 'updateTime', title: '修改时间', align: 'left',
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }
                },
                {field: 'name', title: '操作', align: 'center', formatter: function (value, row, index) {
                        var obj = JSON.stringify(row);
                        var html = '<div class="btn-group"> ';
                        html += "<button type='button' class='btn btn-primary btn-sm myTableOperBtn' onclick='updateVersionFun("+obj+")'>编辑</button>";
                        html += "<button type='button' class='btn btn-danger btn-sm myTableOperBtn' onclick='deleteVersionFun("+obj+")'>删除</button>";
                        html += "<button type='button' class='btn btn-success btn-sm myTableOperBtn' onclick='guidangFun("+obj+")'>归档</button>";
                        html += '</div>';
                        return html;
                    }}
            ]]
        });
        
        $("#openModuleAdd").click(function () {
           loadChildView("/module/addView?id="+$("input[name=id]").val()) 
        });
        
        $("#openVersionAdd").click(function () {
            loadChildView("/projectVersion/addView?id="+$("input[name=id]").val())            
        });

        $('#projectModuleTable').bootstrapTable({
            url: '/module/page?projectId='+$("input[name=id]").val(),
            singleSelect: true,//只能单选
            clickToSelect: true,
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            sortable: false,      //是否启用排序
            height:300,
            // striped:true,
            contentType: "application/x-www-form-urlencoded",
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
            //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            columns: [[
                {field: 'id', title: '编号', width: 75, align: 'left'},
                {field: 'moduleName', title: '名称', align: 'left'},
                {field: 'moduleDesc', title: '描述', align: 'left'},
                {field: 'createBy', title: '创建人', align: 'left'},
                {field: 'createTime', title: '创建时间', align: 'left',
                    formatter: function (value, row, index) {
                    var unixTimestamp = new Date(value);
                    return unixTimestamp.toLocaleString();
                    }
                },
                {field: 'updateBy', title: '修改人', align: 'left'},
                {field: 'updateTime', title: '修改时间', align: 'left',
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }
                },
                {field: 'name', title: '操作', align: 'center', formatter: function (value, row, index) {
                        var obj = JSON.stringify(row);
                        var html = '<div class="btn-group"> ';
                        html += "<button type='button' class='btn btn-primary btn-sm myTableOperBtn' onclick='updateModuleFun("+obj+")'>编辑</button>";
                        html += "<button type='button' class='btn btn-danger btn-sm myTableOperBtn' onclick='deleteModuleFun("+obj+")'>删除</button>";
                        html += '</div>';
                        return html;
                    }}
            ]]
        });
    })
</script>
