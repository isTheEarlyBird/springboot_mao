$(function () {
    showBigImg();
    selectImg();
    changeNum();
});

// 显示大图
function showBigImg() {
    var $img = $(".smallImg img");
    var $span = $(".smallImg span");
    var $bigBox = $(".bigImg");
    var $bigImg = $bigBox.find("img");
    $(".smallImg").mouseenter(function () {
        $span.css("display", "block");
        $bigBox.css("display", "block");
    }).mouseleave(function () {
        $span.css("display", "none");
        $bigBox.css("display", "none");
    }).mousemove(function (e) {
        var x = e.pageX - $img.offset().left - parseInt($span.width()) / 2;
        var y = e.pageY - $img.offset().top - parseInt($span.width()) / 2;
        x = x <= 0 ? 0 : x;
        y = y <= 0 ? 0 : y;
        // 遮罩层横纵最大移动距离
        var smallMax = $img.width() - $span.width();
        x = x >= smallMax ? smallMax : x;
        y = y >= smallMax ? smallMax : y;
        $span.css({
            left: x,
            top: y
        });
        // 大图横纵最大移动距离
        var bigMax = $bigImg.width() - $bigBox.width();
        // 遮罩层的X/大图移动X = 遮罩层最大移动距离/大图box最大移动距离
        var bigX = -x * bigMax / smallMax + "px";
        var bigY = -y * bigMax / smallMax + "px";
        $bigImg.css({
            marginLeft: bigX,
            marginTop: bigY
        })
    });
}

// 选择图片
function selectImg() {
    var src;
    $(".select li ").mouseenter(function () {
        $(this).addClass("now").siblings().removeClass("now");
        // 获取当前选择的图片地址
        src = $(this).find("img").attr("src");
        $(".smallImg img").attr("src", src);
        $(".bigImg img").attr("src", src);
    });
}

// 数量的增减
function changeNum() {
    //获取最大数量
    var max = $(".num input").attr("max");
    var val;
    $(".glyphicon-chevron-up").on("click", function () {
        val = parseInt($(".num input").val()) + 1;
        if (val <= max){
            $(".num input").val(val);
        }
    });
    $(".glyphicon-chevron-down").on("click", function () {
        val = parseInt($(".num input").val()) - 1;
        if (val >= 1) {
            $(".num input").val(val);
        }

    });
}