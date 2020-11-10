/**
 * 图层管理
**/
(function () {
    var layer_dm = {
        data :null,
        init:function () {
            this.info();
            this.save();
        },

        info: function(){
            if($(window.parent.layer_dm.data).length > 0){
                var info = $(window.parent.layer_dm.data)[0];
                $("#id").val(info.id);
                $("#name").val(info.name);
                $("#url").val(info.url);
                $("#lng").val(info.lng);
                $("#lat").val(info.lat);
                $("#address").val(info.address);
                $("#shareStatus").attr("checked",info.shareStatus == 1 ? 'true' : 'false');
            }
        },

        save: function () {
            layui.use('form', function(){
                var form = layui.form;
                //监听提交
                form.on('submit(add)',
                    function(data) {
                        Ajax.postJson("../../" + requestConfig.dataUrl.saveLayer, null, JSON.stringify(data.field), "", function (data) {
                            if (data.code == 200) {
                                //发异步，把数据提交给php
                                layer.alert("保存成功", {
                                        icon: 6
                                    },
                                    function() {
                                        //父页面刷新
                                        layer_dm.tableReload("add");
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
                $(window.parent.layer_dm.table)[0].reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }else{
                layer_dm.table.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        },
    }

    window.layer_dm = layer_dm;
    layer_dm.init();
})();










