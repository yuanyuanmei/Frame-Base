/**
 * 视频加载  模块

 * @author 陶冶
 * @version 1.0 2019/12/3
 */
(function() {
	var oMedia = {
	    // 视频播放信息
		arrMediaOption : new Map(),

		// 视频直播容器
		livePlayer: new Map(),

		// 音视频播放容器
		videoPlayer: new Map(),

		/**
		 * 视频区UI切换
		 * @private
		 */
		_changeView:function(el,_count){
			var _wh = $(document).height();
			if (_count == 0){
				$(el).empty();
				$(el).fadeOut();
				$("#live_list_box").css("height","0px");
			}else if (_count == 1){
				$(el).fadeIn();
				$(el + " .vcp-player").css("width","480px");
				$(el + " .vcp-player").css("height","280px");
				/*$(el).css("width","720px");
				$(el).css("height","390px");

				$(el).children(".live_").css("width","720px");
				$(el).children(".live_").css("height","390px");

				$(el).children(".live_div_").css("width","720px");
				$(el).children(".live_div_").css("height","390px");

				$(el).children(".vcp-player").css("width","720px");
				$(el).children(".vcp-player").css("height","390px");

				$(el).children("video").css("width","720px");
				$(el).children("video").css("height","390px");*/

				// vcp-player video
			}else{
				$(el).fadeIn();
				$(el + " .vcp-player").css("width","240px");
				$(el + " .vcp-player").css("height","140px");
			}

			/*$(".message-box").animate({
					height:(_wh - 210) + "px",
					width:"481px"
				},300,function(){
					$(this).css({height:"calc(100vh - 210px)"})
				});*/
		},

        /**
         * * 增加一个视频参数信息地址
         * @param key  man_id  plane_id  eg:man_2 plane_10
         * @param value
         * {
         *		type:"plane",  // & man
         *		id:data.id,
         *		m3u8:data.hlsPlayAddress,
         *		flv:data.flyPlayAddress,
         *		rmtp:data.rtmpPlayAddress
         * }
         * {
         *		type:"man",  // & man
         *		id:data.calledUser,
         *		signList:data.signList
         * }
         */
        addMediaOpt: function(key,value) {
			this.arrMediaOption.set(key,value);
			//高亮播放图标
			if (key.indexOf('plane_') > -1){
				$("#tmpl_" + key + " .video").attr("src","../static/images/list-video-play.png");
			}
		},

		/**
		 * TODO 播放手机音视频通话
		 * @param key man_id
		 * @param data  音视频通话配置
		 */
		playMediaOfMobile : function(key,data){

			var that = this;
			// 防止本地摄像头 开启多次
			if (this.videoPlayer.get(data.roomId) != null){
			    return;
            }
			var _element = $("#" + key);
			if (_element.length > 0){  // 如果在播放 video_div_plane_10
				return;
			}

			// TRTC 对象
			var rtc = new RtcClient({
				el: "video_list_box",
				userId: data.username,
				roomId: data.roomId,
				sdkAppId: 1400285528,
				userSig: data.userSign,
				// audio: data.audio,
				// video: data.video,
			});
			rtc.join();
			this.videoPlayer.set(data.roomId,rtc);

			//$("#video_list_box").css("height","280px");
			$("#video_list_box").fadeIn();

			// 多人音视频 绑定视频的关闭按钮 destroy fullscreen
			$(document).off('click',"#videoClose")
				.on('click',"#videoClose", function (e) {

					layer.close(oMedia.layerIndex);

					oMedia.videoPlayer.get(data.roomId).leave(); //player.destroy();
					oMedia.videoPlayer.delete(data.roomId);

					$("#video_list_box").fadeOut();
					$("#video_list_box").empty();
					//$("#video_list_box").css("height","0px");

			});

			// 多人音视频 绑定视频的声音和视频 audio
			$(document).off('click',"#videoAudio")
				.on('click',"#videoAudio", function (e) {
				if($(this).hasClass('yuyin-off')){
					//oMedia.videoPlayer.get(data.roomId).muteLocalVideo();
					oMedia.videoPlayer.get(data.roomId).unmuteLocalAudio();
					$(this).removeClass('yuyin-off');
				}else{
					//oMedia.videoPlayer.get(data.roomId).unmuteLocalVideo();
					oMedia.videoPlayer.get(data.roomId).muteLocalAudio();
					$(this).addClass('yuyin-off');
				}
			});

            // 绑定视频的fullscreen
            $("#video_list_box ").off('click',".fullscreen")
                .on('click',".fullscreen",function () {
                if ($(".local_stream").width() > 400){
                    that._zoomIn();
                }else{
                	var pid = $(this).parent().parent()[0].id;
                    that._zoomOut(pid);
                }
            });

		},

		_zoomOut : function(pid){
			this.layerIndex = layer.open({
				type: 1 //Page层类型
				, skin: 'layer-ext-myskin'
				, area: ['1000px', '700px']
				, title: ['多人视频', 'font-size:16px']
				, btn: ['最小化']
				, shadeClose: true
				, shade: .6 //遮罩透明度
				, maxmin: false //允许全屏最小化
				//, content: $("#video_list_box")
				, content: $("#" + pid)
				, success: function () {
					//layui-layer-content
					//layer.alert("success");
				}
				, yes: function (index1, layero1) {
					layer.close(oMedia.layerIndex);
				}
			});
			layer.full(this.layerIndex);
		},

        _zoomIn : function(){
			$("#live_list_box").show(); // 无人机界面

            $("#video_list_box").fadeOut("normal",function () {
				$(".local_stream").css("width","240px");
				$(".local_stream").css("height","140px");

				$(".remote_stream").css("width","240px");
				$(".remote_stream").css("height","140px");

				$("#video_list_box").css("width","490px");
				$("#video_list_box").css("height","280px");

				setTimeout(function () {
					$("#video_list_box").fadeIn();
				},300);
			});

        },

		/**
		 * 随机数
		 * @param len
		 * @returns {string}
		 * @private _randomString(6)
		 */
		_randomString : function(len){
			len = len || 32;
			var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
			var maxPos = $chars.length;
			var pwd = '';
			for (i = 0; i < len; i++) {
				pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
			}
			return pwd;
		},

		/**
		 * 播放无人机推流视频
		 * @param key man_id  plane_id  eg:man_2 plane_10
		 */
		playMedia:function (key) {
		    var that = this;
			//设置div高度
			$("#live_list_box").css("height","280px");
			var _element = $("#live_div_" + key);
			if (_element.length > 0){  // 如果正在播放 live_div_plane_10
				return;
			}
			// 创建一个新的容器ID
			var _el = "live_" + this._randomString(6);
			$('<div>', { id: _el , class: "live_", style : 'overflow: hidden;'}).appendTo("#live_list_box");
			$('<div>', { id: "live_div_" + key , class: "live_div_" }).appendTo("#" + _el);

			// 获取视频播放配置信息
			var _playOpt = this.arrMediaOption.get(key);
			_playOpt.key = key;
            _playOpt.timer = null;
            _playOpt.timeCount = 0;

			// 视频 全屏 和 关闭 按钮
			//'live_div_' + key
			//$("#" + _el).append('<div class="video-title">'
			$("#live_div_" + key).append('<div class="video-title">'
				+'<span>'+_playOpt.name+'</span>'
				+'<a class="close"></a>'
				+'<a class="fullscreen"></a>'
				+'</div>');

			var _w = $("#live_div_" + key)[0].offsetWidth;
			var _h = $("#live_div_" + key)[0].offsetHeight;

			_playOpt.m3u8 = "https://" + _playOpt.m3u8.split('://')[1];
			_playOpt.flv = "https://" + _playOpt.flv.split('://')[1];

			// TcPlayer 参数
			var options = {
				m3u8: _playOpt.m3u8,
				flv: _playOpt.flv,
				rtmp: _playOpt.rmtp,
				autoplay: true, //是否自动播放
				controls:"none", //default 显示默认控件，none 不显示控件
				live: true,	//设置视频是否为直播类型，将决定是否渲染时间轴等控件，以及区分点直播的处理逻辑
				flash:true,
				width:_w,
				height: _h,
				flashUrl:'../static/libs/QCPlayer.swf',
				"wording": {
					2:"正在获取视频，请等待！",
					2032: "请求视频失败，请检查网络",
					2048: "请求m3u8文件失败，可能是网络错误或者跨域问题",
					//1002: "正在加载！"
				},
				listener: function (msg) {
					if(msg.type == 'error') {
						if(msg.detail.code == 1002){
							$(".vcp-error-tips").hide();
						}
						_playOpt.timeCount++;
						if(_playOpt.timeCount >=5 || msg.type == 'playing'){

						}else{
							msg.src.load(options.flv);
						}
						//每秒执行1次，最多执行6次
						/*var dm = setInterval(function() {
							count++;
							msg.src.load(options.flv);
							if(count>=5 || msg.type == 'playing') clearInterval(dm);
						}, 1000);*/
					}
					if (msg.type == 'playing'){
						$(".vcp-error-tips").hide();
					}
				}
			};


			var player = new TcPlayer('live_div_' + key, options);
			// 播放对象加入map  key: live_1  val : player
			this.livePlayer.set(_el , player);

			this._changeView("#live_list_box",this.livePlayer.size);

			var that = this;
			// 绑定视频的关闭按钮 destroy fullscreen
			$("#" + _el + " .close").off('click').on('click',function () {
				oMedia.livePlayer.get(_el).destroy(); //player.destroy();
				oMedia.livePlayer.delete(_el);

				$("#" + _el).remove();

				that._changeView("#live_list_box",oMedia.livePlayer.size);
			});

			// 绑定视频的fullscreen
			$("#" + _el + " .fullscreen").off('click').on('click',function () {
				/*var _player = oMedia.livePlayer.get(_el);
				_player.width = "720px";
				_player.height = "380px";*/

				oMedia.livePlayer.get(_el).fullscreen(true); //player.destroy();
			});
		},

		/**
		 * 按 el 查找 播放器对象
		 * @param el  元素id   live_1   video_2
		 * @param players  this.livePlayer  this.videoPlayer
		 * @private
		 */
		_getPlayer : function(el, players){

			for (var i=0;i<players.length;i++){
				if (el == players[i].id){

				}
			}

		},

		/**
		 * TODO 关闭视频窗口，清除Player
		 * @param key
		 * @param value
		 */
		closeMedia:function (key,value) {
			var _parent = $("#live_div_" + key).parent();
			if (_parent.length > 0){
				var _el = $(_parent).attr("id");

				oMedia.livePlayer.get(_el).destroy(); //player.destroy();
				oMedia.livePlayer.delete(_el);

				$("#" + _el).remove();

				this._changeView();
			}

		},

		/**
		 * TODO 关闭播放按钮，清除URL，关闭视频窗口，清除Player
		 * @param key
		 * @param value
		 */
        removeMediaOpt:function (key,value) {
			this.arrMediaOption.delete(key); //视频信息移除

			if($("#tmpl_" + key + " .video").attr("src").indexOf('off.png') == -1){
				$("#tmpl_" + key + " .video").attr("src","../static/images/list-video-on.png");
				$("#tmpl_" + key + " .video").attr("data-key","");
				// 无人机 视频播放
				$("#tmpl_" + key).off("click");
			}
			this.closeMedia(key,value);
		}

	};

	window.oMedia = oMedia;

})();
