/**
 * 全景图列表
**/
(function () {
    var panorama = {
        data :null,
        init:function () {
            this.save();
            this.info();
        },

        info: function(){
            if($(window.parent.panorama.data).length > 0){
                var info = $(window.parent.panorama.data)[0];
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
                        Ajax.postJson("../../" + requestConfig.dataUrl.savePan, null, JSON.stringify(data.field), "", function (data) {
                            if (data.code == 200) {
                                //发异步，把数据提交给php
                                layer.alert("保存成功", {
                                        icon: 6
                                    },
                                    function() {
                                        //父页面刷新
                                        panorama.tableReload("add");
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
                $(window.parent.panorama.table)[0].reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }else{
                panorama.table.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        },
    }

    window.panorama = panorama;
    panorama.init();
})();










