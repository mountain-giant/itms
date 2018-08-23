<div id="roleFormView">
    <section class="content-header my-content-header" style="background: #F4F4F4;">
        <h1>
            <span class="my-view-title2" id="windowTitle"> 角色管理 <i class="glyphicon glyphicon-arrow-right"></i> <label id="viewTitle">${title}</label></span>
            <span class="pull-right">  
                <a class="btn btn-default" style="margin-top: -6px" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
                <a class="btn btn-success save" style="margin-top: -6px" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </h1>
    </section>

    <section class="content">
        <form class="form-horizontal" id="roleForm">
            <blockquote><small>基本信息</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="form-group">
                        <input type="hidden" name="roleId" value="${role.roleId!}">
                        <label class="col-sm-2 control-label">角色名称</label>
                        <div class="col-sm-6">
                            <input type="text" name="roleName" value="${role.roleName!}" class="form-control" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="row col-sm-8">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色描述</label>
                        <div class="col-sm-6">
                            <textarea class="form-control" style="resize:none;" name="roleDesc" rows="3" placeholder="最多输入500个字符...">${role.roleDesc!}</textarea>
                        </div>
                    </div>
                </div>
            </div>
            <blockquote><small>权限配置</small></blockquote>
            <div class="box-body">
                <div class="row col-sm-8">
                    <div class="easyui-panel" style="width:370px;">
                        <ul id="menuTree"></ul>
                    </div>
                </div>
            </div>
        </form>
        <div class="box-footer">
            <span class="pull-right">  
                <a class="btn btn-default" href="javascript:void(0)" role="button" onclick="history.back()">返回</a> 
                <a class="btn btn-success save" href="javascript:void(0)" role="button"> 保存</a>
            </span>
        </div>
        <br/>
    </section>
</div>
<script type="text/javascript" src="/custom/js/base.js"></script>
<script>
    /**
     * 加载菜单权限
     */
    function loadMenuTree(roleId) {
        var href;
        if (roleId != undefined) {
            href = '/resource/permissions?roleId=' + roleId;
        } else {
            href = '/resource/permissions';
        }
        $.post(href, '', function (result) {
            $('#menuTree').tree({
                data: result,
                checkbox: true
            });
        }, "json");
    }
    
    $(function () {
        loadMenuTree(${role.roleId!});
        $("#roleFormView .save").click(function () {
            var roleId = $("#roleForm input[name=roleId]").val();
            var roleName = $("#roleForm input[name=roleName]").val();
            if (roleName == null || roleName == "") {
                toastrwarning('请输入名称');
                return;
            }
            var title = $("#viewTitle").text();
            var menuIds = new Array();
            var menuChecks = new Array();
            var banId = new Array();   // 半选中的复选框节点Id数组
            var childs = $('#menuTree').tree('getChildren');
            var solids = $("#menuTree").tree("getSolidExt");    //获取半选中的复选框节点
            for (var i = 0; i < solids.length; i++) {              //遍历节点
                var id = solids[i].className;                   //获取节点的class名称
                var newId = id.substring(39);                   //从名称中截取节点ID
                banId.push(newId);
            }
            for (var i = 0; i < childs.length; i++) {
                var id = childs[i].id;
                var is = false;
                for (var j = 0; j < banId.length; j++) {
                    if (id == banId[j]) {
                        is = true;
                    }
                }
                var checked = childs[i].checked;
                menuIds.push(id);
                if (is) {
                    menuChecks.push(true);
                } else {
                    menuChecks.push(checked);
                }
            }

            $("#roleFormView .save").text("正在保存").attr({"disabled": "disabled"});
            if (title == "添加角色") {
                $.post("/role/addRole", {
                    roleName: roleName,
                    roleDesc: $("#roleForm textarea[name=roleDesc]").val(),
                    menuIds: menuIds.toString(),
                    menuChecks: menuChecks.toString()
                }, function (data) {
                    if (data.code == "0000") {
                        toastrsuccess('添加角色成功...');
                        history.back();
                    } else {
                        toastrerror(data.msg);
                    }
                    $("#roleFormView .save").text("保存").removeAttr("disabled");
                }, "json");
            } else {
                $.post("/role/update", {
                    roleId: roleId,
                    roleName: roleName,
                    roleDesc: $("#roleForm textarea[name=roleDesc]").val(),
                    menuIds: menuIds.toString(),
                    menuChecks: menuChecks.toString()
                }, function (data) {
                    if (data.code == "0000") {
                        toastrsuccess('修改角色成功...');
                        history.back();
                    } else {
                        toastrerror(data.msg);
                    }
                    $("#roleFormView .save").text("保存").removeAttr("disabled");
                }, "json");
            }
        });
        
        // 扩展EasyUI TreeView 的获取实心复选框元素的方法
        $.extend($.fn.tree.methods, {
            getSolidExt: function (jq) {
                // 获取节点中span的class为tree-checkbox2的前一个元素，因为我在前一个元素中的图标span中把节点id作为图标的值
                var checkbox2 = $(jq).find("span.tree-checkbox2").prev(); //获取实心的选项 也就是实心方块的   
                return checkbox2;
            }
        });
    })
</script>
