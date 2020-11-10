/**
 * 飞行区域管理列表
 **/
(function () {
    var apk = {
        data :null,
        table : null,
        init:function () {
            this.save();
            this.info();
        },

        info: function(){
            if($(window.parent.apk.data).length > 0){
                var info = $(window.parent.apk.data)[0];
                $("#id").val(info.id);
                $("#versionName").val(info.versionName);
                $("#versionCode").val(info.versionCode);
                $("#apkName").val(info.apkName);
                $("#memo").val(info.memo);
                $("#appKey").val(info.appKey);
                $("#isForceUpdate").attr("checked",info.isForceUpdate == 1 ? 'true' : 'false');
            }
        },
        save: function () {
            layui.use(['form','table'], function(){
                var form = layui.form;
                //监听提交
                form.on('submit(add)',
                    function(data) {
                        Ajax.postJson("../../" + requestConfig.apkUrl.update, null, JSON.stringify(data.field), "", function (data) {
                            if (data.code == 200) {
                                //发异步，把数据提交给php
                                layer.alert("保存成功", {
                                        icon: 6
                                    },
                                    function() {
                                        //父页面刷新
                                        apk.tableReload("add");
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
                $(window.parent.apk.table)[0].reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }else{
                apk.table.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        },

    }

    window.apk = apk;
    apk.init();
})();










