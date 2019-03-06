/**
 * 跨域请求JS
 * @type {undefined}
 */

var resultData = undefined;
/* 默认调用方法 */
function doResponse(data){
    resultData = data;
}
/**
 * 跨域请求
 * @param {*} url 
 * @param {*} param 
 * @param {*} fun 
 */
function getSpanReqeust( url , param , fun ){
    var script = document.createElement('script');
    script.id = 't_spanDomain_script';
    script.type = 'text/javascript';
    script.src = 'http://gengmingyan.cccyun.cf/jsonp_api/'+url;
    //处理参数
    script.src += paramSerialize(param);
    /*if( fun_name != undefined ){

        if( paramNum == 0 ){
            script.src += "?fun_name="+fun_name;
        }else{
            script.src += '&fun_name='+fun_name;
        }
    }*/
    document.head.append(script);


    var num = 0;
    var t_time =  setInterval(function(){
        if( num != undefined ){
            fun(resultData);
            clearInterval(t_time);
            //删除script
            document.head.removeChild(script);
        }
        if( num == 5 && resultData == undefined ){
            console.log('接受失败！');
            document.head.removeChild(script);
        }
        num++;
    },1000);
}

//处理参数方法
function paramSerialize(param){
    if( param == undefined || param == null ){
        return "";
    }
    //处理参数
    var paramNum = 0;
    var param_str = '?';
    for( var p in param ){
        param_str = ( paramNum == 0 ? p +'='+param[p] : '&' + p + '=' + param[p]  );
        paramNum++;
    }
    if( paramNum == 0 ){
        return "";
    }
    return param_str;

}









