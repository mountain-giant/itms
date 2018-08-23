/**
 * Created by Lister on 24/10/2017.
 */
$(function () {
    init();

    /**
     * 查询
     */
    $("#search_btn").click(function () {
        queryTable();
    });

    /**
     * 打开添加用例界面
     */
    $("#addEnvironment").click(function () {
        $("#bodydiv").hide();
        $("#addAndEditDiv").show();
        $("#windowTitle").text("添加用例集");
        initSelect2();
        reset();
    });

    $("#closeAddOrEdit").click(function () {
        $("#bodydiv").show();
        $("#addAndEditDiv").hide();
    });

    $(".productSelect").change(function () {
        $("#rightData").children().remove();
        changeLoadVersionList($(this).val());
        changeLoadNetTestCaseList($(this).val());
    });

    $("#productName").next("ul").on("click",".myliitem",function(){
        var productId = $(this).children("a").attr("data-value");
        $.post("/productVersion/versionByProduct.bms",{projectId:localStorage.getItem("stotage_project_id"),productId:productId},function (data) {
            initmyselect($("#versionName"),data);
        },"json");
        echoPlease($("#versionName"));
    });
    
    /**
     * 保存用例集
     */
    $("#savebtn").click(function () {
        var name = $("#name").val();
        if (name.trim() == "") {
            toastrwarning("用例集名称不允许为空");
            return;
        }
        var product = $("#addAndEditDiv .productSelect").select2("data")[0];
        var productId = product.id;
        var productName = product.text;
        var productVersion = $("#addAndEditDiv .productVersionSelect").select2("data")[0];
        var versionId = productVersion.id;
        var versionName = productVersion.text;
        var desc = $("#desc").val();
        var testCases = "";
        $('#rightData').find('li').each(function () {
            testCases += $(this).attr("value") + ",";
        });
        if (testCases == "") {
            toastrwarning("必须选择要执行的用例");
            return;
        }
        testCases = testCases.substr(0, testCases.length - 1);
        var windowTitle = $("#windowTitle").text();
        if (windowTitle == "添加用例集") {
            $.post("/netGroup/save.bms", {
                projectId: localStorage.getItem("stotage_project_id"),
                projectName: localStorage.getItem("stotage_project_name"),
                name: name,
                productId: productId,
                productName: productName,
                versionId: versionId,
                versionName: versionName,
                desc: desc,
                testCases: testCases
            }, function (data) {
                if (data.code == "0000") {
                    toastrsuccess("添加用例集成功");
                    reset();
                    queryTable();
                } else {
                    toastrwarning(data.msg)
                }
            }, "json");
        } else {
            var id = $("#groupId").val();
            $.post("/netGroup/edit.bms", {
                id:id,
                projectId: localStorage.getItem("stotage_project_id"),
                projectName: localStorage.getItem("stotage_project_name"),
                name: name,
                productId: productId,
                productName: productName,
                versionId: versionId,
                versionName: versionName,
                desc: desc,
                testCases: testCases
            }, function (data) {
                if (data.code == "0000") {
                    toastrsuccess("修改用例集成功");
                    reset();
                    queryTable();
                    $.post("/netGroup/get.bms", {id: id}, function (data) {
                        setRightWindowValue(data);
                    }, "json");
                    $("#bodydiv").show();
                    $("#addAndEditDiv").hide();
                } else {
                    toastrwarning(data.msg)
                }
            }, "json");
        }
    });

    /**
     * 打开编辑页面
     */
    $("#btnEditForRightWindow").click(function () {
        $("#bodydiv").hide();
        $("#addAndEditDiv").show();
        $("#windowTitle").text("编辑用例集");
        initSelect2();
        $.post("/netGroup/get.bms", {id: $("#testCaseGroupIdFor").val()}, function (data) {
            var base = data.data;
            $("#name").val(base.name);
            $("#groupId").val(base.id);
            $("#addAndEditDiv .productSelect").select2("val",[base.productId]);
            $("#addAndEditDiv .productVersionSelect").select2("val",[base.versionId]);
            $("#desc").val(base.desc);
            $("#leftData").children().remove();
            var alltestcaseList;
            $.ajax({
                type: "POST",
                dataType: "json",
                async: false,
                data: {productId: base.productId},
                url: "/netAutoTest/list.bms",
                success: function (data1) {
                    $.each(data1, function (n, value) {
                        var b = false;
                        $.each(data.data.netAutoTestInterfaceVos, function (n, value1) {
                           if (value1.id == value.id) {
                               b = true;
                           }
                        });
                        if (!b) {
                            $("#leftData").append("<li value='" + value.id + "' class='myselectli leftli'>" + value.caseName + "</li>");
                        }
                    });
                }
            });
            $.each(data.data.netAutoTestInterfaceVos, function (n, value) {
                $("#rightData").append("<li value='" + value.id + "' class='myselectli rightli'>" + value.caseName + "</li>");
            });
            
        }, "json");
    });
});

