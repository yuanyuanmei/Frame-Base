/**
 * 团队列表
 */
(function () {
    var team = {
        id:0,
        data:null,
        init:function () {
            this.info();
            this.save();
        },

        info: function () {
            if($(window.parent.team.data).length > 0){
                team = $(window.parent.team)[0];
                if(team.data != null && team.data.opt == 'upd'){
                    $("#id").val(team.data.id);
                    $("#name").val(team.data.name);
                    $("#pid").val(team.data.pid);
                    $("#pname").val(team.data.pname);
                    $("#memo").text(team.data.memo);
                }else if(team.data != null && team.data.opt == 'addChild'){
                    $("#pid").val(team.data.id);
                    $("#pname").val(team.data.name);
                    $("#prePath").val(team.data.path);
                }else if(team.data != null && team.data.opt == 'addSame'){
                    $("#pid").val(team.data.pid);
                    $("#pname").val(team.data.pname);
                    $("#prePath").val(team.data.parentPath);
                }else{
                    $("#pid").val(0);
                    $("#pname").val("一级团队");
                }
            }

            layui.use('form', function(){
                var form = layui.form;
                Ajax.postJson("../../" + requestConfig.userUrl.list, null, null, "", function (res) {
                    if (res.code == 200) {
                        var optionHtml = "";
                        for(var i = 0;i< res.data.length;i++){
                            optionHtml += "<option value="+res.data[i].id+">"+res.data[i].nickname+"</option>"
                        }
                        $('#userId').html(optionHtml);
                        form.render();
                        var select = 'dd[lay-value=' + team.data.userId + ']';
                        $('#userId').siblings("div.layui-form-select").find('dl').find(select).click();

                    }else{
                        layer.alert(res.msg)
                    }
                }, null);
            })
        },
        
        save: function () {
            layui.use('form', function(){
                var form = layui.form;
                //监听提交
                form.on('submit(add)',
                    function(data) {
                        $.ajax({
                            type : 'POST',
                            dataType : "json",
                            url : "../../" + requestConfig.teamUrl.saveTeam,
                            data : JSON.stringify(data.field),
                            contentType : "application/json; charset=utf-8",
                            cache : false,
                            async : false,
                            success : function(res) {
                                if (res.code == 200) {
                                    //发异步，把数据提交给php
                                    layer.alert("保存成功", {
                                            icon: 6
                                        },
                                        function() {
                                            //父页面刷新
                                            //$(window.parent.team)[0].bindTree($(window.parent.team)[0].id);

                                            // 获得frame索引
                                            var index = parent.layer.getFrameIndex(window.name);
                                            //关闭当前frame
                                            parent.layer.close(index);

                                        });
                                }else{
                                    layer.alert(data.msg)
                                }
                            },
                        });
                        return false;
                    });
            });
        },

    };
    window.team = team;
    team.init();
})();





