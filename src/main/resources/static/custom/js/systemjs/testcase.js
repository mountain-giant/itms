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
var versionId;
// 文件上传
var $btn =$("#ctlBtn");
var uploader;
var isLoadUploadPlugin = false;
//在点击弹出模态框的时候再初始化WebUploader，解决点击上传无反应问题
$("#importTestCaseWindow").on("shown.bs.modal",function() {
    if (isLoadUploadPlugin == false) {
        uploader = WebUploader.create({

            // swf文件路径
            swf: '/static/adminlte/plugins/baiduwebuploader/Uploader.swf',

            // 文件接收服务端。
            server: '/testCase/importTestCase.bms',
            timeout:600000,
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker',

            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false
        });
        // 当有文件被添加进队列的时候
        uploader.on( 'fileQueued', function( file ) {
            var $list = $('#thelist');
            $list.append( '<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>' );
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            if ( !$percent.length ) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>').appendTo( $li ).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');

            $percent.css( 'width', percentage * 100 + '%' );
        });
        uploader.on( 'uploadSuccess', function( file,response ) {
            $( '#'+file.id ).find('p.state').text('已上传');
            $("#"+file.id).remove();
            if ($('#thelist').html()=="") {
                if (response.code == "0000") {
                    toastrsuccess(response.msg);
                } else if (response.code == "9999") {
                    toastrwarning(response.msg);
                } else {
                    toastrerror(response.msg);
                }
                $btn.removeAttr("disabled");
                $btn.text("开始导入");
                $("#closeUploadWindow").removeAttr("disabled");
            }
        });

        uploader.on( 'uploadError', function( file ) {
            $( '#'+file.id ).find('p.state').text('上传出错，请稍后重试');
            toastrwarning("上传出错，请稍后重试");
            $btn.removeAttr("disabled");
            $btn.text("开始导入");
            $("#closeUploadWindow").removeAttr("disabled");
        });

        uploader.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').fadeOut();
        });
        isLoadUploadPlugin = true;
    }
});
function initMySelect() {
    // 获取产品列表
    $.post("/testCase/getAllProduct.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
        initmyselect($("#productName"),data);
    },"json");
    // 需求列表
    $.post("/testCase/getAllRequirement.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
        initmyselect($("#requirementName"),data);
    },"json");
}
function loadTable() {
    var testCaseStatus = $("#caseStatus").attr("data-value");
    var checkstatus = $("#checkstatus").attr("data-value");
    var productId = $("#productName").attr("data-value");
    var versionId = $("#versionName").attr("data-value");
    var requirementId = $("#requirementName").attr("data-value");
    var text = $("#search_text").val();
    var param = {testCaseStatus:testCaseStatus,checkstatus:checkstatus,productId:productId,versionId:versionId,requirementId:requirementId,testCaseName:text};
    $('#testCaseTable').bootstrapTable('refresh',{query:param});
}
function initSelect2() {
    $.post("/product/list.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
        var datas = [];
        var i = 0;
        $.each(data.data, function (n, value) {
            console.log(n+","+value)
            datas[i] ={ id: value.id, text: value.prodcutName }
            i++;
        });
        $(".productSelect").select2({
            data: datas,
            placeholder:'请选择',
        });
        $("#exportProduct").select2({
            data: datas,
            placeholder:'请选择',
        });
        changeLoadVersionList(data.data[0].id);
        changeLoadVersionListForExport(data.data[0].id);
    },"json");
    $.post("/requirement/list.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
        var datas = [];
        var i = 0;
        $.each(data, function (n, value) {
            console.log(n+","+value)
            datas[i] ={ id: value.id, text: value.requirementTitle }
            i++;
        });
        $(".requirementSelect").select2({
            data: datas,
            placeholder:'请选择',
        });
    },"json");
}
function changeLoadVersionList(productId) {
    console.log()
    $.post("/productVersion/list.bms",{productId:productId},function (data) {

        var datas = [];
        $(".productVersionSelect").empty();
        var i = 0;
        $.each(data.data, function (n, value) {
            console.log(n + "," + value)
            datas[i] = {id: value.id, text: value.versionName}
            i++;
        });
        $(".productVersionSelect").select2({
            data: datas,
            placeholder: '请选择',
        });
        $('#addAndEditDiv .productVersionSelect').select2("val", [versionId]);
    },"json"); 
}
function changeLoadVersionListForExport(productId) {
    $.post("/productVersion/list.bms",{productId:productId},function (data) {
        var datas = [];
        $("#exportProductVersion").empty();
        datas[0] = {id:'0',text:'请选择'};
        var i = 1;
        $.each(data.data, function (n, value) {
            console.log(n + "," + value)
            datas[i] = {id: value.id, text: value.versionName};
            i++;
        });
        $("#exportProductVersion").select2({
            data: datas,
            placeholder: '请选择',
        });
    },"json");
}
function setRightWindowValue(row) {
    $("#projectName").text(row.projectName);
    $("#testCaseName").text(row.testCaseName);
//        $("#testCaseId").text("#"+row.id);
    $("#testCaseIdValue").val(row.id);
    $("#productNameText").text(row.productName);
    $("#productIdValue").val(row.productId);
    $("#requirementNameText").text(row.requirementName);
    $("#requirementIdValue").val(row.requirementId);
    $("#versionNameText").text(row.versionName);
    $("#versionIdValue").val(row.versionId);
    if (row.testCaseStatus == 1) {
        $("#testCaseStatus").text("正常");
    } else {
        $("#testCaseStatus").text("N/A");
    }
    if (row.testCaseType == 1) {
        $("#testCaseTypeText").text("正例");
    } else {
        $("#testCaseTypeText").text("反例");
    }
    $("#testCaseTypeValue").val(row.testCaseType);
    if (row.checkstatus == 1) {
        $("#btnEditForRightWindow").show();
        $("#checkstatusText").text("待评审");
    } else {
        $("#btnEditForRightWindow").hide();
        $("#checkstatusText").text("已提交");
    }
    $("#checkstatusValue").val(row.checkstatus);
    $("#createBy").text(localStorage.getItem("userid-"+row.createBy));
    $("#createTime").text(formatDate(new Date(row.createTime)));
    $("#updateBy").text(localStorage.getItem("userid-"+row.updateBy));
    $("#updateTime").text(formatDate(new Date(row.updateTime)));
    $("#testCaseDesc").html(row.testCaseDesc);
}

