/**
 * 字典管理列表
 **/
(function () {
    var dict = {
        data :null,
        init:function () {
            this.save();
            this.info();
        },

        info: function(){

            if($(window.parent.dict.data).length > 0){
                var info = $(window.parent.dict.data)[0];
                $("#seq").val(info.seq);
                $("#id").val(info.id);
                $("#name").val(info.name);
                $("#code").val(info.code);
                $("#memo").val(info.memo);
                $("#sort").val(info.sort);
            }

        },

        save: function () {
            layui.use(['form','table'], function(){
                var form = layui.form;
                //监听提交
                form.on('submit(add)',
                    function(data) {
                        data.field.op = 'desc';
                        Ajax.postJson("../../" + requestConfig.dict.save, null, JSON.stringify(data.field), "", function (data) {
                            if (data.code == 200) {
                                //发异步，把数据提交给php
                                layer.alert("保存成功", {
                                        icon: 6
                                    },
                                    function() {
                                        //父页面刷新
                                        dict.tableReload('add');
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
                $(window.parent.dict.table)[0].reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }else{
                dict.table.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        },

    }

    window.dict = dict;
    dict.init();
})();










