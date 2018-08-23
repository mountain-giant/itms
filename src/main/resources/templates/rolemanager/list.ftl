<section class="content-header my-content-header" style="background: #F4F4F4;">
    <h1>
        <span class="my-view-title">系统管理</span>
        <small>角色管理</small>
        <span class="pull-right">           
            <a class="btn btn-success" style="margin-top: -6px" href="javascript:void(0)" role="button" id="openAdd"><i class="glyphicon glyphicon-plus"></i> 添加角色</a>  
        </span>
    </h1>
</section>
<section class="content">
    <div class="box-header with-border">
        <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
                <input type="text" id="search_text" class="form-control" placeholder="关键词">
            </div>
            <button type="button" id="search_btn" class="btn btn-default btn-flat"><i
                    class="glyphicon glyphicon-search"></i> 查询
            </button>
        </form>
    </div>
    <div class="box-body">
        <table id="roleinfoTable"></table>
    </div>
    <blockquote>
        <small>
            <i class="fa fa-bar-chart"></i> 角色用户数量统计
            <a class="btn-sm btn-success pull-right" href="javascript:void(0)" role="button" onclick="refreshData();"><i class="glyphicon glyphicon-repeat"></i> 刷新</a>
        </small>
    </blockquote>
    <div class="box-body">
        <div id="donutChart" style="width: 100%;height:400px;"></div>
    </div>
</section>
<script type="text/javascript" src="/custom/js/base.js"></script>
<#include "../base/report_js.ftl"/>
<script>
    function deleteFun(row) {
        myConfirm("系统提示", "是否删除角色" + row.roleName, function () {
            $.post("/role/del", "id=" + row.roleId, function (data) {
                if (data.code == "0000") {
                    search();
                    toastrsuccess(data.msg);
                } else {
                    toastrerror(data.msg);
                }

            }, "json");
            $("#prompt_modal").modal('hide');
        });
    }
    function updateFun(row) {
        loadChildView("/role/updateView?roleId="+row.roleId)
    }
    /**
     * 带条件查询
     */
    function search() {
        var text = $("#search_text").val();
        var param = {roleName: text};
        $('#roleinfoTable').bootstrapTable('refresh', {query: param});
    }

    $(function () {
        $('#roleinfoTable').bootstrapTable({
            url: '/role/page',
            singleSelect: true,//只能单选
            clickToSelect: true,
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,     //是否显示分页（*）
            sortable: false,      //是否启用排序
            height:500,
            sortOrder: "asc",     //排序方式
            pageNumber: 1,      //初始化加载第一页，默认第一页
            pageSize: 10,
            // striped:true,
            contentType: "application/x-www-form-urlencoded",
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
            //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            queryParams:function(params) {
                var text = $("#search_text").val();
                var param = {roleName:text,pageSize: params.pageSize, pageNumber: params.pageNumber};
                return param;
            },
            columns: [[
                {
                    field: 'roleId', title: '角色编号', width: 75, align: 'left',
                    formatter: function (value, row, index) {
                        return '<span class="badge bg-blue">' + value + '</span>';
                    }
                },
                {field: 'roleName', title: '角色名称', width: 220, align: 'left'},
                {field: 'name', title: '创建用户', width: 200, align: 'left'},
                {
                    field: 'createDate', title: '创建时间', width: 900, align: 'left',
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }
                },
                {field: 'name', title: '操作', width: 200, align: 'center', formatter: function (value, row, index) {
                    var obj = JSON.stringify(row);
                    var html = '<div class="btn-group"> ';
                    html += "<button type='button' class='btn btn-primary btn-sm myTableOperBtn' onclick='updateFun("+obj+")'>编辑</button>";
                    html += "<button type='button' class='btn btn-danger btn-sm myTableOperBtn' onclick='deleteFun("+obj+")'>删除</button>";
                    html += '</div>';
                    return html;
                }}
            ]]
        });

        $("#search_btn").click(function () {
            search();
        });

        $("#openAdd").click(function () {
            loadChildView("/role/addView")
        });

        generateReport();
        toastrinfo('角色下用户数量统计完成...');
    });

    function refreshData() {
        generateReport();
    }

    function generateReport() {
        $.post("/report/roleUserNumReport", "", function (data) {
            var labels = [];
            var values = [];
            for (var i = 0; i < data.length; i++) {
                labels[i] = data[i]["label"];
                values[i] = {'value': data[i]["value"], 'name': data[i]["label"]};
            }

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('donutChart'), 'shine');
            // 指定图表的配置项和数据
            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                roseType: 'angle',//南丁格尔图
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: labels
                },
                series: [
                    {
                        name: '占有比例',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: values,
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

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }, "json");
    }
</script>
