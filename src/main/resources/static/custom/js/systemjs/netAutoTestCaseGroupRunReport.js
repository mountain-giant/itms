/**
 * Created by Lister on 2017/11/5.
 */
/**
 * Created by Lister on 26/10/2017.
 */
$(function () {
    init();
    /**
     * 查询
     */
    $("#search_btn").click(function () {
        queryTable();
    });
});


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
 * 加载表格
 */
function loadTable() {
    $('#netAutoTestIntefaceGroupRunReportTable').bootstrapTable({
        url: '/netGroupRun/reportList.bms?projectId=' + localStorage.getItem("stotage_project_id"),
        singleSelect: true,//只能单选
        clickToSelect: true,
        // paginationVAlign: 'top',
        // paginationHAlign: 'left',
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
        detailView:true,
        detailFormatter:function(index, row) {
            var html = '<table class="table table-striped">' +
                '<tr>' +
                '<th>#</th>' +
                '<th>接口</th>' +
                '<th>请求地址</th>' +
                '<th>请求方式</th>' +
                '<th>参数类型</th>' +
                '</tr>';
            var i = 1;
            $.each(row.netAutoTestInterfaceVos, function (n, value) {
                html += '<tr>' +
                    '<th>'+i+'</th>' +
                    '<th>'+value.caseName+'</th>' +
                    '<th>'+value.url+'</th>' +
                    '<th>'+value.method+'</th>' +
                    '<th>'+value.paramsType+'</th>' +
                    '</tr>'
                i++;
            });
            html += '</table>'
            return html;
        },
        columns: [
            {field: 'runId', title: '编号', align: 'left'},
            {field: 'name', title: '用例集', align: 'left'},
            {field: 'productName', title: '产品', align: 'left'},
            {field: 'versionName', title: '版本', align: 'left'}
            , {field: 'runStatus', title: '状态', align: 'center',
                formatter: function (value, row, index) {
                    if (value == "S") {
                        return '<span class="badge bg-green">S</span>';
                    } else if (value == 'F') {
                        return '<span class="badge bg-red">F</span>';
                    }
                }
            },
            {field: 'caseNumber', title: '用例数', align: 'center'},
            {field: 'runPerson', title: '执行人', align: 'left'},
            {field: 'runTime', title: '执行时间', align: 'left'},
            {title: '操作', align: 'center', width:80, formatter: function (value, row, index) {
                var obj = JSON.stringify(row);
                var html = "<div class='btn-group'> " +
                    "<button type='button' class='btn btn-sm btn-info myTableOperBtn' onclick='testDetails("+obj+")'>报告</button> " +
                    "</div>";
                return html;
            }}
        ]
    });
}

/**
 * 查询
 */
function queryTable() {
    var productId = $("#productName").attr("data-value");
    var text = $("#input-search").val();
    var param = {productId: productId, name: text};
    $('#netAutoTestIntefaceGroupRunReportTable').bootstrapTable('refresh', {query: param});
}

/**
 * 打开测试报告页面
 * @param row
 */
function testDetails(row) {
    loadChildView('/netGroupRun/testReport.bms?groupId='+row.groupId+'&runId='+row.runId);
}
