/**
 * Created by xwy_brh on 2017/8/21.
 */

var func_app = new Vue({
    el: '#func_wrapper_app',
    data: {
        func_list:[//{"icon":"../images/modifyPhone@3x.png","title":"更换电话"},
            {"icon":"../images/sign_date@3x.png","title":"签到"}]
            //{"icon":"../images/shopCheck@3x.png","title":"消费查询"},
            //{"icon":"../images/giftChange@3x.png","title":"积分换礼"},
            //{"icon":"../images/littleGames@3x.png","title":"小游戏"}]
    },
    methods: {
        didSelect:function(index, openid) {
            console.log(index, openid);
            if(index == 0) {
                window.location.href = "sign.jsp?openid=" + openid;
            } else {
                window.location.href = "404.jsp";
            }
        }
    }
});

