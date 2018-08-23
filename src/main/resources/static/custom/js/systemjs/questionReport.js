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
function initSelect2() {
    $.post("/product/list.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
        var datas = [];
        var i = 0;
        $.each(data.data, function (n, value) {
            console.log(n+","+value)
            datas[i] ={ id: value.id, text: value.prodcutName }
            i++;
        });
        $("#productSelect").select2({
            data: datas,
            placeholder:'请选择',
        });
        changeLoadVersionList(data.data[0].id);
    },"json");
    $.post("/requirement/list.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
        var datas = [];
        var i = 0;
        $.each(data, function (n, value) {
            console.log(n+","+value)
            datas[i] ={ id: value.id, text: value.requirementTitle }
            i++;
        });
        $("#requirementSelect").select2({
            data: datas,
            placeholder:'请选择',
        });
    },"json");
}
function changeLoadVersionList(productId) {
    $.post("/productVersion/list.bms",{productId:productId},function (data) {

        var datas = [];
        $("#productVersionSelect").empty();
        var i = 0;
        $.each(data.data, function (n, value) {
            console.log(n + "," + value)
            datas[i] = {id: value.id, text: value.versionName}
            i++;
        });
        $("#productVersionSelect").select2({
            data: datas,
            placeholder: '请选择',
        });
    },"json");
}
$(function () {
    $(".select2").select2();
    initSelect2();
    $("#productSelect").change(function () {
        changeLoadVersionList($(this).val());
    });
    $("#buildReport").click(function () {
        var p = $("#productSelect").select2("data")[0];
        var v = $("#productVersionSelect").select2("data")[0];
        var s = $("#questionStage").select2("data")[0];
        location.href = "/useCase/generalReport.bms?projectId="+localStorage.getItem("stotage_project_id")+"&productId="+p.id+"&productName="+p.text+"&versionId="+v.id+"&versionName="+v.text+"&reportStage="+s.id;
    });
    $.post("/testCase/getAllProduct.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
        initmyselect($("#productName"),data);
    },"json");
    // 打开测试报告窗口
    $("#testReport").click(function () {
        $("#testReportWindow").modal("show");
    });
    $("#productName").next("ul").on("click",".myliitem",function(){
        var productId = $(this).children("a").attr("data-value");
        $.post("/testCase/getAllProductVersion.bms",{projectId:localStorage.getItem("stotage_project_id"),productId:productId},function (data) {
            initmyselect($("#versionName"),data);
        },"json");
        echoPlease($("#versionName"));
    });
    $(document).on("click",".myliitem",function(){
        if ($("#productName").attr("data-value") == "") {
            $("#versionName").html($("#versionName").attr("showTextHead")+"：请选择 <span class='caret'></span>");
            $("#versionName").attr("data-value","");
        }
        var versionId = $("#versionName").attr("data-value");
        if (versionId == "") {
            $("#fugaiReport").show();
            $("#addAndCloseReportLine").hide();
            return;
        }
        $("#fugaiReport").hide();
        $("#addAndCloseReportLine").show();
        $.post("/question/reportQuestionAddAndClose.bms",{versionId:versionId},function (data) {
            generateAddAndCloseReport(data.data);
        },"json");
    });
    generateQuestionZhanbiReport("status");
    generateLevelZhanbiReport();
});

function generateAddAndCloseReport(data) {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('addAndCloseReportLine'), 'shine');
    var option = {
        color: ['#38b4ee', '#4caf50'],
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show: true,
            right: '4%',
            top: '2%',
            feature: {
                saveAsImage: {
                    show: true
                }
            }
        },
        title: {
            text: '缺陷收敛图',
            textStyle: {
                fontWeight: 'normal',
                fontSize: 25,
                color: '#848484'
            },
            left: '2%',
            top: '2%'
        },
        legend: {
            padding: [10, 20, 0, 20],
            data: [{
                name: '新增',
                icon: 'circle'
            }, {
                name: '关闭',
                icon: 'circle'
            }],
            right: '8%',
            top: '2%',
            selected: {
                '新增': true,
                '关闭': true
            },
            textStyle: {
                color: '#848484'
            }
        },
        grid: {
            left: '2%',
            right: '3%',
            bottom: '3%',
            top: '13%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#C6C6C6'
                }
            },
            splitLine: { //网格线
                show: true,
                lineStyle: {
                    type: 'solid'
                }
            },
            data: data.dates
        },
        yAxis: {
            axisTick: {
                show: false
            },
            axisLine: {
                show: false,
                //    onZero:false
            },
            axisLabel: {
                textStyle: {
                    color: '#C6C6C6'
                }
            },
            splitLine: { //网格线
                show: true,
                lineStyle: {
                    type: 'solid'
                }
            }
        },
        series: [{
            name: '新增',
            type: 'line',
            smooth: true,
            symbolSize: 12,
            data: data.add,
            label: {
                normal: {
                    show: false,
                    position: 'top' //值显示
                }
            }
        }, {
            name: '关闭',
            type: 'line',
            smooth: true,
            symbolSize: 12,
            data: data.close,
            label: {
                normal: {
                    show: false,
                    position: 'top'
                }
            }
        }]
    };
    myChart.setOption(option);
}

