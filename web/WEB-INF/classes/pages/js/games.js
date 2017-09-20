/**
 * Created by xwy_brh on 2017/8/21.
 */

var games_app = new Vue({
    el: "#games_app",
    data: {
        games:["大转盘", "刮刮乐"]
    },
    methods: {
        didSelectGameAt: function (index) {
            console.log(this.games[index]);
            switch (index){
                case 0:
                    window.location.href = "rotate.jsp";
                    break;
                case 1:
                    window.location.href = "scratch.jsp";
                    break;
                default:
                    window.location.href = "NotFound.jsp";
            }
        }
    }
});