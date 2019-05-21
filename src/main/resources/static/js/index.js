$(function () {

    // 鼠标移动至分类主体
    $(".main-body .category-tab>ul li").on("mouseenter",function () {
        $(".category-tab .category-tab-right").css("display", "block");
        $(this).addClass("selected").siblings().removeClass("selected");
    }).on("mouseleave",function () {
        colse();
        $(this).removeClass("selected");
    });


});

// 鼠标移走，隐藏分类右侧
function colse() {
    $(".main-body .category-tab").on("mouseleave", function () {
        $(".category-tab .category-tab-right").css("display", "none");
    });
}