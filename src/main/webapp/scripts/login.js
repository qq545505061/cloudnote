/**
 * js封装处理
 * @returns
 */
// log_in.html主处理
$(function() {// 页面载入完毕
	// 给登录按钮绑定单击处理
	$("#login").click(checkLogin);
	// 给输入框绑定回车事件
	$("input").keydown(toCheckLogin)
	// 给注册按钮啊绑定单击处理
	$("#regist_button").click(registUser);
});
// 转向登录检查
function toCheckLogin(event){
	var code = event.keyCode;
	if(code == 13){
		checkLogin();
	}
}
// 登录检查
function checkLogin() {
	// 获取请求参数
	var name = $("#count").val().trim();
	var password = $("#password").val().trim();
	// 检测参数格式
	// 清空以前提示信息
	$("#count_span").html("");
	$("#password_span").html("");
	var ok = true;// 检测是否通过
	if (name == "") {
		ok = false;
		$("#count_span").html("用户名为空");
	}
	if (password == "") {
		ok = false;
		$("#password_span").html("密码为空");
	}
	// 发送Ajax请求
	if (ok) {
		$.ajax({
			url : base_path + "/user/login.do",
			type : "post",
			data : {
				"name" : name,
				"password" : password
			},
			dataType : "json",
			success : function(result) {
				if (result.status == 0) {
					// 登录成功，将信息写入Cookie
					var user = result.data; //获取返回的user对象
					addCookie("uid",user.cn_user_id,2);
					addCookie("uname",user.cn_user_name,2);
					window.location.href = 'edit.html';

				} else if (result.status == 1) {
					// 用户名错误
					$("#count_span").html(result.msg);
				} else if (result.status == 2) {
					$("#password_span").html(result.msg);
				}
			},
			error : function() {
				alert("登录异常");
			}
		});
	}
}
//注册处理
function registUser(){
	//获取请求参数
	var name = $("#regist_username").val().trim();
	var password = $("#regist_password").val().trim();
	var f_password = $("#final_password").val().trim();
	var nick = $("#nickname").val().trim();
	//清空消息
	$("#warning_1 span").html("");
	$("#warning_2 span").html("");
	$("#warning_3 span").html("");
	//格式检查
	var ok = true;
	if(name == ""){
		$("#warning_1").show();
		$("#warning_1 span").html("用户名为空");
		ok = false;
	}
	if(password == ""){
		$("#warning_2").show();
		$("#warning_2 span").html("密码为空");
		ok = false;
	} else if(password.length<6){
		$("#warning_2").show();
		$("#warning_2 span").html("密码长度太短");
		ok = false;
	}
	if(f_password == ""){
		$("#warning_3").show();
		$("#warning_3 span").html("确认密码为空");
		ok = false;
	} else if(f_password != password){
		$("#warning_3").show();
		$("#warning_3 span").html("密码输入不一致");
		ok = false;
	}
	//发送ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/user/add.do",
			type:"post",
			data:{"name":name,"nick":nick,"password":password},
			dataType:"json",
			success:function(rst){
				if(rst.status == 0){//成功
					alert(rst.msg);
					//转向到登录页面
					$("#back").click();
				} else if(rst.status == 1){//用户名被占用
					$("#warning_1").show();
					$("#warning_1 span").html("用户名被占用");
				}
			},
			error:function(){
				alert("注册异常！");
			}
		});
	}
}