function frm_back_login_check(){
	return true;
}

function getAction(m){
	return ctxtPath + "/action/back/" + m;
}

$(document).ready(function(){
	$("#password").bind("keyup", function(event){
	   if (event.keyCode=="13"){
	       $("#commit").click();
	   }
	});
});

$(document).ready(function() {
	$('.popbox').popbox();
	
	$(".up").each(function() {
		var storeinoss = false;
		var forcenewfile = false;
		
		if ($(this).attr("inoss") != undefined)
			storeinoss = $(this).attr("inoss");
		
		if ($(this).attr("forcenewfile") != undefined)
			forcenewfile = $(this).attr("forcenewfile");
				
		$(this).uploadify({
			'swf' : ctxtPath + '/js/uploadify.swf',
			'buttonImage' : ctxtPath +'/images/browse-btn.png',
			'uploader' : getAction("upload"),
			'fileTypeExts' : $(this).attr("exts"),
			'fileTypeDesc' : $(this).attr("exts"),
			'multi' : false,
			'formData' : {
				'tar' : $(this).attr("tar"),
				'id' : $(this).attr("rowid"),
				'storeinoss' : storeinoss,
				'forcenewfile' : forcenewfile
			},
			'onFallback' : function() {
				alert("您未安装FLASH控件，无法上传！请安装FLASH控件后再试。");
			},
			'onUploadSuccess' : function(file, data, response) {
				var r = eval("(" + data + ")");
				//alert(r.message);
				window.location.reload();
			}
		});
	});
})


function delete_kv_datadict(ddid) {
	if (confirm("确认删除吗？")) {
		$.post(getAction("datadict_kv_delete"), {ddid:ddid}, function(data) {
			alert (data.msg);
			
			if (data.result > 0)
				window.location.reload();
			
		}, "json");
	}
}
function savedatadict_kv(ddid){
	var err = "";
	
	if ($("#name").val() == "") {
		err += "名称不能为空\n";
	}
	if ($("#itemkey").val() == "") {
		err += "键不能为空\n";
	}
	if ($("#itemvalue").val() == "") {
		err += "值不能为空\n";
	}
	
	if ($("#used").val() == "") {
		err += "有效值不能为空\n";
	}
	
	if (err != "") {
		alert (err);
		return;
	}
	
	$.post(getAction("datadict_kv_edit")
			, {ddid:ddid, name:$("#name").val(), itemvalue:$("#itemvalue").val(), itemkey:$("#itemkey").val(), used:$("#used").val()}
			, function (data) {
				alert(data.result + data.msg);
			}, "json"
	);
}
function add_datadict_kv() {
	var name = $("#new_itemname").val();
	var itemkey = $("#new_itemkey").val();
	var itemvalue = $("#new_itemvalue").val();
	var dept = $("#new_itemdept").val();
	var error = "";
	
	if (name == "")
		error += "别名不能为空！\n";
	if (itemkey == "")
		error += "数据名不能为空\n";
	if (itemvalue == "")
		error += "数据值不能为空\n";
	
	if (error != "") {	
		alert(error);
	} else {
		$.post(
			getAction("datadict_kv_add")
			, {itemname:name, itemkey:itemkey, itemvalue:itemvalue, dept:dept}
			, function (data) {
				alert(data.msg);
				
				if(data.result > 0)
					window.location.reload();
			}
		, "json");
	}
}

function add_datadict(pddid){
	
	var name = $("#new_itemname").val();
	var itemkey = $("#new_itemkey").val();
	var itemvalue = $("#new_itemvalue").val();
	var dept = $("#new_itemdept").val();
	var ordinal = $("#new_itemordinal").val();
	var error = "";
	
	if (name == "")
		error += "别名不能为空！\n";
	if (itemkey == "")
		error += "数据名不能为空\n";
	if (itemvalue == "")
		error += "数据值不能为空\n";
	if (ordinal == "")
		error += "排序不能为空\n";
	
	if (error != "") {	
		alert(error);
	} else {
		$.post(
			getAction("datadict_tree_add")
			, {pddid:pddid, name:name, itemkey:itemkey, itemvalue:itemvalue, dept:dept, ordinal:ordinal}
			, function (data) {
				alert(data.msg);
				
				if(data.result > 0) {
					window.location.reload();
				}
			}
		, "json");
	}
}

function saveDatadict(ddid) {
	var name = $("#name").val();
	var itemkey = $("#itemkey").val();
	var itemvalue = $("#itemvalue").val();
	var dept = $("#dept").val();
	var ordinal = $("#ordinal").val();
	var error = "";
	
	if (name == "")
		error += "别名不能为空！\n";
	if (itemkey == "")
		error += "数据名itemkey不能为空\n";
	if (itemvalue == "")
		error += "数据值不能为空\n";
	if (ordinal == "")
		error += "排序不能为空\n";
	
	if (error != "") {	
		alert(error);
	} else {
		$.post(
			getAction("datadict_tree_edit")
			, {ddid:ddid, name:name, itemkey:itemkey, itemvalue:itemvalue, dept:dept, ordinal:ordinal, deleted:$("select[name='deleted']").val()}
			, function (data) {
				alert(data.msg);
				//window.location.reload();
			}
		, "json");
	}
}

function delete_datadict(ddid){
	if(confirm("确认删除吗？删除将无法恢复，且所有子项数据也将同时被删除！")) {
		$.post(
			getAction("datadict_tree_delete")
			, {ddid:ddid}
			, function (data) {
				alert(data.msg);
				
				if(data.result > 0)
					window.location.reload();
			}
			, "json");
	}
}

function layerAlert(msg, bdiv) {
	if (bdiv) {
		msg = getScrollText(msg);
	}
	layer.alert(msg, -1, !1);
}
function getScrollText(msg) {
	return "<div style='height:400px;overflow-y:auto; overflow-x:hidden;text-align: left;border: solid 1px rgb(211, 211, 211);'>" + msg + "</div>";
}