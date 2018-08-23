/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

/**
 * Created by Lister on 2017/8/10.
 */
function updateFun(row) {
    $("#myModalLabel").text("修改环境");
    $("#environmentId").val(row.id);
    $("#environmentName").val(row.environment);
    // Open window
    $("#environmentWindow").modal('show');
}
/**
 * 带条件查询
 */
function search() {
    var text = $("#search_text").val();
    var param = {};
    param = {environment: text};
    $('#environmentTable').bootstrapTable('refresh', {query: param});
}

$(function () {

    $("#search_btn").click(function(){
        search();
    })

    $('#environmentTable').bootstrapTable({
        url: '/environment/find.bms',
        singleSelect: true,//只能单选
        clickToSelect: true,
        paginationVAlign:'top',
        paginationHAlign:'left',
        paginationDetailHAlign:'right',
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageNumber: 1,      //初始化加载第一页，默认第一页
        pageSize: 10,
        height:500,
        contentType: "application/json;charset=UTF-8",
        queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
        //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
        sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
        //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: true,
        columns: [{field: 'environment', title: '环境名称', width: 800, align: 'left'},
            {field: 'id', title: '编号',visible:false,width: 800, align: 'left'},
            {field: 'name', title: '操作', width: 200, align: 'center', formatter: function (value, row, index) {
                var obj = JSON.stringify(row);
                var html = '';
                html += "<a class='btn btn-primary btn-xs' onclick='updateFun("+obj+")'>编辑</a>&nbsp;";
                return html;
            }}]
    });


    $("#addEnvironment").click(function () {
        $("#environmentName").val("");
        $("#myModalLabel").text("添加环境");
        $("#environmentWindow").modal('show');
    });

    $("#btnSave").click(function () {
        var id = $("#environmentId").val();
        var environment = $("#environmentName").val();
        if (environment == null || environment == "") {
            toastrwarning('请输入名称');
            return;
        }
        var title = $("#myModalLabel").text();
        $(this).text("正在保存").attr({"disabled": "disabled"});
        if (title == "添加环境") {
            $.post("/environment/create.bms", {
                environment: environment
            }, function (data) {
                if (data.code == "0000") {
                    $("#form")[0].reset();
                    search();
                    toastrsuccess('添加环境成功...');
                } else {
                    toastrerror(data.msg);
                }
                $("#btnSave").text("保存").removeAttr("disabled");
                $("#environmentWindow").modal('hide');
            }, "json");
        } else {
            $.post("/environment/update.bms", {
                id:id,
                environment: environment
            }, function (data) {
                if (data.code == "0000") {
                    search();
                    toastrsuccess('修改环境成功...');
                } else {
                    toastrerror(data.msg);
                }
                $("#btnSave").text("保存").removeAttr("disabled");
                $("#environmentWindow").modal('hide');
            }, "json");
        }
    });

});
