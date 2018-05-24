/**alert.js 封装对话框处理**/
//检查是否弹出删除笔记本对话框
function checkDeleteBookWindow(){
	var $li = $(this).parents("li");
	var bookId = $li.data("bookId");
	$.ajax({
		url : base_path + "/note/loadnotes.do",
		type : "post",
		data : {"bookId" : bookId},
		dataType : "json",
		success : function(rst) {
			if (rst.status == 0) {
				var notes = rst.data;
				if(notes == ""){
					//弹出删除笔记本
					alertDeleteBookWindow();
				} else {
					alert("笔记本不为空，请清空笔记本中的笔记后重试")
				}
			}
		},
		error : function() {
			alert("加载笔记列表异常");
		}
	});
}
//弹出删除笔记本对话框
function alertDeleteBookWindow(){
	$("#can").load("alert/alert_delete_notebook.html");
	$(".opacity_bg").show();
}
//弹出彻底删除笔记对话框
function alertDeleteRollbackWindow(){
	//给li添加选中效果
	$(this).parent().addClass("checked");
	$("#can").load("alert/alert_delete_rollback.html");
	$(".opacity_bg").show();
}
//弹出恢复笔记对话框加载下拉列表
function alertReplayNoteWindow(){
	//给li添加选中效果
	$(this).parent().addClass("checked");
	$("#can").load("alert/alert_replay.html",function(){
		// 加载下拉框中笔记本列表
		var books = $("#book_list li");
		for (i = 0; i < books.length; i++) {
			var $li = $(books[i]);
			var name = $li.text();
			var bookId = $li.data("bookId");
			var option = '<option value="' + bookId + '">- ' + name +' -</option>';
			$("#replaySelect").append(option);
		}
	});
	$(".opacity_bg").show();
}
//弹出移动对话框并加载下拉列表
function alertMoveNoteWindow() {
	// 弹出移动笔记对话框
	$("#can").load("alert/alert_move.html",function(){
		// 加载下拉框中笔记本列表
		var books = $("#book_list li");
		for (i = 0; i < books.length; i++) {
			var $li = $(books[i]);
			var name = $li.text();
			var bookId = $li.data("bookId");
			var option = '<option value="' + bookId + '">- ' + name +' -</option>';
			$("#moveSelect").append(option);
		}
	});
	$(".opacity_bg").show();
}
//弹出删除笔记对话框
function alertDeleteNoteWindow(){
	$("#can").load("alert/alert_delete_note.html");
	$(".opacity_bg").show();
}
//弹出创建笔记对话框
function alertAddNoteWindow(){
	//如果没有选中笔记本，提示
	var $a = $("#book_list a.checked");
	if($a.length == 0){
		alert("请选择笔记本");
	} else{
		$("#can").load("alert/alert_note.html");
		$(".opacity_bg").show();
	}
}
//弹出重命名笔记本对话框
function alertRenameBookWindow(){
	$("#can").load("alert/alert_rename.html");
	$(".opacity_bg").show();
}
//弹出创建笔记本对话框
function alertAddBookWindow(){
	//弹出对话框
	$("#can").load("alert/alert_notebook.html");
	$(".opacity_bg").show();
}
//关闭对话框
function closeAlertWindow(){
	$("#can").empty(); //清除对话框内容
	$(".opacity_bg").hide(); //隐藏背景div
}