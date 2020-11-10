/**
 * 飞行区域管理列表
**/
(function () {

    var equipmentadd = {
        data :null,
        teamId: null,
        init:function () {
            this.save();
            this.info();
            this.checkboxJs();
        },

        info: function(){
            layui.use(['form','element'], function(){
                var form = layui.form,
                    element = layui.element;
                if($(window.parent.equipment.data).length > 0){
                    var equipment = $(window.parent.equipment.data)[0];
                    equipmentadd.teamId = equipment.id;
                    $("#id").val(equipment.id);
                    $("#name").val(equipment.name);
                    var uavHtml = "";
                    for(var i = 0; i < equipment.equipUavList.length; i++){
                        var uav = equipment.equipUavList[i];
                        uavHtml += "<input name=\"uavIds\" lay-skin=\"primary\" type=\"checkbox\" title="+uav.name+" value="+uav.id+">";
                    }
                    $("#uavList").html(uavHtml);
                    var carHtml = "";
                    for(var i = 0; i < equipment.equipCarList.length; i++){
                        var car = equipment.equipCarList[i];
                        carHtml += "<input name=\"carIds\" lay-skin=\"primary\" type=\"checkbox\" title="+car.name+" value="+car.id+">";
                    }
                    $("#carList").html(carHtml);
                    var memberHtml = "";
                    for(var i = 0; i < equipment.equipMemberList.length; i++){
                        var member = equipment.equipMemberList[i];
                        memberHtml += "<input name=\"memberIds\" lay-skin=\"primary\" type=\"checkbox\" title="+member.nickname+" value="+member.id+">";
                    }
                    $("#memberList").html(memberHtml);
                    form.render('checkbox');
                }


            });
        },

        checkboxJs: function(){
            layui.use(['form','layer'], function(){
                $ = layui.jquery;
                var form = layui.form
                    ,layer = layui.layer;

                form.on('checkbox(father)', function(data){

                    if(data.elem.checked){
                        $(data.elem).parent().siblings('td').find('input').prop("checked", true);
                        form.render();
                    }else{
                        $(data.elem).parent().siblings('td').find('input').prop("checked", false);
                        form.render();
                    }
                });
            });

            var _hmt = _hmt || []; (function() {
                var hm = document.createElement("script");
                hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
                var s = document.getElementsByTagName("script")[0];
                s.parentNode.insertBefore(hm, s);
            })();
        },

        save: function () {
            layui.use('form', function(){
                var form = layui.form;
                //监听提交
                form.on('submit(add)',
                    function(data) {
                        //获取checkbox[name='like']的值
                        var uavIds = new Array();
                        $("input:checkbox[name='uavIds']:checked").each(function(i){
                            uavIds[i] = $(this).val();
                        });

                        var carIds = new Array();
                        $("input:checkbox[name='carIds']:checked").each(function(i){
                            carIds[i] = $(this).val();
                        });

                        var memberIds = new Array();
                        $("input:checkbox[name='memberIds']:checked").each(function(i){
                            memberIds[i] = $(this).val();
                        });

                        var json = {
                            "id": equipmentadd.teamId,
                            "uavIds" : uavIds,
                            "carIds" : carIds,
                            "memberIds" : memberIds
                        }
                        Ajax.postJson("../../" + requestConfig.teamUrl.bindTeam, null, JSON.stringify(json), "", function (data) {
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
        },

        tableReload: function () {
            $(window.parent.equipment.table)[0].reload({
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }

    }

    window.equipmentadd = equipmentadd;
    equipmentadd.init();
})();










