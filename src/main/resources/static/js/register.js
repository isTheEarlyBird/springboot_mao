$(function() {

	// 校验
	$("#register").validate({
		rules : {
			"name" : {
				"required" : true,
				"isExist" : true
			},
			"password" : {
				"required" : true,
				"rangelength" : [ 6, 12 ]
			},
			"rePassword" : {
				"required" : true,
				"rangelength" : [ 6, 12 ],
				"equalTo" : "#password"
			}
		},
		messages : {
			"name" : {
				"required" : "会员名不能为空",
				"isExist": "会员名已存在"
			},
			"password" : {
				"required" : "密码不能为空",
				"rangelength" : "密码长度为6~12位"
			},
			"rePassword" : {
				"required" : "密码不能为空",
				"rangelength" : "密码长度为6~12位",
				"equalTo" : "两次密码不一致"
			}
		}
	});


});
