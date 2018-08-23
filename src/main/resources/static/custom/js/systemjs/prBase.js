/**
 * 生产发布通用JS
 * Created by Lister on 2018/1/3.
 */
var prInfo;
// 文件上传
var uploader;
var filePaths = {};
var removeFile = "";
function initWebUpload() {
    if (uploader != null) {
        uploader.destroy();
    }
    uploader = WebUploader.create({
        auto: true,
        // swf文件路径
        swf: '/static/adminlte/plugins/baiduwebuploader/Uploader.swf',

        // 文件接收服务端。
        server: '/file/upload.bms',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',

        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        compress: false,
        resize: false,
        // accept: {
        //     title: "Images",
        //     extensions: 'jpg,jpeg,png,bmp',
        //     mimeTypes: "image/jpg,image/jpeg,image/png,image/bmp"
        // }
    });
    uploader.on('beforeFileQueued', function (file) {
        return true;
    });

    // 当有文件被添加进队列的时候
    uploader.on('fileQueued', function (file) {
        var $list = $('#thelist');
        $list.append( '<div id="' + file.id + '" class="alert alert-success alert-dismissible">' +
            '<button type="button" class="close remove_file" data-dismiss="alert" aria-hidden="true">×</button>' +
            '<h4 class="info">' + file.name + '</h4>' +
            '<p class="state">等待上传...</p>' +
            '</div>' );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
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
    uploader.on('uploadSuccess', function (file, response) {
        console.log(response)
        if (response.code == "0000") {
            filePaths[file.id+""] = response.data;
            $("#" + file.id).find('p.state').text("上传成功");
            $("#" + file.id).find('button.remove_file').attr("value",file.id);
            // TODO
            console.log(filePaths)
        } else {
            $("#" + file.id).find('p.state').text("上传失败");
            $("#" + file.id).removeClass("alert-success");
            $("#" + file.id).addClass("alert-danger");
        }
    });

    uploader.on('uploadError', function (file) {
        $('#' + file.id).find('p.state').text('上传出错，请稍后重试');
        uploader.destroy();
        initWebUpload();
    });

    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.progress').fadeOut();
    });
}
$("#commitProcessWindow").on("shown.bs.modal",function() {
    initWebUpload();
});
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
        changeLoadVersionList(data.data[0].id);
    },"json");
}

function changeLoadVersionList(productId) {
    $.post("/productVersion/list.bms",{productId:productId},function (data) {
        var datas = [];
        $(".productVersionSelect").empty();
        var i = 0;
        $.each(data.data, function (n, value) {
            console.log(n + "," + value)
            datas[i] = {id: value.id, text: value.versionName};
            i++;
        });
        $(".productVersionSelect").select2({
            data: datas,
            placeholder: '请选择',
        });
    },"json");
}

function setRightWindowValue(row) {
    $("#prIdForRight").val(row.id);
    if (row.status == "S") {
        $("#newProcessNameForRight").text("发布成功")
    } else if(row.status == "F") {
        $("#newProcessNameForRight").text("发布失败")
    } else {
        $("#newProcessNameForRight").text(row.newProcessName)    
    }
    
    $("#rightWindowTitle").text(row.processName);
    $("#productNameForRight").text(row.productName);
    $("#versionNameForRight").text(row.versionName);
    if (row.level == 1) {
        $("#levelForRight").html('<span class="badge bg-red">L1</span>');
    } else if (row.questionLevel == 2) {
        $("#levelForRight").html('<span class="badge bg-yellow">L2</span>');
    } else if (row.questionLevel == 3) {
        $("#levelForRight").html('<span class="badge bg-aqua">L3</span>');
    } else if (row.questionLevel == 4) {
        $("#levelForRight").html('<span class="badge bg-green">L4</span>');
    }
    if (row.status == "E") {
        $("#statusForRight").text("执行中");
    }
    if (row.status == "S") {
        $("#statusForRight").text("发布成功");
    }
    if (row.status == "B") {
        $("#statusForRight").text("版本回滚");
    }
    $("#processDescForRight").text(row.processDesc);
    if (row.isDbchange == "Y") {
        $("#isDbChangeForRight").text("是");
    } else {
        $("#isDbChangeForRight").text("否");
    }
    $("#commitPersonForRight").text(row.createNameBy);
    $("#agentPersonForRight").text(row.agentName)
    // 文件列表
    $("#fileListForRight").children().remove();
    var i = 1;
    $("#fileListForRight").append("<tr> <th style='width: 10px'>#</th> <th>文件名称</th> </tr>");
    $.each(row.prFileDOS, function (n, value) {
        var path = "/pr/download.bms?path="+value.filePath+"&name="+value.fileName;
        $("#fileListForRight").append("<tr> <th>"+i+"</th> <th><a href='"+path+"'>"+value.fileName+"</a></th> </tr>");
        i++;
    });
    $("#createDateForRight").text(formatDate(new Date(row.createTime)));
    $("#updateDateForRight").text(formatDate(new Date(row.updateTime)));
    //createTime
    showCommentsTable(row);
    showQuestionOper(row);
}
function showCommentsTable(row) {
    $("#processCommentTable").children().remove();
    if (row.prCommentsDOS.length != 0){
        for (var i = 0; i < row.prCommentsDOS.length; i++) {
            var value = row.prCommentsDOS[i];
            var html = "";
            if (i%2==0) {
                html = '<div class="item" style="padding: 10px;margin: 0px;">';

            } else {
                html = '<div class="item" style="background: #eeeeee;padding: 10px;margin: 0px;">';

            }
            html+= '<img src="'+value.userImage+'" alt="user image">'+
                '   <p class="message">'+
                '   <a href="javascript:void(0)" class="name"> '+
                '   <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> '+formatDate(new Date(value.createTime))+'</small>  ' +
                value.createByName+
                '</a>'+
                value.comment+
                '</p>  '+
                '</div>';
            $("#processCommentTable").append(html);
        }
    } else {
        $("#processCommentTable").append('<div class="col-xs-12 text-center" style="font-size: 20px;color: #a5a5a5;margin-top: 200px;margin-bottom: 200px;">暂无记录</div>');
    }
}
function showQuestionOper(row) {
    $("#processOperLogDiv").children().remove();
    for (var i = 0; i < row.prProcessDOS.length; i++) {
        var value = row.prProcessDOS[i];
        var html = "";
        if (i%2==0) {
            html = '<div class="item" style="padding: 10px;margin: 0px;">';

        } else {
            html = '<div class="item" style="background: #eeeeee;padding: 10px;margin: 0px;">';

        }
        html+= '<img src="'+value.userImage+'" alt="user image">'+
            '   <p class="message">'+
            '   <a href="javascript:void(0)" class="name"> '+
            '   <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> '+(value.operTime==null?'':formatDate(new Date(value.operTime)))+'</small>  ' +
            value.agentName+
            '</a>'+
            value.processName+
            ' <i class="glyphicon glyphicon-arrow-right"></i> ';
        if (value.status == "Y") {
            html += '<small class="label label-success">完成</small> ';
            if (value.passOrRefuse == "Y") {
                html += ' <small class="label label-success">通过</small> ';
            } else {
                html += ' <small class="label label-danger">拒绝</small> '+value.refuseReason;
            }
        } else {
            html += '<small class="label label-warning">未完成</small> ';
        }
                
            ''+(value.status=="Y"?"完成":"未完成")+'</p>  '+
            '</div>';
        $("#processOperLogDiv").append(html);
    }
}

