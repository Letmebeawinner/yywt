<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>处理任务</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">任务处理</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">任务处理</span><br>
            1.本页面用于任务处理；<br>
            2.保存：点击<span style="color:red">保存</span>，办理该申请；<br>

    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div class="pr">
            <form class="stdform stdform2" method="post" id="completeTask">
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="completeTask();return false;">添 加</button>
                <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
            </p>
        </div>
    </div>
</div>
<script type="text/javascript">
    var taskId = '${task.id}';
    function completeTask() {
        var obj = jQuery("input");
        var flag = 0;
        jQuery.each(obj, function() {
            if (jQuery(this).attr("lang") == 'required' && !jQuery(this).val()) {
                flag = 1;
                var str = jQuery(this).parent("span").siblings("label").html();
                var len = str.indexOf("<");
                if(len != -1) {
                    str = str.substring(0, len);
                }
                alert(str + "不能为空");
                return;
            }
        });
        if (flag == 1) {
            return;
        }

        var data = jQuery('#completeTask').serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/task/complete.json",
            type: "post",
            dataType: "json",
            cache: false,
            data: data,
            success: function(result) {
                if (result.code=="0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/task/to/complete/list.json";
                } else {
                    alert(result.message);
                    return;
                }
            }
        });
    }
    jQuery(function(){
        jQuery.ajax({
            url: "${ctx}/admin/oa/task/form.json",
            data: {
                "taskId": taskId
            },
            type: "post",
            cache: false,
            dataType: "json",
            success: function(data) {
                var result = "<input type = 'hidden' name = 'taskId' value = '" + taskId + "'/>";
                if (data.message == 'formKey') {
                    jQuery('#completeTask').html(result + data.data);
                } else {
                    jQuery.each(data.data.taskFormData.formProperties, function() {
                        var requiredStyle = this.required == true ?"<em style='color: red;'>*</em>" : "";
                        var writable = this.writable == true ? "" : "readonly = 'true'";
                        result += createFieldHtml(data.data, this, requiredStyle, writable);
                        if(this.required == true) {
                        }
                    });
                    jQuery('#completeTask').html(result);
                }
                bindDate();
            }
        });
    });

    /**
     * form对应的string/date/long/enum/boolean类型表单组件生成器
     * fp_的意思是form paremeter
     */
    var formFieldCreator = {
        long: function(formData, prop, requiredStyle, writable) {
            if (prop.value == null || prop.value == 'null') {
                prop.value = '';
            }
            var str = requiredStyle == ""? requiredStyle: " lang = 'required'";
            var readOnlyAttr = "";
            var result = "";
            if (writable != "") {
                readOnlyAttr = writable;
                result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>" +  "<input type='text' id='" + prop.id + "' name='tp_" + prop.id + "' value = '" + prop.value + "' " + readOnlyAttr + " " + str + "/></span></p>";
            } else {
                result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>" +  "<input type='text' id='" + prop.id + "' name='fp_" + prop.id + "' value = '" + prop.value + "' " + readOnlyAttr + " " + str + "/></span></p>";
            }

            return result;
        },
        string: function(formData, prop, requiredStyle, writable) {
            if (prop.value == null || prop.value == 'null') {
                prop.value = '';
            }
            var str = requiredStyle == ""? requiredStyle: " lang = 'required'";
            var readOnlyAttr = "";
            var result = "";
            if (writable != "") {
                readOnlyAttr = writable;
                result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>" +  "<input type='text' id='" + prop.id + "' name='tp_" + prop.id + "' value = '" + prop.value + "' " + readOnlyAttr + " " + str + "/></span></p>";
            } else {
                result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>" +  "<input type='text' id='" + prop.id + "' name='fp_" + prop.id + "' value = '" + prop.value + "' " + readOnlyAttr + " " + str +  "/></span></p>";
            }

            return result;
        },
        date: function(formData, prop, requiredStyle, writable) {
            if (prop.value == null || prop.value == 'null') {
                prop.value = '';
            }
            var str = requiredStyle == ""? requiredStyle: " lang = 'required'";
            var readOnlyAttr = "";
            var dateClass = "";
            if (writable != "") {
                readOnlyAttr = writable;
                dateClass = "";
                var result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>" +  "<input type='text' id='" + prop.id + "' name='tp_" + prop.id + "' value = '" + prop.value + "' readonly = 'true' class = " + dateClass + " " + str + "/></span></p>";
            } else {
                dateClass = "'layDate'"
                var result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>" +  "<input type='text' id='" + prop.id + "' name='fp_" + prop.id + "' value = '" + prop.value + "' readonly = 'true' class = " + dateClass + " " + str + "/></span></p>";
            }

            return result;
        },
        'enum': function(formData, prop, requiredStyle) {
            var result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>";
            if(prop.writable == true) {
                result += "<select id='" + prop.id + "' name='fp_" + prop.id + "' readonly='true'>";
                jQuery.each(formData[prop.id], function(k, v) {
                    console.log("k = " + k + "v = " + v);
                    result += "<option value = '" + k + "'>" + v + "</option>";
                });
                result += "</select></span></p>";
            } else {
            }
            return result;
        },
        'users': function(formData, prop, requiredStyle) {
            return result;
        }
    };

    /**
     * 生成一个field的html代码
     */
    function createFieldHtml(formData, prop, requiredStyle, writable) {
        return formFieldCreator[prop.type.name](formData, prop, requiredStyle, writable);
    }

    function bindDate() {
        laydate.skin('molv');
        jQuery(".laydate").bind('click', function() {
            laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
        });
    }

</script>
</body>
</html>