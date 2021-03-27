$(function(){
	$("#publishBtn").click(publish);
});

function publish(){
	$("#publishModal").modal("hide");
	//获取标题和内容
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	//发送异步请求
	$.post(
		"/mycommunity/discuss/add",
		{"title":title,"content":content},
		function (data){
			console.log(data.msg);
			data = $.parseJSON(data);
			$("#hintBody").text(data.msg);
			//显示提示框
			$("#hintModal").modal("show");
			//两秒后自动隐藏
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//刷新页面
				if(data.code == 0)
					window.location.reload();
			}, 2000);
		}
	);

	// $("#hintModal").modal("show");
	// setTimeout(function(){
	// 	$("#hintModal").modal("hide");
	// 	//刷新页面
	// 	// if(data.code == 0)
	// 	// 	window.location.reload();
	// }, 2000);

}