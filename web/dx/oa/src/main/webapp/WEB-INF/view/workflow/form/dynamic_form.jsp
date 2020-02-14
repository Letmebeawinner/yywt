<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${processDefinition.name}</title>
</head>
<body>
<div class="centercontent">
    <div class="pageheader">
        <h1 class="pagetitle">${processDefinition.name}</h1>
    </div><!--pageheader-->
    <div style="margin-left: 20px;">
        <span style="color:red">${processDefinition.name}</span><br>
        1.本页面用于${processDefinition.name}提交；<br>
        2.<em style="color: red;">*</em>代表必填项；<br>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" action="${ctx}/admin/oa/process/start.json" id="startProcess">
            </form>
            <p class="stdformbutton" style="text-align: center">
                <button class="submit radius2" onclick="addFormSubmit();return false;">添 加</button>
                <input type="reset" class="reset radius2" value="重 置" onclick="resetData()"/>
            </p>
            <br/>
        </div><!--subcontent-->
    </div>
</div>
<script type="text/javascript">
     var processDefinitionId = '${processDefinition.id}';
    // 读取启动时的表单
     jQuery(function(){
         jQuery.ajax({
             url: "${ctx}/admin/oa/process/form/find/start.json",
             data: {
                 "processDefinitionId": processDefinitionId
             },
             type: "post",
             cache: false,
             dataType: "json",
             success: function(data) {
                 var result = "<input type = 'hidden' name = 'processDefinitionId' value = '" + processDefinitionId + "'/>";
                 if (data.message == 'formKey') {
                    jQuery('#startProcess').html(result + data.data);
                    bindDate();
                 } else {
                     jQuery.each(data.data.form.formProperties, function() {
                         var requiredStyle = this.required == true ?"<em style='color: red;'>*</em>" : "";
                         result += createFieldHtml(data.data, this, requiredStyle);
                         if(this.required == true) {
                         }
                     });
                     console.log(result);
                     jQuery('#startProcess').html(result);
                     bindDate();
                 }
             }
         });
     });

    /**
     * form对应的string/date/long/enum/boolean类型表单组件生成器
     * fp_的意思是form paremeter
     */
    var formFieldCreator = {
        long: function(formData, prop, requiredStyle) {
            var str = requiredStyle == ""? requiredStyle: " lang = 'required'";
            var result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>" +  "<input type='text' id='" + prop.id + "' name='fp_" + prop.id + "'" + str +  "/>" + "</span></p>";
            return result;
        },
        string: function(formData, prop, requiredStyle) {
            var str = requiredStyle == ""? requiredStyle: " lang = 'required'";
            var result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>" +  "<input type='text' id='" + prop.id + "' name='fp_" + prop.id + "'" + str +  "/>" + "</span></p>";
            return result;
        },
        date: function(formData, prop, requiredStyle) {
            var str = requiredStyle == ""? requiredStyle: " lang = 'required'";
            var result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>" +  "<input type='text' id='" + prop.id + "' name='fp_" + prop.id + "'" + str + " class = 'layDate' readonly = 'true'/>" + "</span></p>";
            return result;
        },
        'enum': function(formData, prop, requiredStyle) {
            var str = requiredStyle == ""? requiredStyle: " lang = 'required'";
            var result = "<p><label>" + prop.name + requiredStyle + "</label>" + "<span class = 'field'>";
            if(prop.writable == true) {
                result += "<select id='" + prop.id + "' name='fp_" + prop.id + "'" + str + " readonly='true'>";
                jQuery.each(formData['enum_' + prop.id], function(k, v) {
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
    function createFieldHtml(formData, prop, requiredStyle) {
        return formFieldCreator[prop.type.name](formData, prop, requiredStyle);
    }

    function addFormSubmit() {
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
        var data = jQuery('#startProcess').serialize();
        jQuery.ajax({
            url: "${ctx}/admin/oa/process/start.json",
            type: "post",
            dataType: "json",
            data: data,
            cache: false,
            success: function(result) {
                if (result.code == "0") {
                    alert(result.message);
                    window.location.href = "${ctx}/admin/oa/process/list.json";
                } else {
                    alert(result.message);
                }
            }

        });
    }

    function bindDate() {
       laydate.skin('molv');
       jQuery(".laydate").bind('click', function() {
           laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'});
       });
    }

    function resetData() {
        jQuery(".longinput").val("");
        jQuery()
    }

</script>
</body>
</html>