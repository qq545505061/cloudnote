//退出登录
function logout() {
	delCookie("uid");
	window.location.href = base_path + "/log_in.html";
}
//检查cookie
function checkCookie() {
	if (getCookie("uid") == null) {
		window.location.href = "log_in.html";
	}
}