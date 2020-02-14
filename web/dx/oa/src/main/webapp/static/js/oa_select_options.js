var urls = {
    NEWS: {
        QUERY_TYPE_LIST: contextPath + "/admin/oa/ajax/queryAllNewsType.json",
        QUERY_TYPE_NAME: contextPath + "/admin/oa/ajax/queryNewsType.json",
        MESSAGE: "请选择新闻类别",
        NO_SELECT_MESSAGE: "未选择新闻类别"
    },
    MEETINGS: {
        QUERY_LIST: contextPath + "/admin/oa/ajax/queryAllMeeting.json",
        QUERY_NAME: contextPath + "/admin/oa/ajax/get/meeting.json",
        MESSAGE: "请选择会议室名称",
        NO_SELECT_MESSAGE: "未选择会议室"
    },
    ARCHIVE: {
        QUERY_TYPE_LIST: contextPath + "/admin/oa/ajax/queryArchiveTypeList.json",
        QUERY_TYPE_NAME: contextPath + "/admin/oa/ajax/archive/getName.json",
        MESSAGE: "请选择档案类型",
        NO_SELECT_MESSAGE: "未选择档案类型"
    },
    CAR: {
        QUERY_CAR_LIST: contextPath + "/admin/oa/ajax/get/all/car.json",
        QUERY_NAME: contextPath + "/admin/oa/ajax/car/getCar.json",
        MESSAGE: "请选择车牌号",
        NO_SELECT_MESSAGE: "未选择车牌号"
    },
    TOPIC: {
        QUERY_LIST: contextPath + "/admin/oa/ajax/get/all/topic.json",
        QUERY_NAME: contextPath + "/admin/oa/ajax/topic/getTopic.json",
        MESSAGE: "请选择议题",
        NO_SELECT_MESSAGE: "未选择议题"
    },
    DRIVER: {
        QUERY_LIST: contextPath + "/admin/oa/ajax/get/all/driver.json",
        QUERY_NAME: contextPath + "/admin/oa/ajax/driver/getName.json",
        MESSAGE: "请选择驾驶员",
        NO_SELECT_MESSAGE: "未选择驾驶员"
    }
}

/**
 * 需要赋值页面的id
 * @param id
 * @param type 查询什么类型的列表信息
 * 0 新闻类型
 * 1 会议
 * 2 档案
 * 3 车辆
 * 4 议题
 * 5 驾驶员
 */
function queryListInfo(id, type) {
    var url = "";
    var message;
    switch (type) {
        case 0:
            url = urls.NEWS.QUERY_TYPE_LIST;
            message = urls.NEWS.MESSAGE;
            break;
        case 1:
            url = urls.MEETINGS.QUERY_LIST;
            message = urls.MEETINGS.MESSAGE;
            break;
        case 2:
            url = urls.ARCHIVE.QUERY_TYPE_LIST;
            message = urls.ARCHIVE.MESSAGE;
            break;
        case 3:
            url = urls.CAR.QUERY_CAR_LIST;
            message = urls.CAR.MESSAGE;
            break;
        case 4:
            url = urls.TOPIC.QUERY_LIST;
            message = urls.TOPIC.MESSAGE;
            break;
        case 5:
            url = urls.DRIVER.QUERY_LIST;
            message = urls.DRIVER.MESSAGE;
            break;
        default:
            break;
    }
    showSelectOption(url, id, message, type);
}

/**
 *
 * @param id 数据id
 * @param selectId 需要赋值的元素id
 * @param type 类型
 * 0 新闻，
 * 1 会议
 * 2 档案
 * 3 车辆
 * 4 议题
 * 5 驾驶员
 */
function queryDateNameById(id, selectId, type) {
    var url = "";
    var message;
    switch (type) {
        case 0:
            url = urls.NEWS.QUERY_TYPE_NAME;
            message = urls.NEWS.NO_SELECT_MESSAGE;
            break;
        case 1:
            url = urls.MEETINGS.QUERY_NAME;
            message = urls.MEETINGS.NO_SELECT_MESSAGE;
            break;
        case 2:
            url = urls.ARCHIVE.QUERY_TYPE_NAME;
            message = urls.ARCHIVE.NO_SELECT_MESSAGE;
            break;
        case 3:
            url = urls.CAR.QUERY_NAME;
            message = urls.CAR.NO_SELECT_MESSAGE;
            break;
        case 4:
            url = urls.TOPIC.QUERY_NAME;
            message = urls.TOPIC.NO_SELECT_MESSAGE;
            break;
        case 5:
            url = urls.DRIVER.QUERY_NAME;
            message = urls.DRIVER.NO_SELECT_MESSAGE;
        default:
            break;
    }
    showNameById(url, id, selectId, message, type);
}

/**
 * 根据url id给下拉框追加下拉属性
 * @param url
 * @param id
 * @param message
 */
function showSelectOption(url, id, message, type) {
    jQuery.ajax({
        url: url,
        data: null,
        type: "post",
        dataType: "json",
        async: false,
        cache : false,
        success: function (result) {
            if (result.code == "0") {
                var str = "<option value = ''>" + message + "</option>";
                var hiddenInput = "";
                jQuery.each(result.data, function(index, value) {
                    if (type == 3) {
                        str += "<option value = "+ value.carID + ">" + value.carID + "</option>";
                    }
                    else if (type == 5){
                        str += "<option value = "+ value.id + " lang = " + value.mobile + ">" + value.userName + "</option>";
                        hiddenInput += "<input type = 'hidden' value = " + value.mobile  + " id = " + value.id + ">";
                    } else {
                        str += "<option value = "+ value.id +  ">" + value.name + "</option>";
                    }

                });
                jQuery("#" + id).html(str);
                jQuery("#" + id).append(hiddenInput);
            } else {
                alert(result.message);
            }
        }
    });
}

/**
 * 通过id获取对应的名字
 * @param url
 * @param id
 * @param message
 */
function showNameById(url, id, selectedId, message, type) {
    jQuery.ajax({
        url: url,
        data: {
            "id": id
        },
        type: "post",
        dataType: "json",
        async: false,
        cache : false,
        success: function (result) {
            if (result.code == "0") {
                if (!result.data) {
                    var str = "<option value = ''"+  + ">" + message + "</option>";
                } else {
                    if (type == 3) {
                        var str = "<option value = "+ result.data.id + ">" + result.data.carID + "</option>";
                    } else if(type == 5) {
                        var str = "<option value = "+ result.data.id + ">" + result.data.userName + "</option>";
                    } else {
                        var str = "<option value = "+ result.data.id + ">" + result.data.name + "</option>";
                    }

                }

                jQuery("#" + selectedId).html(str);
            } else {
                alert(result.message);
            }
        }
    });
}


