var rela = {};

(function(){

    var callData = function(url, reqData, options){

        var beforeSendfnc, successBeforefnc, successDatafnc, errorfnc, async, progress, berrorMsg, filter, orderBy, addParam, pivotParam;

        if($.isPlainObject(options)){
            beforeSendfnc = options.beforeSendfnc;
            successBeforefnc = options.successBeforefnc;
            successDatafnc = options.successDatafnc;
            errorfnc = options.errorfnc;
            async = options.async;
            //progress = options.progress;
            berrorMsg = options.berrorMsg;
            //filter = options.filter;
            //orderBy = options.orderBy;
            addParam = options.addParam;
            //pivotParam = options.pivotParam;
        }

        if(reqData == undefined || reqData == null) reqData = {};
        if(beforeSendfnc == undefined) beforeSendfnc = null;
        if(successBeforefnc == undefined) successBeforefnc = null;
        if(successDatafnc == undefined) successDatafnc = null;
        if(errorfnc == undefined || errorfnc == null) {
            errorfnc = function(retCode, errorMessage) {
                if(retCode == "M"){
                    alert(errorMessage);
                } else {
                    alert("System Error");
                }
            };
        }

        if(async == undefined || async == null) async = true;
        if(progress == undefined || progress == null) progress = true;
        if(berrorMsg == undefined || berrorMsg == null) berrorMsg = true;

        if(filter == undefined ) filter = null;
        if(orderBy == undefined ) orderBy = null;

        if(addParam == undefined || addParam == null) addParam = {};
        if(reqData == undefined || reqData == null) reqData = {};

        var systemErrorFnc = function(x,e){
            alert(e);
        };

        if(!berrorMsg){
            errorfnc = null;
            systemErrorFnc = function(x,e){};
        }

        var data = JSON.stringify({
            "data" : reqData
            ,"addParam" : addParam
        });

        $.ajax({
            type: 'post'
            , async: async
            , url: url
            , data: data
            , contentType: "application/json"
            , dataType: 'json'
            , error: systemErrorFnc
            , beforeSend: function (xhr) {

            }
            , complete: function () {

            }
            , success: function (resData) {
                if(resData == null || resData == undefined){
                    if(errorfnc != null) errorfnc("Server Error!!", "err");
                } else {
                    if(resData.resultCode == "0"){
                        resData.Data = resData.data;
                        resData.Param = resData.param;

                        if(resData.Data != undefined && resData.Data.length != undefined && resData.Data.length > 0){
                            //if(filter != null) resData.Data = filterData(resData.Data, filter);
                            //if(orderBy != null) resData.Data = sortData(resData.Data, orderBy);
                        }

                        //데이터 처리 성공
                        if(successBeforefnc != null) successBeforefnc(resData, resData.Data, resData.Param);
                        if(successDatafnc != null) successDatafnc(resData, resData.Data, resData.Param);
                    } else if(resData.resultCode == "A") {
                        //window.top.location.href=_CONTEXT_PATH;
                    } else {
                        if(errorfnc != null) errorfnc(resData.resultCode, resData.resultMessage, resData);
                    }
                }
            }
        });

    };

    var form2Object = function(element, bSetForm){
        if(bSetForm == undefined || bSetForm == null || bSetForm != false) bSetForm = true;

        //if(bSetForm) unSetForm(element);

        data = new Object();

        element.find(":input").each(function(){
            var el = $(this);
            if(el.is(":radio,:checkbox")){
                if(!el.is(":checked")) return;
            }
            var name = el.attr("name");
            var value = el.val();
            if(name != undefined && name != null && name != ""){
                var temp = data[name];
                if(temp == undefined || temp == null){
                    data[name] = value;
                } else if($.isArray(temp)){
                    temp.push(value);
                } else {
                    temp = new Array();
                    temp.push(data[name]);
                    temp.push(value);
                    data[name] = temp;
                }

            }
        });

        //if(bSetForm) setForm(element);


        var arr = [];
        var narr = [];
        for(var k in data){
            if($.isArray(data[k])){
                arr.push(k);
            } else {
                narr.push(k);
            }
        }

        if(arr.length > 0){
            var tD = [];
            var idx = data[arr[0]].length;
            for(var i = 0 ; i < idx; i++){
                var t = {};
                $.each(arr, function(){
                    t[this] = data[this][i];
                });

//	        $.each(narr, function(){
//		          t[this] = data[this];
//		    });
                tD.push(t);
            }
            data = tD;
        }


        return data;
    };

    rela.callData = callData;
    rela.form2Object = form2Object;

})();