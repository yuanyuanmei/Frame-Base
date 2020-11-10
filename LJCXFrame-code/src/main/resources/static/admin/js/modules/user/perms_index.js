/**
 * 角色管理列表
 **/
(function () {
    var perms = {
        info :null,
        el : null,
        init:function () {
            //this.tableList();
            this.treeList();
        },

        //树结构加载
        treeList: function(){
            var layout = [
                { name: '名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: '' },
                { name: '编号', field: 'id', headerClass: 'value_col', colClass: 'value_col', style: ''},
                {
                    name: '类型',
                    field:'type',
                    headerClass: 'value_col',
                    colClass: 'value_col',
                    style: 'width: 20%',
                    render: function(row) {
                        var item = JSON.parse(row);
                        if(item.id == 0){
                            return "<span class='layui-btn layui-btn-disabled layui-btn-sm'>总览</span>"; //列渲染
                        }else if(item.type == 1 && item.parentId == 0){
                            return "<span class='layui-btn layui-btn-info layui-btn-sm'>目录</span>"; //列渲染
                        }else if(item.type == 1){
                            return "<span class='layui-btn layui-btn-normal layui-btn-sm'>菜单</span>"; //列渲染
                        }else if(item.type == 2){
                            return "<span class='layui-btn layui-btn-danger layui-btn-sm'>按钮</span>"; //列渲染
                        }else if(item.type == 3){
                            return "<span class='layui-btn layui-btn-warm layui-btn-sm'>账号</span>"; //列渲染
                        }

                    }
                },
                { name: '权限标识', field: 'permission', headerClass: 'value_col', colClass: 'value_col', style: ''},
            ];
            layui.config({
                base: "/static/lib/layui/lay/modules/"
            }).use(['form', 'treetable', 'layer'], function() {
                var layer = layui.layer, form = layui.form, $ = layui.jquery;
                var roleNode = [];
                $.ajax({
                    type : 'POST',
                    dataType : "json",
                    url : "../../" + requestConfig.perms.treeList,
                    //data : JSON.stringify({"pageNum":1,"pageSize":10}),
                    contentType : "application/json; charset=utf-8",
                    cache : false,
                    async : false,
                    success : function(res) {
                        if (res.code == 200){
                            roleNode = res.data;
                        }
                    },
                });
                //console.log(JSON.stringify(roleNode))
                var tree = layui.treetable({
                    elem: '#test', //传入元素选择器
                    spreadable: false, //设置是否全展开，默认不展开
                    checkbox : true,
                    nodes: roleNode,
                    layout: layout,
                    callback: {
                        // beforeCheck : treetableBeforeCheck,
                        // onCheck : treetableOnCheck,
                        // beforeCollapse : treetableBeforeCollapse,
                        // onCollapse : treetableOnCollapse,
                        // beforeExpand : treetableBeforeExpand,
                        // onExpand : treetableOnExpand,
                    }
                });
            });
        },
        //列表加载
        tableList: function () {
            // 表格绑定事件
            layui.use('table', function(){   //这个代表引用layui文件modules里面的table.js,所以检查modules是否有table.js
                var table = layui.table;
                table.render({
                    elem: '#test'    //对应table里面的id
                    ,url:"../../" + requestConfig.perms.search //请求数据的地址
                    ,method:"post"
                    ,id:'testTable'
                    ,cols: [[
                        {type:'checkbox'}
                        ,{field:'id', width:80, title: '编号', sort: true}
                        ,{field:'name', minWidth:80, title: '名称'}
                        ,{field:'memo', minWidth:100, title: '备注'}
                        ,{field:'', minWidth:50, title: '操作' , templet:'#optTpl'}
                    ]]
                    ,toolbar:"#toolbarDemo"
                    ,page: true
                    ,contentType: 'application/json; charset=utf-8'
                    ,request:{pageName:'pageNum',limitName:'pageSize'}
                    ,response:{statusCode:200}
                    ,tool: '#optTpl'
                    ,parseData: function(res){ //res 即为原始返回的数据;
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
                        switch (obj.event) {
                            case 'editTable':
                                var data = obj.data;
                                role.info = data;
                                Ajax.postJson("../../" + requestConfig.perms.getRolePerms, null, JSON.stringify({"id":role.info.id}), "", function (res) {
                                    if (res.code == 200) {
                                        var permsIds = [];
                                        $.each(res.data,function () {
                                            permsIds.push($(this).attr('id'));
                                        });
                                        //role.el.setChecked(permsIds,true);
                                    }
                                }, null);
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
    window.perms = perms;
    perms.init();
})();










