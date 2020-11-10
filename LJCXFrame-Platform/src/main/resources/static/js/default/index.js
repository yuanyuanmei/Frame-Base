/**
 * TODO 首页数据加载
 * 2019-11-19 by edify
 *
 * getDataByTeam
 * bind_count(teamid);    //设备分类数量统计
 * bind_online(teamid); // 设备在线率
 * bind_state(teamid); // 无人机状态统计
 */
(function() {
    var teamList = null;
    var teamId = -1;
    var team = null;
    var activePlanId = 0;
    var GroupId = ""; // im群组Id
    //当前用户身份
    var loginInfo = {
        'sdkAppID': '', //用户所属应用id,必填
        // 'accountType': accountType, //用户所属应用帐号类型, 已废弃
        'identifier': '', //当前用户ID,必须是否字符串类型，必填
        'userSig': '',
        //当前用户身份凭证，必须是字符串类型，必填
        'identifierNick': null, //当前用户昵称，不用填写，登录接口会返回用户的昵称，如果没有设置，则返回用户的id
        'headurl': 'img/me.jpg' //当前用户默认头像，选填，如果设置过头像，则可以通过拉取个人资料接口来得到头像信息
    };
    var oIndex = {
        url:{
            getUserInfo: "user/info",  //获取用户信息
            getDataByTeam : "team/list/list",  //获取团队列表
            getTeamMembers : "team/list/info",  //按编号获取团队成员列表
            getPlaneState : "/team/uav/getState",   //无人机状态统计
            getMembersCount : "team/list/getDataNums",   //设备分类数量统计
            getOnline:"/team/list/onlineNums",          //设备在线率统计
            getMessages: "data/scenereport/list",     // 获取当日图文上报信息
            getTeamLayers: "layer/all/list", //获取团队所有图层
            getRoomAndUserSign : "team/list/getRoomAndUserSign", // 单人呼叫音视频
            getPushAddress:"team/uav/getPushAddress", //平台远程开启飞机推流
            getGroupMsg: "/im/group/getMsg", //获取群组历史消息
            sendGroupMsg: "/im/group/sendMsg", //发送群组消息
            getTeamTask: "/team/task/list", //获取团队任务
            getDicDesc: "/sys/dic/list", //获取字典列表
            addTeamTask: "/team/task/save", //添加任务
            cancelTeamTask: "team/task/del", //取消任务
            taskChart:"team/task/initChart", // 任务统计
            planPanel:"/team/uav/planPanel", //飞机面板信息

        },
        m_TeamMembers: {
            "mans":[],
            "cars":[],
            "planes":[],
            "online_mans":[],
            "online_cars":[],
            "online_planes":[],
            "offline_mans":[],
            "offline_cars":[],
            "offline_planes":[],
            "panoramas":[],
            "scenereports":[],
            "layers":[],
            "noFlyAreas":[],
            "limitFlyAreas":[],
            "authFlyAreas":[],
            "warnFlyAreas":[],
            "enhancedWarnFlyAreas":[],
            "users":[],
        }, // 团队成员信息

        currentUser:[],

        init: function () {
			layui.config({
			    base: '../static/libs/layui/lay/'
			}).extend({
			    treeSelect: 'treeSelect/treeSelect'
			});
			
            this.bindUser();
            this.selectTreeList();
            this.bindEvent();   // 页面元素事件绑定
            this.TaskTypeList();
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

		            },
		            // 加载完成后的回调函数
		            success: function (d) {
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
                                        sessionStorage.setItem("teamId",result.data[i].id);
                                        that.teamId  = result.data[i].id;
                                        that.team = result.data[i];
                                        //选中节点，根据id筛选
                                        treeSelect.checkNode('tree', that.teamId);
                                        that.getInfoByTeam(result.data[i]);
                                        break;
                                    }else{
                                        if(result.data[i].id == sessionStorage.getItem("teamId")){
                                            //选中节点，根据id筛选
                                            that.teamId  = result.data[i].id;
                                            that.team = result.data[i];
                                            treeSelect.checkNode('tree', that.teamId);
                                            that.getInfoByTeam(result.data[i]);
                                            break;
                                        }
                                    }
                                }
                            }
                        }, function (XMLHttpRequest, textStatus, errorThrown) {});


		            }
		        });
		
		    });
		},

        bindUser:function(){
            var that = this;
            Ajax.postJson(oIndex.url.getUserInfo, null, null, "", function (result) {
                if (result.code == 200){
                    $("#user-name-label span").text(result.data.nickname);
                    that.currentUser = result.data;
                    MapGD2D.setZoomAndCenter([result.data.lng,result.data.lat],MapGD2D.map.zoom);
                    oMessage.initMsgListener(result.data.id);
                    //给用户对象赋值
                    loginInfo.sdkAppID = result.data.username;
                    loginInfo.identifier = result.data.username;
                    loginInfo.userSig = result.data.userSig;
                    loginInfo.identifierNick = result.data.nickname;
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {});
        },
        /**
         * TODO 页面元素事件绑定
         */
        bindEvent:function(){
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

                /*图层面板全局关闭*/
                if (em.id  != "div_nofly") {
                    $("#div_nofly").fadeOut();
                }

                /*图层面板全局关闭*/
                if (em.id != "div_layer") {
                    $("#div_layer").fadeOut();
                }

            });

            // IM 消息发送
            $(document).on('click',"#sendMsgBtn", function (e) {
                var msg =  $("#msgContent").val();
                if(msg.trim() == ""){
                    show("消息内容不能为空");
                }
                var json = {
                    "GroupId":that.GroupId,
                    "From_Account":that.currentUser.username,
                    "MsgBody":[{"MsgId":1,"MsgContent":{"Text":msg}}],
                }
                Ajax.postJson(oIndex.url.sendGroupMsg, null, JSON.stringify(json), "", function (result) {
                    if (result.code == 200){
                        $("#msgContent").val("");
                    }
                }, function (XMLHttpRequest, textStatus, errorThrown) {

                });
            });
            /* 禁飞区面板打开关闭 */


            /* header 主菜单 点击事件 */
            $("#header-menu ul li").on('click', function (e) {
                var _team_item = $(e.target).attr("data-item");
                // active 样式切换
                $("#header-menu ul li").removeClass("active");
                $(e.target).addClass("active");
                // 切换左侧面板 left-panel
                for(var i=0;i<$(".left-panel").length;i++){
                    var item = $(".left-panel")[i];
                    if ($(item).attr("data-filter") === _team_item){
                        $(item).show();
                    }else{
                        $(item).hide();
                    }
                }

                // 切换中下统计面板 center_box_panel
                for(var i=0;i<$(".center_box_panel").length;i++){
                    var item = $(".center_box_panel")[i];
                    if ($(item).attr("data-filter") === _team_item){
                        $(item).show();
                    }else{
                        $(item).hide();
                    }
                }

                // 切换面板加载不同内容
                if(_team_item == 'team'){
                    oIndex.initChart(oIndex.team); //统计图
                }else if(_team_item == 'task'){
                    oIndex.getInfoByTask(oIndex.team); //任务
                }else if(_team_item == 'message'){ //现场上报
                    oIndex.getMessages(oIndex.teamId); //
                }else if(_team_item == 'im-message'){ // im
                    oIndex.getInfoByIm();
                }
            });

            /*人机车分类查看tab点击事件*/
            $("#deviceType li a").on('click', function (e) {
                var $target = $(e.target).parent();
                var _team_item = $target.attr("data-item");

                if ($("#deviceType").attr("data-item") != _team_item){
                    $("#deviceType").attr("data-item",_team_item);
                    $("#deviceType li").removeClass("active");
                    $target.addClass("active");

                    /*分类显示团队成员显示 all man plane car*/
                    that.toggleTeamMembers(_team_item);
                }
            });

            /* 任务 高级搜索面板 显示隐藏事件 */
            $("#search-label").on('click', function (e) {
                if ($("#search-form").css("display") === 'none'){
                    $("#task_list").css("height",$("#task-panel").height() -340 + "px");
                    $("#search-form").fadeIn();
                }else{
                    $("#task_list").css("height",$("#task-panel").height() + 340 + "px");
                    $("#search-form").fadeOut();
                }
            });


            /* 任务 搜索事件 */
            $("#searchBtn").on('click', function (e) {
                var json = {
                    'name':$("#select_task").val(),
                    'type':$("#search-taskType option:selected").val(),
                    'createTime':$("#search-createTime").val(),
                    'completeStatus':$("input[name='search-taskState']:checked").val(),
                    'teamId': oIndex.teamId,
                }
                oIndex.bindTask(json);
            });

            /* 取消任务事件 */
            $(document).on('click',".cancelTask", function (e) {
                var that = this;
                layer.confirm('确定取消该任务吗', {
                    btn: ['确定', '取消'] //可以无限个按钮
                }, function(index1, layero1){
                    Ajax.postJson(oIndex.url.cancelTeamTask, null, JSON.stringify({"id":$(that).attr('layer-id')}), "", function (res) {
                        if(res.code == 200){
                            layer.alert("取消成功", {
                                    icon: 6,
                                    skin: 'layer-ext-moon',
                                },
                                function (index2,layero2) {
                                    oIndex.bindTask({'teamId':oIndex.teamId}); //任务
                                    oIndex.init_task(oIndex.teamId);  //任务面板
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

            /*人机车分类查看tab点击事件*/
            $("#deviceType li a").on('click', function (e) {
                var $target = $(e.target).parent();
                var _team_item = $target.attr("data-item");

                if ($("#deviceType").attr("data-item") != _team_item){
                    $("#deviceType").attr("data-item",_team_item);
                    $("#deviceType li").removeClass("active");
                    $target.addClass("active");

                    /*分类显示团队成员显示 all man plane car*/
                    that.toggleTeamMembers(_team_item);
                }
            });
            // 限飞区面板开关事件 nofly_switch
            $("#div_nofly .nofly_switch").on('click', function (e) {
                var _nofly = $(e.target).attr("data-nofly");
                if ($(e.target).hasClass("switch_off")){
                    $(e.target).removeClass("switch_off");
                    $(e.target).addClass("switch_on");
                    if(_nofly == 1){
                        oMap.toggleFeatures("noFly",true);
                    }else if(_nofly == 2){
                        oMap.toggleFeatures("limitFly",true);
                    }else if(_nofly == 3){
                        oMap.toggleFeatures("authFly",true);
                    }else if(_nofly == 4){
                        oMap.toggleFeatures("warnFly",true);
                    }else if(_nofly == 5){
                        oMap.toggleFeatures("enhanceFly",true);
                    }
                }else{
                    $(e.target).removeClass("switch_on");
                    $(e.target).addClass("switch_off");
                    if(_nofly == 1){
                        oMap.toggleFeatures("noFly",false);
                    }else if(_nofly == 2){
                        oMap.toggleFeatures("limitFly",false);
                    }else if(_nofly == 3){
                        oMap.toggleFeatures("authFly",false);
                    }else if(_nofly == 4){
                        oMap.toggleFeatures("warnFly",false);
                    }else if(_nofly == 5){
                        oMap.toggleFeatures("enhanceFly",false);
                    }
                }
            });

            // 图层面板开关事件 layer_switch
            $(document).on('click',"#div_layer .layer_switch", function (e) {
                var _layer = $(e.target).attr("data-layer");
                if ($(e.target).hasClass("switch_off")){
                    $(e.target).removeClass("switch_off");
                    $(e.target).addClass("switch_on");
                    //alert("open:" + _layer);
                    if(_layer == 1){
                        oMap.showMark(0,"panorama");
                    }else if(_layer == 3){
                        oMap.showMark(0,"scenereport");
                    }else if(_layer > 3){ // 自定义图层
                        for(var i=0;i<that.m_TeamMembers.layers.length;i++){
                            if(that.m_TeamMembers.layers[i].id == (_layer-3)){
                                if (that.m_TeamMembers.layers[i].layer){
                                    /*if (Array.isArray(that.m_TeamMembers.layers[i].layer)){
                                        for(var j=0;j<that.m_TeamMembers.layers[i].layer.length;j++){
                                            that.m_TeamMembers.layers[i].layer[j].setVisible(true);
                                        }
                                    }else{
                                        that.m_TeamMembers.layers[i].layer.setVisible(true);
                                    }*/
                                    var layer = that.m_TeamMembers.layers[i];
                                    that.m_TeamMembers.layers[i].layer.setVisible(true);
                                    // edify 定位
                                    MapGD2D.map.getView().setCenter([layer.lng,layer.lat]);
                                    MapGD2D.map.getView().setZoom(15);

                                    //that.m_TeamMembers.layers[i].layer.getExtent()
                                }
                            }
                        }
                        //oMap.showMark(_layer-3,"layer");
                    }
                }else{
                    $(e.target).removeClass("switch_on");
                    $(e.target).addClass("switch_off");
                    if(_layer == 1){
                        oMap.hideMark(0,"panorama");
                    }else if(_layer == 3){
                        oMap.hideMark(0,"scenereport");
                    }else if(_layer > 3){
                        //oMap.hideMark(_layer-3,"layer");
                        for(var i=0;i<that.m_TeamMembers.layers.length;i++){
                            if(that.m_TeamMembers.layers[i].id == (_layer-3)){
                                if (that.m_TeamMembers.layers[i].layer){
                                   /* if (Array.isArray(that.m_TeamMembers.layers[i].layer)){
                                        for(var j=0;j<that.m_TeamMembers.layers[i].layer.length;j++){
                                            that.m_TeamMembers.layers[i].layer[j].setVisible(false);
                                        }
                                    }else{
                                        that.m_TeamMembers.layers[i].layer.setVisible(false);
                                    }*/
                                    that.m_TeamMembers.layers[i].layer.setVisible(false);
                                }
                            }
                        }
                    }
                }
            });

            /*地图底图切换图层展示 隐藏事件*/
            $("#map-dm").mouseenter(function(){
                $("#map-pic-dm").fadeIn();
            });
            $("#map-dm").mouseleave(function(){
                $("#map-pic-dm").fadeOut();
            });

            /*地图底图切换事件*/
            $(".map-pic img").on("click",function (e) {
                var _type = $(e.target).attr("data-layer");
                if(_type != '3d'){
                    $(".map-b img").attr("src",$(e.target).attr("src"));
                }

                oMap.toggleBaseLayer(_type);
            });

            // 平台远程推流飞机事件
            $(document).on('click',"#team_members_list_online .plane-on .video",function(e) {
                var _planeId = $(e.target).attr("data-planeid");
                if($(e.target).attr("src").indexOf('on.png') > -1){
                    oIndex._callPlane(_planeId);
                }else if($(e.target).attr("src").indexOf('play.png') > -1){
                    var $target = $(e.target);
                    var _key = $target.attr("data-planeid");
                    oMedia.playMedia("plane_"+_key);  // 无人机播放

                    // 2020/02/28 飞机推流后地图切换位置
                    var p = $target.attr("data-location");
                    if (p != "" && p != undefined){
                        var _position = p.split(',');
                        if (_position[0] != 0 && _position[0] != "NaN"){
                            MapGD2D.setZoomAndCenter(_position, null);
                        }
                    }

                }else if($(e.target).attr("src").indexOf('off.png') > -1){

                }
            });

            // 人员在线呼叫事件
            $('#team_members_list_online').on("click",'.man-on .video', function(e) {
                var _userId = $(e.target).attr("data-userid");
                //是否被邀请，0未邀请，1.已邀请
                var isInvite = 0;
                if($(e.target).attr("src").indexOf('on.png') > -1){
                    isInvite = 1 ;
                }
                oIndex._callMan(_userId,isInvite);
            }).on("click",'.location', function(e) {
                if ($(e.target).attr("src").indexOf('on.png') > -1
                    || $(e.target).attr("src").indexOf('play.png') > -1){
                    //alert("data-location");
                    var p = $(e.target).attr("data-location");
                    if (p != "" && p != undefined){
                        var _position = p.split(',');
                        if (_position[0] != 0 && _position[0] != "NaN"){
                            MapGD2D.setZoomAndCenter(_position, null);
                        }
                    }
                }
            });

            /*工具栏点击事件*/
            $(".wraper_tools li a").on('click', function (e) {
                var _action = $(e.target).parent().attr("data-action");
                if (_action != "noFly"){
                    $("#div_nofly").fadeOut();
                }
                if (_action != "layer"){
                    $("#div_layer").fadeOut();
                }
                switch (_action) {
                    case "noFly":   // 限飞区面板
                        $("#div_nofly").fadeIn();
                        break;
                    case "layer":   // 图层面板
                        // var _left = $(".wraper_tools ul")[0].offsetLeft - $("#div_layer").width()/2 + 105;
                        // $("#div_layer").css("left",_left + "px");
                        $("#div_layer").fadeIn();
                        break;
                    case "distance":    // 测量距离工具
                        //MapGD2D.ruler.turnOn();
                        MapGD2D.mouseTool.rule();
                        break;
                    case "area":    // 测量面积工具
                        MapGD2D.mouseTool.measureArea();
                        break;
                    case "high":    // 测量高度工具
                        break;
                    case "excavating":  // 测量挖填方工具
                        break;
                    case "profile": // 剖面工具
                        break;
                    case "time":    // 时间轴
                        break;
                }
            });

            // 现场上传  图片点击放大事件
            $("#message-box-ul").on('click', "img", function (e) {
                var image = new Image();
                var src = $(this).attr("src");
                image.src = src;

                oIndex.zoomOutImage(src,image);
            });
            // IM  图片点击放大事件
            $(".infor-list").on('click', "img", function (e) {
                var image = new Image();
                var src = $(this).attr("src");
                image.src = src;

                oIndex.zoomOutImage(src,image);
            });

            $(window).on('resize',function(){

            });

        },

        //放大图片并居中
        zoomOutImage : function(_src,image){
            if(image.width>0||image.height>0){
                var width=image.width;
                var height=image.height;
                if(width>=height){//宽大于高
                    width=660;
                    height=width*(image.height/image.width);
                }else{//高大于宽
                    height=660;
                    width=height*(image.width/image.height);
                }
                layer.open({
                    type:1,
                    id:'layers-photo',
                    shade:[0.8, '#000000'],
                    title:false,
                    shadeClose:true,
                    area:[width+"px",height+"px"],
                    maxWidth:'100%',
                    content: '<img style="width:100%" src = "'+ _src +'">'
                });
            }
        },


        /**
         * 获取当日图文上报信息
         */
        getMessages:function(teamId){
           var that = this;
            Ajax.postJson(this.url.getMessages, null, JSON.stringify({"teamId":teamId}), "", function (res) {
                //console.log("当日图文上报信息~~~~~~~~，{}",res);
                $("#message-box-ul").empty();
                $("#tmpl_message").tmpl(res).appendTo('#message-box-ul');
                that.m_TeamMembers.scenereports = [];
                that.m_TeamMembers.scenereports.push.apply(that.m_TeamMembers.scenereports,res.data);
                // $(".reddot").text(that.m_TeamMembers.scenereports.length);
                // if(that.m_TeamMembers.scenereports.length > 99){
                //     $(".reddot").text("99+");
                // }

            }, function (XMLHttpRequest, textStatus, errorThrown) { });
        },


        /**
         * TODO 得到团队所有信息 ， 包括统计 、成员列表
         * @param teamid 团队ID
         */
        getInfoByTeam: function (team) {
            var that = this;
            that.teamId = team.id;
            this.bindTeamMembers(team.id,"all",true); // 成员列表信息
            this.bindLayers(team.id); //图层信息
            this.initChart(team);
        },

        /**
         * 加载任务
         */
        getInfoByTask: function(team){
            this.bindTask({"teamId":team.id});
            this.TaskTeam(team);
            this.init_task(team.id); //任务
        },

        /**
         * 加载im消息
         */
        getInfoByIm: function(){
            this.bindIMGroup(this.GroupId);
            selToID = this.GroupId;
        },

        /**
         * TODO 加载/筛选 团队图层信息
         * @param teamid    团队编号， -1 为所有
         */
        bindLayers:function (teamId) {
            var that = this;
            Ajax.postJson(this.url.getTeamLayers, null, JSON.stringify({ "teamId":teamId }), "", function (result) {
                if (result.code == 200){
                    that.m_TeamMembers.panoramas = [];
                    that.m_TeamMembers.layers = [];
                    that.m_TeamMembers.noFlyAreas = [];
                    that.m_TeamMembers.authFlyAreas = [];
                    that.m_TeamMembers.limitFlyAreas = [];
                    that.m_TeamMembers.warnFlyAreas = [];
                    that.m_TeamMembers.enhancedWarnFlyAreas = [];

                    that.m_TeamMembers.panoramas.push.apply(that.m_TeamMembers.panoramas,result.data.panoramaList);
                    that.m_TeamMembers.layers.push.apply(that.m_TeamMembers.layers,result.data.layerList);
                    that.m_TeamMembers.noFlyAreas.push.apply(that.m_TeamMembers.noFlyAreas,result.data.noFlyArea);
                    that.m_TeamMembers.authFlyAreas.push.apply(that.m_TeamMembers.authFlyAreas,result.data.authFlyArea);
                    that.m_TeamMembers.limitFlyAreas.push.apply(that.m_TeamMembers.limitFlyAreas,result.data.limitFlyArea);
                    that.m_TeamMembers.warnFlyAreas.push.apply(that.m_TeamMembers.warnFlyAreas,result.data.warnFlyArea);
                    that.m_TeamMembers.enhancedWarnFlyAreas.push.apply(that.m_TeamMembers.enhancedWarnFlyAreas,result.data.enhancedWarnFlyArea);
                    var custom_layer = "";
                    for(var i = 0;i<that.m_TeamMembers.layers.length; i++){
                        var item = that.m_TeamMembers.layers[i];
                        /**
                         * 返回值包括如下
                         * item.name item.url item.lng item.lat item.extent item.minZoom item.maxZoom item.createTime
                         */
                        custom_layer += "<li>\n" +
                            "            <div class=\"ulLayer-layer\">\n" +
                            "                <span>"+item.name+"</span>\n" +
                            "                <span>"+item.createTime+"</span>\n" +
                            "            </div>\n" +
                            "            <a class=\"switch_off layer_switch\" data-layer="+(item.id+3)+" data-location=\""+ item.lng + "," + item.lat +"\"></a>\n" +
                            "        </li>"
                        var _layer = new ol.layer.Tile({
                            title : item.name,
                            source: new ol.source.XYZ({
                                url: item.url
                            }),
                            minZoom: item.minZoom,  //14
                            maxZoom: item.maxZoom,  //22
                            // extent: [113.973488, 22.522699, 113.978882, 22.528247], // 红树湾
                            extent: item.extent == '' ? [] : item.extent.split(','), // item.extent 要是数组 如  [114.068579, 22.622768, 114.071771, 22.625441]
                            visible: false
                        });
                        that.m_TeamMembers.layers[i].layer = _layer;
                        MapGD2D.map.addLayer(_layer);
                    };
                    $("#div_layer ul").html("<li>\n" +
                        "            <div class=\"ulLayer-switch\">\n" +
                        "                <span class=\"fl\">全景</span>\n" +
                        "                <a class=\"switch_on fr layer_switch\" data-layer=\"1\"></a>\n" +
                        "            </div>\n" +
                        "            <div class=\"ulLayer-switch\">\n" +
                        "                <span class=\"fl\">航线</span>\n" +
                        "                <a class=\"switch_off fr layer_switch\" data-layer=\"2\"></a>\n" +
                        "            </div>\n" +
                        "            <div class=\"ulLayer-switch\">\n" +
                        "                <span class=\"fl\">现场</span>\n" +
                        "                <a class=\"switch_off fr layer_switch\" data-layer=\"3\"></a>\n" +
                        "            </div>\n" +
                        "        </li>"
                        + custom_layer);


                    that.createLayerMark(); //创建所有图标
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {

            });
        },


        /**
         * TODO 加载/筛选 团队成员
         * @param teamid    团队编号， -1 为所有
         */
        bindTeamMembers:function (teamId) {
            var that = this;
            Ajax.postJson(this.url.getTeamMembers, null, JSON.stringify({ "id":teamId }), "", function (result) {
                if (result.code == 200){
                    that.m_TeamMembers.online_cars = [];
                    that.m_TeamMembers.online_mans = [];
                    that.m_TeamMembers.online_planes = [];
                    that.m_TeamMembers.offline_cars = [];
                    that.m_TeamMembers.offline_mans = [];
                    that.m_TeamMembers.offline_planes = [];
                    that.m_TeamMembers.users = [];

                    that.m_TeamMembers.online_cars.push.apply(that.m_TeamMembers.online_cars,result.data.online_cars);
                    that.m_TeamMembers.online_mans.push.apply(that.m_TeamMembers.online_mans,result.data.online_members);
                    that.m_TeamMembers.online_planes.push.apply(that.m_TeamMembers.online_planes,result.data.online_uavs);
                    that.m_TeamMembers.offline_cars.push.apply(that.m_TeamMembers.offline_cars,result.data.offline_cars);
                    that.m_TeamMembers.offline_mans.push.apply(that.m_TeamMembers.offline_mans,result.data.offline_members);
                    that.m_TeamMembers.offline_planes.push.apply(that.m_TeamMembers.offline_planes,result.data.offline_uavs);

                    that.m_TeamMembers.users.push.apply(that.m_TeamMembers.users,result.data.online_members);
                    that.m_TeamMembers.users.push.apply(that.m_TeamMembers.users,result.data.offline_members);
                    that.createAllMark(); //创建所有图标

                    /*获取正在推流直播的无人机 播放信息*/
                    for (var i=0; i < that.m_TeamMembers.online_planes.length ; i++){
                        var _data = that.m_TeamMembers.online_planes[i];
                        if (_data.action == "play"){
                            oMedia.addMediaOpt("plane_" + _data.id,{
                                type:"plane",
                                id : _data.id,
                                name : _data.name,
                                m3u8 : _data.hlsPlayAddress,
                                flv  : _data.flyPlayAddress,
                                rmtp : _data.rtmpPlayAddress
                            });
                        }
                        if(_data.position != ''){
                            if(_data.position == "NaN,NaN"
                                || _data.position.split(",")[0] < 0 || _data.position.split(",")[0] > 180
                                || _data.position.split(",")[1] < 0 || _data.position.split(",")[1] > 180)
                            {
                                _data.position = "";
                            }
                        }

                    }

                    that.toggleTeamMembers("all"); //分类显示团队成员显示
                    that.GroupId = result.data.imGroupId;
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {

            });
        },


        bindIMGroup: function(GroupId){
            var that = this;
            var json = {
                "GroupId":GroupId,
                "ReqMsgNumber":5,
            }
            Ajax.postJson(this.url.getGroupMsg, null, JSON.stringify(json), "", function (result) {
                if (result.code == 200){
                    if(result.data.actionStatus == "OK"){
                        $(".infor-list").empty();
                        var reqMsgList = result.data.rspMsgList;
                        var msgDiv = "";
                        var info_align = "infor-col-l";
                        for(var i = 0; i< reqMsgList.length; i++){
                            //如果为空洞消息则过滤掉
                            if(reqMsgList[i].IsPlaceMsg == 1){
                                continue;
                            }
                            if(that.currentUser.username == reqMsgList[i].From_Account){
                                reqMsgList[i].nickname = that.currentUser.nickname;
                            }else{
                                for(var j = 0; j<that.m_TeamMembers.users.length; j++){
                                    var user = that.m_TeamMembers.users[j];
                                    if(reqMsgList[i].From_Account == user.username){
                                        reqMsgList[i].nickname = user.nickname;
                                        break;
                                    }
                                }
                            }
                            if(reqMsgList[i].nickname == undefined){
                                reqMsgList[i].nickname = reqMsgList[i].From_Account;
                            }

                            if(reqMsgList[i].From_Account == that.currentUser.username){
                                info_align = "infor-col-r";
                            }else{
                                info_align = "infor-col-l";
                            }
                             msgDiv = "<div class=\"dmMsg clearfix  "+info_align+"\">\n"+
                                 "<div class=\"infor-col2 clearfix\">\n" +
                                "                        <span class=\"name\">"+reqMsgList[i].nickname+"</span>\n" +
                                "                        <span class=\"time\">"+that.getMyDate(reqMsgList[i].MsgTimeStamp*1000)+"</span>\n" +
                                "                    </div>\n" +
                                 that.MsgBody(reqMsgList[i].MsgBody)+
                                "         </div>";

                            $(".infor-list").prepend(msgDiv);
                        }


                    }
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {

            });
        },

        MsgBody: function(MsgBody){
            var msgDiv = "";
            for(var i = 0;i<MsgBody.length;i++){
                if(MsgBody[i].MsgType == "TIMTextElem"){
                    msgDiv += "                    <div class=\"infor-col3 clearfix\">\n" +
                        "                        <p>"+MsgBody[i].MsgContent.Text+"</p>\n" +
                        "                    </div>";
                }else if(MsgBody[i].MsgType == "TIMImageElem"){
                    //image 有三种规格，默认第二种
                    msgDiv += "<div class=\"dialog\">\n" +
                        "                        <div class=\"imgDiv\">\n" +
                        "                            <img src=\""+ MsgBody[i].MsgContent.ImageInfoArray[2].URL +"\">\n" +
                        "                        </div>\n" +
                        "                    </div>";
                }else if(MsgBody[i].MsgType == "TIMSoundElem"){
                    msgDiv += "<div class=\"infor-col3 clearfix\">\n" +
                        "                        <p><audio src=" + MsgBody[i].MsgContent.Url + " controls=\"controls\" onplay=\"onChangePlayAudio(this)\" preload=\"none\" style='width: 230px;'></audio></p>\n" +
                        "                    </div>";
                }
            }
            return msgDiv;
        },

        getMyDate: function(str){
            var that = this;
            var oDate = new Date(str),
                oYear = oDate.getFullYear(),
                oMonth = oDate.getMonth()+1,
                oDay = oDate.getDate(),
                oHour = oDate.getHours(),
                oMin = oDate.getMinutes(),
                oSen = oDate.getSeconds(),
                oTime = oYear +'-'+ that.getzf(oMonth) +'-'+ that.getzf(oDay) +' '
                    + that.getzf(oHour) +':'+ that.getzf(oMin) +':'+that.getzf(oSen);//最后拼接时间
            return oTime;
        },

        //补0操作
         getzf:function(num){
            if(parseInt(num) < 10){
                num = '0'+num;
            }
            return num;
         },
		 
		toggleTeamTaskPanel:function(type){
			if (type == "teamPanel"){
			    $("#teamPanel").show()
				$("#taskPanel").hide()
			}else if(type == "taskPanel"){
			    $("#teamPanel").hide()
			    $("#taskPanel").show()
			}
		},
        /**
         * TODO 分类显示团队成员显示
         * @param type all man plane car
         */
        toggleTeamMembers:function(type){
            if (this.m_TeamMembers == null) return;

            $("#team_members_list_online").empty();
            $("#team_members_list_offline").empty();
            if (type == "all"){
                this._showMans();
                this._showPlanes();
                this._showCars();

                oMap.showMark(0,"man");
                oMap.showMark(0,"plane");
                oMap.showMark(0,"car");

            }else if(type == "man"){

                this._showMans();
                oMap.showMark(0,"man");
                oMap.hideMark(0,"plane");
                oMap.hideMark(0,"car");
            }else if(type == "plane"){
                this._showPlanes();
                oMap.hideMark(0,"man");
                oMap.showMark(0,"plane");
                oMap.hideMark(0,"car");
            }else if(type == "car"){
                this._showCars();
                oMap.hideMark(0,"man");
                oMap.hideMark(0,"plane");
                oMap.showMark(0,"car");
            }
        },

        /**
         * 创建所有图标
         */
        createAllMark:function(){
            var _mans = this.m_TeamMembers.online_mans;
            for(var i=0;i<_mans.length;i++){
                oMap._createManMark(_mans[i]);
            }

            var _cars = this.m_TeamMembers.online_cars;
            for(var i=0;i<_cars.length;i++){
                oMap._createCarMark(_cars[i]);
            }

            var _planes = this.m_TeamMembers.online_planes;
            for(var i=0;i<_planes.length;i++){
                oMap._createPlaneMark(_planes[i]);
            }
        },

        /**
         * 创建图层图标
         */
        createLayerMark:function(){
            //全景图
            var _panoramas = this.m_TeamMembers.panoramas;
            //现场上报
            var _scenereports  = this.m_TeamMembers.scenereports;
            //图层列表
            var _layers = this.m_TeamMembers.layers;
            //授权区
            var _authFlyAreas = this.m_TeamMembers.authFlyAreas;
            //禁飞区
            var _noFlyAreas = this.m_TeamMembers.noFlyAreas;
            //加强警告区
            var _enhancedWarnFlyAreas = this.m_TeamMembers.enhancedWarnFlyAreas;
            //限飞区
            var _limitFlyAreas = this.m_TeamMembers.limitFlyAreas;
            //警告区
            var _warnFlyAreas =  this.m_TeamMembers.warnFlyAreas;

            for(var i=0;i<_authFlyAreas.length;i++){
                if(_authFlyAreas[i].flyZoneType == 'POLY'){
                    for(var j = 0; j<_authFlyAreas[i].child.length; j++){
                        _authFlyAreas[i].child[j].name = _authFlyAreas[i].name;
                        _authFlyAreas[i].child[j].color = "#00a0e9";
                        _authFlyAreas[i].child[j].memberType = "authFly";
                        oMap._createAuthFlyMark(_authFlyAreas[i].child[j],false);
                    }
                }else if(_authFlyAreas[i].flyZoneType == 'CIRCLE'){
                    _authFlyAreas[i].color = "#00a0e9";
                    _authFlyAreas[i].memberType = "authFly";
                    oMap._createAuthFlyCircleMark(_authFlyAreas[i],false);
                }
            }

            for(var i=0;i<_noFlyAreas.length;i++){
                if(_noFlyAreas[i].flyZoneType == 'POLY'){
                    for(var j = 0; j<_noFlyAreas[i].child.length; j++){
                        _noFlyAreas[i].child[j].name = _noFlyAreas[i].name;
                        _noFlyAreas[i].child[j].color = "#DE4329";
                        _noFlyAreas[i].child[j].memberType = "noFly";
                        oMap._createAuthFlyMark(_noFlyAreas[i].child[j],false);
                    }
                }else if(_noFlyAreas[i].flyZoneType == 'CIRCLE'){
                    _noFlyAreas[i].color = "#DE4329";
                    _noFlyAreas[i].memberType = "noFly";
                    oMap._createAuthFlyCircleMark(_noFlyAreas[i],false);
                }
            }
            for(var i=0;i<_enhancedWarnFlyAreas.length;i++){
                if(_enhancedWarnFlyAreas[i].flyZoneType == 'POLY'){
                    for(var j = 0; j<_enhancedWarnFlyAreas[i].child.length; j++){
                        _enhancedWarnFlyAreas[i].child[j].name = _enhancedWarnFlyAreas[i].name;
                        _enhancedWarnFlyAreas[i].child[j].color = "#e98500";
                        _enhancedWarnFlyAreas[i].child[j].memberType = "enhanceFly";
                        oMap._createAuthFlyMark(_enhancedWarnFlyAreas[i].child[j],false);
                    }
                }else if(_enhancedWarnFlyAreas[i].flyZoneType == 'CIRCLE'){
                    _enhancedWarnFlyAreas[i].color = "#e98500";
                    _enhancedWarnFlyAreas[i].memberType = "enhanceFly";
                    oMap._createAuthFlyCircleMark(_enhancedWarnFlyAreas[i],false);
                }
            }

            for(var i=0;i<_limitFlyAreas.length;i++){
                if(_limitFlyAreas[i].flyZoneType == 'POLY'){
                    for(var j = 0; j<_limitFlyAreas[i].child.length; j++){
                        _limitFlyAreas[i].child[j].name = _limitFlyAreas[i].name;
                        _limitFlyAreas[i].child[j].color = "#786b5d";
                        _limitFlyAreas[i].child[j].memberType = "limitFly";
                        oMap._createAuthFlyMark(_limitFlyAreas[i].child[j],false);
                    }
                }else if(_limitFlyAreas[i].flyZoneType == 'CIRCLE'){
                    _limitFlyAreas[i].color = "#786b5d";
                    _limitFlyAreas[i].memberType = "limitFly";
                    oMap._createAuthFlyCircleMark(_limitFlyAreas[i],false);
                }
            }

            for(var i=0;i<_warnFlyAreas.length;i++){
                if(_warnFlyAreas[i].flyZoneType == 'POLY'){
                    for(var j = 0; j<_warnFlyAreas[i].child.length; j++){
                        _warnFlyAreas[i].child[j].name = _warnFlyAreas[i].name;
                        _warnFlyAreas[i].child[j].color = "#ffff00";
                        _warnFlyAreas[i].child[j].memberType = "warnFly";
                        oMap._createAuthFlyMark(_warnFlyAreas[i].child[j],false);
                    }
                }else if(_warnFlyAreas[i].flyZoneType == 'CIRCLE'){
                    _warnFlyAreas[i].color = "#ffff00";
                    _warnFlyAreas[i].memberType = "warnFly";
                    oMap._createAuthFlyCircleMark(_warnFlyAreas[i],false);
                }
            }

            //全景图
            for(var i=0;i<_panoramas.length;i++){
                oMap._createPanoramaMark(_panoramas[i]);
            }

            //现场上报
            for(var i=0;i<_scenereports.length;i++){
                oMap._createScenerportMark(_scenereports[i]);
            }
            oMap.hideMark(0,'scenereport');

        },

        /**
         * 更新成员列表
         * @private
         */
        _updateTeamMenbers :function(msg){
            var that = this;
            var data = jQuery.parseJSON(msg.data);
            //更新面板数据
            oIndex.initChart(oIndex.team);
            if (msg.messageType=="plane"){
                if(data.status == 1){ // 无人机上线
                    if ($("#tmpl_plane_" + data.id).length == 0){ return; }

                    $("#tmpl_plane_" + data.id).removeClass("plane-off");
                    $("#tmpl_plane_" + data.id).addClass("plane-on");

                    $("#tmpl_plane_" + data.id).html("<div class=\"team-plane-info\">\n" +
                        "            <div class=\"content-li-text\">\n" +
                        "                <div class=\"content-li-text-name\">"+data.name+"</div>\n" +
                        "                <div class=\"content-li-text-info\">"+data.model+"</div>\n" +
                        "            </div>\n" +
                        "            <div class=\"content-li-tool\">\n" +
                        "                    <a><img class=\"fl location\" src=\"../static/images/list-position-off.png\"></a>\n" +
                        "                    <a><img class=\"fr video\" src=\"../static/images/list-video-off.png\" data-planeid="+data.id+"></a>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <div class=\"team-line\"></div>\n" +
                        "\n" +
                        "        <ul class=\"content-li-data\" id=\"dy_li_{{= _plane.id}}\">\n" +
                        "            <li class=\"fl\">\n" +
                        "                <span class=\"content-li-text-info\">高度(m)</span>\n" +
                        "                <span class=\"content-li-text-data\" id=\"dy_high_"+data.id+"\"></span>\n" +
                        "            </li>\n" +
                        "            <li class=\"fl\">\n" +
                        "                <span class=\"content-li-text-info\">速度(m/s)</span>\n" +
                        "                <span class=\"content-li-text-data\" id=\"dy_speed_"+data.id+"\"></span>\n" +
                        "            </li>\n" +
                        "            <li class=\"fl\">\n" +
                        "                <span class=\"content-li-text-info\">电量</span>\n" +
                        "                <span class=\"content-li-text-data\" id=\"dy_elec_"+data.id+"\"></span>\n" +
                        "            </li>\n" +
                        "        </ul>\n" +
                        "        <div class=\"clear\"></div>\n");
                    if(data.high != undefined){
                        $("#dy_high_"+ data.id).html(data.high.toFixed(1));
                    }
                    if(data.speed != undefined){
                        $("#dy_speed_"+ data.id).html(data.speed.toFixed(1));
                    }
                    $("#dy_elec_"+ data.id).html(data.electricity + "%");
                    //$("#dy_state_"+ data.id).html(data.flightName);
                    //$("#dy_state_"+ data.id).addClass("plane-wrong");
                    //$("#tmpl_plane_" + data.id + " .video").attr("data-planeid",data.id);

                    if(data.position == "" ||  data.position == "NaN,NaN" ||  data.position == undefined
                        || data.position.split(",")[0] < 0 || data.position.split(",")[0] > 180
                        || data.position.split(",")[1] < 0 || data.position.split(",")[1] > 180){
                        // 切换图标样式
                        $("#tmpl_plane_" + data.id + " .location").attr("src","../static/images/list-position-off.png");
                    }else{
                        // 切换图标样式
                        $("#tmpl_plane_" + data.id + " .location").attr("src","../static/images/list-position.png");
                    }

                    $("#tmpl_plane_" + data.id + " .location").attr("data-location",data.position);

                    if(data.action == 'play'){
                        $("#tmpl_plane_" + data.id + " .video").attr("src","../static/images/list-video-play.png");
                    }else{
                        if(data.canLive == 0){
                            $("#tmpl_plane_" + data.id + " .video").attr("src","../static/images/list-video-off.png");
                        }else{
                            $("#tmpl_plane_" + data.id + " .video").attr("src","../static/images/list-video-on.png");
                        }

                    }


                    if($("#team_members_list_online #tmpl_plane_"+ data.id).length == 0){
                        that.m_TeamMembers.online_planes.push(data);
                        that.m_TeamMembers.offline_planes = that.m_TeamMembers.offline_planes.filter(function (item) {
                            return item.id != data.id;
                        });
                        $("#tmpl_plane_" + data.id).clone().appendTo($("#team_members_list_online"));
                        $("#team_members_list_offline #tmpl_plane_"+ data.id).remove();
                    }
                    //创建图标
                    oMap._createPlaneMark(data);
                    //显示图标
                    oMap.showMark(data.id,"plane");
                    for(var i=0;i<this.m_TeamMembers.online_planes.length;i++){
                        if (this.m_TeamMembers.online_planes[i].id == data.id){
                            this.m_TeamMembers.online_planes[i].electricity = data.electricity + "%";
                            if(data.speed != undefined){
                                this.m_TeamMembers.online_planes[i].speed = data.speed.toFixed(1);
                            }
                            if(data.high != undefined){
                                this.m_TeamMembers.online_planes[i].high = data.high.toFixed(1);
                            }
                            this.m_TeamMembers.online_planes[i].status = data.status;
                            this.m_TeamMembers.online_planes[i].postion = data.postion;
                            this.m_TeamMembers.online_planes[i].flightName = data.flightName;
                            this.m_TeamMembers.online_planes[i].action = data.action;
                            this.m_TeamMembers.online_planes[i].canLive = data.canLive;
                            break;
                        }
                    }

                    //2020-03-27 更新面板数据
                    oIndex.updateGaugePlane(data.high,data.speed,data.electricity);
                }else if(data.status == 0){ // 无人机下线
                    $("#tmpl_plane_" + data.id).removeClass("plane-on");
                    $("#tmpl_plane_" + data.id).addClass("plane-off");

                    // 2020-05-12 通过id获取对象信息
                    var current_plane_s = that.m_TeamMembers.online_planes.filter(function (item) {
                        return item.id == data.id;
                    });

                    $("#tmpl_plane_" + data.id).html(" <div class=\"content-li-text\">\n" +
                        "                <div class=\"content-li-text-name\">"+current_plane_s[0].name+"</div>\n" +
                        "                <div class=\"content-li-text-info\">"+current_plane_s[0].model+"</div>\n" +
                        "            </div>\n" +
                        "            <div class=\"content-li-tool\">\n" +
                        "                <a><img class=\"fl location\" src=\"../static/images/list-position-off.png\"></a>\n" +
                        "                <a><img class=\"fr video\" src=\"../static/images/list-video-off.png\"></a>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <div class=\"clear\"></div>");
                    if($("#team_members_list_offline #tmpl_plane_"+ data.id).length == 0){
                        that.m_TeamMembers.offline_planes.push(current_plane_s[0]);
                        that.m_TeamMembers.online_planes = that.m_TeamMembers.online_planes.filter(function (item) {
                            return item.id != data.id;
                        });
                        $("#tmpl_plane_" + data.id).clone().appendTo($("#team_members_list_offline"));
                        $("#team_members_list_online #tmpl_plane_"+ data.id).remove();
                    }
                    oMap.removeMark(data.id,"plane");
                }

            }else if (msg.messageType=="man"){
                if(data.status == 1){ // 人员上线
                    if ($("#tmpl_man_" + data.id).length == 0){ return; }

                    $("#tmpl_man_" + data.id).removeClass("man-off");
                    $("#tmpl_man_" + data.id).addClass("man-on");
                    // 切换图标样式
                    $("#tmpl_man_" + data.id + " .location").attr("src","../static/images/list-position.png");
                    $("#tmpl_man_" + data.id + " .location").attr("data-location",data.position);
                    //data-userid
                    $("#tmpl_man_" + data.id + " .video").attr("data-userid",data.id);
                    if(data.inRoom == 0){
                        $("#tmpl_man_" + data.id + " .video").attr("src","../static/images/list-video-on.png");
                    }else{
                        $("#tmpl_man_" + data.id + " .video").attr("src","../static/images/list-video-play.png");
                    }

                    if($("#team_members_list_online #tmpl_man_"+ data.id).length == 0){
                        that.m_TeamMembers.online_mans.push(data);
                        that.m_TeamMembers.offline_mans = that.m_TeamMembers.offline_mans.filter(function (item) {
                            return item.id != data.id;
                        });
                        $("#tmpl_man_" + data.id).clone().appendTo($("#team_members_list_online"));
                        $("#team_members_list_offline #tmpl_man_"+ data.id).remove();
                    }


                    //创建图标
                    oMap._createManMark(data);
                    //显示图标
                    oMap.showMark(data.id,"man");
                }else if(data.status == 0){ // 人员下线
                    $("#tmpl_man_" + data.id).removeClass("man-on");
                    $("#tmpl_man_" + data.id).addClass("man-off");

                    $("#tmpl_man_" + data.id + " .location").attr("src","../static/images/list-position-off.png");
                    $("#tmpl_man_" + data.id + " .video").attr("src","../static/images/list-video-off.png");
                    $("#tmpl_man_" + data.id + " .video").attr("data-userid","");
                    $("#tmpl_man_" + data.id + " .location").attr("data-location","");

                    if($("#team_members_list_offline #tmpl_man_"+ data.id).length == 0){
                        // 2020-05-12 通过id获取对象信息
                        var current_man_s = that.m_TeamMembers.online_mans.filter(function (item) {
                            return item.id == data.id;
                        });
                        that.m_TeamMembers.offline_mans.push(current_man_s[0]);
                        that.m_TeamMembers.online_mans = that.m_TeamMembers.online_mans.filter(function (item) {
                            return item.id != data.id;
                        });
                        $("#tmpl_man_" + data.id).clone().appendTo($("#team_members_list_offline"));
                        $("#team_members_list_online #tmpl_man_"+ data.id).remove();
                    }

                    //删除图标
                    oMap.removeMark(data.id,"man");
                }

                for(var i=0;i<this.m_TeamMembers.online_mans.length;i++){
                    if (this.m_TeamMembers.online_mans[i].id == data.id){
                        this.m_TeamMembers.online_mans[i].status = data.status;
                        this.m_TeamMembers.online_mans[i].postion = data.postion;
                        this.m_TeamMembers.online_mans[i].inRoom = data.inRoom;
                        break;
                    }
                }
            }

        },

        /**
         * 主动呼叫 在线人员
         * @private
         */
        _callPlane : function(id){
            var param = {
                id: id,
                teamId : this.teamId,
            };
            Ajax.postJson(oIndex.url.getPushAddress, null, JSON.stringify(param), "", function (result) {
                if (result.code == 200){
                    var _data = result.data;
                    //加载进数组
                    oMedia.addMediaOpt("plane_" + _data.id,{
                        type:"plane",
                        id : _data.id,
                        name : _data.name,
                        m3u8 : _data.hlsPlayAddress,
                        flv  : _data.flyPlayAddress,
                        rmtp : _data.rtmpPlayAddress
                    });
                    //播放
                    oMedia.playMedia("plane_"+id);
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {});
        },
        /**
         * 主动呼叫 在线人员
         * @private
         */
        _callMan : function(id,isInvite){
            var param = {
                teamId : this.teamId,
                type: 1, // 单人呼叫
                calledId: [id],  // 要呼叫的人员ID
                isFail: 0,
                isInvite:isInvite,
            };
            Ajax.postJson(oIndex.url.getRoomAndUserSign, null, JSON.stringify(param), "", function (result) {
                if (result.code == 200){
                    oMedia.playMediaOfMobile("man_" + result.data.userId,result.data);
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {});

        },
        /**
         * TODO 显示团队人员列表 及地图图标
         * @private
         */
        _showMans:function(){
            this.m_TeamMembers.mans = this.m_TeamMembers.online_mans;
            $("#tmpl_man").tmpl(this.m_TeamMembers).appendTo('#team_members_list_online');
            this.m_TeamMembers.mans = this.m_TeamMembers.offline_mans;
            $("#tmpl_man").tmpl(this.m_TeamMembers).appendTo('#team_members_list_offline');
        },

        /**
         * TODO 显示团队人员列表 及地图图标
         * @private
         */
        _showCars:function(){
            this.m_TeamMembers.cars = this.m_TeamMembers.online_cars;
            $("#tmpl_car").tmpl(this.m_TeamMembers).appendTo('#team_members_list_online');
            this.m_TeamMembers.cars = this.m_TeamMembers.offline_cars;
            $("#tmpl_car").tmpl(this.m_TeamMembers).appendTo('#team_members_list_offline');
        },

        /**
         * TODO 显示团队人员列表 及地图图标
         * @private
         */
        _showPlanes:function(){
            this.m_TeamMembers.planes = this.m_TeamMembers.online_planes;
            $("#tmpl_plane").tmpl(this.m_TeamMembers).appendTo('#team_members_list_online');
            this.m_TeamMembers.planes = this.m_TeamMembers.offline_planes;
            $("#tmpl_plane").tmpl(this.m_TeamMembers).appendTo('#team_members_list_offline');
        },


        /**
         * 统计图表
         */
        // 初始化 统计
        initChart:function(team){
            /* 团队面板上的统计图 */
            var that = this;
            var onlineCount = 0;
            var totalCount = 0;
            var memberCount = 0;
            var uavCount = 0;
            var carCount = 0;
            $.ajax({
                type : 'POST',
                dataType : "json",
                url : this.url.getOnline,
                data : JSON.stringify({"teamId":team.id}),
                contentType : "application/json; charset=utf-8",
                cache : false,
                async : false,
                success : function(res) {
                    if (res.code == 200){
                        onlineCount = res.data.totalOnlineNums;
                        totalCount = res.data.totalNums;
                        memberCount = res.data.dataNums.memberCount == undefined ? 0 : res.data.dataNums.memberCount;
                        uavCount = res.data.dataNums.uavCount == undefined ? 0 : res.data.dataNums.uavCount;
                        carCount = res.data.dataNums.carCount == undefined ? 0 : res.data.dataNums.carCount;
                    }
                },
            });
            $("#box_panel_teamTitle").text(team.name);
            this.bind_state(onlineCount,totalCount); // 在线率
            this.initPie_team('team-all-num',totalCount,totalCount,'总数','#00E5F9'); // 传绘团队总数量制环状图
            this.initPie_team('team-people-num',memberCount,totalCount,'人','#2A73FE'); // 传绘团队人制环状图
            this.initPie_team('team-plane-num',uavCount,totalCount,'机','#2FE3A1'); // 传绘团队机制环状图
            this.initPie_team('team-car-num',carCount,totalCount,'车','#8A46DE'); // 传绘团队车制环状图
            this.initPie_team('team-online-num',onlineCount,totalCount,null,'#00E5F9'); // 传绘团队在线率制环状图

            /* 设备面板上的统计图 */
            //this.initGaugePlane('plane-gauge-chart',0, 0, 89);
            //this.updateGaugePlane(10,20,30); // 模拟更新数据
            //this.initPie_team('plane-battery',93,100,null,'#00E5F9');// plane-battery 飞机电池健康
            //this.init_task(teamId);

        },

        /**
         * 任务面板
         */
        init_task:function(teamId){
            var that = this;
            /* 任务面板上的统计图 */
            $.ajax({
                type : 'POST',
                dataType : "json",
                url : this.url.taskChart,
                data : JSON.stringify({"teamId":teamId}),
                contentType : "application/json; charset=utf-8",
                cache : false,
                async : false,
                success : function(res) {
                    if (res.code == 200){
                        that.barChart_taskflytime(res.data.flightHoursMap);  // 团队任务飞行时长 分时统计
                        that.initPieRadar_team('task-type',res.data.typeList,res.data.totalCount == 0 ? 1: res.data.totalCount);// plane-battery 飞机电池健康
                        $("#plane-task-count").text(res.data.completeStatusMap.ok == undefined ? 0 : res.data.completeStatusMap.ok);
                        $("#plane-task-no-count").text(res.data.completeStatusMap.not_ok == undefined ? 0 : res.data.completeStatusMap.not_ok);
                        $("#plane-task-fly-hours").text(res.data.totalFlightHours);
                    }
                },
            });
        },

        /**
         * TODO 无人机状态统计
         * @param teamid
         */
        bind_state:function(totalOnlineNums,totalNums){
            var that = this;

            var lixian =  totalNums-totalOnlineNums;
            var xData = ["作业","离线","异常"];
            var yData = [totalOnlineNums,lixian,0];

            var myChart = echarts.init(document.getElementById("plane-status-chart"));

            option = {
                tooltip: {
                    show:false
                },
                grid: {
                    top:'5px',
                    left: '0px',
                    right: '5px',
                    bottom: '5px',
                    containLabel: true
                },
                yAxis: [{ // 竖轴
                    type: 'category',
                    data: xData,
                    inverse: true,
                    axisTick: {
                        alignWithLabel: true,
                    },
                    axisLabel: {
                        margin: 5,
                        textStyle: {
                            fontSize: 12,
                            color:'#ffffff'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#626262',
                            width:2
                        }
                    },
                    splitLine: {
                        show: false,
                    }
                }],
                xAxis: [{ // 横轴
                    type: 'value',
                    nameGap: 15,
                    max: totalNums,//_max,
                    axisTick: {
                        show:false,
                        alignWithLabel: true,
                    },
                    axisLabel: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    splitLine: {
                        show: false
                    }
                }],
                series: [{
                    type: 'bar',
                    barWidth:14,
                    data: yData,
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight',
                            textStyle: {
                                color: 'white' //color of value
                            },
                            formatter: function (params) {
                                //如果值大于0 正常显示，否则不显示
                                if (params.value > 0) {
                                    return params.value;
                                } else {
                                    return '';
                                }
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            // barBorderRadius: [0, 5,5, 0],
                            shadowColor: 'rgba(0,0,0,0.1)',
                            shadowBlur: 3,
                            shadowOffsetY: 3,
                            color:function(params){
                                var colorList = ['#00E5F9','#2A73FE','#DE464A'];
                                return colorList[params.dataIndex]
                            }
                        }
                    }
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },


        /**
         * 传参绘制环状图
         * @param {Object} el
         * @param {Object} value 显示数
         * @param {Object} count 总数
         * @param {Object} text 文本
         */
        initPie_team: function(el,value,count,text,color){
            var myChart = echarts.init(document.getElementById(el));
            var dValue = count - value; //差值
            var option = {
                color: [ '#374345',color],
                tooltip: {
                    show: false
                },
                title:{
                    text: (text == null ? '' : text),
                    subtext: (text == null ? value + '%' : value == 0 ? '0' : value),
                    x: 'center',
                    y: '30%',
                    itemGap: 5,
                    textStyle : {
                        color : '#FFFFFF',
                        fontSize : 14,
                    },
                    subtextStyle:{
                        color : '#FFFFFF',
                        fontSize : 18,
                        fontWeight : 'bolder'
                    }
                },
                series: [{
                    type: 'pie',
                    radius: ['85%', '70%'],
                    label: {
                        normal: {
                            position: 'center'
                        },
                    },
                    hoverAnimation: false,
                    itemStyle: {
                        normal: {
                            show:false
                        }
                    },
                    data: [{
                        value: dValue,
                        name: '在线率',
                        label: {
                            normal: {
                                show:false
                            }
                        }
                    }, {
                        value: value,
                        name: '占位',
                        label: {
                            normal: {
                                show:false
                            }
                        }
                    }]
                }]
            };
            myChart.setOption(option); // 使用刚指定的配置项和数据显示图表。
        },

        /**
         * 通过接收消息改变 无人机仪表盘 及 列表中数字
         * @param {Object} high
         * @param {Object} speed
         * @param {Object} electric
         */
        updateGaugePlane: function(high,speed,electric){

            // high = (Math.random()*400).toFixed(0) - 0;
            // speed = (Math.random()*240).toFixed(0) - 0;
            // electric = (Math.random()*100).toFixed(0) - 0;
            if(oIndex.gaugePlaneOption != undefined){
                oIndex.gaugePlaneOption.series[0].data[0].value = speed;
                oIndex.gaugePlaneOption.series[1].data[0].value = high;
                oIndex.gaugePlaneOption.series[2].data[0].value = electric;
                oIndex.gaugePlaneEChart.setOption(oIndex.gaugePlaneOption,true);

                $("#high_id_1").html(high);
                $("#speed_id_1").html(speed);
                $("#electric_id_1").html(electric + "%");
            }
        },

        /**
         * 另一种样式
         * @param {Object} el
         * @param {Object} high
         * @param {Object} speed
         * @param {Object} electric
         */
        initGaugePlane1:function(el,high,speed,electric){
            oIndex.gaugePlaneEChart = echarts.init(document.getElementById(el));
            oIndex.gaugePlaneOption = {
                series: [
                    {
                        name: '速度',
                        type: 'gauge',
                        min: 0,
                        max: 240,
                        splitNumber:6,
                        center: ['50%', '60%'],
                        radius: '76%',
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: [[1, '#00ff7e']],
                                width: 5,
                                // shadowColor: '#fff', //默认透明
                                // shadowBlur: 0
                            }
                        },
                        axisLabel: {            // 坐标轴小数字标记
                            fontWeight: 'bolder',
                            fontSize: 8,
                            color: '#fff'
                        },
                        splitLine: {           // 分隔线
                            length: 3,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                width: 2,
                                color: '#fff'
                            }
                        },
                        pointer: {           // 分隔线
                            length: '75%',
                            width: 2,
                            shadowColor: '#fff', //默认透明
                            shadowBlur: 5
                        },
                        title: {
                            offsetCenter: ['0%', '-30%'],
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: '400',
                                fontSize: 10,
                                fontStyle: 'italic',
                                color: '#fff'
                            }
                        },
                        detail: { // 数值
                            //backgroundColor: 'rgba(4,54,64,0.6)',
                            //borderWidth: 0.5,
                            //borderColor: '#fff',
                            //shadowColor: '#fff', //默认透明
                            //shadowBlur: 5,
                            offsetCenter: [0, '56%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: '#fff',
                                fontSize: 12
                            }
                        },
                        data: [{value: speed, name: '速度 km/h'}]
                    }
                    ,{
                        name: '高度',
                        type: 'gauge',
                        center: ['20%', '60%'],    // 默认全局居中
                        radius: '76%',
                        min: 0,
                        max: 400,
                        //endAngle: 45,
                        splitNumber: 4,
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: [[1, '#009cff']],
                                width: 5,
                                // shadowColor: '#fff', //默认透明
                                // shadowBlur: 6
                            }
                        },
                        axisLabel: {            // 坐标轴小数字标记
                            fontWeight: 'bolder',
                            fontSize: 8,
                            color: '#fff'
                        },
                        splitLine: {           // 分隔线
                            length: 2,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                width: 1,
                                color: '#fff'
                            }
                        },
                        pointer: {           // 分隔线
                            length: '75%',
                            width: 2,
                            shadowColor: '#fff', //默认透明
                            shadowBlur: 5
                        },
                        title: {
                            offsetCenter: ['0%', '-30%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: '400',
                                fontSize: 10,
                                fontStyle: 'italic',
                                color: '#fff'
                            }
                        },
                        detail: {
                            // backgroundColor: 'rgba(4,54,64,0.6)',
                            // borderWidth: 0.5,
                            // borderColor: '#fff',
                            // shadowColor: '#fff', //默认透明
                            // shadowBlur: 5,
                            offsetCenter: [0, '56%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: '#fff',
                                fontSize: 12
                            }
                        },
                        data: [{value: high, name: '高度 m'}]
                    },
                    {
                        name: '电量',
                        type: 'gauge',
                        center: ['80%', '60%'],    // 默认全局居中
                        radius: '76%',
                        min: 0,
                        max: 100,
                        //startAngle: 140,
                        //endAngle: -45,
                        splitNumber: 4,
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: [[1, '#ed4d76']],
                                width: 5,
                                // shadowColor: '#fff', //默认透明
                                // shadowBlur: 6
                            }
                        },
                        axisLabel: {            // 坐标轴小数字标记
                            fontWeight: 'bolder',
                            fontSize: 8,
                            color: '#fff'
                        },
                        splitLine: {           // 分隔线
                            length: 3,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                width: 2,
                                color: '#fff'
                            }
                        },
                        pointer: {           // 分隔线
                            length: '75%',
                            width: 2,
                            shadowColor: '#fff', //默认透明
                            shadowBlur: 5
                        },
                        title: {
                            offsetCenter: [0, '-30%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: '400',
                                fontSize: 10,
                                fontStyle: 'italic',
                                color: '#fff'
                            }
                        },
                        detail: {
                            // backgroundColor: 'rgba(4,54,64,0.6)',
                            // borderWidth: 0.5,
                            // borderColor: '#fff',
                            // shadowColor: '#fff', //默认透明
                            // shadowBlur: 5,
                            offsetCenter: [0, '56%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: '#fff',
                                fontSize: 12
                            },
                            formatter:'{value}%'
                        },
                        data: [{value: electric, name: '剩余电量 %'}]
                    }
                ]
            };
            oIndex.gaugePlaneEChart.setOption(oIndex.gaugePlaneOption); // 使用刚指定的配置项和数据显示图表。
        },
        /**
         * 无人机仪表盘
         * @param {Object} el
         * @param {Object} high
         * @param {Object} speed
         * @param {Object} electric
         */
        initGaugePlane:function(el,high,speed,electric){
            oIndex.gaugePlaneEChart = echarts.init(document.getElementById(el));
            oIndex.gaugePlaneOption = {
                series: [
                    {
                        name: '速度',
                        type: 'gauge',
                        min: 0,
                        max: 240,
                        splitNumber:6,
                        center: ['50%', '58%'],
                        radius: '88%',
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: [[0.35, 'lime'], [0.82, '#1e90ff'], [1, '#ff4500']],
                                width: 3,
                                shadowColor: '#fff', //默认透明
                                shadowBlur: 6
                            }
                        },
                        axisLabel: {            // 坐标轴小数字标记
                            fontWeight: 'bolder',
                            fontSize: 8,
                            color: '#fff'
                        },
                        splitLine: {           // 分隔线
                            length: 3,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                width: 2,
                                color: '#fff'
                            }
                        },
                        pointer: {           // 分隔线
                            length: '75%',
                            width: 4,
                            shadowColor: '#fff', //默认透明
                            shadowBlur: 5
                        },
                        title: {
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: '400',
                                fontSize: 10,
                                fontStyle: 'italic',
                                color: '#fff'
                            }
                        },
                        detail: { // 数值
                            backgroundColor: 'rgba(4,54,64,0.6)',
                            borderWidth: 0.5,
                            borderColor: '#fff',
                            shadowColor: '#fff', //默认透明
                            shadowBlur: 5,
                            offsetCenter: [0, '56%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: '#fff',
                                fontSize: 12
                            }
                        },
                        data: [{value: speed, name: '速度 km/h'}]
                    }
                    ,{
                        name: '高度',
                        type: 'gauge',
                        center: ['25%', '62%'],    // 默认全局居中
                        radius: '78%',
                        min: 0,
                        max: 400,
                        endAngle: 45,
                        splitNumber: 4,
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: [[0.30, 'lime'], [0.86, '#1e90ff'], [1, '#ff4500']],
                                width: 3,
                                shadowColor: '#fff', //默认透明
                                shadowBlur: 6
                            }
                        },
                        axisLabel: {            // 坐标轴小数字标记
                            fontWeight: 'bolder',
                            fontSize: 8,
                            color: '#fff'
                        },
                        splitLine: {           // 分隔线
                            length: 3,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                width: 2,
                                color: '#fff'
                            }
                        },
                        pointer: {           // 分隔线
                            length: '75%',
                            width: 2,
                            shadowColor: '#fff', //默认透明
                            shadowBlur: 5
                        },
                        title: {
                            offsetCenter: ['0%', '-30%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: '400',
                                fontSize: 10,
                                fontStyle: 'italic',
                                color: '#fff'
                            }
                        },
                        detail: {
                            backgroundColor: 'rgba(4,54,64,0.6)',
                            borderWidth: 0.5,
                            borderColor: '#fff',
                            shadowColor: '#fff', //默认透明
                            shadowBlur: 5,
                            offsetCenter: [0, '56%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: '#fff',
                                fontSize: 12
                            }
                        },
                        data: [{value: high, name: '高度 m'}]
                    },
                    {
                        name: '电量',
                        type: 'gauge',
                        center: ['77%', '62%'],    // 默认全局居中
                        radius: '78%',
                        min: 0,
                        max: 100,
                        startAngle: 140,
                        endAngle: -45,
                        splitNumber: 4,
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: [[0.25, '#ff4500'], [0.75, '#1e90ff'], [1, 'lime']],
                                width: 3,
                                shadowColor: '#fff', //默认透明
                                shadowBlur: 6
                            }
                        },
                        axisLabel: {            // 坐标轴小数字标记
                            fontWeight: 'bolder',
                            fontSize: 8,
                            color: '#fff'
                        },
                        splitLine: {           // 分隔线
                            length: 3,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                width: 2,
                                color: '#fff'
                            }
                        },
                        pointer: {           // 分隔线
                            length: '75%',
                            width: 2,
                            shadowColor: '#fff', //默认透明
                            shadowBlur: 5
                        },
                        title: {
                            offsetCenter: [0, '-30%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: '400',
                                fontSize: 10,
                                fontStyle: 'italic',
                                color: '#fff'
                            }
                        },
                        detail: {
                            backgroundColor: 'rgba(4,54,64,0.6)',
                            borderWidth: 0.5,
                            borderColor: '#fff',
                            shadowColor: '#fff', //默认透明
                            shadowBlur: 5,
                            offsetCenter: [0, '56%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: '#fff',
                                fontSize: 12
                            },
                            formatter:'{value}%'
                        },
                        data: [{value: electric, name: '剩余电量 %'}]
                    }
                ]
            };
            oIndex.gaugePlaneEChart.setOption(oIndex.gaugePlaneOption); // 使用刚指定的配置项和数据显示图表。
        },

        /**
         * 团队任务面板中 飞行时长 分时统计
         */
        barChart_taskflytime:function(data){
            var that = this;
            var xData = ["< 5","06-10","11-15","16-20","21-25","26-30","31-35","> 35"];
            var yData = [data.i_0,data.i_1,data.i_2,data.i_3,data.i_4,data.i_5,data.i_6,data.i_7,data.i_8];

            var myChart = echarts.init(document.getElementById("task-fly-time"));
            var option = {
                grid:{ //设置图表边距
                    top:'5px',
                    left: '0px',
                    right: '5px',
                    bottom: '5px',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: xData,
                    axisLabel: {  // X轴 字体样式
                        textStyle: {
                            color: '#fff',
                            fontSize: 10,
                        }
                    },
                },
                yAxis: {
                    type: 'value',
                    splitLine: {show: false},//去除网格线
                    axisLabel: {  // X轴 字体样式
                        textStyle: {
                            color: '#fff',
                            fontSize: 10,
                        }
                    }
                },
                series: [{
                    type: 'bar',
                    data: yData,
                    barWidth: '15px', // 柱子宽度
                    itemStyle: {
                        normal: {
                            // barBorderRadius: [0, 5,5, 0],
                            shadowColor: 'rgba(0,0,0,0.1)',
                            shadowBlur: 3,
                            shadowOffsetY: 3,
                            // 柱子颜色 渐变
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#de9cf3'
                            }, {
                                offset: 1,
                                color: '#965df2'
                            }])
                        }
                    }
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },

        /**
         * 雷达图传参
         * @param {Object} el
         * @param {Object} _data 数组对象  [{vaule:12,name:'rose1'},{vaule:13,name:'rose2'}]
         */
        initPieRadar_team: function(el,_data,max){
            var indicator = [
                {name: '现场侦察',max:max},
                { name: 'VR全景',max:max},
                { name: '二维影像',max:max},
                { name: '三维模型',max:max},
                {name: '其它任务',max:max},
            ]
            var rose_data = _data
            var value_list = [];
            //将value值取出来
            for(var i = 0 ;i<_data.length; i++){
                value_list.push(_data[i].value);
            }
            var myChart = echarts.init(document.getElementById(el));
            option = {
                tooltip: { // 鼠标称动的提示
                    trigger: 'axis'
                },
                radar: [{
                    indicator: indicator,
                    radius: 47,  // 圆的半径，数组的第一项是内半径，第二项是外半径。
                    center: ['50%', '55%'],
                    name: { // (圆外的标签)雷达图每个指示器名称的配置项。
                        formatter: '{value}',
                        textStyle: {
                            fontSize: 12,
                            color: '#FFFFFF'
                        }
                    },
                    nameGap: 2, // 指示器名称和指示器轴的距离。[ default: 15 ]
                    splitArea: {
                        show: false // 坐标轴在 grid 区域中的分隔区域，默认不显示。
                    },
                }],
                series: [{
                    type: 'radar',
                    tooltip: { trigger: 'item' },
                    areaStyle: {},
                    data: [{
                        name: '任务类型',
                        value: value_list
                    }]
                }]
            };
            myChart.setOption(option); // 使用刚指定的配置项和数据显示图表。
        },


        /* 任务绑定事件 */
        bindTask: function (json) {
            Ajax.postJson(oIndex.url.getTeamTask, null, JSON.stringify(json), "", function (result) {
                var task_list = "";
                if (result.code == 200){
                    for(var i = 0;i< result.data.length; i++){
                        var task = result.data[i];
                        if(task.completeStatus == 0){
                            color = "#FD2F34";
                        }else{
                            color = "green";
                        }

                        task_list += "<a href='/task_detail.html/"+task.id+"'><div class=\"right-tab-content-li\">\n" +
                            "\t\t\t\t\t<div class=\"task_title\">\n" +
                            "\t\t\t\t\t\t"+task.name+"\n" +
                            "\t\t\t\t\t</div>\n" +
                            "\t\t\t\t\t<div class=\"task_content\">\n" +
                            "\t\t\t\t\t\t<span>任务类型："+task.typeName+"</span>\n" +
                            "\t\t\t\t\t\t<span>任务状态：<font font-weight='400' color="+color+">"+task.completeStatusName+"</font></span>\n" +
                            "\t\t\t\t\t\t<span>任务团队："+task.teamName+"</span>\n" +
                            "\t\t\t\t\t\t<span>发布时间："+task.createTime+"</span>\n" +
                            "\t\t\t\t\t</div>\n" +
                            "\t\t\t\t\t<div class=\"task_tools\">\n" +
                            "\t\t\t\t\t\t<a onclick='saveTask("+JSON.stringify(task)+")' style='cursor:pointer'><span class=\"edit-img fl\"></span>编辑任务</a>\n" +
                            // "\t\t\t\t\t\t<a id=\"message-label\"><span class=\"line-edit-img fl\"></span>编辑航线</a>\n" +
                            "\t\t\t\t\t\t<a class='cancelTask' layer-id="+task.id+" style='cursor:pointer'><span class=\"cancle-img fl\"></span>取消任务</a>\n" +
                            "\t\t\t\t\t</div>\n" +
                            "\t\t\t\t</div></a>";
                    }


                    $("#task_list").html(task_list)
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {});

        },

        /* 任务类型列表绑定事件 */
        TaskTypeList: function () {
            Ajax.postJson(oIndex.url.getDicDesc, null, JSON.stringify({"code":"TASK_TYPE"}), "", function (result) {
                var dic_list = "";
                if (result.code == 200){
                    for(var i = 0;i< result.data.length; i++){
                        var dic = result.data[i];
                        dic_list += "<option value="+dic.id+">"+dic.name+"</option>"
                    }
                    $("#add-taskType").html(dic_list);
                    $("#search-taskType").append(dic_list);
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {});
        },

        /**
         * 任务执行团队
         */
        TaskTeam: function (team) {
            $("#search-taskTeam").val(team.name);
            $("#add-taskTeam").val(team.name);
        },


    };

    window.oIndex = oIndex;
    oIndex.init();
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
                "teamId": oIndex.teamId,
                "memo": $("#add-taskmemo").val(),
            }
            if(item != undefined){
                json.id = item.id;
            }
            Ajax.postJson(oIndex.url.addTeamTask, null, JSON.stringify(json), "", function (res) {
                if (res.code == 200) {
                    layer.close(index1);
                    layer.alert("保存成功", {
                            icon: 6,
                            color: '#FFFFFF;',
                        },
                        function (index2, layero2) {
                            oIndex.bindTask({'teamId': oIndex.teamId}); //任务
                            oIndex.init_task(oIndex.teamId);  //任务面板
                            //关闭当前frame
                            layer.close(index2);

                        });
                }
            }, function (XMLHttpRequest, textStatus, errorThrown) {
            });
        }
    });
};

