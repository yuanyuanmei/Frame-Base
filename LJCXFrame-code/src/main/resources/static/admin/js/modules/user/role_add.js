/**
 * 角色管理列表
**/
(function () {
    var roleadd = {
        info :null,
        el : null,
        init:function () {
            this.treeList();
            this.save();
        },

        //绑定树结构
        treeList:function () {
            var that = this;
            layui.config({
                base: "/static/lib/layui/lay/mymodules/"
            }).use(['jquery','table','eleTree','code'], function(){
                var $ = layui.jquery;
                var eleTree = null;
                eleTree = layui.eleTree;
                Ajax.postJson("../../" + requestConfig.perms.treeList, null, null, "", function (res) {
                    if (res.code == 200) {
                        // 初始化权限树
                        roleadd.el = eleTree.render({
                            elem: '.ele1',
                            data: res.data,
                            showCheckbox: true,
                            defaultExpandAll: true,
                        });

                        // 当编辑模式下，加载编辑对象的部分信息， add模式下不加载
                        if($(window.parent.role.info).length > 0 && window.parent.role.type == "edit"){
                            var role = $(window.parent.role.info)[0];
                            $("#id").val(role.id);
                            $("#name").val(role.name);
                            $("#memo").val(role.memo);
                            $("#sort").val(role.sort);

                            that.checkTreeNode();
                        }

                    }else{
                        layer.alert(res.msg)
                    }
                }, null);

            });
        },

        /* 选中权限 */
        checkTreeNode:function(){
            Ajax.postJson("../../" + requestConfig.perms.getRolePerms, null, JSON.stringify({"id":window.parent.role.info.id}), "", function (res) {
                if (res.code == 200) {
                    var permsIds = [];
                    $.each(res.data,function () {
                        permsIds.push($(this).attr('id'));
                    });
                    roleadd.el.setChecked(permsIds,true);
                }
            }, null);
        },

        save: function (){
            layui.use('form', function(){
                var form = layui.form;
                //监听提交
                form.on('submit(add)',
                    function(data) {
                        var permsIds = [];
                        $.each(role.el.getChecked(false,false),function () {
                            permsIds.push($(this).attr('id'));
                        }) ;
                        data.field.permissionIds = permsIds;
                        Ajax.postJson("../../" + requestConfig.roleUrl.addRole, null, JSON.stringify(data.field), "", function (data) {
                            if (data.code == 200) {
                                //发异步，把数据提交给php
                                layer.alert("保存成功", {
                                        icon: 6
                                    },
                                    function() {
                                        //父页面刷新
                                        // 获得frame索引
                                        var index = parent.layer.getFrameIndex(window.name);
                                        //关闭当前frame
                                        parent.layer.close(index);

                                    });
                            }else{
                                layer.alert(data.msg)
                            }
                        }, null);
                        return false;
                    });
            });
        }


    }

    window.role = roleadd;
    roleadd.init();
})();










