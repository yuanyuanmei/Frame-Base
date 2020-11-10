var loginForm = function(o) {
	this.opt = {};
	$.extend(this.opt, this, o);
}
$.extend(loginForm.prototype, {
	init : function() {
		this.bindEvent();
		$('#load').show();
		setTimeout(function() {
			$('#load').hide();
			$('.login-box').show();
		}, 400);
	},
	bindEvent : function() {
		var that = this;
		var checked = true;
		$(document).keyup(function(event){
			  if(event.keyCode ==13){
			   that.login();
			  }
		});

		$("#switch").on('click', function () {
			if(checked){
				$(this).removeClass('on');
				checked = false;
			}else{
				$(this).addClass('on');
				checked = true;
			}

		});
		$("#login").on('click',function(){
			that.login();
		});
		//输入框有变化，隐藏提示信息
		$("#username").on("input propertychange",function(){
			$('.login-tips').css({'visibility':'hidden'});
		});
		//输入框有变化，隐藏提示信息
		$("#userpwd").on("input propertychange",function(){
			$('.login-tips').css({'visibility':'hidden'});
		});
	},
	login:function(){
		var that = this;
		//防止重复点击提交
		  if($('#login').hasClass('disable')) 
		  return;
		  if($.trim($('#username').val()) == ''){
		  		$('.login-tips').text('请输入用户名!');
		  		$('.login-tips').css({'visibility':'visible'});
		  		return;
		  }
		  if($('#userpwd').val() == ''){
		  		$('.login-tips').text('请输入密码!');
		  		$('.login-tips').css({'visibility':'visible'});
		  		return;
		  }
		 $('#login').addClass('disable').text('登录中……');
		 that.userLogin();

	},
	userLogin:function(){
		var data={
            "account":$.trim($('#username').val()),
            "password":$("#userpwd").val(),
            "rememberMe":$("#switch").hasClass("on")
        };
        $.ajax({
            type : 'POST',
            dataType : "json",
            url : "login/login",
            data :  JSON.stringify(data),
            cache : false,
			contentType : "application/json; charset=utf-8",
            success : function(data) {
              if(data.code=="200"){
                  location.href = 'index.html';
			  }else{
                  $('.login-tips').text(data.msg);
                  $('.login-tips').css({'visibility':'visible'});
                  $('#login').removeClass('disable').text('登陆');
			  }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {

            }

        });

	}
});
(new loginForm()).init();
