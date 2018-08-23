<section class="content-header my-content-header" style="background: #F4F4F4;">
    <h1>
        <span class="my-view-title">系统管理</span>
        <small>项目管理</small>
        <span class="pull-right">           
            <a class="btn btn-success" style="margin-top: -6px" href="javascript:void(0)" role="button" id="openAdd"><i class="glyphicon glyphicon-plus"></i> 添加项目</a>  
        </span>
    </h1>
</section>
<section class="content">
    <div class="box-header with-border">
        <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
                <input type="text" id="search_text" class="form-control" placeholder="关键词">
            </div>
            <div class="form-group">
                <select id="productSelect" class="form-control select2" style="width: 200px;">
                    <option selected="selected" value="">选择产品</option>
                    <#list (products)! as product>
                        <option value="${product.id}">${product.productName}</option>
                    </#list>
                </select> &nbsp;
            </div>
            <button type="button" id="search_btn" class="btn btn-default btn-flat"><i class="glyphicon glyphicon-search"></i> 查询
            </button>
        </form>
    </div>
    <div class="box-body">
        <table id="projectTable"></table>
    </div>
</section>
<script type="text/javascript" src="/custom/js/base.js"></script>
<script>
    function updateFun(row) {
        loadChildView("/project/updateView?id="+row.id)
    }
    
    function getInfo(id) {
        loadChildView("/project/info?id="+id);
    }
    
    /**
     * 带条件查询
     */
    function search() {
        var text = $("#search_text").val();
        var productId = $("#productSelect").select2("data")[0].id;
        var param = {roleName: text,productId:productId};
        $('#projectTable').bootstrapTable('refresh', {query: param});
    }

    $(function () {
        $('#projectTable').bootstrapTable({
            url: '/project/page',
            singleSelect: true,//只能单选
            clickToSelect: true,
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,     //是否显示分页（*）
            sortable: false,      //是否启用排序
            height:500,
            // striped:true,     
            undefinedText:'',
            sortOrder: "asc",     //排序方式
            pageNumber: 1,      //初始化加载第一页，默认第一页
            pageSize: 10,
            contentType: "application/x-www-form-urlencoded",
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
            //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            queryParams:function(params) {
                var text = $("#search_text").val();
                var productId = $("#productSelect").select2("data")[0].id;
                var param = {projectName:text,productId:productId,pageSize: params.pageSize, pageNumber: params.pageNumber};
                return param;
            },
            columns: [[
                {field: 'id', title: '项目编号', width: 75, align: 'left'},
                {field: 'projectName', title: '项目名称', align: 'left',
                    formatter: function (value, row, index) {
                        return '<a class="text-blue" href="javascript:void(0)" onclick="getInfo('+row.id+')">'+value+'</a>'
                    }
                },
                {field: 'productName', title: '产品', align: 'left'},
                {field: 'projectDesc', title: '项目描述', align: 'left'},
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
                    html += '</div>';
                    return html;
                }}
            ]]
        });

        $("#search_btn").click(function () {
            search();
        });

        $("#openAdd").click(function () {
            loadChildView("/project/addView")
        });
    });

</script>
