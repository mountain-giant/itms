<head>
    <style>
        .bs-glyphicons {
            padding-left: 0;
            padding-bottom: 1px;
            margin-bottom: 20px;
            list-style: none;
            overflow: hidden;
        }

        .bs-glyphicons li {
            float: left;
            width: 25%;
            height: 115px;
            padding: 10px;
            margin: 0 -1px -1px 0;
            font-size: 12px;
            line-height: 1.4;
            text-align: center;
            border: 1px solid #ddd;
        }

        .bs-glyphicons .glyphicon {
            margin-top: 5px;
            margin-bottom: 10px;
            font-size: 24px;
        }

        .bs-glyphicons .glyphicon-class {
            display: block;
            text-align: center;
            word-wrap: break-word; /* Help out IE10+ with class names */
        }

        .bs-glyphicons li:hover {
            background-color: rgba(86, 61, 124, .1);
        }

        @media (min-width: 768px) {
            .bs-glyphicons li {
                width: 12.5%;
            }
        }
    </style>
</head>
<section class="content-header my-content-header" style="background: #F4F4F4;">
    <h1>
        <span class="my-view-title">系统管理</span>
        <small>资源管理</small>
        <span class="pull-right">           
                <a class="btn btn-success" style="margin-top: -6px" href="javascript:void(0)" role="button" id="openAdd"><i class="glyphicon glyphicon-plus"></i> 添加资源</a>  
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
            <table id="menuinfoTable"></table>
        </div>
    <#include "../viewblock/iconpage.ftl"/>
</section>
<script type="text/javascript" src="/custom/js/base.js"></script>
<script>
    function deleteFun(row) {
        myConfirm("系统提示", "是否删除资源" + row.name, function () {
            $.post("/resource/del", "id=" + row.id, function (data) {
                if (data.code == "0000") {
                    search();
                    toastrsuccess('删除成功...');
                } else {
                    toastrerror(data.msg);
                }

            }, "json");
            $("#prompt_modal").modal('hide');
        });
    }
    function updateFun(row) {
        loadChildView('/resource/updateView?id='+row.id);
    }
    /**
     * 带条件查询
     */
    function search() {
        var text = $("#search_text").val();
        var param = {name: text};
        $('#menuinfoTable').bootstrapTable('refresh', {query: param});
    }

    $(function () {
        $('#menuinfoTable').bootstrapTable({
            url: '/resource/page',
            singleSelect: true,//只能单选
            clickToSelect: true,
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,     //是否显示分页（*）
            sortable: false,      //是否启用排序
            sortOrder: "asc",     //排序方式
            pageNumber: 1,      //初始化加载第一页，默认第一页
            pageSize: 10,
            height:500,
            // striped:true,
            contentType: "application/x-www-form-urlencoded",
            queryParamsType: '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
            //queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            //search: true,      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            columns: [[
                {
                    field: 'id', title: '资源编号', width: 75, align: 'left',
                    formatter: function (value, row, index) {
                        return '<span class="badge bg-blue">' + value + '</span>';
                    }
                },
                {
                    field: 'parentId', title: '父级编号', width: 75, align: 'left',
                    formatter: function (value, row, index) {
                        return '<span class="badge bg-green">' + value + '</span>';
                    }
                },
                {
                    field: 'sort', title: '排序号', width: 60, align: 'left',
                    formatter: function (value, row, index) {
                        return '<span class="badge bg-red">' + value + '</span>';
                    }
                },
                {field: 'icon', title: '图标', width: 40, align: 'center', formatter: function (value, row, index) {
                    return "<i class='" + value + "'></i>";
                }},
                {field: 'iconColor', title: '颜色', width: 40, align: 'center', formatter: function (value, row, index) {
                    return '<i class="glyphicon glyphicon-stop" style="color: '+value+';"></i>';
                }},
                {field: 'name', title: '资源名称', width: 230, align: 'left'},
                {
                    field: 'type', title: '资源类型', width: 100, align: 'left',
                    formatter: function (value, row, index) {
                        if (value == "M") {
                            return '<span class="badge bg-blue">菜单</span>';
                        } else {
                            return '<span class="badge bg-red">操作</span>';
                        }
                    }
                },
                {field: 'href', title: '链接地址', width: 300, align: 'left'},
                {field: 'userName', title: '创建用户', width: 100, align: 'left'},
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
            loadChildView('/resource/addView')
        });
    });
</script>
