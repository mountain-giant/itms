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
// 文件上传
var $btn =$("#ctlBtn");
var uploader;
var isLoadUploadPlugin = false;
//在点击弹出模态框的时候再初始化WebUploader，解决点击上传无反应问题
$("#fileUploadWindow").on("shown.bs.modal",function() {
    if (isLoadUploadPlugin == false) {
        uploader = WebUploader.create({

            // swf文件路径
            swf: '/static/adminlte/plugins/baiduwebuploader/Uploader.swf',

            // 文件接收服务端。
            server: '/knowledge/upload.bms',

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
        uploader.on( 'uploadSuccess', function( file ) {
            $( '#'+file.id ).find('p.state').text('已上传');
            $("#"+file.id).remove();
            if ($('#thelist').html()=="") {
                toastrsuccess("文件上传成功");
                $btn.removeAttr("disabled");
                $("#closeUploadWindow").removeAttr("disabled");
                getTree();
            }
        });

        uploader.on( 'uploadError', function( file ) {
            $( '#'+file.id ).find('p.state').text('上传出错，请稍后重试');
            toastrsuccess("上传出错，请稍后重试");
            $btn.removeAttr("disabled");
            $("#closeUploadWindow").removeAttr("disabled");
        });

        uploader.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').fadeOut();
        });
        isLoadUploadPlugin = true;
    }
});

