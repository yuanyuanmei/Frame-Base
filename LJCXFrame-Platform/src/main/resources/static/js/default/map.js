/**
 * 通用地图业务模块
 * platform
 * @author 陶冶
 * @version 1.0 2016/6/15
 */
(function() {
	var oMap = {
		isPanorama:false,
		panorama : null, // 全景地图控件
		level : 18,// 地图级别

		init : function() {
			// 二维地图初始化
			MapGD2D.createMap({el:"2dmap"},function(){
				// 监听加载状态改变
			});

			// 任务地图加载
			// MapGD2D.createMap({el:"taskMap"},function(){
			// 	// 监听加载状态改变
			// });

			window.setTimeout(function(){
				$("#loadingDiv").remove();
				var fls = flashChecker();
				if (!fls.f)
					layer.msg("您没有加载flash，视频播放会有延时");
			},1500);

			// 绑定鼠标划入事件
			//this.markerEvent();
		},

		_createAuthFlyMark:function(_option,_isShow){
			MapGD2D.createPolygon(_option,_isShow);
		},

		_createAuthFlyCircleMark:function(_option,_isShow){
			if (_option.coordinate && _option.coordinate != ""){
				var option = {
					lon:_option.coordinate.split(" ")[0],
					lat:_option.coordinate.split(" ")[1],
					memberType:_option.memberType,
					markType:"FlyArea",
					fillColor: _option.color,   // 圆形填充颜色
					fillOpacity:0.1,
					strokeColor: _option.color, // 描边颜色
					strokeOpacity:0.7,
					strokeWeight: 1, // 描边宽度
					radius:_option.radius,
					name: _option.name,
				}
				MapGD2D.createCircle(option,_isShow);
			}
		},
		_createPlaneMark:function(_planes){
			if (_planes.status == 1){
				var content = '<div id="plane-'+ _planes.id +'" class="mark-plane">' +
					'<div class="mark-icon"><img src="../static/images/marker/icon_plane.png"></div>' +
					'<div class="info">'+ _planes.name +'</div>' +
					'</div>';
				if (_planes.position && _planes.position != "" && _planes.position != "NaN,NaN"){
					// 2020-5-12 edify
					// WGS-84 飞机，GCJ-02 高德
					var gcj02 = [_planes.position.split(",")[0],_planes.position.split(",")[1]];
					if(_planes.wcs == 'WGS-84'){
						 gcj02 = GPSTransformation.wgs84togcj02(_planes.position.split(',')[0],_planes.position.split(',')[1]);
					}
					MapGD2D.updateMark({
						lon:gcj02[0],
						lat:gcj02[1],
						memberId: _planes.id,
						memberType: "plane",
						name: _planes.name,
						content:content,
					});
				}
			}
		},

		_createManMark:function(_mans){
			if (_mans.status == 1){
				var content = '<div id="man-'+ _mans.id +'" class="mark-man">' +
					'<div class="mark-icon"><img src="../static/images/marker/icon_human.png"></div>' +
					'<div class="info">'+ _mans.name +'</div>' +
					'</div>';
				if (_mans.position && _mans.position != ""){
					MapGD2D.updateMark({
						lon:_mans.position.split(',')[0],
						lat:_mans.position.split(',')[1],
						memberId: _mans.id,
						memberType: "man",
						name: _mans.name,
						content:content
					});
				}
			}
		},

		_createCarMark:function(_cars){
			if (_cars.status == 1){
				var content = '<div id="car-'+ _cars.id +'" class="mark-car">' +
					'<div class="mark-icon"><img src="../static/images/marker/icon_car.png"></div>' +
					'<div class="info">'+ _cars.name +'</div>' +
					'</div>';
				if (_cars.position && _cars.position != ""){
					MapGD2D.updateMark({
						lon:_cars.position.split(',')[0],
						lat:_cars.position.split(',')[1],
						memberId: _cars.id,
						memberType: "car",
						name: _cars.name,
						content:content
					});
				}
			}
		},

        // 全景图标
		_createPanoramaMark:function(_panoramaMark){

			var content = '<div id="panorama-'+ _panoramaMark.id +'" class="mark-panorama">' +
				'<div class="mark-icon"><img src="../static/images/marker/icon_panorama.png"></div>' +
				//'<div class="info">'+ _panoramaMark.name +'</div>' +
				'</div>';
			if (_panoramaMark.lng && _panoramaMark.lng != ""){
				MapGD2D.updateMark({
					lon:_panoramaMark.lng,
					lat:_panoramaMark.lat,
					memberId: _panoramaMark.id,
					memberType: "panorama",
					name: _panoramaMark.name,
					content:content,
					url: _panoramaMark.url,
					iconSrc:"../static/images/marker/icon_panorama.png"
					// markType: "circle",
					// fillColor: "#0018ff",
					// strokeColor: "#0018ff",
				});
			}

		},

		//自定义图标
		_createCustomMark:function(_customMark){
			MapGD2D.updateMark({
				lon:_customMark.lon,
				lat:_customMark.lat,
				memberId: _customMark.id,
				memberType: "custom",
				name: _customMark.name,
				content:_customMark.content,
				url: _customMark.url,
			});

		},

		_createScenerportMark:function(_scenereportMark){

			var content = '<div id="scenereport-'+ _scenereportMark.id +'"  class="mark-scenereport">' +
				'<div class="mark-icon"><img src="../static/images/marker/icon_scenereport.png"></div>' +
				//'<div class="info">'+ _scenereportMark.content +'</div>' +
				'</div>';
			if (_scenereportMark.lng && _scenereportMark.lng != ""){
				MapGD2D.updateMark({
					lon:_scenereportMark.lng,
					lat:_scenereportMark.lat,
					memberId: _scenereportMark.id,
					memberType: "scenereport",
					name: _scenereportMark.content,
					content:content
					// markType: "circle",
					// fillColor: "#d200ff",
					// strokeColor: "#d200ff",
				});
			}

		},

		_createLayerMark:function(_layerMark){
			return;
			if (_layerMark.lng && _layerMark.lng != ""){
				MapGD2D.updateMark({
					lon:_layerMark.lng,
					lat:_layerMark.lat,
					memberId: _layerMark.id,
					memberType: "layer",
					name: _layerMark.content,
					//content:content,
					markType: "circle",
					fillColor: "red",
					strokeColor: "red",
					url: _layerMark.url,
				});
			}
		},

		/**
		 * 2020-05-12 删除点标记
		 */
		removeMark: function(memberId,memberType){
			if (oIndex.m_TeamMembers == null) return;
			MapGD2D.removeMark(memberId,memberType);
		},

		/**
		 * 地图切换
		 */
		toggleBaseLayer:function(type){
			MapGD2D.toggleBaseLayer(type);
		},
		/**
		 * TODO 显示隐藏分类图标
		 * @param memberType 分类
		 */
		showMark: function(memberId,memberType){
			//if (oIndex.m_TeamMembers == null) return;
			MapGD2D.showOrHideMark(memberId,memberType,true);
		},

		hideMark: function(memberId,memberType){
			if (oIndex.m_TeamMembers == null) return;
			MapGD2D.showOrHideMark(memberId,memberType,false);
		},

		/**
		 * 隐藏显示 禁飞区feature
		 * @param memberType  禁飞区类型
		 * @param _b  true  false
		 */
		toggleFeatures:function(memberType , _b){
			MapGD2D.showOrHideFeatures(memberType , _b);
		},

		// 通过坐标串得到polygon
		getPolygon : function(bounds) {
			var points = bounds.split(',');
			var ps = [];
			for (var p = 0; p < points.length; p++) {
				var point = points[p].split(' ');
				ps.push(new BMap.Point(point[0], point[1]));
			}

			var lines = [];
			var line = new BMap.LinearRing(ps);
			lines.push(line);

			var Polygon = new BMap.Polygon(lines);
			return Polygon;
		},

		// 通过坐标串得到polygon
		getPolygonEx : function(arrPoints) {
			// 正确代码段
			var ps = [];
			for (var i=0;i<arrPoints.length ;i++) {
			    ps.push(new BMap.Point(arrPoints[i].lng,arrPoints[i].lat));
			}
			var Polygon = new BMap.Polygon(ps, {strokeWeight: 2, strokeColor: '#0905ff', fillColor: '#5182E4', fillOpacity: 0.2});
			return Polygon;
		},

		// 显示或隐藏图标集合
		toggleMark : function(marks,b,c,id) {
			// 关闭气泡
			MapGD2D.map.closeInfoWindow();
			for (var i = 0; i < marks.length; i++) {
				if (b) {
					marks[i].show();
					if(c!=null&&c!=""&c!=undefined){
						c.show();
					}
				} else {
					if(id=="mapList_online"){
						$(".icon_classification").hide();
					}
					marks[i].hide();
					if(c!=null&&c!=""&c!=undefined){
						c.hide();
					}
				}
			}
		},

		resetMapPosition : function() {
			$('.labelDivInMap').stop();
			MapGD2D.map.enableDragging();
			clearTimeout(this.timer);
			$('.labelDivInMap').css({
				top : '-16px',
				left : '-5px'
			});
			$('.labelDivInMap').removeClass('ss');
			MapGD2D.map.removeOverlay(this.canvasMarker);
		},

		// 展开气泡窗口
		openInfo : function(content, e) {
			var p = e.target;
			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat,20);
			var infoWindow = new BMap.InfoWindow(content, this.winOpts); // 创建信息窗口对象
			MapGD2D.map.openInfoWindow(infoWindow, point); // 开启信息窗口

			/*
			 * var myCompOverlay = new AutoOverlay(point);
			 * MapGD2D.map.addOverlay(myCompOverlay);
			 */
		},

		/* 清空所有，气泡，图标 */
		clearAll : function() {
			MapGD2D.map.clearOverlays();
		},

		/* 清空气泡 */
		clearPopup : function() {

		},
		// 通过覆盖物的某个ID获取到对应的位置并放大 param:id 覆盖物的 ID content :覆盖物对应的信息窗口的内容
		// isShowCont:是否显示信息窗口的内容
		getLargePointByattr : function(id, content, isShowCont,type) {
			var allOverlay = MapGD2D.map.getOverlays();
			for (var i = 0; i < allOverlay.length; i++) {
				if (allOverlay[i].id == id) {
					//MapGD2D.map.setCenter(allOverlay[i].getPosition().lng,allOverlay[i].getPosition().lat);
					allOverlay[i].setZIndex(30000);
					MapGD2D.map.centerAndZoom(
							new BMap.Point(allOverlay[i].getPosition().lng,
									allOverlay[i].getPosition().lat), 20);
					allOverlay[i].show();
					if(type!=null&&type!=""){
						var opts={
								width:350
						}
					}else{
						var opts={
								//width:350
						}
					}
					
					if (isShowCont) {
						MapGD2D.map.openInfoWindow(new BMap.InfoWindow(content,opts),
								new BMap.Point(allOverlay[i].getPosition().lng,
										allOverlay[i].getPosition().lat)); // 开启信息窗口
					}
				}
			}
		},

		// 清除指定类型的覆盖物
		clearTypeOverlays : function(type) {
			var allOverlay = MapGD2D.map.getOverlays();
			for (var i = 0; i < allOverlay.length; i++) {
				if (allOverlay[i].type == type) {
					MapGD2D.map.removeOverlay(allOverlay[i]);
				}
			}
		},

		zoomToExtent : function(bound) {

			var pt = bound.getCenter();
			var zoom = 11;

			zoom = this._getZoomByBound(bound);
			MapGD2D.map.centerAndZoom(pt, zoom);
		},

		_getZoomByBound : function(bound) {
			var zoom = [ "50", "100", "200", "500", "1000", "2000", "5000",
					"10000", "20000", "25000", "50000", "100000", "200000",
					"500000", "1000000", "2000000" ];// 级别18到3。
			var max_pt = bound.getNorthEast(); // 创建点坐标A
			var min_pt = bound.getSouthWest(); // 创建点坐标B
			var distance = MapGD2D.map.getDistance(max_pt, min_pt).toFixed(1); // 获取两点距离,保留小数点后两位
			for (var i = 0, zoomLen = zoom.length; i < zoomLen; i++) {
				if (zoom[i] - distance > 0) {
					return 18 - i + 3;// 之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。
				}
            }
        },

		// 根据原始数据计算中心坐标和缩放级别，并为地图设置中心坐标和缩放级别。
		setZoom : function(points) {
			if (points.length > 0) {
				var maxLng = points[0].lng;
				var minLng = points[0].lng;
				var maxLat = points[0].lat;
				var minLat = points[0].lat;
				var res;
				for (var i = points.length - 1; i >= 0; i--) {
					res = points[i];
					if (res.lng > maxLng)
						maxLng = res.lng;
					if (res.lng < minLng)
						minLng = res.lng;
					if (res.lat > maxLat)
						maxLat = res.lat;
					if (res.lat < minLat)
						minLat = res.lat;
                }
                var cenLng = (parseFloat(maxLng) + parseFloat(minLng)) / 2;
				var cenLat = (parseFloat(maxLat) + parseFloat(minLat)) / 2;
				var zoom = oMap.getZoom(maxLng, minLng, maxLat, minLat);
				MapGD2D.map.centerAndZoom(new BMap.Point(cenLng, cenLat), zoom);
			} else {
				MapGD2D.map.centerAndZoom(new BMap.Point(114.252348, 30.651167),18);
			}
		},

		// 根据经纬极值计算绽放级别。
		getZoom : function(maxLng, minLng, maxLat, minLat) {
			var zoom = [ "50", "100", "200", "500", "1000", "2000", "5000",
					"10000", "20000", "25000", "50000", "100000", "200000",
					"500000", "1000000", "2000000" ];//级别18到3。
			var pointA = new BMap.Point(maxLng, maxLat); // 创建点坐标A  
			var pointB = new BMap.Point(minLng, minLat); // 创建点坐标B  
			var distance = MapGD2D.map.getDistance(pointA, pointB).toFixed(1); //获取两点距离,保留小数点后两位  
			for (var i = 0, zoomLen = zoom.length; i < zoomLen; i++) {
				if (zoom[i] - distance > 0) {
					return 18 - i + 3;//之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。  
				}
            }
        },
		
		//百度坐标转高德（传入经度、纬度）
		 bd2gd : function(bd_lng, bd_lat) {
		    var X_PI = Math.PI * 3000.0 / 180.0;
		    var x = bd_lng - 0.0065;
		    var y = bd_lat - 0.006;
		    var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
		    var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
		    var gg_lng = z * Math.cos(theta);
		    var gg_lat = z * Math.sin(theta);
		    return [gg_lng,gg_lat];
		},

		//高德坐标转百度（传入经度、纬度）
		gd2bd : function(gg_lng, gg_lat) {
		    var X_PI = Math.PI * 3000.0 / 180.0;
		    var x = gg_lng, y = gg_lat;
		    var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
		    var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
		    var bd_lng = z * Math.cos(theta) + 0.0065;
		    var bd_lat = z * Math.sin(theta) + 0.006;
		    return [bd_lat,bd_lng];
		}
	};

	window.oMap = oMap;
	oMap.init();

})();

