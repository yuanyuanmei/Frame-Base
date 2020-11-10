/**
 * 飞行区域管理列表
 **/
(function () {
    var job_index = {
        info :null,
        table:null,
        init:function () {
            this.tableList();
            this.bindEvent();
        },

        //列表加载
        tableList: function () {
            // 表格绑定事件
            layui.use('table', function(){   //这个代表引用layui文件modules里面的table.js,所以检查modules是否有table.js
                var table = layui.table;
                job_index.table = table.render({
                    elem: '#test'    //对应table里面的id
                    ,url:"../../" + requestConfig.monitorUrl.jobList //请求数据的地址
                    ,method:"post"
                    ,id:'testTable'
                    ,cols: [[
                        {type:'checkbox'}
                        ,{field:'id', width:80, title: '任务编号', sort: true}
                        ,{field:'jobName', minWidth:80, title: '任务名称'}
                        ,{field:'jobGroup', minWidth:100, title: '任务分组'}
                        ,{field:'invokeTarget', minWidth:100, title: '调用目标字符串不能为空'}
                        ,{field:'cronExpression', minWidth:100, title: 'Cron执行表达式'}
                        ,{field:'status', minWidth:100, title: '任务状态'}
                        ,{field:'createTime', minWidth:100, title: '创建时间'}
                        ,{field:'', minWidth:50, title: '操作' , templet:'#optTpl'}
                    ]]
                    ,toolbar:"#toolbarDemo"
                    ,page: true
                    ,contentType: 'application/json; charset=utf-8'
                    ,request:{pageName:'pageNum',limitName:'pageSize'}
                    ,response:{statusCode:200}
                    ,tool: '#optTpl'
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

                //操作按钮
                table.on('tool(test)',
                    function(obj) {
                        switch (obj.event) {
                            case 'editTable':
                                var data = obj.data;
                                job_index.data = data;
                                xadmin.open('定时任务管理','./job_add.html', 520, 720, false)
                                break;
                            case 'delTable':
                                var data = obj.data;
                                layer.confirm('确认要删除吗？',
                                    function(index) {
                                        layer_dm.tableReload("del");
                                        var ids = []
                                        ids.push(data.id)
                                        //发异步删除数据
                                        Ajax.postJson("../../" + requestConfig.dataUrl.delLayer, null, JSON.stringify({"ids":ids}), "", function (res) {
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

        bindEvent: function(){
            //添加按钮
            $("#addButton").on('click',function () {
                job_index.data = null;
                xadmin.open('添加定时任务','./job_add.html',520,720,false)
            });
        },


    }

    window.job_index = job_index;
    job_index.init();
})();










