$(function () {
    // 显示手机版二维码
    $("header .container #phone").on("mouseenter",function () {
        $("header .container .phone-img").css("display","");
    }).on("mouseleave",function () {
        $("header .container .phone-img").css("display","none");
    });


});