function initSelect2() {
    $.ajax({
        type: "POST",
        dataType: "json",
        async: false,
        data: {projectId: localStorage.getItem("stotage_project_id")},
        url: "/product/list.bms",
        success: function (data) {
            var datas = [];
            var i = 0;
            $.each(data.data, function (n, value) {
                datas[i] = {id: value.id, text: value.prodcutName}
                i++;
            });
            $(".productSelect").select2({
                data: datas,
                placeholder: '请选择',
            });
            $("#exportProduct").select2({
                data: datas,
                placeholder: '请选择',
            });
            changeLoadVersionList(data.data[0].id);
            changeLoadNetTestCaseList(data.data[0].id);
        }
    });
}

function changeLoadNetTestCaseList(id) {
    $.ajax({
        type: "POST",
        dataType: "json",
        async: false,
        data: {productId: id},
        url: "/netAutoTest/list.bms",
        success: function (data) {
            $("#leftData").children().remove();
            $.each(data, function (n, value) {
                $("#leftData").append("<li value='" + value.id + "' class='myselectli leftli'>" + value.caseName + "</li>");
            });
        }
    });
}

/**
 * 清空表单
 */
function reset() {
    $("#name").val("");
    $("#desc").val("");
    $("#leftData").append($("#rightData").html());
    $("#rightData").children().remove();
}

/**
 * 加载版本
 * @param productId
 */
function changeLoadVersionList(productId) {
    $.ajax({
        type: "POST",
        dataType: "json",
        async: false,
        data: {productId: productId},
        url: "/productVersion/list.bms",
        success: function (data) {
            var datas = [];
            $(".productVersionSelect").empty();
            var i = 0;
            $.each(data.data, function (n, value) {
                datas[i] = {id: value.id, text: value.versionName}
                i++;
            });
            $(".productVersionSelect").select2({
                data: datas,
                placeholder: '请选择',
            });
        }
    });
}

/**
 * 初始化
 */
function init() {
    $(".select2").select2();
    // 初始化产品
    $.post("/product/getAllProduct.bms", {projectId: localStorage.getItem("stotage_project_id")}, function (data) {
        initmyselect($("#productName"), data);
    }, "json");
    loadTable();
}

/**
 * 查询
 */
function queryTable() {
    var productId = $("#productName").attr("data-value");
    var versionId = $("#versionName").attr("data-value");
    var text = $("#input-search").val();
    var param = {productId: productId, name: text,versionId:versionId};
    $('#netAutoTestIntefaceGroupTable').bootstrapTable('refresh', {query: param});
}

/**
 * 加载表格
 */
function loadTable() {
    $('#netAutoTestIntefaceGroupTable').bootstrapTable({
        url: '/netGroup/find.bms?projectId=' + localStorage.getItem("stotage_project_id"),
        singleSelect: true,//只能单选
        clickToSelect: true,
        height:500,
        // striped:true,
        paginationDetailHAlign: 'right',
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        sortOrder: "asc",     //排序方式
        pageNumber: 1,      //初始化加载第一页，默认第一页
        pageSize: 10,
        contentType: "application/json;charset=UTF-8",
        queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
        queryParams: function (params) {
            var productId = $("#productName").attr("data-value");
            var versionId = $("#versionName").attr("data-value");
            var text = $("#input-search").val();
            var param = {
                productId: productId,
                versionId:versionId,
                name: text,
                pageSize: params.pageSize,
                pageNumber: params.pageNumber
            };
            return param;
        },
        //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
        sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
        //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: true,
        columns: [
            {field: 'name', title: '用例集', align: 'left'},
            {field: 'productName', title: '产品', align: 'left'},
            {field: 'versionName', title: '版本', align: 'left'},
            {field: 'desc', title: '描述', align: 'left'}
        ], onClickRow(row, $element) {
            $.post("/netGroup/get.bms", {id: row.id}, function (data) {
                setRightWindowValue(data);
            }, "json");
            isOpen = false;
            $(".right_window").show();
            $(".right_window").animate({width: "600px"});
            setTimeout(function () {
                isOpen = true;
            }, 1000);
        }
    });
}

function setRightWindowValue(data) {
    var base = data.data;
    $("#testCaseGroupIdFor").val(base.id);
    $("#nameFor").text(base.name);
    $("#descFor").text(base.desc);
    $("#createByFor").text(localStorage.getItem("userid-" + base.createBy));
    $("#createTimeFor").text(formatDate(new Date(base.createDate)));
    $("#updateByFor").text(localStorage.getItem("userid-" + base.updateBy));
    $("#updateTimeFor").text(formatDate(new Date(base.updateTime)));
    $("#testCaseTableRight").children().remove();
    var i = 1;
    $("#testCaseTableRight").append("<tr> <th style='width: 10px'>#</th> <th>用例名称</th> </tr>");
    $.each(data.data.netAutoTestInterfaceVos, function (n, value) {
        $("#testCaseTableRight").append("<tr> <th>"+i+"</th> <th>"+value.caseName+"</th> </tr>");
        i++;
    });
}
