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
    
    // 保存或修改自动任务
    $("#saveSetting").click(function () {
        var taskId = $("#taskIdForWindow").val();
        var groupId = $("#groupIdForWindow").val();
        var cron = $("#cron").val();
        if (cron.trim() == "") {
            toastrwarning("cron表达式必填");
            return;
        }
        var param = {id:taskId,jobCron:cron,groupId:groupId,status:$("#status").val()};
        if (taskId == "") {
            // 新增
            $.post("/netGroupRun/saveSetting.bms",param,function (data) {
                queryTable();
                $("#settingTaskJobWindow").modal("hide");
            },"json");
        } else {
            // 编辑    
            $.post("/netGroupRun/editSetting.bms",param,function (data) {

                queryTable();
                $("#settingTaskJobWindow").modal("hide");
            },"json");
            
        }
    });
    $("#productName").next("ul").on("click",".myliitem",function(){
        var productId = $(this).children("a").attr("data-value");
        $.post("/productVersion/versionByProduct.bms",{projectId:localStorage.getItem("stotage_project_id"),productId:productId},function (data) {
            initmyselect($("#versionName"),data);
        },"json");
        echoPlease($("#versionName"));
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
    $('#netAutoTestIntefaceGroupTable').bootstrapTable({
        url: '/netGroupRun/runList.bms?projectId=' + localStorage.getItem("stotage_project_id"),
        singleSelect: true,//只能单选
        clickToSelect: true,
        paginationVAlign: 'top',
        paginationHAlign: 'left',
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
            {field: 'name', title: '用例集', align: 'left'},
            {field: 'productName', title: '产品', align: 'left'},
            {field: 'versionName', title: '版本', align: 'left'},
            {field: 'runNumber', title: '执行次数', align: 'left',
                formatter: function (value, row, index) {
                    if (value == "") {
                       return "0";
                    }
                    return value;
                }
            
            
            }
            , {field: 'latestRunStatus', title: '执行状态', align: 'left',
                formatter: function (value, row, index) {
                    if (value == "S") {
                        return '<span class="badge bg-green">成功</span>';
                    } else if (value == 'F') {
                        return '<span class="badge bg-red">失败</span>';
                    } else {
                        return '<span class="badge bg-gray">未执行</span>';
                    }
                }
            },
            {field: 'latestRunTime', title: '执行时间', align: 'left'},
            {field: 'isautorun', title: '定时执行？', align: 'center'},
            {field: 'status', title: '状态', align: 'center', formatter: function (value, row, index) {
                if (value == "1") {
                    return '<span class="badge bg-green">启用</span>';
                } else if(value == "2") {
                    return '<span class="badge bg-red">关闭</span>';
                } else {
                    return "-";
                } 
            }},
            {title: '操作', align: 'center', width:240, formatter: function (value, row, index) {
                var obj = JSON.stringify(row);
                var html = "<div class='btn-group'> " +
                    "<button type='button' class='btn btn-sm btn-primary myTableOperBtn' onclick='run("+obj+")'>执行</button> " +
                    "<button type='button' class='btn btn-sm btn-info myTableOperBtn' onclick='taskRun("+obj+")'>定时设置</button> ";
                    if (row.status == "1") {
                        html+="<button type='button' class='btn btn-sm btn-danger myTableOperBtn' onclick='closeTask("+obj+")'>关闭</button> ";
                    } else if (row.status == "2") {
                        html+="<button type='button' class='btn btn-sm btn-success myTableOperBtn' onclick='openTask("+obj+")'>启用</button> ";
                    }
                    html+="</div>";
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
    var versionId = $("#versionName").attr("data-value");
    var text = $("#input-search").val();
    var param = {productId: productId, name: text,versionId:versionId};
    $('#netAutoTestIntefaceGroupTable').bootstrapTable('refresh', {query: param});
}

/**
 * 定时执行
 * @param row
 */
function taskRun(row) {
    $("#taskIdForWindow").val(row.taskId);
    $("#groupIdForWindow").val(row.id);
    $("#settingTaskJobWindow").modal("show");
    $("#cron").val(row.jobCron);
    if (row.status != null) {
        $('#status').select2("val", [row.status]);
    } else {
        $('#status').select2("val", [1]);
    }

}

/**
 * 启用
 * @param row
 */
function openTask(row) {
    var param = {id:row.taskId,jobCron:row.jobCron,groupId:row.id,status:'1'};
    $.post("/netGroupRun/editSetting.bms",param,function (data) {
        queryTable();
    },"json");
}

/**
 * 关闭
 * @param row
 */
function closeTask(row) {
    var param = {id:row.taskId,jobCron:row.jobCron,groupId:row.id,status:'2'};
    $.post("/netGroupRun/editSetting.bms",param,function (data) {
        queryTable();
    },"json");
}

/**
 * 执行
 * @param data
 */
function run(data) {
    $("#loadingWindow").modal({backdrop: 'static', keyboard: false});
    $.post("/netGroupRun/run.bms", {groupId: data.id}, function (data) {
        if (data.code == "0000") {
            toastrsuccess("执行完成");
        } else {
            toastrwarning("执行失败");
        }
        queryTable();
        $("#loadingWindow").modal('hide');
    }, "json");
}

