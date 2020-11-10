Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};
var Ajax = {
	contentType : {
		"application_json" : "application/json; charset=utf-8",
		"text_xml" : "text/xml; charset=utf-8",
		"application_form" : "application/x-www-form-urlencoded; charset=utf-8",
	},
	/**
	 * ajax post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param contentType
	 *            请求类型
	 * @param data
	 *            数据
	 * @param token
	 *            设置header的token
	 * @param callBack
	 *            回调函数
	 * @param errorFun
	 *            错误处理函数
	 */
	postJson : function(url, contentType, data, token, callBack, errorFun) {
		contentType = contentType != null ? contentType : this.contentType.application_json;
		$.ajax({
			type : 'POST',
			dataType : "json",
			url : url,
			data : data,
			contentType : contentType,
			cache : false,
			// beforeSend : function(request) {
			// 	request.setRequestHeader("token", token);
			// },
			async : true,
			success : function(data) {
				callBack(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				errorFun(XMLHttpRequest, textStatus, errorThrown);
			}

		});
	},
	/**
	 * ajax post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param contentType
	 *            请求类型
	 * @param data
	 *            数据
	 * @param token
	 *            设置header的token
	 * @param callBack
	 *            回调函数
	 * @param errorFun
	 *            错误处理函数
	 */
	getJson : function(url, contentType, data, token, callBack, errorFun) {
		contentType = contentType != null ? contentType : this.contentType.application_json;
		$.ajax({
			type : 'GET',
			dataType : "json",
			url : url,
			data : data,
			contentType : contentType,
			cache : false,
			// beforeSend : function(request) {
			// 	request.setRequestHeader("token", token);
			// },
			async : true,
			success : function(data) {
				callBack(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				errorFun(XMLHttpRequest, textStatus, errorThrown);
			}

		});
	},

	/**
	 * 获取cookie
	 * 
	 * @param name
	 *            cookie名称
	 * @returns
	 */
	getCookie : function(name) {
		var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
		if (arr = document.cookie.match(reg)) {
			return unescape(arr[2]);
		} else {
			return "";
		}

	},
	/**
	 * 设置cookie
	 * 
	 * @param name
	 * @param value
	 */
	setCookie : function(name, value) {
		var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
		document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
	},
	/**
	 * 删除cookie
	 * 
	 * @param name
	 *            名称
	 */
	delCookie : function(name) {
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval = this.getCookie(name);
		if (cval != null)
			document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
	},
	/**
	 * 获取字典
	 * 
	 * @param url
	 *            请求地址
	 * @param typeCode
	 *            分类编码
	 */
	getDictionary : function(url, typeCode, callBack, errorFun) {
		this.getJson(url, this.contentType.application_form, {
			"typeCode" : typeCode
		}, this.getCookie("token"), callBack, errorFun);
	},

	/**
	 * 
	 * @param key 接收地址栏参数 key:参数名称
	 * @returns
	 */
	getQuery : function(key) {
		var search = location.search.slice(1); // 得到get方式提交的查询字符串
		var arr = search.split("&");
		for (var i = 0; i < arr.length; i++) {
			var ar = arr[i].split("=");
			if (ar[0] == key) {
				return ar[1];
			}
		}
	},
};