function initPlanPanel(obj) {
    if($(obj).hasClass('plane-off')){
        return;
    }
    $(".team-member-card.plane-on").removeClass("active");
    $(obj).parents('.team-member-card').addClass("active");
    // 切换中下统计面板 center_box_panel
    oIndex.activePlanId = $(obj).attr("data-plan-id");

    $(".center_box_panel").hide();
    for(var i=0;i<$(".center_box_panel").length;i++){
        var item = $(".center_box_panel")[i];
        if ($(item).attr("data-filter") === 'plane_status'){
            $.ajax({
                type : 'POST',
                dataType : "json",
                url : oIndex.url.planPanel,
                data : JSON.stringify({"teamId":oIndex.teamId,"id":oIndex.activePlanId}),
                contentType : "application/json; charset=utf-8",
                cache : false,
                async : false,
                success : function(res) {
                    if (res.code == 200){
                        if(res.data != null){
                            $("#plan-panel-name").text(res.data.name);
                            $("#plane-fly-count").text(res.data.taskNum);
                            $("#plane-fly-time").html(res.data.flyTime);
                            oIndex.initGaugePlane('plane-gauge-chart',res.data.high,res.data.speed,res.data.electricity);
                            //oIndex.updateGaugePlane(); // 模拟更新数据
                            oIndex.initPie_team('plane-battery',res.data.batteryHealth,100,null,'#00E5F9');// plane-battery 飞机电池健康
                        }
                    }
                },
            });
            $(item).show();
            /*
             * TODO 需要重新加载不同无人机的仪表盘
             * that.updateGaugePlane(...);
             */
            break;
        }
    }
}