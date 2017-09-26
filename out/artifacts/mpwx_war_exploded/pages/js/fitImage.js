/**
 * Created by xwy_brh on 2017/8/24.
 */
/**
 * Created by xwy_brh on 2017/8/1.
 */
var _w = parseInt($(window).width()); //获取浏览器的宽度
console.log(_w);
$(".new_mess_c").each(function(i) {
    console.log(i);
    var img = $(this);
    var realWidth; //真实的宽度
    var realHeight; //真实的高度
    $("<img/>").attr("src", $(img).attr("src")).load(function() {

        realWidth = this.width;
        realHeight = this.height;

        if (realWidth >= _w) {
            $(img).css("width", "100%").css("height", "auto");
        } else {
            $(img).css("width", realWidth + 'px').css("height", realHeight + 'px');
        }
    });
});