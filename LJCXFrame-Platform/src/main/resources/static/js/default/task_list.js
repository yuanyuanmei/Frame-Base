/**
 * TODO 任务详情页加载
 * 2019-11-19 by edify
 *
 */
(function() {
    var oTaskList = {
        url: {
            getTaskList: "team/task/pageList",  //获取用户信息
            getDicDesc: "/sys/dic/list", //获取字典列表
            addTeamTask: "/team/task/save", //添加任务
            cancelTeamTask: "team/task/del", //取消任务
        },
        total: 0,
        teamId:0,

        init: function () {
            layui.config({
                base: '../static/libs/layui/lay/'
            }).extend({
                treeSelect: 'treeSelect/treeSelect'
            });
            this.selectTreeList();
            this.TaskTypeList();
            this.bindEvent();
        },

        getInfoByTeam: function (team) {
            this.bindTask({"teamId":team.id}); //任务
            this.TaskTeam(team);
            this.bindPage();
        },


        /**
         * TODO 页面元素事件绑定
         */
        bindEvent: function () {
            var that = this;

            // 登出，清理缓存teamId
            $(document).on('click',"#logout", function (e) {
                sessionStorage.clear();
                location.href="/logout";
            });


            $('body').off('click').on('click', function (e) {
                var target = e.srcElement || e.target, em = target, i = 1;
                while (em && !em.id && i <= 4) {
                    em = em.parentNode;
                    i++;
                }
                if (!em || !em.id) {
                    return;
                }
                // 用户信息 点击事件
                if (em.id.indexOf('user-name-label') != -1) {
                    $('.head-right .user-menu').slideToggle();
                }else{
                    $('.head-right .user-menu').fadeOut();
                }

            });

            /* 任务 搜索事件 */
            $("#searchBtn").on('click', function (e) {
                var json = {
                    'name':$("#select_task").val(),
                    'type':$("#search-taskType option:selected").val(),
                    'createTime':$("#search-createTime").val(),
                    'completeStatus':$("input[name='search-taskState']:checked").val(),
                    'teamId': oTaskList.teamId,
                }
                oTaskList.bindTask(json);
            });

            /* 取消任务事件 */
            $(document).on('click',".cancelTask", function (e) {
                var that = this;
                layer.confirm('确定取消该任务吗', {
                    btn: ['确定', '取消'] //可以无限个按钮
                }, function(index1, layero1){
                    Ajax.postJson(oTaskList.url.cancelTeamTask, null, JSON.stringify({"id":$(that).attr('layer-id')}), "", function (res) {
                        if(res.code == 200){
                            layer.alert("取消成功", {
                                    icon: 6,
                                    skin: 'layer-ext-moon',
                                },
                                function (index2,layero2) {
                                    oTaskList.bindTask({'teamId':oTaskList.teamId}); //任务
                                    //关闭当前frame
                                    layer.close(index1);
                                    layer.close(index2);
                                });
                        }
                    }, function (XMLHttpRequest, textStatus, errorThrown) { });

                }, function(index){
                    //按钮【按钮二】的回调
                    layer.close(index);
                });

            });
        },

        /**
         * 下拉框
         */
        selectTreeList: function(){
            var that = this;
            layui.use(['treeSelect','form','laydate'], function () {
                var treeSelect= layui.treeSelect;
                var form = layui.form
                    ,laydate = layui.laydate;
                //初始化 日期范围
                laydate.render({
                    elem: '#search-createTime'
                    ,range: true
                });

                treeSelect.render({
                    // 选择器
                    elem: '#team-selTree',
                    // 数据
                    data: "team/list/treeList",
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
                        console.log(d);
                        Ajax.postJson("team/list/list", null, null, "", function (result) {
                            for(var i = 0;i< result.data.length; i++){
                                if(!result.data[i].canSelect){
                                    //未选中节点，添加颜色
                                    treeSelect.cantSelect('tree', result.data[i].id);
                                }
                            }

                            if(result.data.length > 0){
                                for(var i = 0;i< result.data.length; i++){
                                    if(sessionStorage.getItem("teamId") == null && result.data[i].canSelect){
                                        //选中节点，根据id筛选
                                        sessionStorage.setItem("teamId",result.data[i].id);
                                        oTaskList.teamId = result.data[i].id;
                                        treeSelect.checkNode('tree', result.data[i].id);
                                        that.getInfoByTeam(result.data[i]);
                                        break;
                                    }else if(result.data[i].id == sessionStorage.getItem("teamId")){
                                        oTaskList.teamId = result.data[i].id;
                                        treeSelect.checkNode('tree', result.data[i].id);
                                        that.getInfoByTeam(result.data[i]);
                                        break;
                                    }
                                }
                            }
                        }, function (XMLHttpRequest, textStatus, errorThrown) {});


                    }
                });

            });
        },

        bindTask:function (json) {
            $.ajax({
                type : 'POST',
                dataType : "json",
                url : '../../'+this.url.getTaskList,
                data : JSON.stringify(json),
                contentType : "application/json; charset=utf-8",
                cache : false,
                async : false,
                success : function(res) {
                    $("#list-table table tbody").empty();
                    if (res.code == 200){
                        oTaskList.total = res.data.total;
                        for(var i = 0; i < res.data.records.length; i++){
                            var task = res.data.records[i];
                            $("#list-table table tbody").append("<tr>\n" +
                                "<td style='cursor: pointer' onclick=location.href='task_detail.html\/"+task.id+"'>"+task.name+"</td>\n" +
                                "<td>"+task.typeName+"</td>\n" +
                                "<td>"+task.completeStatusName+"</td>\n" +
                                "<td>"+task.teamName+"</td>\n" +
                                "<td>"+task.createTime+"</td>\n" +
                                "<td class=\"task_tools\">\n" +
                                "<a onclick='saveTask("+JSON.stringify(task)+")' style='cursor:pointer' class=\"edit-img fl\">编辑任务</a>\n" +
                                "<a id=\"message-label\" class=\"line-edit-img fl\">编辑航线</a>\n" +
                                "<a  class='cancelTask' layer-id="+task.id+" style='cursor:pointer' class=\"cancle-img fl\">取消任务</a>\n" +
                                "</td>\n" +
                                "</tr>");
                        }
                    }
                },
            });
        },

        bindPage:function(){
            layui.use(['laypage', 'layer'], function(){
                var laypage = layui.laypage
                    ,layer = layui.layer;

                //总页数大于页码总数
                laypage.render({
                    elem: 'pageUtil'
                    ,count: oTaskList.total //数据总数
                    ,jump: function(obj,first){
                        //首次不执行
                        if(!first){
                            oTaskList.bindTask({"pageNum":obj.curr,"pageSize":obj.limit})  //加载数据
                        }
                    }
                });
            });
        },

        /* 任务类型列表绑定事件 */
        TaskTypeList: function () {
            Ajax.postJson(oTaskList.url.getDicDesc, null, JSON.stringify({"code":"TASK_TYPE"}), "", function (result) {
                var dic_list = "";
                if (result.code == 200){
                    for(var i = 0;i< result.data.length; i++){
                        var dic = result.data[i];
                        dic_list += "<option value="+dic.id+">"+dic.name+"</option>"
                    }
                    $("#search-taskType").append(dic_list);
                    $("#add-taskType").html(dic_list);
                    layui.use(['form'], function() {
                        var form = layui.form;
                        form.render('select');
                    });
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {});
        },

        /**
         * 任务执行团队
         */
        TaskTeam: function (team) {
            $("#search-taskTeam").val(team.name);
            $("#add-taskTeam").val(team.name);
            $("#taskTitle").text(team.name);
        },
    }

    window.oTaskList = oTaskList;
    oTaskList.init();
})();

/* 添加任务事件 */
function saveTask(item) {
    //layer-addtask
    //页面层
    layer.open({
        type: 1 //Page层类型
        , skin: 'layer-ext-myskin'
        , area: ['402px', '403px']
        , title: ['新增任务', 'font-size:16px']
        , btn: ['确定', '取消']
        , shadeClose: true
        , shade: .6 //遮罩透明度
        , maxmin: false //允许全屏最小化
        , content: $("#layer-addtask")
        , success: function () {
            if(item != undefined){
                $("#add-taskname").val(item.name);
                $("#add-taskType").val(item.type);
                $("#add-taskmemo").val(item.memo);
                layui.use(['form'], function() {
                    var form = layui.form;
                    form.render('select');
                });
            }else{
                //$('#addtask-form')[0].reset();
                $("#add-taskname").val("");
                $("#add-taskType").val(1);
                $("#add-taskmemo").val("");
                layui.use(['form'], function() {
                    var form = layui.form;
                    form.render('select');
                });
            }

        }
        , yes: function (index1, layero1) {
            if($("#add-taskname").val() == ""){
                layer.alert("任务名称不能为空");
                return false;
            }
            var json = {
                "name": $("#add-taskname").val(),
                "type": $("#add-taskType option:selected").val(),
                "teamId": oTaskList.teamId,
                "memo": $("#add-taskmemo").val(),
            }
            if(item != undefined){
                json.id = item.id;
            }
            Ajax.postJson(oTaskList.url.addTeamTask, null, JSON.stringify(json), "", function (res) {
                if (res.code == 200) {
                    layer.close(index1);
                    layer.alert("保存成功", {
                            icon: 6,
                            color: '#FFFFFF;',
                        },
                        function (index2, layero2) {
                            oTaskList.bindTask({'teamId': oTaskList.teamId}); //任务
                            //关闭当前frame
                            layer.close(index2);

                        });
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {
            });
        }
    });
};