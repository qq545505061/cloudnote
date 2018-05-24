/** note.js 封装笔记相关操作* */
//显示收藏列表
function loadLike() {
	$("#pc_part_2").hide();
	$("#pc_part_4").hide();
	$("#pc_part_6").hide();
	$("#pc_part_7").show();
	$("#pc_part_8").hide();
}
// 预览笔记
function loadShareNote(){
	//获取请求参数
	var noteId = $(this).data("noteId");
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/load_share.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(rst){
			if(rst.status == 0){
				var share = rst.data;
				var title = share.cn_share_title;
				var body = share.cn_share_body;
				$("#noput_note_title").html(title);
				$("#noput_note_title").next().html(body);
				$("#pc_part_3").hide();
				$("#pc_part_5").show();
			}
		},
		error:function(){
			alert("预览笔记异常");
		}
	});
}
//恢复笔记
function replayNote(){
	//获取参数
	var $li = $("#rollback_list a.checked").parent();
	var noteId = $li.data("noteId");
	var bookId = $("#replaySelect").val();
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/replay_note.do",
		type:"post",
		data:{"noteId":noteId,"bookId":bookId},
		dataType:"json",
		success:function(rst){
			if(rst.status == 0){
				$li.remove();
				alert(rst.msg);
			}
		},
		error:function(){
			alert("移动笔记异常");
		}
	});
}
//彻底删除笔记
function deleteRollbackNote(){
	//获取参数
	var $li = $("#rollback_list .checked").parent();
	var noteId = $li.data("noteId");
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/delete_rollback.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(rst){
			if(rst.status == 0){
				//删除li元素
				$li.remove();
				alert(rst.msg);
			}
		},
		error:function(){
			alert("彻底删除笔记异常");
		}
	});
	
}
//加载更多分享笔记
function loadMore(){
	if(more){
		//将page+1
		page = page+1;
		//发送ajax请求加载数据
		searchSharePage(keyword,page);
	} else {
		$("#more_note").html("没有更多数据了...");
	}
}
//分页加载搜索分享笔记
function searchSharePage(keyword,page){
	$.ajax({
		url:base_path+"/note/search_share.do",
		type:"post",
		data:{"keyword":keyword,"page":page},
		dataType:"json",
		success:function(rst){
			if(rst.status == 0){
				//获取返回数据
				var shares = rst.data;
				if(shares.length == 0){
					more = false;
				}
				//生成搜索结果列表
				for(i=0; i<shares.length; i++){
					var share = shares[i];
					var title = share.cn_share_title;
					var noteId = share.cn_note_id;
					var li = '<li class="online">'
						+ '<a>'
						+ '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'
						+ title
						+ '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down">'
						+ '<i class="fa fa-star"></i></button>'
						+ '</a></li>';
					$li = $(li).data("noteId",noteId);
					$("#pc_part_6 ul").append($li);
				}
			}
		},
		error:function(){
			alert("搜索分享笔记异常");
		}
	});
}
//搜索分享笔记
function searchShareNote(event){
	var code = event.keyCode;
	if(code == 13){//回车键
		//清除之前的列表
		$("#pc_part_6 ul").html("");
		//显示搜索结果列表，其他列表隐藏
		$("#pc_part_2").hide();
		$("#pc_part_4").hide();
		$("#pc_part_7").hide();
		$("#pc_part_8").hide();
		$("#pc_part_6").show();
		//获取请求参数
		var keyword = $("#search_note").val();
		page = 1;
		//发送ajax请求
		searchSharePage(keyword,1);
	}
}
//分享笔记
function shareNote(){
//获取参数
//	var $li = $("#note_list a.checked").parent();
	var $li = $(this).parents("li");
	var noteId = $li.data("noteId");
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/share.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(rst){
			if(rst.status == 0){
				//添加分享图标
				var img = '&nbsp;<i class="fa fa-sitemap"></i>';
				$li.find(".btn_slide_down").before(img);
				alert(rst.msg);
			} else if(rst.status == 2){
				alert(rst.msg);
			}
		},
		error:function(){
			alert("分享笔记异常");
		}
	});
}
//加载回收站列表
function loadRollback(){
	$("#pc_part_2").hide();
	$("#pc_part_4").show();
	$("#pc_part_6").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").hide();
	var userId = getCookie("uid");
	$.ajax({
		url:base_path+"/note/rollback.do",
		type:"post",
		data:{"userId":userId},
		dataType:"json",
		success:function(rst){
			if(rst.status == 0){
				//清楚之前的列表
				$("#rollback_list").html("");
				var notes = rst.data;
				for(i=0; i<notes.length; i++){
					var note = notes[i];
					var noteTitle = note.cn_note_title;
					var noteId = note.cn_note_id;
					var lis = "";
					lis += '<li class="disable">';
					lis += '<a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
					lis += noteTitle;
					lis += '<button type="button" class="btn btn-default btn-xs btn_position btn_delete">';
					lis += '<i class="fa fa-times"></i></button>';
					lis += '<button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay">';
					lis += '<i class="fa fa-reply"></i></button>';
					lis += '</a>';
					lis += '</li>';
					$li = $(lis);
					$li.data("noteId",noteId);
					$("#rollback_list").append($li);
				}
			}
		},
		error:function(){
			alert("回收站列表加载异常");
		}
	});
}
//移动笔记
function moveNote(){
	//获取参数
	var $li = $("#note_list a.checked").parent();
	var noteId = $li.data("noteId");
	var bookId = $("#moveSelect").val();
	//检查格式
	var ok = true;
	if(bookId == "none"){
		ok = false;
		alert("请选择要将笔记移动到哪个笔记本")
	}
	//发送ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/note/move.do",
			type:"post",
			data:{"noteId":noteId,"bookId":bookId},
			dataType:"json",
			success:function(rst){
				if(rst.status == 0){
					$li.remove();
					alert(rst.msg);
				}
			},
			error:function(){
				alert("移动笔记异常");
			}
		});
	}
}

