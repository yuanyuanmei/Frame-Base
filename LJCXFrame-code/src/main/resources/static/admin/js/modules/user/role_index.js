/**
 * 角色管理列表
**/
(function () {
    var role = {
        info :null,
        init:function () {
            this.tableList();
        },

        //列表加载
        tableList: function () {
            // 表格绑定事件
            layui.use('table', function(){   //这个代表引用layui文件modules里面的table.js,所以检查modules是否有table.js
                var table = layui.table;
                table.render({
                    elem: '#test'    //对应table里面的id
                    ,url:"../../" + requestConfig.roleUrl.search //请求数据的地址
                    ,method:"post"
                    ,id:'testTable'
                    ,cols: [[
                        {type:'checkbox'}
                        ,{field:'id', width:80, title: '编号', sort: true}
                        ,{field:'name', minWidth:80, title: '名称'}
                        ,{field:'memo', minWidth:100, title: '备注'}
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

                //添加按钮
                $("#addButton").on('click',function () {
                    role.type = "add"; // 增加模式
                    xadmin.open('添加角色','./role_add.html')
                });


                //监听单元格编辑
                table.on('edit(test)',
                    function(obj) {
                        var value = obj.value //得到修改后的值
                            , data = obj.data //得到所在行所有键值
                            , field = obj.field; //得到字段
                        layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
                });


                //操作按钮
                table.on('tool(test)',
                    function(obj) {
                        switch (obj.event) {
                            case 'editTable':
                                var data = obj.data;
                                role.info = data;
                                role.type = "edit"; // 编辑模式
                                xadmin.open('编辑角色','./role_add.html')
                                break;
                            case 'delTable':
                                var data = obj.data;
                                layer.confirm('确认要删除吗？',
                                    function(index) {
                                        //发异步删除数据
                                        Ajax.postJson("../../" + requestConfig.roleUrl.deleteRole, null, JSON.stringify({"id":data.id}), "", function (res) {
                                            if (res.code == 200) {
                                                $(obj).attr("tr").remove();
                                                layer.msg('已删除!', {
                                                    icon: 1,
                                                    time: 1000
                                                });
                                            }else{
                                                layer.msg(res.msg);
                                            }
                                        }, null);

                                    });
                                break;
                        };
                    });
            });

        },

    }

    window.role = role;
    role.init();
})();










