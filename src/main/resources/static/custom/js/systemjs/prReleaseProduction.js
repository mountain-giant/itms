/**
 * Created by Lister on 2017/12/23.
 */
$(function () {
    $.post("/user/getByProcessRole.bms",{processRole:'TEST'},function (data) {
        var datas = [];
        var i = 0;
        $.each(data, function (n, value) {
            console.log(n+","+value)
            datas[i] ={ id: value.id, text: value.name }
            i++;
        });
        $("#agentSelect").select2({
            data: datas,
            placeholder:'请选择',
        });
    },"json"); 
    /**
     * 打开发起流程窗口
     */
    $("#openCommitProcessBtn").click(function () {
        $("#commitProcessWindowTitle").text("发起流程");
        $("#publishProcessForm")[0].reset();
        $('#thelist').children().remove();
        filePaths = {};
        $("#commitProcessWindow").modal('show'); 
    });
    /**
     * 打开编辑流程窗口
     */
    $("#btnEditForRightWindow").click(function () {
        $("#commitProcessWindowTitle").text("编辑流程");
        $("#publishProcessForm")[0].reset();
        $('#thelist').children().remove();
        console.log(prInfo)
        filePaths = {};
        removeFile = "";
        $('#publishProcessForm select[name=productId]').select2("val", [prInfo.productId]);
        $('#publishProcessForm select[name=versionId]').select2("val", [prInfo.versionId]);
        $('#publishProcessForm input[name=processName]').val(prInfo.processName);
        $('#publishProcessForm input[name=id]').val(prInfo.id);
        $('#publishProcessForm select[name=level]').select2("val", [prInfo.level]);
        $('#publishProcessForm select[name=isDbchange]').select2("val", [prInfo.isDbchange]);
        $('#publishProcessForm select[name=agent]').select2("val", [prInfo.agent]);
        $('#publishProcessForm textarea[name=processDesc]').val(prInfo.processDesc);

        var $list = $('#thelist');
        $.each(prInfo.prFileDOS, function (n, value) {
            $list.append('<div class="alert alert-info alert-dismissible">' +
                '<button type="button" class="close remove_file" data-dismiss="alert" aria-hidden="true" value="" fileid='+value.id+'>×</button>' +
                '<h4 class="info">' + value.fileName + '</h4>' +
                '<p class="state"> 已有文件 </p>' +
                '</div>' );
        });
        
        
        $("#commitProcessWindow").modal('show');
    });

    $('#processTable').bootstrapTable({
        url: '/pr/pageMyProcess.bms?projectId=' + localStorage.getItem("stotage_project_id"),
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
            var prLevel = $("#prLevel").attr("data-value");
            var prStatus = $("#prStatus").attr("data-value");
            var productId = $("#productName").attr("data-value");
            var versionId = $("#versionName").attr("data-value");
            var text = $("#search_text").val();
            var param = {
                level: prLevel,
                status: prStatus,
                productId: productId,
                versionId: versionId,
                processName: text,
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
            {field: 'id', title: '编号', align: 'left', width: 40},
            {field: 'processName', title: '主题', align: 'left'},
            {field: 'productName', title: '产品', align: 'left'},
            {field: 'versionName', title: '版本', align: 'left'},
            {
                field: 'level', width: 40, title: '优先级', align: 'center',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="badge bg-red">L1</span>';
                    } else if (value == 2) {
                        return '<span class="badge bg-yellow">L2</span>';
                    } else if (value == 3) {
                        return '<span class="badge bg-aqua">L3</span>';
                    } else if (value == 4) {
                        return '<span class="badge bg-green">L4</span>';
                    }
                }
            },
            {
                field: 'status', width: 80, title: '状态', align: 'center', formatter: function (value, row, indedx) {
                if (value == 'S') {
                    return '<span class="badge bg-green">发布成功</span>';
                } else if (value == 'B') {
                    return '<span class="badge bg-red">版本回滚</span>';
                } else if (value == 'E') {
                    return '<span class="badge bg-blue">执行中</span>';
                }
            }
            },
        ], onClickRow(row, $element) {
            $.post("/pr/getPublishInfo.bms", {id: row.id}, function (data) {
                prInfo = data;
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
    
    // 保存流程
    $("#saveProcess").click(function () {
        var text = $("#commitProcessWindowTitle").text();
        var data = $("#publishProcessForm").serialize();
        $.each(filePaths, function (n, value) {
            data+="&fileRequestNameAndPath="+value;
        });
        if (text == '发起流程') {
            data+="&projectId="+localStorage.getItem("stotage_project_id");
            
            $.post("/pr/commit.bms",data,function (data) {
                if(data.code == "0000"){
                    toastrsuccess(data.msg);
                    loadTable();
                    $("#commitProcessWindow").modal('hide');
                } else {
                    toastrerror(data.msg);
                }
                $("#addAndEditDiv").hide();    
            },"json");
        } else {
            data+="&removeFile="+removeFile;
            $.post("/pr/update.bms",data,function (data) {
                if(data.code == "0000"){
                    toastrsuccess(data.msg);
                    loadTable();
                    reloadRightInfo($('#publishProcessForm input[name=id]').val());
                    $("#commitProcessWindow").modal('hide');
                } else {
                    toastrerror(data.msg);
                }
                $("#addAndEditDiv").hide();
            },"json");
        }
    });
    
    
});

