/**
 * 百度鹰眼巡逻轨迹业务模块
 * @author 陶冶
 * @version 1.0 2018/4/25
 */
var current_line=null;
 
(function() {
	var oTrack = {
		isTrack:false,
		index:null,//记录播放到第几个point
	    points:null,//坐标点数据
	    timejson:[],//时间数据
	    trackLine:null,//时间轴对象
	    isPlay:false,
		planeId:0,
		recordsId:0,
		map: null,

		// 初始化地图
		init : function(val){
			if (this.map == null){
				MapGD2D.createMap({el:"routeMap", zoom:15},function(){
					// 监听加载状态改变
				});
				MapGD2D.toggleBaseLayer("vec");
				this.map = MapGD2D.map;
				this.map.on('pointermove', function() {

				});
				oTrack.getTrack(val);
			}
		},

		/*通过终端name 得到轨迹*/
		getTrack:function (val){
			//MapGD2D.clearAll();

			oTrack.index = 0;
			oTrack.points = [];
			oTrack.timejson=[];
			this.isTrack=true;
			var pointsLine = "";

			// 创建轨迹线
			for(var i=0;i<val.length;i++){
				var points = val[i].position;
				var gcpoints = GPSTransformation.wgs84togcj02(points.split(",")[0],points.split(",")[1]);
				pointsLine+= i==0 ? gcpoints[0]+","+gcpoints[1] : (" "+gcpoints[0]+","+gcpoints[1]);
				if(i>0){
					//如果上一个的结束时间和下一个的开始时间是在10分钟之内的话，就默认是连续的
					if((parseInt(new Date(Config.timestampToTime(val[i].createTimeStamp, "yyyy-MM-dd HH:mm:ss"))-(new Date(Config.timestampToTime(val[i-1].localTimeStamp, "yyyy-MM-dd HH:mm:ss"))))/1000/ 60)<=10){
						oTrack.timejson.push({min:new Date(Config.timestampToTime(val[i].localTimeStamp, "yyyy-MM-dd HH:mm:ss")),max:new Date(Config.timestampToTime(val[i].localTimeStamp, "yyyy-MM-dd HH:mm:ss"))});
					}else{
						oTrack.timejson.push({min:new Date(Config.timestampToTime(val[i].createTimeStamp, "yyyy-MM-dd HH:mm:ss")),max:new Date(Config.timestampToTime(val[i].localTimeStamp, "yyyy-MM-dd HH:mm:ss"))});
					}
				}else{
					oTrack.timejson.push({min:new Date(Config.timestampToTime(val[i].createTimeStamp, "yyyy-MM-dd HH:mm:ss")),max:new Date(Config.timestampToTime(val[i].localTimeStamp, "yyyy-MM-dd HH:mm:ss"))});
				}
				oTrack.points.push(gcpoints);
			}

			//添加时间轴

			if(oTrack.points.length > 0){
				$("#xlgjdiv").show();
				oTrack.trackElent();
				oTrack.planeId = val[0].uavId;
				oTrack.recordsId = val[0].recordsId;

				//添加起始点
				var content = '<div id="custom-1000" class="mark-custom">' +
					'<div class="mark-icon"><img src="../static/images/marker/icon_start.png" style="transform: scale(0.8)"></div>' +
					'</div>';

				MapGD2D.updateMark({
					lon:oTrack.points[0][0],
					lat:oTrack.points[0][1],
					memberId: 1000,
					memberType: "custom",
					name: "start",
					content:content,
					url: "",
				});

				//添加终止点
				var content = '<div id="custom-9999" class="mark-custom">' +
					'<div class="mark-icon"><img src="../static/images/marker/icon_end.png" style="transform: scale(0.8)"></div>' +
					'</div>';

				MapGD2D.updateMark({
					lon:oTrack.points[oTrack.points.length-1][0],
					lat:oTrack.points[oTrack.points.length-1][1],
					memberId: 9999,
					memberType: "custom",
					name: "end",
					content:content,
					url: "",
				});

				//飞机
				var content = '<div id="custom-'+ oTrack.planeId +'"  class="mark-custom">' +
					'<div class="mark-icon"><img src="../static/images/marker/icon_plane.png" style="transform: scale(0.8)"></div>' +
					'</div>';

				MapGD2D.updateMark({
					lon:oTrack.points[0][0],
					lat:oTrack.points[0][1],
					memberId: oTrack.planeId,
					memberType: "custom",
					name: "plane",
					content:content,
					url: "",
					offset:[-16,-16]
				});

				//画线
				MapGD2D.createLine({
					vertices:pointsLine,
					memberType:"flyLine",
                    color:"#28F",
                    strokeWidth:5,
					flyLineId:oTrack.recordsId,
					name:"测试线"
				},true);


				//等比例缩放
				MapGD2D.map.getView().fit(MapGD2D.lineLayer.getSource().getExtent(),MapGD2D.map.getSize());
			}else{
				// 清除 时间轴
				//MapGD2D.map.setCenterAndZoom([],15);
				$("#xlgjdiv").hide();
			}
		},

		//播放轨迹
	    play:function(){
			var point = oTrack.points[oTrack.index];

            MapGD2D.updateMark({
                lon:point[0],
                lat:point[1],
                memberId: oTrack.planeId,
                memberType: "custom",
            });
			//oTrack.car.setPosition(point);
		    if(oTrack.trackLine!=null){
		        //oTrack.trackLine.setDate(Config.timestampToTime(oTrack.timejson[oTrack.index], "yyyy-MM-dd"));
		    	oTrack.trackLine.setValue(oTrack.timejson[oTrack.index].min);

		    }
		    oTrack.index++;
		    if(oTrack.index < oTrack.points.length && oTrack.isPlay) {
		    	oTrack.timer = window.setTimeout("oTrack.play(" + oTrack.index + ")",500);
		    }
		},

		//初始化时间轴
		trackElent:function (){
			var date=new Date();
			if(oTrack.timejson.length>0){
				date=oTrack.timejson[0].min;
			}
			oTrack.trackLine=$("#trackline").trackLime({
				date:Config.timestampToTime(date, "yyyy-MM-dd"),
				bounds:oTrack.timejson,
				value:new Date(Config.timestampToTime(date+" 00:00:00", "yyyy-MM-dd HH:mm:ss")), //默认选中区域
				onmousemove : function(event){
					 for(var i=0;i<oTrack.timejson.length;i++){
						 if(parseInt(new Date(oTrack.timejson[i].max)-(new Date(event)))/1000<5){
							 oTrack.car.setPosition(oTrack.points[i]);
						 }
					 }
				},
				onmousedown: function(event){
					clearTimeout(oTrack.timer);
				}
			 });
		},

		//按钮的暂停播放
		playOrpause:function(){
			if(oTrack.index==oTrack.points.length){
				oTrack.index=0;
			}
			oTrack.isPlay = !oTrack.isPlay;
			if(oTrack.isPlay){
				$("#playBox").addClass("play");
				oTrack.play(oTrack.index);
			}else{
				$("#playBox").removeClass("play");
			}
		},

		
	};

	window.oTrack = oTrack;
})();





