$(function () {
    $(".show-product").validate({
        rules : {
            "name" : {
                "required" : true
            },
            "phone" : {
                "required" : true,
            },
            "address": {
                "required" : true,
            }
        },
        messages : {
            "name" : {
                "required" : "收货人不能为空"
            },
            "phone" : {
                "required" : "联系电话不能为空",
            },
            "address" : {
                "required" : "收货地址不能为空",
            }
        }
    });
});