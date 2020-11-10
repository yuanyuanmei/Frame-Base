/**
 * 用户管理列表
**/
$(function () {
    var user = {
        data : null,
        table: null,
        src: '',
        init:function () {
            this.tableList();
            this.bindEvent();
        },

        tableList: function () {
            // 表格绑定事件
            layui.use('table', function(){   //这个代表引用layui文件modules里面的table.js,所以检查modules是否有table.js
                var table = layui.table;
                user.table = table.render({
                    elem: '#test'    //对应table里面的id
                    ,url:"../../" + requestConfig.userUrl.search //请求数据的地址
                    ,method:"post"
                    ,id:'testTable'
                    ,cols: [[
                        {type:'checkbox', field:"id"}
                        ,{field:'id', width:80, title: '编号', sort: true}
                        ,{field:'account', minWidth:100, title: '账号'}
                        ,{field:'nickname', minWidth:150, title: '昵称'}
                        ,{field:'status', minWidth:100, title: '状态', templet:'#switchTpl'}
                        ,{field:'phone', minWidth:100, title: '手机号码'}
                        ,{field:'roleName', minWidth:100, title: '用户角色'}
                        ,{field:'createTime', minWidth:100, title: '创建日期' , sort: true}
                        ,{field:'', minWidth:100, title: '操作' , templet:'#optTpl'}
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

                $('#search').on('click', function () {
                    var type = $(this).data('type');
                    active[type] ? active[type].call(this) : '';
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

                //头工具栏事件
                table.on('toolbar(test)',
                    function(obj) {
                        var checkStatus = table.checkStatus(obj.config.id);
                        switch (obj.event) {
                            case 'getCheckData':
                                var data = checkStatus.data;
                                layer.alert(JSON.stringify(data));
                                break;
                            case 'getCheckLength':
                                var data = checkStatus.data;
                                layer.msg('选中了：' + data.length + ' 个');
                                break;
                            case 'isAll':
                                layer.msg(checkStatus.isAll ? '全选': '未全选');
                                break;
                        };
                    });

                //操作按钮
                table.on('tool(test)',
                    function(obj) {
                        console.log(obj)
                        switch (obj.event) {
                            case 'editTable':
                                var data = obj.data;
                                user.data = data;
                                user.src = '';
                                xadmin.open('编辑用户','./user_add.html',420,450,false)
                                break;
                            case 'delTable':
                                var data = obj.data;
                                layer.confirm('确认要删除吗？',
                                    function(index) {
                                        //发异步删除数据
                                        Ajax.postJson("../../" + requestConfig.userUrl.del, null, JSON.stringify({"userIds":[data.id]}), "", function (res) {
                                            if (res.code == 200) {
                                                user.tableReload('del');
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
                            case 'isAll':
                                layer.msg(obj.isAll ? '全选': '未全选');
                                break;
                        };
                    });
            });

            // 日期控件绑定事件
            layui.use('laydate',
                function() {
                    var laydate = layui.laydate;

                    //执行一个laydate实例
                    laydate.render({
                        elem: '#start' //指定元素

                    });

                    //执行一个laydate实例
                    laydate.render({
                        elem: '#end' //指定元素
                    });
                });
        },


        bindEvent: function(){
            //添加按钮
            $("#addButton").on('click',function () {
                user.data = null;
                user.src = '';
                xadmin.open('添加用户','./user_add.html',420,520,false)
            });
            //删除按钮
            $("#delAll").on('click',function () {
                var ids = [];
                var checkStatus = layui.table.checkStatus('testTable').data;
                // 获取选中的id
                for(var i=0;i<checkStatus.length;i++){
                        ids.push(checkStatus[i].id)
                };

                layer.confirm('确认要删除吗？',
                    function(index) {
                        //发异步删除数据
                        Ajax.postJson("../../" + requestConfig.userUrl.del, null, JSON.stringify({"userIds":ids}), "", function (res) {
                            if (res.code == 200) {
                                layer.msg('删除成功', {icon: 1});
                                $(".layui-form-checked").not('.header').parents('tr').remove();
                            }else{
                                layer.msg(res.msg);
                            }
                        }, null);

                    });
            });

        },

        tableReload: function (opt) {
            user.table.reload({
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        },

    }

    window.user = user;
    user.init();

});