function setDirInfo(id) {
    $.post("/knowledge/dir/get.bms",{id:id},function (data) {
        var obj = data.data;
        $("#dirId").val(obj.id);
        $("#dirName").text(obj.dirctoryName);
        $("#createByForDir").text(localStorage.getItem("userid-"+obj.createBy));
        $("#updateByForDir").text(localStorage.getItem("userid-"+obj.updateBy));
        $("#createTimeForDir").text(formatDate(new Date(obj.createTime)));
        $("#updateTimeForDir").text(formatDate(new Date(obj.updateTime)));
    },"json");
}
function setFileInfo(id) {
    $("#fileinfocontext").children().remove();
    $.post("/knowledge/file/get.bms",{id:id},function (data) {
        var obj = data.data;
        $("#fileId").val(obj.id);
        $("#fileName").text(obj.fileName);
        $("#fileType").text(obj.fileType);
        $("#fileSize").text(obj.fileSize);
        $("#dowloadFile").attr("path",obj.filePath);
        $("#createByForFile").text(localStorage.getItem("userid-"+obj.createBy));
        $("#updateByForFile").text(localStorage.getItem("userid-"+obj.updateBy));
        $("#createTimeForFile").text(formatDate(new Date(obj.createTime)));
        $("#updateTimeForFile").text(formatDate(new Date(obj.updateTime)));
        if (obj.fileType == "png" || obj.fileType == "jpg" || obj.fileType == "gif" || obj.fileType == "jpeg") {
            $("#fileinfocontext").append("<img class='fr-fil' style='width: 100%' src='/knowledge/getFile.bms?fileId="+obj.id+"'/>");
        } else {
            $("#fileinfocontext").append('<div class="text-center" style="font-size: 20px;color: #a5a5a5;height: 100px;width: 100%;margin-top: 200px;margin-bottom: 200px;">该文件无预览...</div>');
        }
    },"json");
}
$(function () {
    $btn.on( 'click', function() {
        $(this).attr({"disabled":"disabled"});
        $("#closeUploadWindow").attr({"disabled":"disavled"});
        var obj = new Object();
        obj.dirId = $("#dirId").val();
        uploader.options.formData = obj;
        uploader.upload();
    });
    getTree();
    // 文件上传
    $("#uploadFile").click(function () {
        $("#fileUploadWindow").modal({backdrop: 'static', keyboard: false});

    });
    // 添加根目录
    $("#openAddBaseDirWindow").click(function () {
        $("#addDirWindow").modal("show");
        $("#dirParentIdFowWindow").val("0");
        $("#dirwindowTitle").text("添加根目录");
    });
    // 添加子目录
    $("#addChildDir").click(function () {
        $("#addDirWindow").modal("show");
        $("#dirParentIdFowWindow").val($("#dirId").val());
        $("#dirwindowTitle").text("添加子目录");
    });
    // 编辑文件夹
    $("#editDirNameBtn").click(function () {
        $("#addDirWindow").modal("show");
        $("#dirNameForWindow").val($("#dirName").text());
        $("#dirwindowTitle").text("编辑目录");
    });
    // 编辑文件   
    $("#editFileNameBtn").click(function () {
        $("#editFileWindow").modal("show");
        $("#fileNameForWindow").val($("#fileName").text());
    });
    // 删除文件夹
    $("#deleteDir").click(function () {
        myConfirm("系统提示","是否删除目录"+$("#dirName").text(),function(){
            $.post("/knowledge/dir/delete.bms",{id:$("#dirId").val()},function (data) {
                if (data.code == "0000") {
                    toastrsuccess("删除成功");
                    getTree();
                    $("#tree_div").removeClass("col-sm-6").addClass("col-sm-12");
                    $("#dir_div").hide();
                    $("#file_div").hide();
                } else {
                    toastrerror(data.msg);
                }
            },"json");
            $("#prompt_modal").modal('hide');
        });

    });
    // 删除文件
    $("#deleteFile").click(function () {
        myConfirm("系统提示","是否删除文件"+$("#fileName").text(),function(){
            $.post("/knowledge/file/delete.bms",{id:$("#fileId").val()},function (data) {
                if (data.code == "0000") {
                    toastrsuccess("删除成功");
                    getTree();
                    $("#tree_div").removeClass("col-sm-4").addClass("col-sm-12");
                    $("#dir_div").hide();
                    $("#file_div").hide();
                } else {
                    toastrerror(data.msg);
                }
            },"json");
            $("#prompt_modal").modal('hide');
        });

    });
    // 保存目录
    $("#saveDir").click(function () {
        var parentId = $("#dirParentIdFowWindow").val();
        var dirId = $("#dirId").val();
        var dirName = $("#dirNameForWindow").val();
        var windowTitle = $("#dirwindowTitle").text();
        console.log(dirId)
        if(windowTitle == "添加根目录" || windowTitle == "添加子目录") {
            $.post("/knowledge/dir/create.bms",{parentId:parentId,dirctoryName:dirName},function (data) {
                if (data.code == "0000") {
                    toastrsuccess("添加成功");
                    getTree();
                    $("#dirNameForWindow").val("");
                    $("#addDirWindow").modal("hide");
                } else {
                    toastrerror(data.msg);
                }
            },"json");
        } else {
            $.post("/knowledge/dir/update.bms",{id:dirId,dirctoryName:dirName},function (data) {
                if (data.code == "0000") {
                    toastrsuccess("编辑成功");
                    getTree();
                    $("#dirName").text(dirName);
                    $("#dirNameForWindow").val("");
                    $("#addDirWindow").modal("hide");
                } else {
                    toastrerror(data.msg);
                }
            },"json");
        }
    });
    // 保存文件名
    $("#saveFile").click(function () {
        var fileId = $("#fileId").val();
        var fileName = $("#fileNameForWindow").val();
        $.post("/knowledge/file/update.bms",{id:fileId,fileName:fileName},function (data) {
            if (data.code == "0000") {
                toastrsuccess("保存成功");
                // getTree();
                $("#fileName").text(fileName);
                $("#fileNameForWindow").val("");
                $("#editFileWindow").modal("hide");
            } else {
                toastrerror(data.msg);
            }
        },"json");
    });
    // 下载文件
    $("#dowloadFile").click(function () {
        location.href = "/knowledge/getFile.bms?fileId="+$("#fileId").val();
    });
    
    var search = function(e) {
        var pattern = $('#input-search').val();
        var options = {
            ignoreCase: $('#chk-ignore-case').is(':checked'),
            exactMatch: $('#chk-exact-match').is(':checked'),
            revealResults: $('#chk-reveal-results').is(':checked')
        };
        var results = $("#tree").treeview('search', [ pattern, options ]);

        var output = '<p>' + results.length + ' matches found</p>';
        $.each(results, function (index, result) {
            output += '<p>- ' + result.text + '</p>';
        });
        $('#search-output').html(output);
    }
    $('#input-search').on('keyup', search);
});
function getTree() {
    $('#tree').hide();
    $("#loading").show();
    $.post("/knowledge/dir/tree.bms",{},function (data) {
        $('#tree').treeview({
            color: "#428bca",
            showTags: true,
            showBorder: false,
            data: data.data
        });
        $("#tree").show();
        $("#loading").hide();
    },"json");
}
// 树点击事件    
$(document).off("click",".list-group-item");
$(document).on("click",".list-group-item",function(e){
    var arr = $('#tree').treeview('getSelected');
    var type = arr[0].type;
    if (type == "D") {
        $("#tree_div").removeClass("col-sm-12").addClass("col-sm-6");
        $("#dir_div").show();
        $("#file_div").hide();
        setDirInfo(arr[0].id);
    } else if (type == "F") {
        $("#tree_div").removeClass("col-sm-12").addClass("col-sm-6");
        $("#file_div").show();
        $("#dir_div").hide();
        setFileInfo(arr[0].id);
    }
}); 
