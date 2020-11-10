var setSkin = function(o){
	this.opt = {};
	$.extend(this,this.opt,o);
}
$.extend(setSkin.prototype,{
	init:function(){
		this.bindData();
		this.bindEvent();
	},
	bindData:function(){
		
		this.skinName = this.getCookie('skin');
		if(this.skinName){
			var skin = this.skinName;
		}else{
			/*默认为蓝色*/
			var skin = 'blue';
		}
			$('.badge-'+skin).siblings().removeClass('active');
			$('.badge-'+skin).addClass('active');
			$('body').attr('data-item',skin);
			$('iframe[name="v-iframe"]').contents().find('body').attr('data-item',skin);
			$('.navbar').attr('data-item',skin);
			$('.sidebar').attr('data-item',skin);
		
		setTimeout(function(){
			$('.loading').hide();
		},400)
	},
	bindEvent:function(){
		var that = this;
		//阻止弹出框关闭
		$("body").on('click',function (e) {
        	$('.skinMenu').hide();
    	});
		$("body").on('click','[data-stopPropagation]',function (e) {
        	e.stopPropagation();
    	});
    	$('#skinBox').on('click', '.a-text',function(){
    		$('.skinMenu').toggle();
    	})
    	$('#switchSkin').on('click','.badge',function(){
    		var _color = $(this).data('color');
    		$(this).siblings().removeClass('active');
    		$(this).addClass('active');
    		if(_color != that.skinName){
    			that.setCookie('skin',_color);
    			that.skinName = that.getCookie('skin');
    			$('body').attr('data-item',_color);
				$('.navbar').attr('data-item',_color);
				$('.sidebar').attr('data-item',_color);
				 $('iframe[name="v-iframe"]').contents().find('body').attr('data-item',_color);
				 $('.skinMenu').hide();
				 $('#rightInfo .dropdown-toggle').dropdown('toggle');
    		}
    	})
   },
	/**
     * 读取COOKIE
     */
    getCookie: function (name) {
        var reg = new RegExp("(^| )" + name + "(?:=([^;]*))?(;|$)"), val = document.cookie.match(reg);
        return val ? (val[2] ? unescape(val[2]).replace(/(^")|("$)/g, "") : "") : null;
    },
    /**
     * 写入COOKIES
     */
    setCookie: function (name, value, expires, path, domain, secure) {
        var exp = new Date(), expires = arguments[2] || null, path = arguments[3] || "/", domain = arguments[4] || null, secure = arguments[5] || false;
        expires ? exp.setMinutes(exp.getMinutes() + parseInt(expires)) : "";
        document.cookie = name + '=' + escape(value) + (expires ? ';expires=' + exp.toGMTString() : '') + (path ? ';path=' + path : '') + (domain ? ';domain=' + domain : '') + (secure ? ';secure' : '');
    }
});
(new setSkin()).init();
