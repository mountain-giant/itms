/**
 * Created by Lister on 26/09/2017.
 */
$(function () {
    init();
    /**
     * 添加用例按钮单击事件
     */
    $("#addEnvironment").click(function () {
        $("#windowTitle").text("添加用例");
        $("#bodydiv").hide();
        $("#loadAddAndEditView").show();
        $("#loadAddAndEditView").children().remove();
        $("#loadAddAndEditView").append('<br><div class="col-xs-12 text-center"><i class="fa fa-spin fa-refresh"></i>&nbsp; 页面加载中...</div>');
        $.ajax({url:'/netAutoTest/addAndEditView.bms?flag=A',success:function(page){
            $("#loadAddAndEditView").children().remove();
            $("#loadAddAndEditView").append(page);
        },error:function (xhr,status,error) {
            $.post("/sys/404.bms","",function (page) {
                $("#loadAddAndEditView").children().remove();
                $("#loadAddAndEditView").append(page);
            });
        }});
    });

    /**
     * 查询
     */
    $("#search_btn").click(function () {
        queryTable();
    });
    // 参数列表
    $("#paramsTableFor").bootstrapTable({
        singleSelect: true,//只能单选
        strictSearch: true,
        height: 200,
        columns: [
            {field: 'inOrRelationDesc', title: '参数类型', align: 'left'},
            {field: 'keyname', title: 'KEY', align: 'left'},
            {field: 'paramValues', title: 'VALUE', align: 'left'},
            {field: 'relationInterfaceName', title: '关联用例', align: 'left'},
            {field: 'relationKey', title: '关联KEY', align: 'left'}
        ]
    });

    // 断言列表
    $('#assertionTableFor').bootstrapTable({
        singleSelect: true,//只能单选
        strictSearch: true,
        height: 200,
        columns: [
            {field: 'compareField', title: '断言KEY', align: 'left'},
            {field: 'compareValue', title: '断言VALUE', align: 'left'},
            {field: 'judgmentMethodDesc', title: '比较方式', align: 'left'}
        ]
    });

    /**
     * 打开编辑页面
     */
    $("#btnEditForRightWindow").click(function () {
        var id = $("#testCaseIdFor").val();
        $("#windowTitle").text("编辑用例");
        $("#bodydiv").hide();
        $("#loadAddAndEditView").show();
        $("#loadAddAndEditView").children().remove();
        $("#loadAddAndEditView").append('<br><div class="col-xs-12 text-center"><i class="fa fa-spin fa-refresh"></i>&nbsp; 页面加载中...</div>');
        $.ajax({url:'/netAutoTest/addAndEditView.bms?flag=E&caseId='+id,success:function(page){
            $("#loadAddAndEditView").children().remove();
            $("#loadAddAndEditView").append(page);
        },error:function (xhr,status,error) {
            $.post("/sys/404.bms","",function (page) {
                $("#loadAddAndEditView").children().remove();
                $("#loadAddAndEditView").append(page);
            });
        }});
    });
});

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
    var text = $("#input-search").val();
    var param = {productId: productId, caseName: text};
    $('#netAutoTestIntefaceTable').bootstrapTable('refresh', {query: param});
}
/**
 * 加载数据表
 */
function loadTable() {
    $('#netAutoTestIntefaceTable').bootstrapTable({
        url: '/netAutoTest/find.bms?projectId=' + localStorage.getItem("stotage_project_id"),
        singleSelect: true,//只能单选
        clickToSelect: true,
        paginationDetailHAlign: 'right',
        cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,     //是否显示分页（*）
        sortable: false,      //是否启用排序
        height:500,
        // striped:true,
        sortOrder: "asc",     //排序方式
        pageNumber: 1,      //初始化加载第一页，默认第一页
        pageSize: 10,
        contentType: "application/json;charset=UTF-8",
        queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
        queryParams: function (params) {
            var productId = $("#productName").attr("data-value");
            var text = $("#input-search").val();
            var param = {
                productId: productId,
                caseName: text,
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
            {field: 'caseName', title: '用例名称', align: 'left'},
            {field: 'productName', title: '产品', align: 'left'},
            {field: 'url', title: '请求地址', align: 'left'},
            {field: 'method', title: '请求方式', align: 'left'},
            {field: 'paramsType', title: '参数类型', align: 'left'}
        ], onClickRow(row, $element) {
            $.post("/netAutoTest/get.bms", {id: row.id}, function (data) {
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

/**
 * 显示详情
 * @param data
 */
function setRightWindowValue(data) {
    var base = data.data.base;
    $("#testCaseIdFor").val(base.id);
    $("#interfaceNameFor").text(base.caseName);
    $("#urlFor").text(base.url);
    $("#methodFor").text(base.methodDesc);
    $("#paramTypeFor").text(base.paramsTypeDesc);
    $("#createByFor").text(localStorage.getItem("userid-" + base.createBy));
    $("#createTimeFor").text(formatDate(new Date(base.createDate)));
    $("#updateByFor").text(localStorage.getItem("userid-" + base.updateBy));
    $("#updateTimeFor").text(formatDate(new Date(base.updateTime)));

    // 参数信息
    if (base.paramsType == "2") {
        $("#keyvalue_params").hide();
        $("#text_param").show();
        $("#text_param").text(base.paramValue);
    } else {
        $("#keyvalue_params").show();
        $("#text_param").hide();
        $("#paramsTableFor").bootstrapTable('load', data.data.params);
    }

    // 断言
    // assertionTable
    $('#assertionTableFor').bootstrapTable('load', data.data.asserts);
}

