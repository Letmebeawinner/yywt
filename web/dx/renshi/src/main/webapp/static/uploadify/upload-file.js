
/**
 * 图片文件上传方法
 * @param id 控件ID
 * @param auto 是否自动上传,true自动，false非自动
 * @param dir 目录
 * @param resServicePath 资源服务全域名
 * @param callback 回调方法
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
        buttonText: '上传图片',
        buttonClass: '',
        onSelect:function (file) {//每添加一个文件至上传队列时触发该事件。
            alert(2)
        },
        onUploadComplete:function (file) {//每一个文件上传完成都会触发该事件，不管是上传成功还是上传失败。
            alert(1)
        },
        onQueueComplete:function (queueData) {//队列中的所有文件被处理完成时触发该事件。
            //queueData.uploadsSuccessful
        },
        onUploadSuccess:function(file, data, response){//每一个文件上传成功时触发该事件。
            callback(data);
        },
        onUploadError:function(file, errorCode, errorMsg, errorString){
            console.log("图片上传错误："+errorString);
            alert("图片文件上传失败");
        }
    });
}

/**
 * 文件上传方法
 * @param id 控件ID
 * @param auto 是否自动上传,true自动，false非自动
 * @param dir 目录
 * @param resServicePath 资源服务全域名
 * @param callback 回调方法
 */
function uploadFile(id,auto,dir,resServicePath,callback){
        jQuery("#"+id).uploadify({
        uploader: resServicePath+'/upload/res/file/uploadFile',
        swf:'/static/uploadify/uploadify.swf',
        formData:{'dir':dir},
        fileObjName:'files',
        auto:true,
        method:'psot',
        multi:false,
        height: 30,
        width: 120,
        buttonText: '上传文件',
        buttonClass: '',
        onSelect:function (file) {//每添加一个文件至上传队列时触发该事件。

        },
        onUploadComplete:function (file) {//每一个文件上传完成都会触发该事件，不管是上传成功还是上传失败。

        },
        onQueueComplete:function (queueData) {//队列中的所有文件被处理完成时触发该事件。
            //queueData.uploadsSuccessful
        },
        onUploadSuccess:function(file, data, response){//每一个文件上传成功时触发该事件。
            callback(data);
        },
        onUploadError:function(file, errorCode, errorMsg, errorString){
            console.log("文件上传错误："+errorString);
            alert("文件上传失败");
        }
    });
}
