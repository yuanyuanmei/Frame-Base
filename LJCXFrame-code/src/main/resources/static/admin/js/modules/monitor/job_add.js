/**
 * 字典管理列表
 **/
(function () {
    var job_add = {
        data :null,
        init:function () {
            this.save();
            this.info();
        },

        info: function(){

            if($(window.parent.job_index.data).length > 0){
                var info = $(window.parent.job_index.data)[0];
                $("#id").val(info.id);
                $("#jobName").val(info.jobName);
                $("#jobGroup").val(info.jobGroup);
                $("#invokeTarget").val(info.invokeTarget);
                $("#cronExpression").val(info.cronExpression);
                $("input[type=radio][name=misfirePolicy][value="+info.misfirePolicy+"]").attr("checked",'checked');
                $("input[type=radio][name=concurrent][value="+info.concurrent+"]").attr("checked",'checked');
                $("input[type=radio][name=status][value="+info.status+"]").attr("checked",'checked');
                $("#remark").val(info.remark);
            }

        },

        save: function () {
            layui.use(['form','table'], function(){
                var form = layui.form;
                //监听提交
                form.on('submit(add)',
                    function(data) {
                        var monitorUrl = "../../" + requestConfig.monitorUrl.add;
                        if(data.field.id > 0){
                             monitorUrl = "../../" + requestConfig.monitorUrl.editSave;
                        }
                        Ajax.postJson(monitorUrl, null, JSON.stringify(data.field), "", function (data) {
                            if (data.code == 200) {
                                //发异步，把数据提交给php
                                layer.alert("保存成功", {
                                        icon: 6
                                    },
                                    function() {
                                        //父页面刷新
                                        job_add.tableReload('add');
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
        },
        tableReload: function (opt) {
            if(opt == 'add'){
                $(window.parent.job_index.table)[0].reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }else{
                job_index.table.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        },

    }

    window.job_add = job_add;
    job_add.init();
})();










