<section class="content-header my-content-header" style="background: #F4F4F4;">
    <h1>
        <span class="my-view-title">系统管理</span>
        <small>产品管理</small>
        <span class="pull-right">           
            <a class="btn btn-success" style="margin-top: -6px" href="javascript:void(0)" role="button" id="openAdd"><i class="glyphicon glyphicon-plus"></i> 添加产品</a>  
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
        <table id="productTable"></table>
    </div>
</section>
<script type="text/javascript" src="/custom/js/base.js"></script>
<script>
    function deleteFun(row) {
        myConfirm("系统提示", "是否删除产品" + row.productName, function () {
            $.post("/product/del", "id=" + row.id, function (data) {
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
        loadChildView("/product/updateView?id="+row.id)
    }
    /**
     * 带条件查询
     */
    function search() {
        var text = $("#search_text").val();
        var param = {productName: text};
        $('#productTable').bootstrapTable('refresh', {query: param});
    }

    $(function () {
        $('#productTable').bootstrapTable({
            url: '/product/page',
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
                var param = {productName:text,pageSize: params.pageSize, pageNumber: params.pageNumber};
                return param;
            },
            columns: [[
                {field: 'id', title: '产品编号', width: 75, align: 'left'},
                {field: 'productName', title: '产品名称', align: 'left'},
                {field: 'productDesc', title: '产品描述', align: 'left'},
                {field: 'createBy', title: '创建人', align: 'left'},
                {field: 'createTime', title: '创建时间', align: 'left',
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }
                },
                {field: 'updateBy', title: '修改人', align: 'left'},
                {field: 'updateTime', title: '修改时间', align: 'left',
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }
                },
                {field: 'name', title: '操作', align: 'center', formatter: function (value, row, index) {
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
            loadChildView("/product/addView")
        });
    });

</script>
