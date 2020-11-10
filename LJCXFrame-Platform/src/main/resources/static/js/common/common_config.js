/**
 *  系统请求路径
 */
var Config = {
	teamId:0,

	/**将时间戳转化成指定格式的日期*/
	timestampToTime:function (timestamp,format) {
		var date;
		//时间戳为10位需*1000，时间戳为13位的话不需乘1000
		if((timestamp+"").length<=10){
			date = new Date(timestamp * 1000);
		}else{
			date = new Date(timestamp);
		}
		Y = date.getFullYear() + '-';
		M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
		D = date.getDate() + ' ';
		h = date.getHours() + ':';
		m = date.getMinutes() + ':';
		s = date.getSeconds();
		return new Date(Y+M+D+h+m+s).format(format);
	},

	/**将时间化成指定格式的日期*/
	dateToTime:function (date,format) {
		Y = date.getFullYear() + '-';
		M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
		D = date.getDate() + ' ';
		h = date.getHours() + ':';
		m = date.getMinutes() + ':';
		s = date.getSeconds();
		return new Date(Y+M+D+h+m+s).format(format);
	},

	/**日期解析，字符串转日期
	 * @param dateString 可以为2017-02-16，2017/02/16，2017.02.16
	 * @returns {Date} 返回对应的日期对象
	 */
	dateParse:function(dateString){
		var SEPARATOR_BAR = "-";
		var SEPARATOR_SLASH = "/";
		var SEPARATOR_DOT = ".";
		var dateArray;
		if(dateString.indexOf(SEPARATOR_BAR) > -1){
			dateArray = dateString.split(SEPARATOR_BAR);
		}else if(dateString.indexOf(SEPARATOR_SLASH) > -1){
			dateArray = dateString.split(SEPARATOR_SLASH);
		}else{
			dateArray = dateString.split(SEPARATOR_DOT);
		}
		return new Date(dateArray[0], dateArray[1]-1, dateArray[2]);
	},

	/**
	 * 日期比较大小
	 * compareDateString大于dateString，返回1；
	 * 等于返回0；
	 * compareDateString小于dateString，返回-1
	 * @param dateString 日期
	 * @param compareDateString 比较的日期
	 */
	dateCompare:function(dateString, compareDateString){
		var dateTime = this.dateParse(dateString).getTime();
		var compareDateTime = this.dateParse(compareDateString).getTime();
		if(compareDateTime > dateTime){
			return 1;
		}else if(compareDateTime == dateTime){
			return 0;
		}else{
			return -1;
		}
	},

	/**
	 * 判断日期是否在区间内，在区间内返回true，否返回false
	 * @param dateString 日期字符串
	 * @param startDateString 区间开始日期字符串
	 * @param endDateString 区间结束日期字符串
	 * @returns {Number}
	 */
	isDateBetween:function(dateString, startDateString, endDateString){
		var flag = false;
		var startFlag =(this.dateCompare(dateString, startDateString) < 1);
		var endFlag = (this.dateCompare(dateString, endDateString) > -1);
		if(startFlag && endFlag){
			flag = true;
		}
		return flag;
	},

};

Date.prototype.format = function(format) {
    var date = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "H+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S+" : this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for ( var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
};