var questionZhanbiReportChart = echarts.init(document.getElementById('questionZhanbiReport'), 'shine');
function generateQuestionZhanbiReport(dimensions) {
    if (dimensions == 'product' || dimensions == 'type' || dimensions == 'stage') {
        $.post("/question/reportQuestionOnDimensions.bms",{projectId:localStorage.getItem("stotage_project_id"),dimensions:dimensions},function (data) {
            var obj = data.data;
            var xData = obj.xData;
            var yData = obj.yData;
            generateReportsBasedOnDimensions(xData,yData);
        },"json");
    } else if (dimensions == 'status') {
        $.post("/project/getProjectOverview.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
            var obj = data.data;
            var xData = [];
            var yData = [];
            var i = 0;
            if (obj.unsolvedQuestion != 0) {
                xData[i] = "未解决";
                yData[i] = {value:obj.unsolvedQuestion, name:'未解决'};
                i++;
            }
            if (obj.uncheckQuestion != 0) {
                xData[i] = "待验证";
                yData[i] = {value:obj.uncheckQuestion, name:'待验证'};
                i++;
            }
            if (obj.rejectQuestion != 0) {
                xData[i] = "已拒绝";
                yData[i] = {value:obj.rejectQuestion, name:'已拒绝'};
                i++;
            }
            if (obj.sovledQuestion != 0) {
                xData[i] = "已解决";
                yData[i] = {value:obj.sovledQuestion, name:'已解决'};
                i++;
            }
            if (obj.delayedQuestion != 0) {
                xData[i] = "已挂起";
                yData[i] = {value:obj.delayedQuestion, name:'已挂起'};
                i++;
            }
            if (obj.closedQuestion != 0) {
                xData[i] = "已关闭";
                yData[i] = {value:obj.closedQuestion, name:'已关闭'};
                i++;
            }
            generateReportsBasedOnDimensions(xData,yData);
        },"json");
    }
}
function generateReportsBasedOnDimensions(xData,yData) {
    var option = {
        title: {
            text: '缺陷占比',
            x:'center',
            textStyle: {
                fontWeight: 'normal',
                fontSize: 25,
                color: '#848484'
            },
        },
        toolbox: {
            show: true,
            right: '4%',
            top: '2%',
            feature: {
                saveAsImage: {
                    show: true
                }
            }
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: xData
        },
        series : [
            {
                name: '数量占比',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:yData,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    questionZhanbiReportChart.setOption(option);
}

function generateLevelZhanbiReport() {
    var myChart = echarts.init(document.getElementById('levelZhanbiReport'), 'infographic');
    $.post("/project/getProjectOverview.bms",{projectId:localStorage.getItem("stotage_project_id")},function (data) {
        var obj = data.data;
        var option = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            toolbox: {
                show: true,
                right: '4%',
                top: '2%',
                feature: {
                    saveAsImage: {
                        show: true
                    }
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                top:'14%',
                containLabel: true
            },
            title: {
                text: '各优先级缺陷数量',
                textStyle: {
                    fontWeight: 'normal',
                    fontSize: 25,
                    color: '#848484'
                },
                left: '2%',
                top: '2%'
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['L1', 'L2', 'L3', 'L4'],
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'缺陷数量',
                    type:'bar',
                    barWidth: '60%',
                    data:[obj.levelL1, obj.levelL2, obj.levelL3, obj.levelL4]
                }
            ]
        };

        myChart.setOption(option);
    },"json");
}