// 删除笔记
function deleteNote(){
	//获取参数
	var $li = $("#note_list a.checked").parent();
	var noteId = $li.data("noteId");
	var noteTitle = $li.text();
	//发送ajax请求
	$.ajax({
		url:base_path+"/note/delete.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(rst){
			if(rst.status == 0){
				//删除笔记列表中的笔记li
				$li.remove();
				//消息提示
				alert(rst.msg);
			}
		},
		error:function(){
			alert("删除笔记异常");
		}
	});
}
//隐藏笔记菜单
function hideNoteMenu() {
	$("#note_list div").hide();
}
// 弹出笔记菜单
function popNoteMenu() {
	// 隐藏之前的菜单
	$("#note_list div").hide();
	// 显示点击的笔记菜单
	$menu = $(this).parent().next();
	$menu.slideDown(1000);
	// 设置点击笔记选中效果
	$("#note_list a").removeClass("checked");
	$(this).parent().addClass("checked");
	// 阻止事件向上冒泡
	return false;
}
// 创建笔记
function addNote() {
	// 获取请求参数
	var userId = getCookie("uid");
	var bookId = $("#book_list a.checked").parent().data("bookId");
	var title = $("#input_note").val();
	// 检查格式
	var ok = true;
	if (userId == null) {
		ok = false;
		window.location.href = "log_in.html"
	}
	if (title == "") {
		ok = false;
		$("#note_span").html("笔记名为空");
	}
	// 发送ajax请求
	if (ok) {
		$.ajax({
			url : base_path + "/note/add.do",
			type : "post",
			data : {
				"userId" : userId,
				"bookId" : bookId,
				"title" : title
			},
			dataType : "json",
			success : function(rst) {
				if (rst.status == 0) {
					//获取参数
					var noteId = rst.data;
					//创建一个笔记li元素
					createLi(noteId,title);
					//消息提示
					alert("添加笔记成功");
				}
			},
			error : function() {
				alert("添加笔记异常");
			}
		});
	}
}
//“保存笔记”按钮处理
function saveNote() {

	// 获取请求参数
	var title = $("#input_note_title").val().trim();
	var body = um.getContent();
	// 获取选中的笔记li元素
	var $li = $("#note_list a.checked").parent();
	var noteId = $li.data("noteId");
	// 清除之前的提示消息
	$("#note_title_span").html("");
	// 检查格式
	if ($li.length == 0) {
		alert("请选择要保存的笔记");
	} else if (title == "") {
		// 检查标题是否为空
		$("#note_title_span").html("标题不能为空");
	} else {
		$.ajax({
			url : base_path + "/note/update.do",
			type : "post",
			data : {
				"noteId" : noteId,
				"title" : title,
				"body" : body
			},
			dataType : "json",
			success : function(rst) {
				if(rst.status == 0){
					//更新列表li中标题
					var lis ="";
					lis += '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
					lis	+= title;
					lis	+= '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down">';
					lis += '<i class="fa fa-chevron-down"></i></button>';
					$li.find("a").html(lis);	 
				}
			},
			error : function() {
				alert("保存笔记异常");
			}
		});
	}
}
//加载笔记内容
function loadNote() {
	//切换显示
	$("#pc_part_3").show();
	$("#pc_part_5").hide();
	// 设置笔记选中
	$("#note_list a").removeClass("checked");
	$(this).find("a").addClass("checked");
	// 获取参数(笔记ID)
	var noteId = $(this).data("noteId");
	//清空之前的笔记信息
	$("#input_note_title").empty();
	um.setContent("");
	// 发送ajax请求
	$.ajax({
		url : base_path + "/note/load.do",
		type : "post",
		data : {
			"noteId" : noteId
		},
		dataType : "json",
		success : function(rst) {
			if (rst.status == 0) {
				// 获取笔记标题
				var title = rst.data.cn_note_title;
				// 获取笔记内容
				var body = rst.data.cn_note_body;
				// 设置到编辑区
				$("#input_note_title").val(title);
				um.setContent(body);
			}
		},
		error : function() {
			alert("加载笔记异常");
		}
	});
}
//加载笔记列表
function loadBookNotes() {
	//切换列表显示
	$("#pc_part_2").show();
	$("#pc_part_4").hide();
	$("#pc_part_6").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").hide();
	// 清空之前li选中效果
	$("#book_list a").removeClass("checked");
	// 设置笔记本li选中效果
	$(this).find("a").addClass("checked");
	// 获取请求参数
	var bookId = $(this).data("bookId");
	// 发送ajax请求
	$.ajax({
		url : base_path + "/note/loadnotes.do",
		type : "post",
		data : {
			"bookId" : bookId
		},
		dataType : "json",
		success : function(rst) {
			if (rst.status == 0) {
				var notes = rst.data;
				// 清空原有笔记列表
				$("#note_list li").remove();
				for (i = 0; i < notes.length; i++) {
					var note = notes[i];
					// 获取笔记ID和标题
					var noteId = note.cn_note_id;
					var title = note.cn_note_title;
					var typeId = note.cn_note_type_id;
					createLi(noteId, title);
					if(typeId == 2){
						var img = '&nbsp;<i class="fa fa-sitemap"></i>';
						//获取新添加的li元素
						var $li = $("#note_list li:last");
						$li.find(".btn_slide_down").before(img);
					}
				}
			}
		},
		error : function() {
			alert("加载笔记列表异常");
		}
	});
}
// 创建笔记列表li元素
function createLi(noteId,title){
	var li = '<li class="online">'
		+ '<a>'
		+ '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'
		+ title
		+ '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down">'
		+ '<i class="fa fa-chevron-down"></i></button>'
		+ '</a>'
		+ '<div class="note_menu" tabindex="-1">'
		+ '<dl>'
		+ '<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'
		+ '<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'
		+ '<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'
		+ '</dl>' + '</div>' + '</li>';
	var $li = $(li).data("noteId", noteId);
	$("#note_list").append($li);
}