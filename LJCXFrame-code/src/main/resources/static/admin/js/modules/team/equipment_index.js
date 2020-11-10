/**
 * 飞行区域管理列表
**/
(function () {

    var equipment = {
        info :null,
        teamId: 0,
        init:function () {
            //this.selectTreeList();
            this.bindEvent();
            this.bindTree();
        },


        bindTree: function(){
            var that = this;
            layui.config({
                base: "/static/lib/layui/lay/mymodules/"
            }).use(['jquery','table','eleTree','code'], function(){
                var $ = layui.jquery;
                var eleTree = null;
                eleTree = layui.eleTree;
                Ajax.postJson("../../" + requestConfig.teamUrl.treeList, null, JSON.stringify({'id':0}), "", function (res) {
                    if (res.code == 200) {
                        var el = eleTree.render({
                            elem: '.ele1',
                            data: res.data,
                            showCheckbox: false,
                            highlightCurrent:true,
                            expandOnClickNode: false,
                            //contextmenuList: [{eventName: "addSame", text: "添加同级节点"},{eventName: "addChild", text: "添加子节点"},{eventName: "upd", text: "编辑"}],
                            defaultExpandAll: true,
                        });
                        that.loadEquipment();
                    }else{
                        layer.alert(res.msg)
                    }
                }, null);

                eleTree.on("nodeClick(data1)",function(d) {
                    equipment.teamId = d.data.currentData.id; // 点击节点对于的数据
                    $("#teamText").text(d.data.currentData.name);
                    that.loadEquipment();
                })

            });
        },

        loadEquipment:function(){
            var that = this;
            that.uavList();
            that.carList();
            that.memberList();
        },

        //列表加载
        uavList: function () {
            // 表格绑定事件
            layui.use('table', function(){   //这个代表引用layui文件modules里面的table.js,所以检查modules是否有table.js
                var table1 = layui.table;
                table1.render({
                    elem: '#uav'    //对应table里面的id
                    ,url:"../../" + requestConfig.teamUrl.uavList //请求数据的地址
                    ,method:"post"
                    ,id:'uavTable'
                    ,cols: [[
                        {type:'checkbox',width:'5%',}
                        ,{field:'id', width:'10%', title: '编号', sort: true}
                        ,{field:'name', width:'15%', title: '无人机名称'}
                        ,{field:'no', width:'15%', title: '无人机SN码'}
                        ,{field:'teamName', width:'15%', title: '所属团队'}
                        ,{field:'model',  width:'15%',title: '型号'}
                        ,{field:'createTime', width:'15%', title: '上传时间'}
                        ,{field:'', width:'10%', title: '操作' , templet:'#optTplUav'}
                    ]]
                    //,toolbar:"#toolbarDemo"
                    ,page: true
                    ,contentType: 'application/json; charset=utf-8'
                    ,where : {
                         teamId:equipment.teamId
                        ,type:'query'
                    }
                    ,request:{pageName:'pageNum',limitName:'pageSize'}
                    ,response:{statusCode:200}
                    //,tool: '#optTplUav'
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

                //操作按钮
                table1.on('tool(uav)',
                    function(obj) {
                        var that = this;
                        console.log(obj)
                        switch (obj.event) {
                            case 'removeUav':
                                var data = obj.data;
                                layer.confirm('确认要解除绑定吗？',
                                    function(index) {
                                        var ids = []
                                        ids.push(data.id)
                                        var json = {
                                            "mtype":1,
                                            "id":equipment.teamId,
                                            "mIds":ids
                                        }
                                        equipment.cancelTeam(json,obj);
                                    });
                                break;
                        };
                    });
            });
        },

        //列表加载
        carList: function () {
            // 表格绑定事件
            layui.use('table', function(){   //这个代表引用layui文件modules里面的table.js,所以检查modules是否有table.js
                var table2 = layui.table;
                table2.render({
                    elem: '#car'    //对应table里面的id
                    ,url:"../../" + requestConfig.teamUrl.carList //请求数据的地址
                    ,method:"post"
                    ,id:'carTable'
                    ,cols: [[
                        {type:'checkbox'}
                        ,{field:'id', width:80, title: '编号', sort: true}
                        ,{field:'name', minWidth:100, title: '名称'}
                        ,{field:'teamName', minWidth:100, title: '所属团队'}
                        ,{field:'createTime', minWidth:100, title: '上传时间'}
                        ,{field:'', minWidth:50, title: '操作' , templet:'#optTplCar'}
                    ]]
                    //,toolbar:"#toolbarDemo"
                    ,page: true
                    ,contentType: 'application/json; charset=utf-8'
                    ,where : {
                        teamId:equipment.teamId
                        ,type:'query'
                    }
                    ,request:{pageName:'pageNum',limitName:'pageSize'}
                    ,response:{statusCode:200}
                    //,tool: '#optTplCar'
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

                //操作按钮
                table2.on('tool(car)',
                    function(obj) {
                        var that = this;
                        console.log(obj)
                        switch (obj.event) {
                            case 'removeCar':
                                var data = obj.data;
                                layer.confirm('确认要解除绑定吗？',
                                    function(index) {
                                        var ids = []
                                        ids.push(data.id)
                                        var json = {
                                            "mtype":2,
                                            "id":equipment.teamId,
                                            "mIds":ids
                                        }
                                        equipment.cancelTeam(json,obj);
                                    });
                                break;
                        };
                    });
            });
        },

        //列表加载
        memberList: function () {
            // 表格绑定事件
            layui.use('table', function(){   //这个代表引用layui文件modules里面的table.js,所以检查modules是否有table.js
                var table3 = layui.table;
                table3.render({
                    elem: '#member'    //对应table里面的id
                    ,url:"../../" + requestConfig.userUrl.search //请求数据的地址
                    ,method:"post"
                    ,id:'memberTable'
                    ,cols: [[
                        {type:'checkbox'}
                        ,{field:'id', width:80, title: '编号', sort: true}
                        ,{field:'nickname', minWidth:80, title: '昵称'}
                        ,{field:'teamName', minWidth:100, title: '所属团队'}
                        ,{field:'username', minWidth:100, title: '用户名'}
                        ,{field:'phone', minWidth:100, title: '手机号码'}
                        ,{field:'', minWidth:50, title: '操作' , templet:'#optTplMember'}
                    ]]
                    //,toolbar:"#toolbarDemo"
                    ,page: true
                    ,contentType: 'application/json; charset=utf-8'
                    ,where : {
                        teamId:equipment.teamId
                        ,type:'query'
                    }
                    ,request:{pageName:'pageNum',limitName:'pageSize'}
                    ,response:{statusCode:200}
                    //,tool: '#optTplMember'
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

                //操作按钮
                table3.on('tool(member)',
                    function(obj) {
                        console.log(obj)
                        var that = this;
                        switch (obj.event) {
                            case 'removeMember':
                                var data = obj.data;
                                layer.confirm('确认要解除绑定吗？',
                                    function(index) {
                                        var ids = []
                                        ids.push(data.id)
                                        var json = {
                                            "mtype":3,
                                            "id":equipment.teamId,
                                            "mIds":ids
                                        }
                                        equipment.cancelTeam(json,obj);
                                    });
                                break;
                        };
                    });
            });
        },

        cancelTeam : function(json,obj){
            //发异步删除数据
            Ajax.postJson("../../" + requestConfig.teamUrl.cancelTeam, null, JSON.stringify(json), "", function (res) {
                if (res.code == 200) {
                    $(obj).attr("tr").remove();
                    layer.msg('已解除!', {
                        icon: 1,
                        time: 1000
                    });
                }else{
                    layer.msg(res.msg);
                }
            }, null);
        },

        /**
         * 下拉框
         */
        selectTreeList: function(){
            var that = this;
            layui.config({
                base: "/static/lib/layui/lay/mymodules/"
            }).use(['treeSelect','form'], function () {
                var treeSelect= layui.treeSelect;
                treeSelect.render({
                    // 选择器
                    elem: '#tree',
                    // 数据
                    data: "../../"+"team/list/selectTree",
                    // 异步加载方式：get/post，默认get
                    type: 'post',
                    // 占位符
                    placeholder: '请选择团队',
                    // 是否开启搜索功能：true/false，默认false
                    search: true,
                    // 点击回调
                    click: function(d){
                        console.log(d);
                    },
                    // 加载完成后的回调函数
                    success: function (d) {
                        that.teamId  = d.data[0].id;
                        //选中节点，根据id筛选
                        treeSelect.checkNode('tree', that.teamId);
                        that.uavList();
                        that.carList();
                        that.memberList();
                    }
                });

            });
        },


        bindEvent: function () {
            //绑定按钮
            $("#bindButton").off('click').on('click',function () {
                if(equipment.teamId == 0){
                   layer.alert("请选择一个团队进行操作");
                   return;
                }
                Ajax.postJson("../../" + requestConfig.teamUrl.equipmentList, null, JSON.stringify({'id':equipment.teamId}), "", function (res) {
                    if (res.code == 200) {
                        equipment.data = res.data;
                        xadmin.open('设备管理','./equipment_add.html');
                    }else{
                        layer.alert(res.msg)
                    }
                }, null);
            });

            //添加按钮
            $("#addMemberButton").off('click').on('click',function () {
                if(equipment.teamId == 0){
                    layer.alert("请选择一个团队进行操作");
                    return
                }
                user.src = 'team_user';
                user.teamId = equipment.teamId;
                xadmin.open('添加成员','../user/user_add.html',420,520,false);
            });
        },

        tableReload: function () {
            equipment.table.reload({
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });

        }

    }

    window.equipment = equipment;
    equipment.init();
})();