function initMySelect() {
    // 获取产品列表
    $.post("/product/getAllProduct.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
        initmyselect($("#productName"),data);
    },"json");
}
/**
 * 搜索-加载表格
 */
function loadTable() {
    var prLevel = $("#prLevel").attr("data-value");
    var prStatus = $("#prStatus").attr("data-value");
    var productId = $("#productName").attr("data-value");
    var versionId = $("#versionName").attr("data-value");
    var text = $("#search_text").val();

    var param = {level:prLevel,status:prStatus,productId:productId,versionId:versionId,processName:text};
    $('#processTable').bootstrapTable('refresh',{query:param});
}
$(function () {
    var viewTitle = $("#viewTitle").attr("flag");
    if (viewTitle == "1") {
        $("#btnPass").hide();
        $("#btnJujue").hide();
    } else if (viewTitle == "2"){
        $("#btnEditForRightWindow").hide();
    } else {
        $("#btnPass").hide();
        $("#btnJujue").hide();
        $("#btnEditForRightWindow").hide();
    }
    initSelect2();
    initMySelect();
    $("#productName").next("ul").on("click",".myliitem",function(){
        var productId = $(this).children("a").attr("data-value");
        $.post("/testCase/getAllProductVersion.bms",{projectId:localStorage.getItem("stotage_project_id"),productId:productId},function (data) {
            initmyselect($("#versionName"),data);
        },"json");
        echoPlease($("#versionName"));
    });
    $("#search_btn").click(function () {
        loadTable();
    });
    $(".productSelect").change(function () {
        changeLoadVersionList($(this).val());
    });

    $(document).off("click",".remove_file");
    $(document).on("click",".remove_file",function(){
        var value = $(this).attr("value");
        console.log(value)
        // 新添加的文件的移除
        if (value != "") {
            console.log("value=" + value)
            filePaths[value] = ""
            console.log(filePaths)
        } else {
            // 查询出来的文件信息的移除  
            var fileId = $(this).attr("fileid");
            console.log("fileId:"+fileId)
            removeFile += fileId+",";
        }
    });

    // 打开评论窗口
    $("#btnPinglun").click(function () {
        $("#pinglunWindow").modal('show');
    });

    // 保存评论
    $("#savePinglun").click(function () {
        var prId = $("#prIdForRight").val();
        var context = $("#pingLunContext").val();
        if (context == "") {
            toastrwarning("请输入评论内容");
            return;
        }
        $.post("/pr/saveComment.bms",{prId:prId,comment:context},function (data) {
            if (data.code == "0000") {
                toastrsuccess(data.msg);
                $("#pingLunContext").val("");
                reloadRightInfo(prId);
            } else {
                toastrerror(data.msg);
            }
            $("#pinglunWindow").modal('hide');
        },"json");
    });
    
});
function reloadRightInfo(id) {
    $.post("/pr/getPublishInfo.bms", {id: id}, function (data) {
        prInfo = data;
        setRightWindowValue(data);
    }, "json");
}

