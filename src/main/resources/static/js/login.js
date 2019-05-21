$(function () {
    $("#register").validate({
        rules : {
            "username" : {
                "required" : true
            },
            "password" : {
                "required" : true,
                "rangelength" : [ 6, 12 ]
            }
        },
        messages : {
            "username" : {
                "required" : "会员名不能为空"
            },
            "password" : {
                "required" : "密码不能为空",
                "rangelength" : "密码长度为6~12位"
            }
        }
    });
});