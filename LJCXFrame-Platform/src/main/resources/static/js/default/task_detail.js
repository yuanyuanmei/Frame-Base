/**
 * TODO 任务详情页加载
 * 2019-11-19 by edify
 *
 */
(function() {
    var oTaskDetail = {
        url: {
            getTaskInfo: "team/task/info",  //获取用户信息
            findRoutes: "/team/uav/findRoutes",
        },

        init: function () {
            this.bindTask();
            this.bindEvent();
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

        },

        bindTask:function () {
            $.ajax({
                type : 'POST',
                dataType : "json",
                url : '../../'+this.url.getTaskInfo,
                data : JSON.stringify({"id":$("#taskId").val()}),
                contentType : "application/json; charset=utf-8",
                cache : false,
                async : false,
                success : function(res) {
                    if (res.code == 200){
                        //标题版
                        $("#taskTitle").text(res.data.name);
                        $("#flightCount").text(res.data.flightCount);
                        $("#flightSumSeconds").html(res.data.flightSumSecondsStr);
                        $("#flightAvgSeconds").html(res.data.flightAvgSecondsStr);
                        //信息面板
                        $("#infoTitle").text(res.data.name);
                        $("#infoTypeName").text(res.data.typeName);
                        $("#infoCompleteStatus").text(res.data.completeStatusName);
                        $("#infoTeamName").text(res.data.teamName);
                        $("#infoCreateTime").text(res.data.createTime);
                        $("#infoMemo").text(res.data.memo);
                        //任务记录集合
                        $("#detail-list table tbody").empty();
                        for(var i = 0; i< res.data.recordsList.length; i++){
                            var item = res.data.recordsList[i];
                            $("#detail-list table tbody").append("<tr>\n" +
                                "<td width=\"15%\">"+item.model+"</td>\n" +
                                "<td width=\"25%\">"+item.no+"</td>\n" +
                                "<td width=\"10%\">"+item.nickname+"</td>\n" +
                                "<td width=\"30%\">"+item.endTime+"</td>\n" +
                                "<td width=\"15%\">"+item.flightSeconds+"</td>\n" +
                                "<td width=\"5%\" style=\"border-right: 1px solid #015b69;\"><a onclick='oTaskDetail.layerMap("+item.id+")'>回放</a></td>\n" +
                                "</tr>");
                        }

                        //任务成果
                        for(var i = 0; i< res.data.fileList.length; i++){
                            var file = res.data.fileList[i];
                            if(file.suffix == 'png' || file.suffix == 'jpg'){
                                $("#detail-result").append("<div class=\"picture\">\n" +
                                    "<img src="+file.thumbUrl+" style='width:100%;height:100%;' />\n" +
                                    "<a class=\"download\" onclick='javascript:window.location.href = \"../../sys/file/download?&id="+file.id+"&delete=\"+false;'></a>\n" +
                                    "</div>");
                            }else{
                                $("#detail-result").append("<div class=\"detail-video\">\n" +
                                    "<img src="+file.url+" style='width:100%;height:100%;' />\n" +
                                    "<p>02:35</p>\n" +
                                    "<a class=\"download\" onclick='javascript:window.location.href = \"../../sys/file/download?&id="+file.id+"&delete=\"+false;'></a>\n" +
                                    "</div>");
                            }

                        }

                    }
                },
            });
        },

        layerMap: function (id) {
            var that = this;
            layui.use(['layer', 'form'], function(){
                var layer = layui.layer
                    ,form = layui.form;

                layer.open({
                    type: 1 //Page层类型
                    , skin: 'layer-ext-myskin'
                    , area: ['1200px', '800px']
                    , title: ['航线规划', 'font-size:16px']
                    , shadeClose: true
                    , shade: .6 //遮罩透明度
                    , maxmin: false //允许全屏最小化
                    , content: $("#layer-map")
                    , success: function () {
                        $.ajax({
                            type : 'POST',
                            dataType : "json",
                            url : '../../'+that.url.findRoutes,
                            data : JSON.stringify({"recordsId":id}),
                            contentType : "application/json; charset=utf-8",
                            cache : false,
                            async : false,
                            success : function(res) {
                                if (res.code == 200){
                                    if(oTrack.map == null){
                                        oTrack.init(res.data);
                                    }else{
                                        oTrack.getTrack(res.data);
                                    }
                                }
                            }
                        });
                    }
                    , yes: function (index1, layero1) {
                    }
                    ,end: function () {
                        MapGD2D.clearAll();
                    }
                });

            });


        }

    }

    window.oTaskDetail = oTaskDetail;
    oTaskDetail.init();
})();