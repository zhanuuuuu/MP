/**
 * Created by xwy_brh on 2017/9/12.
 */
var StringTool = {
    isNull: (str) => {
        if (str.toString().replace(/(^s*)|(s*$)/g, "").length == 0) {
            return true
        }
        return false
    }
};









