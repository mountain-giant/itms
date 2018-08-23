/**
 * Created by Lister on 16/11/12.
 */
var isOpen = false;

/**
 * 随机产生颜色
 * @returns {string}
 */
function randomColor() {
    return '#' +
        (function (color) {
            return (color += '0123456789abcdef'[Math.floor(Math.random() * 16)])
            && (color.length == 6) ? color : arguments.callee(color);
        })('');
}

/**
 * 获取系统当前时间
 * @returns {string}
 */
function getTime(/** timestamp=0 **/) {
    var ts = arguments[0] || 0;
    var t, y, m, d, h, i, s;
    t = ts ? new Date(ts * 1000) : new Date();
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) + ' ' + (h < 10 ? '0' + h : h) + ':' + (i < 10 ? '0' + i : i) + ':' + (s < 10 ? '0' + s : s);
}

function formatDate(now) {
    var date = new Date(now);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
}

var clickYesMethod;
var clickNoMethod;

/**
 * 自定义Confirm
 * @param title 标题
 * @param message 内容
 * @param clickYes 点击确定的回调方法
 */
function myConfirm(title, message, clickYes) {
    $("#prompt_title").text(title);
    $("#prompt_body").text(message);
    $("#prompt_modal").modal('show');
    clickYesMethod = clickYes;
}

/**
 * 自定义Confirm
 * @param title 标题
 * @param message 内容
 * @param clickYes 点击确定的回调方法
 * @param clickNo  点击取消按钮的回调方法
 */
function myConfirm(title, message, clickYes, clickNo) {
    $("#prompt_title").text(title);
    $("#prompt_body").text(message);
    $("#prompt_modal").modal('show');
    clickYesMethod = clickYes;
    clickNoMethod = clickNo;
}

/**
 * 点击确定按钮回调方法
 */
function yes() {
    clickYesMethod();
}

/**
 * 点击取消按钮回调方法
 */
function no() {
    clickNoMethod();
}

/**
 * EASYUI COM 初始化
 */
$('.EasyUICOM').combobox({});

/**
 * 多选下拉框初始化
 */
$('.EasyUICOMBoMu').combo({
    required: true,
    multiple: true
});

/**
 * 自定义单选下拉框组件
 */
$(document).on("click", ".myliitem", function () {
    $(this).parent().prev("a").html($(this).parent().prev("a").attr("showTextHead") + "：" + $(this).text() + " <span class='caret'></span>");
    $(this).parent().prev("a").attr("data-value", $(this).children("a").attr("data-value"));
});

function initmyselect($this, list) {
    $this.next("ul").children().remove();
    $this.next("ul").append("<li class='myliitem'><a href='javascript:void(0)' data-value=''>请选择</a></li>");
    for (var i = 0; i < list.length; i++) {
        var data = list[i];
        $this.next("ul").append("<li class='myliitem'><a href='javascript:void(0)' data-value=" + data.value + ">" + data.text + "</a></li>");
    }
}

/**
 * 将下拉框回归为请选择
 * @param $this
 */
function echoPlease($this) {
    $this.html($this.attr("showTextHead") + "：请选择 <span class='caret'></span>");
    $this.attr($this.attr("data-value", ''));
}

$("#closeRightWindow").click(function () {
    $(".right_window").animate({width: "0px"});
    $(".right_window").hide(500);
});

$(".select2").select2();

//iCheck for checkbox and radio inputs
$('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
    checkboxClass: 'icheckbox_minimal-blue',
    radioClass: 'iradio_minimal-blue'
});

$(document).off("click", ".fr-fil,.fr-fin");
$(document).on("click", ".fr-fil,.fr-fin", function () {
    var src = $(this).attr("src");
    if (src == null || src == "") {
        return false;
    }
    $("#showImageInfo").attr("src", src);
    $("#showImageBaseWindow").modal("show");
});

/**
 * 为table指定行添加一行
 *
 * tab 表id
 * row 行数，如：0->第一行 1->第二行 -2->倒数第二行 -1->最后一行
 * trHtml 添加行的html代码
 *
 */
