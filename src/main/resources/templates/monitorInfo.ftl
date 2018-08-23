<%--
  Created by IntelliJ IDEA.
  User: Lister
  Date: 16/11/12
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="content-header my-content-header" style="background: #F4F4F4;">
    <h1>
        <span class="my-view-title">系统管理</span>
        <small>系统监控</small>
    </h1>
</section>
<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#sys_info" data-toggle="tab" aria-expanded="true">系统信息</a></li>
                    <li class=""><a href="#sql" data-toggle="tab" aria-expanded="true">SQL监控</a></li>
                    <li class=""><a href="#session" data-toggle="tab" aria-expanded="false">Session监控</a></li>
                    <li class=""><a href="#uri" data-toggle="tab" aria-expanded="false">URI监控</a></li>
                    <li class="pull-right"><a href="javascript:void(0);" class="text-muted" id="reset_monitor"><i class="glyphicon glyphicon-refresh"></i>&nbsp;重置</a></li>
                </ul>
                <div class="tab-content">
                    <%-- 系统信息 --%>
                    <div class="tab-pane active" id="sys_info">
                        <div class="row">
                            <div class="col-sm-12">
                                <label class="col-sm-2 control-label ">Druid 版本</label>
                                <div class="col-sm-10"><span id="span_druid_version"></span></div>
                            </div>
                            <div class="col-sm-12">
                                <label class="col-sm-2 control-label ">驱动</label>
                                <div class="col-sm-10"><span id="span_drive"></span></div>
                            </div>
                            <div class="col-sm-12">
                                <label class="col-sm-2 control-label ">Java 版本</label>
                                <div class="col-sm-10"><span id="span_java_version"></span></div>
                            </div>
                            <div class="col-sm-12">
                                <label class="col-sm-2 control-label ">JVM 名称</label>
                                <div class="col-sm-10"><span id="span_jvm_name"></span></div>
                            </div>
                            <div class="col-sm-12">
                                <label class="col-sm-2 control-label ">CLASSPATH 路径</label>
                                <div class="col-sm-10"><span id="span_class_path"></span></div>
                            </div>
                            <div class="col-sm-12">
                                <label class="col-sm-2 control-label ">启动时间</label>
                                <div class="col-sm-10"><span id="span_start_time"></span></div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="sql">
                        <table id="sql_monitor_dg"></table> 
                    </div>
                    <div class="tab-pane" id="session">
                        <table id="session_monitor_dg"></table> 
                    </div>
                    <div class="tab-pane" id="uri">
                        <table id="url_monitor_dg"></table>    
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript" src="/static/custom/js/base.js"></script>
<script>
    /**
     * 系统信息
     */
    function systenInfo() {
        $.post("/druid/basic.json","",function(result){
            var context = result.Content;
            $("#span_druid_version").text(context.Version);
            $("#span_class_path").text(context.JavaClassPath);
            $("#span_java_version").text(context.JavaVersion);
            $("#span_jvm_name").text(context.JavaVMName);
            $("#span_start_time").text(context.StartTime);
            $("#span_drive").text(context.Drivers[0]+"/"+context.Drivers[1]);
            toastrinfo("系统数据加载完成")
        },"json");
    }
    /**
     * SQL 监控
     */
    function sqlMonitor() {
        $.post("/druid/sql.json","",function(result) {
            $('#sql_monitor_dg').bootstrapTable({
                data: result.Content,
                singleSelect:true,//只能单选
                clickToSelect:true,
                detailView:true,
                detailFormatter:function(index, row) {
                    return '<div class="callout callout-success"><h4>SQL</h4><p>'+row.SQL+'</p></div>';
                },
                columns: [
                    {field: 'SQL', title: 'SQL', align: 'left',formatter:function (value,row,index) {
                        if (value.length > 100) {
                            return value.substring(0,100-1)+"...";
                        }
                        return value;
                    }},
                    {field:'ExecuteCount',title:'执行次数',width:75,align:'center',formatter:function (value,row,index) {
                            return '<span class="badge bg-green">'+value+'</span>';
                    }},
                    {field:'ErrorCount',title:'错误数',width:75,align:'center',formatter:function (value,row,index) {
                        return '<span class="badge bg-red">'+value+'</span>';
                    }},
                    {field:'ConcurrentMax',title:'最大并发数',width:85,align:'center',formatter:function (value,row,index) {
                        return '<span class="badge bg-blue-active">'+value+'</span>';
                    }},
                    {field:'FetchRowCount',title:'读取行数',width:75,align:'center',formatter:function (value,row,index) {
                        return '<span class="badge bg-yellow-active">'+value+'</span>';
                    }},
                    {field:'TotalTime',title:'执行用时',width:75,align:'center',formatter:function (value,row,index) {
                        return '<span class="badge bg-green-light">'+value+'</span>';
                    }}]
            });
        },"json");
    }
    /**
     *
     * Session 监控
     */
    function sessionMonitor() {
        $.post("/druid/websession.json","",function(result) {
            $('#session_monitor_dg').bootstrapTable({
                data: result.Content,
                singleSelect:true,//只能单选
                clickToSelect:true,
                columns: [
                    {field: 'SESSIONID', title: 'SESSIONID', width: 240, align: 'left',styler:
                        function (value,row,index) {
                            return "font-weight: bold;";
                        }},
                    {field:'CreateTime',title:'创建时间',width:130,align:'left'},
                    {field:'LastAccessTime',title:'最后访问时间',width:130,align:'left'},
                    {field:'RemoteAddress',title:'客户端IP',width:100,align:'left'},
                    {field:'RequestCount',title:'请求次数',width:115,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-green">'+value+'</span>';
                    }},
                    {field:'RequestTimeMillisTotal',title:'总共请求用时',width:140,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-red">'+value+'</span>';
                    }},
                    {field:'ConcurrentMax',title:'最大并发',width:115,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-red-active">'+value+'</span>';
                    }},
                    {field:'JdbcExecuteCount',title:'JDBC执行次数',width:140,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-blue">'+value+'</span>';
                    }},
                    {field:'JdbcExecuteTimeMillis',title:'JDBC执行用时',width:140,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-blue-gradient">'+value+'</span>';
                    }},
                    {field:'JdbcFetchRowCount',title:'读取行数',width:115,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-yellow-gradient">'+value+'</span>';
                    }}]
            });
        },"json");
    }
    /**
     * URI 监控
     */
    function uriMonitor() {
        $.post("/druid/weburi.json","",function(result) {
            $('#url_monitor_dg').bootstrapTable({
                data: result.Content,
                singleSelect:true,//只能单选
                clickToSelect:true,
                columns: [
                    {field: 'URI', title: 'URI', width: 400, align: 'left',styler:
                        function (value,row,index) {
                            return "font-weight: bold;";
                        }},
                    {field:'RequestCount',title:'请求次数',width:115,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-green">'+value+'</span>';
                    }},
                    {field:'RequestTimeMillis',title:'请求时间',width:115,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-yellow-gradient">'+value+'</span>';
                    }},
                    {field:'ConcurrentMax',title:'最大并发',width:115,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-red">'+value+'</span>';
                    }},
                    {field:'JdbcExecuteCount',title:'JDBC执行次数',width:130,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-blue-active">'+value+'</span>';
                    }},
                    {field:'JdbcExecuteErrorCount',title:'JDBC出错数',width:130,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-green-gradient">'+value+'</span>';
                    }},
                    {field:'JdbcFetchRowCount',title:'JDBC读取行数',width:130,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-red-gradient">'+value+'</span>';
                    }},
                    {field:'JdbcUpdateCount',title:'JDBC更新行数',width:130,align:'left',formatter:function (value,row,index) {
                        return '<span class="badge bg-gray-active">'+value+'</span>';
                    }},
                    {field:'Histogram',title:'区间分布[ - - - - - - - - ]',width:180,align:'left'}]
            });
        },"json");
    }
    $(function () {
        systenInfo();
        sqlMonitor();
        sessionMonitor();
        uriMonitor();
        $("#reset_monitor").click(function () {
            myConfirm("系统提示","您确定清除系统监控信息吗？",function () {
                $.post("/druid/reset-all.json","",function (result) {
                    if (result.ResultCode == 1) {
                        location.reload();
                    }
                },"json");  
            });
        })
    });
</script>
