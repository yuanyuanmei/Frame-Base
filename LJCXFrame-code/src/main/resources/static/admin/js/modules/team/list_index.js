/**
 * 团队列表
 */
(function () {
    var team = {
        id:0,
        info:null,
        init:function () {
            //this.teamList();
            this.treeList();
            this.bindEvent();
        },

        //树结构加载
        treeList: function(){
            var layout = [
                { name: '名称', treeNodes: true, headerClass: 'value_col', colClass: 'value_col', style: '' },
                //{ name: '排序', field: 'roomId', headerClass: 'value_col', colClass: 'value_col', style: ''},
                { name: '创建时间', field: 'createTime', headerClass: 'value_col', colClass: 'value_col', style: ''},
                {
                    name: '操作',
                    headerClass: 'value_col',
                    colClass: 'value_col',
                    style: 'width: 20%',
                    render: function(row) {
                        return "<a class='layui-btn layui-btn-info layui-btn-sm' onclick='add(" + row + ")'><i class='layui-icon'>&#xe640;</i> 新增</a>"
                               +"<a class='layui-btn layui-btn-normal layui-btn-sm' onclick='edit(" + row + ")'><i class='layui-icon'>&#xe640;</i> 编辑</a>"
                               +"<a class='layui-btn layui-btn-danger layui-btn-sm' onclick='del(" + row + ")'><i class='layui-icon'>&#xe640;</i> 删除</a>"; //列渲染
                    }
                },

            ];
            layui.config({
                base: "/static/lib/layui/lay/modules/"
            }).use(['form', 'treetable', 'layer'], function() {
                var layer = layui.layer, form = layui.form, $ = layui.jquery;
                var roleNode = [];
                $.ajax({
                    type : 'POST',
                    dataType : "json",
                    url : "../../" + requestConfig.teamUrl.treeList,
                    data : JSON.stringify({"id":null}),
                    contentType : "application/json; charset=utf-8",
                    cache : false,
                    async : false,
                    success : function(res) {
                        console.log(res);
                        if (res.code == 200){
                            roleNode = res.data;
                        }
                    },
                });
                //console.log(JSON.stringify(roleNode))
                var tree = layui.treetable({
                    elem: '#test', //传入元素选择器
                    spreadable: false, //设置是否全展开，默认不展开
                    checkbox : false,
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

        teamList:function() {
            var that = this;
            layui.use('form', function(){
                var form = layui.form;
                Ajax.postJson("../../" + requestConfig.teamUrl.teamList, null, JSON.stringify({'pid':0}), "", function (res) {
                    if (res.code == 200) {
                        var selectHtml = "";
                        var teamId = 0;
                        for(var i = 0;i< res.data.length; i++){
                            selectHtml += "<option value="+res.data[i].id+">"+res.data[i].name+"</option>"
                        }
                        $("select").append(selectHtml);
                        team.id =  res.data[0].id;
                        that.bindTree(team.id);
                        form.render();
                    }else{
                        layer.alert(data.msg)
                    }
                }, null);


                form.on('select(teamList)',function (data) {
                    that.id = data.value;
                    that.bindTree(data.value);
                });
            });
        },

        // bindTree: function(teamId){
        //     layui.config({
        //         base: "/static/lib/layui/lay/mymodules/"
        //     }).use(['jquery','table','eleTree','code'], function(){
        //         var $ = layui.jquery;
        //         var eleTree = null;
        //         eleTree = layui.eleTree;
        //         Ajax.postJson("../../" + requestConfig.teamUrl.treeList, null, JSON.stringify({'id':teamId}), "", function (res) {
        //             if (res.code == 200) {
        //                 console.log(res.data)
        //                 var el = eleTree.render({
        //                     elem: '.ele1',
        //                     data: res.data,
        //                     showCheckbox: true,
        //                     contextmenuList: [{eventName: "addSame", text: "添加同级节点"},{eventName: "addChild", text: "添加子节点"},{eventName: "upd", text: "编辑"}],
        //                     defaultExpandAll: true,
        //                 });
        //             }else{
        //                 layer.alert(res.msg)
        //             }
        //         }, null);
        //
        //         // 添加同级节点事件
        //         eleTree.on("nodeAddSame(data1)",function(d) {
        //             team.data = d.data;
        //             team.data.opt = 'addSame';
        //             xadmin.open('添加团队','../team/list_add.html');
        //             // 取消编辑
        //         });
        //
        //         // 添加子节点事件
        //         eleTree.on("nodeAddChild(data1)",function(d) {
        //             team.data = d.data;
        //             team.data.opt = 'addChild';
        //             xadmin.open('添加团队','../team/list_add.html');
        //             // 取消编辑
        //         });
        //
        //         // 节点被编辑事件
        //         eleTree.on("nodeUpd(data1)",function(d) {
        //             team.data = d.data;
        //             team.data.opt = 'upd';
        //             xadmin.open('编辑团队','../team/list_add.html');
        //             // 取消编辑
        //         });
        //     });
        // },

        bindEvent: function(){
            //添加按钮
            $("#addButton").on('click',function () {
                team.data = null;
                xadmin.open('团队管理','./list_add.html',400,500,false)
            });
            

        },


    };
    window.team = team;
    team.init();
})();

function del(row) {
    layer.confirm('确认要删除吗？',
        function(index) {
            Ajax.postJson("../../" + requestConfig.teamUrl.del, null, JSON.stringify({'id':row.id}), "", function (res) {
                if (res.code == 200) {
                    layer.alert(res.msg);
                }else{
                    layer.alert(res.msg);
                }
            }, null);

        });

}

function add(row) {
    team.data = row;
    team.data.opt = 'addChild';
    xadmin.open('添加团队','../team/list_add.html',420,520,false);
}

function edit(row) {
    team.data = row;
    team.data.opt = 'upd';
    xadmin.open('编辑团队','../team/list_add.html',420,520,false);
}