function addTr(tab, row, trHtml) {
    //获取table最后一行 $("#tab tr:last")
    //获取table第一行 $("#tab tr").eq(0)
    //获取table倒数第二行 $("#tab tr").eq(-2)
    var $tr = $("#" + tab + " tr").eq(row);
    if ($tr.size() == 0) {
        alert("指定的table id或行数不存在！");
        return;
    }
    $tr.after(trHtml);
}

function delTr(ckb) {
    //获取选中的复选框，然后循环遍历删除
    var ckbs = $("input[name=" + ckb + "]:checked");
    if (ckbs.size() == 0) {
        alert("要删除指定行，需选中要删除的行！");
        return;
    }
    ckbs.each(function () {
        $(this).parent().parent().remove();
    });
}

$(".closeRightWindowDiv").click(function () {
    closeRightWindow();
});

function closeRightWindow() {
    if (isOpen) {
        $(".right_window").animate({width: "0px"}, 0);
        $(".right_window").hide(0);
    }
    isOpen = false;
}


var $leftselect;
var leftselectHtml;
$(document).off("click", ".leftli");
$(document).on("click", ".leftli", function () {
    $(".myselectli").css("background-color", "#FFFFFF");
    $(".myselectli").css("color", "#000000");
    $(this).css("background-color", "#0073b7");
    $(this).css("color", "#FFFFFF");
    $leftselect = $(this);
    leftselectHtml = "<li value='" + $(this).attr("value") + "' class='myselectli rightli'>" + $(this).text() + "</li>";
});

var $rightselect;
var rightselectHtml;
$(document).off("click", ".rightli");
$(document).on("click", ".rightli", function () {
    $(".myselectli").css("background-color", "#FFFFFF");
    $(".myselectli").css("color", "#000000");
    $(this).css("background-color", "#0073b7");
    $(this).css("color", "#FFFFFF");
    $rightselect = $(this);
    rightselectHtml = "<li value='" + $(this).attr("value") + "' class='myselectli leftli'>" + $(this).text() + "</li>";
});

$("#onright").click(function () {
    if (leftselectHtml == "") {
        toastrwarning("请选择一行");
        return;
    }
    $("#rightData").append(leftselectHtml);
    $leftselect.remove();
    leftselectHtml = "";
});

$("#onleft").click(function () {
    if (rightselectHtml == "") {
        toastrwarning("请选择一行");
        return;
    }
    $("#leftData").append(rightselectHtml);
    $rightselect.remove();
    rightselectHtml = "";
});

$("#onrightall").click(function () {
    var testCases = "";
    $('#leftData').find('li').each(function () {
        testCases += "<li value='" + $(this).attr("value") + "' class='myselectli rightli'>" + $(this).text() + "</li>";
    });
    $("#rightData").append(testCases);
    $("#leftData").children().remove();
    leftselectHtml = "";
});

$("#onleftall").click(function () {
    var testCases = "";
    $('#rightData').find('li').each(function () {
        testCases += "<li value='" + $(this).attr("value") + "' class='myselectli leftli'>" + $(this).text() + "</li>";
    });
    $("#leftData").append(testCases);
    $("#rightData").children().remove();
    rightselectHtml = "";
});


function loadChildView(href) {
    // 过滤掉javascript:void(0)地址
    if (href.trim() == "javascript:void(0)" || href.trim() == "javascript:void(0);") {
        return;
    }
    $("#child_page_panel").css("min-height",(document.body.clientHeight-48)+"px");
    history.pushState({}, '', href);
    $("#child_page_panel").children().remove();
    $("#child_page_panel").append('<br><div class="col-xs-12 text-center"><i class="fa fa-spin fa-refresh"></i>&nbsp; 页面加载中...</div>');
    $.post({
        url: href, success: function (page) {
            $("#child_page_panel").children().remove();
            $("#child_page_panel").append(page);
        }, error: function (xhr, status, error) {
            $.post("/sys/404.bms", "", function (page) {
                $("#child_page_panel").children().remove();
                $("#child_page_panel").append(page);
            });
        }
    });
}