$(function () {
    // 下载测试用例模板
    $("#dowloadExcelModel").click(function () {
       location.href = "/testCase/downloadModal.bms"; 
    });
    
    // 打开导入测试用例窗口
    $("#importTestCase").click(function () {
        $('#importTestCaseWindow .testCaseCheckStatusSelect').select2("val", [1]);
        $("#importTestCaseWindow").modal({backdrop: 'static', keyboard: false});
    });
    
    // 打开导出测试用例窗口
    $("#exportTestCase").click(function(){
        $("#exportTestCaseWindow").modal('show');
    });
    
    // 开始导入测试用例
    $btn.on( 'click', function() {
        $(this).attr({"disabled":"disabled"});
        $(this).text("正在导入");
        $("#closeUploadWindow").attr({"disabled":"disavled"});
        var obj = new Object();
        var product = $("#importTestCaseWindow .productSelect").select2("data")[0] ;
        obj.productId = product.id;
        obj.productName = product.text;
        var productVersion =  $("#importTestCaseWindow .productVersionSelect").select2("data")[0] ;
        obj.versionId = productVersion.id;
        obj.versionName = productVersion.text;
        var requirement =  $("#importTestCaseWindow .requirementSelect").select2("data")[0] ;
        obj.requirementId = requirement.id;
        obj.requirementName = requirement.text;
        obj.checkstatus = $("#importTestCaseWindow .testCaseCheckStatusSelect").select2("data")[0].id;
        obj.roleId = $("#importTestCaseWindow .role_select").select2("data")[0].id;
        obj.roleName = $("#importTestCaseWindow .role_select").select2("data")[0].text;
        obj.testCaseStatus = $("#importTestCaseWindow .status_select").select2("data")[0].id;
        obj.projectId = localStorage.getItem("stotage_project_id");
        obj.projectName = localStorage.getItem("stotage_project_name");
        uploader.options.formData = obj;
        uploader.upload();
    });
    
    $("#exportBen").click(function () {
       location.href = "/testCase/exportTestCase.bms?projectId="+localStorage.getItem("stotage_project_id")+"&productId="+
           $("#exportProduct").select2("data")[0].id + "&versionId="+
           $("#exportProductVersion").select2("data")[0].id;


    });
    
    $(".testCaseCheckStatusSelect").change(function () {
        if ($(this).select2("val") == 2) {
            $(".statusFormGroup").show();
            $(".roleFormGroup").show();
        } else {
            $(".statusFormGroup").hide();
            $(".roleFormGroup").hide();
        }
    });
    $(".status_select").change(function () {
        if ($(this).select2("val") == 1) {
            $(".roleFormGroup").show();
        } else {
            $(".roleFormGroup").hide();
        }
    });
    $('#edit').editable({
        inlineMode: false, alwaysBlank: true,
        language: "zh_cn",
        height: '400px',
        imageUploadURL: '/image/uploadFroala.bms',//上传到本地服务器
        imageUploadParams: {id: "edit"}
    });
    
    initMySelect();
    
    initSelect2();
    $(".select2").select2();
    $(".productSelect").change(function () {
        changeLoadVersionList($(this).val());
    });
    $("#exportProduct").change(function () {
        changeLoadVersionListForExport($(this).val());
    });
    var isOpen = false;
    $("#search_btn").click(function () {
        loadTable();
    });
    $("#productName").next("ul").on("click",".myliitem",function(){
        var productId = $(this).children("a").attr("data-value");
        $.post("/testCase/getAllProductVersion.bms",{projectId:localStorage.getItem("stotage_project_id"),productId:productId},function (data) {
            initmyselect($("#versionName"),data);
        },"json");
        echoPlease($("#versionName"));
    });
    $('#testCaseTable').bootstrapTable({
        url: '/testCase/find.bms?projectId='+localStorage.getItem("stotage_project_id"),
        singleSelect: true,//只能单选
        clickToSelect: true,
        // striped:true,
        paginationDetailHAlign:'right',
        height:500,
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
            var testCaseStatus = $("#caseStatus").attr("data-value");
            var checkstatus = $("#checkstatus").attr("data-value");
            var productId = $("#productName").attr("data-value");
            var versionId = $("#versionName").attr("data-value");
            var requirementId = $("#requirementName").attr("data-value");
            var text = $("#search_text").val();
            var param = {testCaseStatus:testCaseStatus,checkstatus:checkstatus,productId:productId,versionId:versionId,requirementId:requirementId,testCaseName:text,pageSize: params.pageSize, pageNumber: params.pageNumber};
            return param;
        },
        //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
        sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
        //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: true,
        columns: [
            {field: 'testCaseName', title: '用例名称', align: 'left'},
            {field: 'testCaseType', title: '用例类型', align: 'left',
                formatter:function(value, row, index){
                    if (value == 1) {
                        return '<span class="badge bg-green">正例</span>';
                    } else {
                        return '<span class="badge bg-red">反例</span>';
                    }
                }
            },
            {field: 'requirementName', title: '需求', align: 'left'},
            {field: 'productName', title: '产品', align: 'left'},
            {field: 'versionName', title: '版本', align: 'left'},
            {field: 'testCaseStatus', title: '用例状态', align: 'left',
                formatter:function(value, row, index){
                    if (value == 1) {
                        return '<span class="badge bg-green">正常</span>';
                    } else {
                        return '<span class="badge bg-red">N/A</span>';
                    }
                }
            },
            {field: 'checkstatus', title: '审核状态', align: 'left',
                formatter:function(value, row, index){
                    if (value == 1) {
                        return '<span class="badge bg-red">待评审</span>';
                    } else {
                        return '<span class="badge bg-green">已提交</span>';
                    }
                }},
        ],onClickRow(row, $element) {
            $.post("/testCase/getTestCase.bms",{id:row.id},function (data) {
                console.log(data)
                setRightWindowValue(data);
            },"json");
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
    $("#btnEditForRightWindow").click(function(){
        if ($("#checkstatusValue").val()==2){
            toastrwarning("已提交的测试用例不允许修改...");
            return;
        }
        // $('#questionType').select2("val", [obj.questionType]);
        $("#testCaseIdForm").val($("#testCaseIdValue").val());
        $('#addAndEditDiv .productSelect').select2("val", [$("#productIdValue").val()]);
        $('#addAndEditDiv .productVersionSelect').select2("val", [$("#versionIdValue").val()]);
        versionId = $("#versionIdValue").val();
        $('#addAndEditDiv .requirementSelect').select2("val", [$("#requirementIdValue").val()]);
        $('#addAndEditDiv .testCaseCheckStatusSelect').select2("val", [$("#checkstatusValue").val()]);
        $("#testCaseNameInput").val($("#testCaseName").text());
        $("#edit").children().next().html($("#testCaseDesc").html());
        $("#windowTitle").text("编辑用例");
        $("#testCaseNameInput").attr("disabled",true);
        $("#bodydiv").hide();
        $("#addAndEditDiv").show();

    });
    $("#addTestCase").click(function () {
        $("#windowTitle").text("添加用例");
        $("#testCaseNameInput").attr("disabled",false);
        $("#testCaseNameInput").val("");
        // $('#productSelect').select2("val", [$("#productIdValue").val()]);
        // $('#productVersionSelect').select2("val", [$("#versionIdValue").val()]);
        // $('#requirementSelect').select2("val", [$("#requirementIdValue").val()]);
        $('#addAndEditDiv .testCaseCheckStatusSelect').select2("val", [1]);
        $("#edit").children().next().html("");
        $("#bodydiv").hide();
        $("#addAndEditDiv").show();
    });
    $("#closeAddOrEdit").click(function () {
        $("#addAndEditDiv").hide();
        $("#bodydiv").show();
    })
    $("#savebtn").click(function () {
        var product = $("#addAndEditDiv .productSelect").select2("data")[0] ;
        var productId = product.id;
        var productName = product.text;
        var productVersion =  $("#addAndEditDiv .productVersionSelect").select2("data")[0] ;
        var versionId = productVersion.id;
        var versionName = productVersion.text;
        var requirement =  $("#addAndEditDiv .requirementSelect").select2("data")[0] ;
        var requirementId = requirement.id;
        var requirementName = requirement.text;
        var testCaseName = $("#testCaseNameInput").val();
        if (testCaseName == "") {
            toastrwarning("请输入用例名称");
            return;
        }
        var testCaseDesc = $("#edit").children().next().html();
        if (testCaseDesc == "") {
            toastrwarning("请输入用例描述");
            return;
        }
        var testCaseType =  $("#testCaseTypeSelect").select2("data")[0].id ;
        var checkstatus = $("#addAndEditDiv .testCaseCheckStatusSelect").select2("data")[0].id;
        var title = $("#windowTitle").text();
        var role = $("#addAndEditDiv .role_select").select2("data")[0].id;
        var roleName = $("#addAndEditDiv .role_select").select2("data")[0].text;
        var testCaseStatus = $("#addAndEditDiv .status_select").select2("data")[0].id;

        var param = { id:$("#testCaseIdForm").val(),projectId:localStorage.getItem("stotage_project_id"),
            projectName:localStorage.getItem("stotage_project_name"),
            productId:productId,
            productName:productName,
            requirementId:requirementId,
            requirementName:requirementName,
            testCaseDesc:testCaseDesc,
            testCaseStatus:testCaseStatus,
            testCaseName:testCaseName,
            versionId:versionId,
            roleId:role,
            roleName:roleName,
            checkstatus:checkstatus,
            testCaseType:testCaseType,
            versionName:versionName};
        $(this).text("正在保存").attr({"disabled": "disabled"});
        if (title == "添加用例") {
            $.post("/testCase/create.bms", param, function (data) {
                if (data.code == "0000") {
                    loadTable();
                    toastrsuccess("新增用例成功")
                    $("#addAndEditDiv").hide();
                    $("#bodydiv").show();
                } else {
                    toastrerror(data.msg);
                }
                $("#savebtn").text("保存").removeAttr("disabled");
            }, "json").error(function() {
                toastrerror("服务器异常...");
                $("#savebtn").text("保存").removeAttr("disabled");
            });;
        } else {
            $.post("/testCase/update.bms",param, function (data) {
                if (data.code == "0000") {
                    toastrsuccess('修改用例成功...');
                    $("#testCaseWindow").modal('hide');
                    loadTable();
                    $("#addAndEditDiv").hide();
                    $("#bodydiv").show();
                    $.post("/testCase/getTestCase.bms",{id:$("#testCaseIdForm").val()},function (data) {
                        console.log(data)
                        setRightWindowValue(data);
                    },"json");
                } else {
                    toastrerror(data.msg);
                }
                $("#savebtn").text("保存").removeAttr("disabled");
            }, "json").error(function() {
                toastrerror("服务器异常...");
                $("#savebtn").text("保存").removeAttr("disabled");
            });
        }
    });
});
