/**book.js 封装笔记本相关处理 */
//删除笔记本
function deleteBook(){
	var $li = $("#book_list a.checked").parent();
	var bookId = $li.data("bookId");
	$.ajax({
		url:base_path+"/book/delete.do",
		type:"post",
		data:{"bookId":bookId},
		dataType:"json",
		success:function(rst){
			if(rst.status == 0){
				$li.remove();
				alert(rst.msg);
			}
		},
		error:function(){
			alert("删除笔记本异常");
		}
	});
}
//重名名笔记本
function renameBook() {
	// 获取参数
	var $li = $("#book_list a.checked").parent();
	var bookId = $li.data("bookId");
	var bookName = $("#input_notebook_rename").val();
	// 格式检查
	var ok = true;
	if (bookName == "") {
		ok = false;
		$("#notebook_rename_span").html("笔记本为空");
	}
	// 发送ajax请求
	if (ok) {
		$.ajax({
			url : base_path + "/book/rename.do",
			type : "post",
			data : {
				"bookId" : bookId,
				"bookName" : bookName
			},
			dataType : "json",
			success : function(rst) {
				if (rst.status == 0) {
					var li = '<li class="online"><a>'
							+ '<i class="fa fa-book" title="online" rel="tooltip-bottom">'
							+ '</i>' + bookName + '</a>' + '</li>';
					var $li = $(li).data("bookId", bookId);
					$("#book_list a.checked").parent().html($li);
					closeAlertWindow();
				}
			},
			error : function() {
				alert("重命名异常");
			}
		});
	}
}

// 添加笔记本
function addBook() {
	// 获取请求参数
	var userId = getCookie("uid");
	var bookName = $("#input_notebook").val();
	var ok = true;
	if(bookName == ""){
		ok = false;
		$("#notebook_span").html("笔记本名为空");	
	}
	// 检查格式
	if (userId == null) {
		ok = false;
		window.location.href = "log_in.html";
	}
	// 发送ajax请求
	if(ok){
		$.ajax({
			url : base_path + "/book/add.do",
			type : "post",
			data : {
				"userId" : userId,
				"bookName" : bookName
			},
			dataType : "json",
			success : function(rst) {
				if (rst.status == 0) {
					var bookId = rst.data.cn_notebook_id;
					var li = '<li class="online"><a>'
							+ '<i class="fa fa-book" title="online" rel="tooltip-bottom">'
							+ '</i>' + bookName + '</a>' + '</li>';
					var $li = $(li).data("bookId",bookId);
					$("#book_list").append($li);
					closeAlertWindow();
				}
			},
			error : function() {
				alert("添加笔记本异常");
			}
		});
	}
}
// 加载笔记本列表
function loadUserBooks() {
	// 获取请求参数
	var userId = getCookie("uid");
	// 检查格式
	if (userId == null) {
		window.location.href = "log_in.html";
	}
	//显示用户名
	$(".profile-username").html(getCookie("uname"));
	// 发送ajax请求
	$.ajax({
		url : base_path + "/book/loadbooks.do",
		type : "post",
		data : {
			"userId" : userId
		},
		dataType : "json",
		success : function(rst) {
			if (rst.status == 0) {
				// 获取返回的笔记本集合
				var books = rst.data;
				for (i = 0; i < books.length; i++) {
					// 获取笔记本ID
					var bookId = books[i].cn_notebook_id;
					// 获取笔记本名称
					var bookName = books[i].cn_notebook_name;
					var lis = "";
					lis += '<li class="online"><a>'
							+ '<i class="fa fa-book" title="online" rel="tooltip-bottom">'+ '</i>' 
							+ bookName 
							+ '<button type="button" class="btn btn-default btn_position btn_delete" title="删除"><i class="fa fa-times"></i></button>'
							+'</a></li>';
					// 将bookId绑定到li元素上
					var $li = $(lis);
					$li.data("bookId", bookId);
					// 将li元素添加到ul中
					$("#book_list").append($li);
				}
			}
		},
		error : function() {
			alert("笔记本列表加载失败!!");
		}
	});
}