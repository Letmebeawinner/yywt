
/**
 * 鍥剧墖鏂囦欢涓婁紶鏂规硶
 * @param id 鎺т欢ID
 * @param auto 鏄惁鑷姩涓婁紶,true鑷姩锛宖alse闈炶嚜鍔�
 * @param dir 鐩綍
 * @param resServicePath 璧勬簮鏈嶅姟鍏ㄥ煙鍚�
 * @param callback 鍥炶皟鏂规硶
 */
function uploadImage(id,auto,dir,resServicePath,callback){
    jQuery("#"+id).uploadify({
        uploader: resServicePath+'/upload/res/image/uploadImage',
        swf: '/static/uploadify/uploadify.swf',
        formData:{'dir':dir},
        fileObjName:'imageFiles',
        fileSizeLimit:'20480KB',
        fileTypeExts:'*.jpg;*.png;*.gif',
        auto:auto,
        method:'psot',
        multi:false,
        height: 30,
        width: 120,
        buttonText: '涓婁紶鍥剧墖',
        buttonClass: '',
        onSelect:function (file) {//姣忔坊鍔犱竴涓枃浠惰嚦涓婁紶闃熷垪鏃惰Е鍙戣浜嬩欢銆�

        },
        onUploadComplete:function (file) {//姣忎竴涓枃浠朵笂浼犲畬鎴愰兘浼氳Е鍙戣浜嬩欢锛屼笉绠℃槸涓婁紶鎴愬姛杩樻槸涓婁紶澶辫触銆�

        },
        onQueueComplete:function (queueData) {//闃熷垪涓殑鎵€鏈夋枃浠惰澶勭悊瀹屾垚鏃惰Е鍙戣浜嬩欢銆�
            //queueData.uploadsSuccessful
        },
        onUploadSuccess:function(file, data, response){//姣忎竴涓枃浠朵笂浼犳垚鍔熸椂瑙﹀彂璇ヤ簨浠躲€�
            callback(data);
        },
        onUploadError:function(file, errorCode, errorMsg, errorString){
            console.log("鍥剧墖涓婁紶閿欒锛�"+errorString);
            alert("鍥剧墖鏂囦欢涓婁紶澶辫触");
        }
    });
}

/**
 * 鏂囦欢涓婁紶鏂规硶
 * @param id 鎺т欢ID
 * @param auto 鏄惁鑷姩涓婁紶,true鑷姩锛宖alse闈炶嚜鍔�
 * @param dir 鐩綍
 * @param resServicePath 璧勬簮鏈嶅姟鍏ㄥ煙鍚�
 * @param callback 鍥炶皟鏂规硶
 */
function uploadFile(id,auto,dir,resServicePath,callback){
	console.info("0");
    jQuery("#"+id).uploadify({
        uploader: resServicePath+'/upload/res/file/uploadFile',
        swf: '/static/uploadify/uploadify.swf',
        formData:{'dir':dir},
        fileObjName:'files',
        auto:auto,
        method:'psot',
        multi:false,
        height: 30,
        width: 120,
        buttonText: '涓婁紶鏂囦欢',
        buttonClass: '',
        onSelect:function (file) {//姣忔坊鍔犱竴涓枃浠惰嚦涓婁紶闃熷垪鏃惰Е鍙戣浜嬩欢銆�
        	console.info("1");
        },
        onUploadComplete:function (file) {//姣忎竴涓枃浠朵笂浼犲畬鎴愰兘浼氳Е鍙戣浜嬩欢锛屼笉绠℃槸涓婁紶鎴愬姛杩樻槸涓婁紶澶辫触銆�
        	console.info("2");
        },
        onQueueComplete:function (queueData) {//闃熷垪涓殑鎵€鏈夋枃浠惰澶勭悊瀹屾垚鏃惰Е鍙戣浜嬩欢銆�
            //queueData.uploadsSuccessful
        	console.info("3");
        },
        onUploadSuccess:function(file, data, response){//姣忎竴涓枃浠朵笂浼犳垚鍔熸椂瑙﹀彂璇ヤ簨浠躲€�
            callback(data);
            console.info("4");
        },
        onUploadError:function(file, errorCode, errorMsg, errorString){
        	console.info("5");
            console.log("鏂囦欢涓婁紶閿欒锛�"+errorString);
            alert("鏂囦欢涓婁紶澶辫触");
        }
    });
}