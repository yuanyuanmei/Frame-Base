/**
 * 文件下载列表
 **/
(function () {
    var file = {
        info :null,
        init:function () {
            this.tableList();
            this.save();
        },

        //列表加载
        tableList: function () {
            // 表格绑定事件
            layui.use('table', function(){   //这个代表引用layui文件modules里面的table.js,所以检查modules是否有table.js
                var table = layui.table;
                table.render({
                    elem: '#test'    //对应table里面的id
                    ,url:"../../" + requestConfig.sysUrl.fileList //请求数据的地址
                    ,method:"post"
                    ,id:'testTable'
                    ,cols: [[
                        {type:'checkbox'}
                        ,{field:'id', width:80, title: '编号', sort: true}
                        ,{field:'fileName', minWidth:100, title: '名称'}
                        ,{field:'size', minWidth:80, title: '大小'}
                        ,{field:'suffix', minWidth:80, title: '类型'}
                        ,{field:'createTime', minWidth:100, title: '上传时间'}
                        ,{field:'', minWidth:50, title: '操作' , templet:'#optTpl'}
                    ]]
                    //,toolbar:"#toolbarDemo"
                    ,page: true
                    ,contentType: 'application/json; charset=utf-8'
                    ,request:{pageName:'pageNum',limitName:'pageSize'}
                    ,response:{statusCode:200}
                    //,tool: '#optTpl'
                    ,parseData: function(res){ //res 即为原始返回的数据
                        console.log(res);
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.msg, //解析提示文本
                            "count": res.data.total, //解析数据长度
                            "data": res.data.records //解析数据列表
                        };
                    }

                });

                // 搜索按钮事件
                var $ = layui.$, active = {
                    reload: function () {
                        //执行重载
                        table.reload('testTable', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                            , where: {
                                key: $("input[name=key]").val(),
                                datestart: $("#start").val(),
                                dateend: $("#end").val(),
                            }
                        });
                    },
                };

                //搜索按钮
                $('#search').on('click', function () {
                    var type = $(this).data('type');
                    active[type] ? active[type].call(this) : '';
                });

                //操作按钮
                table.on('tool(test)',
                    function(obj) {
                        switch (obj.event) {
                            case 'downLoad':
                                var data = obj.data;
                                window.location.href = "../../" + requestConfig.sysUrl.download+"?&id="+data.id+"&delete="+false;
                        };
                    });

                //上传文件
                layui.use('upload', function(){
                    var upload = layui.upload;
                    //执行实例
                    var uploadInst = upload.render({
                        elem: '#upload' //绑定元素
                        ,accept: 'file'
                        ,url: "../../" + requestConfig.sysUrl.upload  //上传接口
                        ,done: function(res){
                            layer.msg("上传成功");
                        }
                        ,error: function(res){
                            //请求异常回调
                            layer.msg(res.msg);
                        }
                    });
                });

                //监听单元格编辑
                table.on('edit(test)',
                    function(obj) {
                        var value = obj.value //得到修改后的值
                            ,
                            data = obj.data //得到所在行所有键值
                            ,
                            field = obj.field; //得到字段
                        layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
                    });
            });

        },

        save: function () {
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

    window.file = file;
    file.init();
})();










