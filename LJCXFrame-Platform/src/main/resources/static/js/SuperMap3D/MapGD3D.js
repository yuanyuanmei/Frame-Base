/**
 * 高德地图模块
 * @author 陶冶
 * @version 1.0 2019/2/21
 */

(function() {
	var MapSP3D = {
		viewer : null,
		SCENE_SUOFEIYA: 'http://www.supermapol.com/realspace/services/3D-suofeiya_church/rest/realspace', // 索菲亚大教堂倾斜数据场景

		create3DMap:function(el){
			this.viewer = new Cesium.Viewer(el);

			this.googlelayer = new Cesium.UrlTemplateImageryProvider({
				url:"https://mt1.google.cn/vt/lyrs=s&hl=zh-CN&x={x}&y={y}&z={z}&s=Gali"
			});

			this.viewer.imageryLayers.addImageryProvider(this.googlelayer);
			this.scene = this.viewer.scene;
			this.widget = this.viewer.cesiumWidget;



			this.load3D();

		},

		load3D: function () {
			var that = this;
			try{
				var promise = this.scene.open(this.SCENE_SUOFEIYA);

				Cesium.when(promise,function(layers){
					var layer = that.scene.layers.find('Config');
					var sceneLayer = layer;
					//设置相机位置，定位至模型
					that.scene.camera.setView({
						//将经度、纬度、高度的坐标转换为笛卡尔坐标
						destination : new Cesium.Cartesian3(-2653915.6463913363,3571045.726807149,4570293.566342328),
						orientation : {
							heading : 2.1953426301495345,
							pitch : -0.33632707158103625,
							roll : 6.283185307179586
						}
					});
				},function(){
					var title = '加载SCP失败，请检查网络连接状态或者url地址是否正确？';
					that.widget.showErrorPanel(title, undefined, e);
				});
			}
			catch(e){
				if (this.widget._showRenderLoopErrors) {
					var title = '渲染时发生错误，已停止渲染。';
					this.widget.showErrorPanel(title, undefined, e);
				}
			}
		}

	};

	window.MapSP3D = MapSP3D;
})();
