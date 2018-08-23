<section class="content-header my-content-header" style="background: #F4F4F4;">
    <h1>
        <span class="my-view-title">系统管理</span>
        <small>用户管理</small>
        <span class="pull-right">           
            <a class="btn btn-success" style="margin-top: -6px" href="javascript:void(0)" role="button" id="openAdd"><i class="glyphicon glyphicon-plus"></i> 添加用户</a>  
        </span>
    </h1>
</section>
<section class="content">
        <div class="box-header with-border">
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" id="search_text" class="form-control" placeholder="关键词">
                </div>
                <button type="button" id="search_btn" class="btn btn-default btn-flat"><i class="glyphicon glyphicon-search"></i> 查询</button>
            </form>
        </div>
        <div class="box-body">
            <table id="userinfoTable"></table>    
        </div>
        <blockquote>
            <small>
                <i class="fa fa-bar-chart"></i> 用户活动频率
                <a class="btn-sm btn-success pull-right" href="javascript:void(0)" role="button" onclick="refreshData();"><i class="glyphicon glyphicon-repeat"></i> 刷新</a>
            </small>
        </blockquote>

        <div class="box-body">
            <div id="activityReport" style="width: 100%;height:400px;"></div>
        </div>
        <div class="box-footer">
            <span style="font-size: 12px;">数据来源于系统操作记录和系统登录记录</span>
            <ul class="pagination pagination-sm no-margin pull-right">
                <li><a href="javascript:void(0)" onclick="daysCountReport(5)">5</a></li>
                <li><a href="javascript:void(0)" onclick="daysCountReport(10)">10</a></li>
                <li><a href="javascript:void(0)" onclick="daysCountReport(20)">20</a></li>
                <li><a href="javascript:void(0)" onclick="daysCountReport(30)">30</a></li>
            </ul>
        </div>
</section>
<script src="/adminlte/plugins/echarts/js/echarts.min.js"></script>
<script src="/adminlte/plugins/echarts/theme/shine.js"></script>
<script src="/adminlte/plugins/echarts/theme/infographic.js"></script>
<script src="/adminlte/plugins/echarts/theme/macarons.js"></script>
<script src="/adminlte/plugins/echarts/theme/roma.js"></script>
<script src="/adminlte/plugins/echarts/theme/vintage.js"></script>
<script src="/adminlte/plugins/echarts/theme/dark.js"></script>
<script type="text/javascript" src="/custom/js/base.js"></script>
<script>
    function deleteFun(row) {
        myConfirm("系统提示","是否删除用户"+row.name,function(){
            $.post("/user/del","id="+row.id,function (data) {
                if (data.code=="0000"){
                    search();
                    generateReport();
                    toastrsuccess('删除用户成功...');
                }else {
                    toastrerror(data.msg);
                }
            },"json");
            $("#prompt_modal").modal('hide');
        });
    }
    function updateFun(row) {
        loadChildView("/user/updateView?id="+row.id)
    }
    /**
     * 带条件查询
     */
    function search(){
        var text = $("#search_text").val();
        var param = {queryKeyword:text};
        $('#userinfoTable').bootstrapTable('refresh',{query:param});
    }
    // 用户活动频率报表默认查询天数
    var days = 5;
    $(function () {
        $(".select2").select2();
        $('#userinfoTable').bootstrapTable({
            url: '/user/page',
            singleSelect:true,//只能单选
            clickToSelect:true,
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,     //是否显示分页（*）
            sortable: false,      //是否启用排序
            sortOrder: "asc",     //排序方式
            pageNumber:1,      //初始化加载第一页，默认第一页
            pageSize: 10,
            // striped:true,
            undefinedText:'',
            height:700,
            contentType: "application/x-www-form-urlencoded",
            queryParamsType:'', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
            //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            queryParams:function(params) {
                var text = $("#search_text").val();
                var param = {queryKeyword:text,pageSize: params.pageSize, pageNumber: params.pageNumber};
                return param;
            }, columns: [
                [
                {field:'loginName',title:'登录账号',align:'left'},
                {field:'name',title:'用户名',align:'left'},
                {field:'roleName',title:'系统角色',align:'left'},
                {field:'processRoleName',title:'流程角色',align:'left'},
                {field:'productName',title:'关联产品',align:'left'},
                {field:'email',title:'邮箱',align:'left'},
                {field:'phone',title:'电话',align:'left'},
                {field: 'name', title: '操作', width: 200, align: 'center', formatter: function (value, row, index) {
                    var obj = JSON.stringify(row);
                    var html = '<div class="btn-group"> ';
                    html += "<button type='button' class='btn btn-primary btn-sm myTableOperBtn' onclick='updateFun("+obj+")'>编辑</button>";
                    html += "<button type='button' class='btn btn-danger btn-sm myTableOperBtn' onclick='deleteFun("+obj+")'>删除</button>";
                    html += '</div>';
                    return html;
                }}
            ]
            ]
        });
        
        $("#search_btn").click(function(){
            search();
        });

        $("#openAdd").click(function () {
            loadChildView("/user/addView")
        });

        // 获取用户活动频率
        generateReport();
    });
    function refreshData() {
        generateReport();   
    }
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('activityReport'),'shine');
    function generateReport() {
        $.post("/report/actityReport",{days:days},function (data) {
            var datasets = [];
            var names = [];
            var i = 0;
            $.each(data.datas, function (n, value) {
                names[i] = n;
                datasets[i] ={
                    name:n,
                    type:'bar',
                    data:value
                }
                i++;
            });
            // 指定图表的配置项和数据
            var option = {
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:names
                },
                xAxis : [
                    {
                        type : 'category',
                        data : data.dates
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : datasets
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            toastrinfo('用户活动频率加载完成...');
        },"json");
    }

    function daysCountReport(day) {
        days = day;
        generateReport();
    }
</script>
