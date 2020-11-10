/**
 * 动态消息 模块
 * 包括 MQ初始化
 * @author 陶冶
 * @version 1.0 2018/3/28
 */
(function() {
	var oMessage = {
		WEBSOCKET_URL:"wss://www.ljcxkj.cn:8088/websocket/",
		//WEBSOCKET_URL:"ws://10.5.0.20:8088/websocket/",
		websocket:null,
	    lockReconnect:false,//避免重复连接	
	    userIds:null,
		url :{

		},
		/**
		 * TODO 获取一天内的现场消息，按用户权限
		 *
		 */
		getMessageList: function() {

		},

		/**
		 * websocket重连
		 * @param url
		 */
		reconnect:function(url){
			 if(oMessage.lockReconnect) return;
			 oMessage.lockReconnect = true;
		          //没连接上会一直重连，设置延迟避免请求过多
		     setTimeout(function () {
		    	 //oMessage.websocket = new WebSocket(url);
		    	 oMessage.initMsgListener(oMessage.userIds);
		    	 oMessage.lockReconnect = false;
		     }, 2000);
		},

		/**
		 * TODO 初始化 websocket 连接
		 * @param id 登录用户ID
		 */
		initMsgListener:function(id){
			var destination = id; //队列名称 "ljcx_pf_"
			oMessage.userIds=id;
		    //判断当前浏览器是否支持WebSocket
		    if ('WebSocket' in window) {
		    	oMessage.websocket = new WebSocket(this.WEBSOCKET_URL + destination);
		    }
		    else {
		        alert('当前浏览器 Not support websocket');
		    }
		    //心跳检测
		    var heartCheck = {
		        timeout: 3000,//10000,//60秒
		        timeoutObj: null,
		        reset: function(){
		            clearTimeout(this.timeoutObj);
		            return this;
		        },
		        start: function(){
		            this.timeoutObj = setTimeout(function(){
		                //这里发送一个心跳，后端收到后，返回一个心跳消息，
		                //onmessage拿到返回的心跳就说明连接正常
		            	var str1 = {"userId":oMessage.userIds,"heartBeat":"1"};
		            	oMessage.websocket.send(JSON.stringify(str1));
		            }, this.timeout)
		        }
		    };

		    //接收到消息的回调方法
		    oMessage.websocket.onmessage = function (event) {
		    	  //如果获取到消息，心跳检测重置
	              //拿到任何消息都说明当前连接是正常的
				  var that = this;
	              heartCheck.reset().start();
	              var msg = jQuery.parseJSON(event.data);
				  console.log(msg);
		    	  if(msg.heartBeat==null||msg.heartBeat==undefined||typeof(msg.heartBeat) == "undefined"){

		    		  if(msg.messageType != null && jQuery.parseJSON(msg.data).teamId == oIndex.teamId){
						//刷新在线率
						//console.log("刷新在线率teamId为："+oIndex.teamId)
						//oIndex.initChart(oIndex.teamId);
		    		  	if (msg.messageType == "plane"){ // 无人机实时状态
		    		  		oIndex._updateTeamMenbers(msg);
						}else if(msg.messageType == "man"){ // 人员实时状态
							oIndex._updateTeamMenbers(msg);
						}else if(msg.messageType == "plane_video"){ // 无人机实时视频 推流地址
		    		  		if (msg.action == "play"){
		    		  			var data = jQuery.parseJSON(msg.data);
								oMedia.addMediaOpt("plane_" + data.id,{
									type:"plane",
									id:data.id,
									name:data.name,
									m3u8:data.hlsPlayAddress,
									flv:data.flyPlayAddress,
									rmtp:data.rtmpPlayAddress,
								});
							}else if(msg.action == "close"){
								var data = jQuery.parseJSON(msg.data);
								oMedia.removeMediaOpt("plane_" + data.id,{
									type:"plane",
									id:data.id
								})
							}
							//遍历集合替换action值
							for(var i=0;i<oIndex.m_TeamMembers.online_planes.length;i++){
								if (oIndex.m_TeamMembers.online_planes[i].id == data.id){
									oIndex.m_TeamMembers.online_planes[i].action = data.action;
									break;
								}
							}
                        }else if(msg.messageType == "call"){ // 人员通话
							var data = jQuery.parseJSON(msg.data);
							oMedia.addMediaOpt("man_" + data.callingUser,{
								type:"man",
								callingName:data.callingName,
								userId:data.userId,
								roomId:data.roomId,
								userSign:data.userSign
							});
						}else if(msg.messageType == "call_state"){ // 通话状态
							var data = jQuery.parseJSON(msg.data);
							show(data.calledUserName+"拒绝了您的通话请求！");
						}
		    		  }
		    	  }else{
		    		  if(msg.heartBeat=='1'){
			    		  oMessage.lockReconnect=true;
			    	  } 
		    	  }
		    };

			//连接发生错误的回调方法
			oMessage.websocket.onerror = function () {
				oMessage.reconnect(this.WEBSOCKET_URL + destination);
			};

			//连接成功建立的回调方法
			oMessage.websocket.onopen = function () {
				//心跳检测重置
				heartCheck.reset().start();
			};

		    //连接关闭的回调方法
		    oMessage.websocket.onclose = function () {
		    	oMessage.reconnect(this.WEBSOCKET_URL + destination);
		    }
		}
	};

	window.oMessage = oMessage;
})();