// 2020-5-12 edify
(function() {
	var  GPSTransformation = {
		x_PI: 3.14159265358979324 * 3000.0 / 180.0,
		PI : 3.1415926535897932384626,
		a : 6378245.0,
		ee : 0.00669342162296594323,
		/**
		 * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
		 * 即 百度 转 谷歌、高德
		 * @param bd_lon
		 * @param bd_lat
		 * @returns {*[]}
		 */
		bd09togcj02: function(bd_lon, bd_lat) {
			bd_lon=Number(bd_lon);
			bd_lat=Number(bd_lat);
			var x_pi = 3.14159265358979324 * 3000.0 / 180.0;
			var x = bd_lon - 0.0065;
			var y = bd_lat - 0.006;
			var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
			var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
			var gg_lng = z * Math.cos(theta);
			var gg_lat = z * Math.sin(theta);
			return [gg_lng, gg_lat]
		},
		/**
		 * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
		 * 即谷歌、高德 转 百度
		 * @param lng
		 * @param lat
		 * @returns {*[]}
		 */
		gcj02tobd09: function(lng, lat) {
			lng=Number(lng);
			lat=Number(lat);
			var z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * this.x_PI);
			var theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * this.x_PI);
			var bd_lng = z * Math.cos(theta) + 0.0065;
			var bd_lat = z * Math.sin(theta) + 0.006;
			return [bd_lng, bd_lat]
		},
		/**
		 * WGS84转GCj02
		 * @param lng
		 * @param lat
		 * @returns {*[]}
		 */
		wgs84togcj02: function(lng, lat) {
			lng=Number(lng);
			lat=Number(lat);
			if (this.out_of_china(lng, lat)) {
				return [lng, lat]
			} else {
				var dlat = this.transformlat(lng - 105.0, lat - 35.0);
				var dlng = this.transformlng(lng - 105.0, lat - 35.0);
				var radlat = lat / 180.0 * this.PI;
				var magic = Math.sin(radlat);
				magic = 1 - this.ee * magic * magic;
				var sqrtmagic = Math.sqrt(magic);
				dlat = (dlat * 180.0) / ((this.a * (1 - this.ee)) / (magic * sqrtmagic) * this.PI);
				dlng = (dlng * 180.0) / (this.a / sqrtmagic * Math.cos(radlat) * this.PI);
				var mglat = lat + dlat;
				var mglng = lng + dlng;
				return [mglng, mglat]
			}
		},
		/**
		 * GCJ02 转换为 WGS84
		 * @param lng
		 * @param lat
		 * @returns {*[]}
		 */
		gcj02towgs84: function(lng, lat) {
			lng=Number(lng);
			lat=Number(lat);
			if (this.out_of_china(lng, lat)) {
				return [lng, lat]
			} else {
				var dlat = this.transformlat(lng - 105.0, lat - 35.0);
				var dlng = this.transformlng(lng - 105.0, lat - 35.0);
				var radlat = lat / 180.0 * this.PI;
				var magic = Math.sin(radlat);
				magic = 1 - this.ee * magic * magic;
				var sqrtmagic = Math.sqrt(magic);
				dlat = (dlat * 180.0) / ((this.a * (1 - this.ee)) / (magic * sqrtmagic) * this.PI);
				dlng = (dlng * 180.0) / (this.a / sqrtmagic * Math.cos(radlat) * this.PI);
				var mglat = lat + dlat;
				var mglng = lng + dlng;
				return [lng * 2 - mglng, lat * 2 - mglat]
			}
		},

		transformlat: function(lng, lat) {
			var ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
			ret += (20.0 * Math.sin(6.0 * lng * this.PI) + 20.0 * Math.sin(2.0 * lng * this.PI)) * 2.0 / 3.0;
			ret += (20.0 * Math.sin(lat * this.PI) + 40.0 * Math.sin(lat / 3.0 * this.PI)) * 2.0 / 3.0;
			ret += (160.0 * Math.sin(lat / 12.0 * this.PI) + 320 * Math.sin(lat * this.PI / 30.0)) * 2.0 / 3.0;
			return ret
		},

		transformlng: function(lng, lat) {
			var ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
			ret += (20.0 * Math.sin(6.0 * lng * this.PI) + 20.0 * Math.sin(2.0 * lng * this.PI)) * 2.0 / 3.0;
			ret += (20.0 * Math.sin(lng * this.PI) + 40.0 * Math.sin(lng / 3.0 * this.PI)) * 2.0 / 3.0;
			ret += (150.0 * Math.sin(lng / 12.0 * this.PI) + 300.0 * Math.sin(lng / 30.0 * this.PI)) * 2.0 / 3.0;
			return ret
		},
		/**
		 * 判断是否在国内，不在国内则不做偏移
		 * @param lng
		 * @param lat
		 * @returns {boolean}
		 */
		out_of_china: function(lng, lat) {
			return (lng < 72.004 || lng > 137.8347) || ((lat < 0.8293 || lat > 55.8271) || false);
		}
	};
	window.GPSTransformation = GPSTransformation;
})();