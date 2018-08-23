<section class="content-header my-content-header" style="background: #F4F4F4;">
    <h1>
        <span class="my-view-title">系统管理</span>
        <small>系统日志</small>
    </h1>
</section>
<section class="content">
        <div class="box-header with-border">
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <select id="zhipaiWindowUser" class="form-control select2" style="width: 200px;">
                        <option selected="selected" value="">选择用户</option>
                        <#list (users)! as u>
                            <option value="${u.id}">${u.name}</option>
                        </#list>
                    </select> &nbsp;
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <button type="button" class="btn btn-default pull-right" id="daterange-btn">
                            <span><i class="fa fa-calendar"></i> 日期范围</span>
                            <i class="fa fa-caret-down"></i>
                        </button>
                    </div>
                </div>
                <button type="button" id="search_btn" class="btn btn-default btn-flat"><i
                        class="glyphicon glyphicon-search"></i> 查询
                </button>
            </form>
        </div>
        <div class="box-body">
            <table id="operainfoTable"></table>
        </div>
</section>
<script type="text/javascript" src="/custom/js/base.js"></script>
<script>
    $(function () {
        $(".select2").select2();
        $('#daterange-btn').daterangepicker({
                ranges: {
                    '今天': [moment(), moment()],
                    '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    '一周': [moment().subtract(6, 'days'), moment()],
                    '一月': [moment().subtract(29, 'days'), moment()],
                    '本月': [moment().startOf('month'), moment().endOf('month')],
                    '上一月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                },
                startDate: moment().subtract(29, 'days'),
                endDate: moment()
            },
            function (start, end) {
                $('#daterange-btn span').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
            }
        );
        $('#operainfoTable').bootstrapTable({
            url: '/sys/pageOperas',
            singleSelect: true,//只能单选
            clickToSelect: true,
            paginationDetailHAlign:'right',
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            height:500,
            pagination: true,     //是否显示分页（*）
            sortable: false,      //是否启用排序
            sortOrder: "asc",     //排序方式
            pageNumber: 1,      //初始化加载第一页，默认第一页
            pageSize: 10,
            escape:true,
            // striped:true,
            queryParams:function(params) {
                var date = $('#daterange-btn span').text();
                var startDate = '';
                var endDate = '';
                if (date.trim() != "日期范围") {
                    var dates = date.split(" - ");
                    startDate = dates[0];
                    endDate = dates[1];
                }
                var userId = $("#zhipaiWindowUser").select2("data")[0].id;
                var param = {endDate: endDate, startDate: startDate,value:userId, pageSize: params.pageSize, pageNumber: params.pageNumber};
                return param;
            },
            detailView:true,
            detailFormatter:function(index, row) {
                return '<div class="callout callout-success"><h4>参数</h4><p>'+row.params+'</p></div>'+
                        '<div class="callout callout-info"><h4>返回值</h4><p>'+row.result+'</p></div>';
            },
            contentType: "application/json;charset=UTF-8",
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
            //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            columns: [
                {field:'path',title:'路径',align:'left'},
                {field:'logName',title:'名称',align:'left'},
                {field:'logType',title:'类型',align:'left',
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return '<span class="badge bg-red">操作</span>';
                        } else if (value == 2) {
                            return '<span class="badge bg-blue">页面</span>';
                        } else if (value == 3) {
                            return '<span class="badge bg-green">登录</span>';
                        } else {
                            return '<span class="badge bg-yellow">其他</span>';
                        }
                    }
                },
                {field:'userName',title:'用户',align:'left'},
                {field:'ip',title:'用户IP',align:'left'},
                {field:'status',title:'状态',align:'left',
                    formatter: function (value, row, index) {
                        if (value == "S") {
                            return '<span class="badge bg-green">成功</span>';
                        } else if (value == "F") {
                            return '<span class="badge bg-red">失败</span>';
                        } else if (value == "E") {
                            return '<span class="badge bg-yellow">异常</span>';
                        }
                    }
                },
                {
                    field: 'logTime', title: '操作时间', align: 'left',
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }
                },
                {field:'timeConsuming',title:'耗时',align:'left'}]
        });

        $("#search_btn").click(function () {
            search();
        });

        /**
         * 带条件查询
         */
        function search() {
            var date = $('#daterange-btn span').text();
            var startDate = '';
            var endDate = '';
            if (date.trim() != "日期范围") {
                var dates = date.split(" - ");
                startDate = dates[0];
                endDate = dates[1];
            } 
            var userId = $("#zhipaiWindowUser").select2("data")[0].id;
            var param = {endDate: endDate, startDate: startDate, value: userId};
            $('#operainfoTable').bootstrapTable('refresh', {query: param});
        }

    });
    
</script>
