/**
 * 用户管理列表
**/
$(function () {
    var user = {
        data : null,
        src: '',
        init:function () {
            this.save();
            this.verify();
            this.info();
        },

        info: function(){
            var userId = 0;
            var user = $(window.parent.user)[0];
            if($(window.parent.user.data).length > 0){
                var userInfo = user.data;
                $("#accountDiv").remove();
                $("#pwdDiv").remove();
                $("#repwdDiv").remove();
                $("#id").val(userInfo.id);
                $("#nickname").val(userInfo.nickname);
                $("#type").val(userInfo.type);
                $("#phone").val(userInfo.phone);
                $("#email").val(userInfo.email);
                userId = userInfo.id;
            }

            layui.use(['form','element'], function(){
                var form = layui.form,
                    element = layui.element;

                Ajax.postJson("../../" + requestConfig.roleUrl.userRoleList, null, JSON.stringify({"userId":userId}), "", function (res) {
                    if (res.code == 200) {
                        var roleHtml = "";
                        for(var i = 0; i < res.data.length; i++){
                            var role = res.data[i];
                            if(role.isChecked > 0){
                                roleHtml += "<input name=\"roleIds\" lay-skin=\"primary\" checked type=\"checkbox\" title="+role.name+" value="+role.id+">";
                            }else{
                                roleHtml += "<input name=\"roleIds\" lay-skin=\"primary\" type=\"checkbox\" title="+role.name+" value="+role.id+">";
                            }
                            //如果是团队成员将角色列表默认为APP并隐藏
                            if(user.src == 'team_user' && role.name == 'APP账号'){
                                roleHtml = "<input name=\"roleIds\" lay-skin=\"primary\" checked type=\"checkbox\" title="+role.name+" value="+role.id+">";
                                $("#teamId").val(user.teamId);
                                break;
                            }
                        }
                        $("#roleDiv").html(roleHtml);
                        form.render('checkbox');
                    }else{
                        layer.alert(res.msg)
                    }
                }, null);
            });

        },

        verify: function () {
            /**
             * 用户管理添加
             */
            layui.use(['form', 'layer'],
                function() {
                    $ = layui.jquery;
                    var form = layui.form,
                        layer = layui.layer;

                    //自定义验证规则
                    form.verify({
                        account: [/(.+){6,12}$/, '账号必须6到12位'],
                        // nickname: function(value) {
                        //     if (value.length < 5) {
                        //         return '昵称至少得5个字符啊';
                        //     }
                        // },
                        pass: [/(.+){6,12}$/, '密码必须6到12位'],
                        repass: function (value) {
                            if ($('#password').val() != $('#repassword').val()) {
                                return '两次密码不一致';
                            }
                        }
                    });
                })
        },

        save: function () {
            layui.use('form', function() {
                var form = layui.form;
                //监听提交
                form.on('submit(add)',
                    function (data) {
                        //获取checkbox[name='like']的值
                        var roleIds = new Array();
                        $("input:checkbox[name='roleIds']:checked").each(function(i){
                            roleIds[i] = $(this).val();
                        });
                        data.field.roleIds = roleIds;
                        var url = "";
                        if(data.field.id > 0){
                            url = "../../" + requestConfig.userUrl.update;
                        }else{
                            url = "../../" + requestConfig.userUrl.save;
                        }
                        Ajax.postJson(url, null, JSON.stringify(data.field), "", function (data) {
                            if (data.code == 200) {
                                //发异步，把数据提交给php
                                layer.alert("保存成功", {
                                        icon: 6
                                    },
                                    function () {
                                        if(user.src == 'team_user'){
                                            equipment.tableReload('add');
                                        }else{
                                            user.tableReload('add');
                                        }

                                        // 获得frame索引
                                        var index = parent.layer.getFrameIndex(window.name);
                                        //关闭当前frame
                                        parent.layer.close(index);
                                    });
                            } else {
                                layer.alert(data.msg)
                            }
                        }, null);
                        return false;
                    });
            });
        },

        tableReload: function (opt) {
            if(opt == 'add'){
                $(window.parent.user.table)[0].reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }else{
                user.table.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        },
    }

    window.user = user;
    user.init();

});




