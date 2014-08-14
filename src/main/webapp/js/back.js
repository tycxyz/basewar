function backlogin(){
	$.post(
		getAction("login")
		, {name:$("#name").val(), password:$("#password").val()}
		, function(data) {
			if (data.result > 0) {
				var address = window.location.href;
				address = address.substring(0, address.lastIndexOf("/admin")) + "/admin/home";
				window.location.href = address;
			} else {
				$("#err").text(data.msg);
			}
		}
		, "json"
	);
}

function logout(){
	$.post(getAction("logout")
			, {}
			, function (data) {
				if (data.result > 0) {
					var address = window.location.href;
					address = address.substring(0, address.lastIndexOf("/admin")) + "/admin";
					window.location.href = address;
				} else {
					alert(data.msg);
				}
			} 
			, "json"
	);
}

function musiccheck(){
	var err = "";
	
	if(!($("input[name=type]").is(":checked"))){
		err = "请选择【类别】！\n";	
	}
	if(!($("input[name=language]").is(":checked"))){
		err += "请选择【语言】！\n";	
	}
	if ($("input[name='name']").val() == "") {
		err += "请填写【音乐别名】！\n";		
	}
	if ($("select[name='restype']").val() == "") {
		err += "请选择【费用】！\n";		
	}
	
	if (err != "") {
		alert(err);
		return false;
	}
	return true;
}
//新增音乐
function musicadd(){
	if (!musiccheck()) {
		return;
	}
	
	var languages = "";	
	$("input[name=language]:checked").each(function(){
	    if(languages == '')
	    	languages+=$(this).val();
	    else
	    	languages+=","+$(this).val();
	});
	

	var types = "";
	$("input[name=type]:checked").each(function(){
	    if(types == "")
	    	types+=$(this).val();
	    else
	    	types+=","+$(this).val();
	});
	
	$.post(
		  getAction("musicadd")
		, {types:types, mname:$("input[name='name']").val(), 
			  langs:languages, restype:$("select[name='restype']").val()}
		, function(data) {
			alert(data.msg);
			
			if (data.result > 0) {
				var address = window.location.href;
				address = address.substring(0, address.lastIndexOf("/")) + '/musicedit/' + data.mid;
				window.location.href=address;
			}
		  }
		, "json"
	);
}
//修改音乐
function musicedit(mid){
	if (!musiccheck()) {
		return;
	}
	
	var languages = "";	
	$("input[name=language]:checked").each(function(){
	    if(languages == '')
	    	languages+=$(this).val();
	    else
	    	languages+=","+$(this).val();
	});
	

	var types = "";
	$("input[name=type]:checked").each(function(){
	    if(types == "")
	    	types+=$(this).val();
	    else
	    	types+=","+$(this).val();
	});
	
	$.post(
		  getAction("musicedit")
		, {types:types, mname:$("input[name='name']").val(), deleted:$("select[name='deleted']").val(), mid:mid, langs:languages
			  , restype:$("select[name='restype']").val(), performer:$("input[name='performer']").val(),
			  author:$("input[name='author']").val(),  recsupport:$("select[name='recsupport']").val()}
		, function(data) {alert(data.msg);}
		, "json"
	);
}

function update_music_file_size() {
	$.post( getAction("update_music_file_size")
		, {}
		, function(data) {
			layer.alert(data.msg, -1, !1);
		}
		, "json"
	);
}