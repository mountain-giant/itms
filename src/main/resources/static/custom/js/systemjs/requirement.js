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
/**
 * 带条件查询
 */
function search() {
    var text = $("#search_text").val();
    var param = {};
    param = {requirementTitle: text};
    $('#requirementTable').bootstrapTable('refresh', {query: param});
}
function setRightWindowValue(row) {
    $("#requirementId").val(row.id);
    $("#requirementTitleForRight").text(row.requirementTitle);
    $("#requirementDesc").html(row.requirementDesc);
    $("#createByFor").text(localStorage.getItem("userid-"+row.createBy));
    $("#updateByFor").text(localStorage.getItem("userid-"+row.updateBy));
    $("#createTimeFor").text(formatDate(new Date(row.createTime)));
    $("#updateTimeFor").text(formatDate(new Date(row.updateTime)));
}
$(function () {
    $("#btnEditForRightWindow").click(function(){
        $("#myModalLabel").text("修改需求");
        $("#requirementTitle").val($("#requirementTitleForRight").text());
        $("#edit").children().next().html($("#requirementDesc").html());
        // Open window
        $("#bodydiv").hide();
        $("#addAndEditDiv").show();
    });
    $('#edit').editable({
        inlineMode: false, alwaysBlank: true,
        language: "zh_cn",
        height: '400px',
        imageUploadURL: '/image/uploadFroala.bms',//上传到本地服务器
        imageUploadParams: {id: "edit"}
    });
    $("#search_btn").click(function(){
        search();
    })

    $('#requirementTable').bootstrapTable({
        url: '/requirement/find.bms?projectId='+localStorage.getItem("stotage_project_id"),
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
        contentType: "application/json;charset=UTF-8",
        queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
        queryParams:function(params) {
            var text = $("#search_text").val();
            var param = {};
            param = {requirementTitle: text,pageSize: params.pageSize, pageNumber: params.pageNumber};
            return param;
        },
        //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
        sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
        //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: true,
        showHeader:false,
        columns: [{field: 'requirementTitle', align: 'left'},
        ], onClickRow(row, $element) {
            setRightWindowValue(row);
            isOpen = false;
            $(".right_window").show();
            $(".right_window").animate({width: "600px"});
            setTimeout(function () {
                isOpen = true;
            }, 1000);
        }
    });

    $(".closeRightWindowDiv").click(function () {
        if (isOpen) {
            $(".right_window").animate({width: "0px"},0);
            $(".right_window").hide(0);
        }
        isOpen = false;
    });

    $("#addEnvironment").click(function () {
        $("#requirementTitle").val("");
        $("#edit").children().next().html("");
        $("#myModalLabel").text("添加需求");
        $("#requirementWindow").modal('show');

        $("#bodydiv").hide();
        $("#addAndEditDiv").show();
    });

    $("#closeAddOrEdit").click(function () {
        $("#bodydiv").show();
        $("#addAndEditDiv").hide();
    })
    $("#btnSave").click(function () {
        var id = $("#requirementId").val();
        var requirement = $("#requirementTitle").val();
        var requirementDesc = $("#edit").children().next().html();
        if (requirement == null || requirement == "") {
            toastrwarning('请输入名称');
            return;
        }
        var title = $("#myModalLabel").text();
        $(this).text("正在保存").attr({"disabled": "disabled"});
        if (title == "添加需求") {
            $.post("/requirement/create.bms", {
                projectId:localStorage.getItem("stotage_project_id"),
                requirementTitle: requirement,
                requirementDesc:requirementDesc
            }, function (data) {
                if (data.code == "0000") {
                    $("#requirementTitle").val("");
                    $("#edit").children().next().html("");
                    search();
                    toastrsuccess('添加需求成功...');
                    $("#bodydiv").show();
                    $("#addAndEditDiv").hide();
                } else {
                    toastrerror(data.msg);
                }
                $("#btnSave").text("保存").removeAttr("disabled");
            }, "json");
        } else {
            $.post("/requirement/update.bms", {
                id:id,
                projectId:localStorage.getItem("stotage_project_id"),
                requirementTitle: requirement,
                requirementDesc:requirementDesc
            }, function (data) {
                if (data.code == "0000") {
                    search();
                    toastrsuccess('修改需求成功...');
                    $.post("/requirement/get.bms",{id:id},function (data) {
                        setRightWindowValue(data.data);
                    },"json");
                    $("#bodydiv").show();
                    $("#addAndEditDiv").hide();
                } else {
                    toastrerror(data.msg);
                }
                $("#btnSave").text("保存").removeAttr("disabled");
            }, "json");
        }
    });

});
