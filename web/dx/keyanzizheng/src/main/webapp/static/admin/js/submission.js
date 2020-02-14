// 提交表单
function searchForm() {
    jQuery("#listCategory").submit();
}

// 保存
function saveForm() {
    var name = jQuery("input[name='submission.name']").val()
    var typeId = jQuery("input[name='submission.typeId']").val()
    var typeName = jQuery("input[name='submission.typeId']:checked").next().html()
    var fileUrl = jQuery("#fileUrl").val()

    if (!name) {
        alert("投稿名称为空, 请重新填写")
        return false
    }
    if (!typeId) {
        alert("类型为空, 请重新填写")
        return false
    }
    if (!fileUrl) {
        alert("未上传申请表, 请重新上传")
        return false
    }

    jQuery("#typeName").val(typeName)
    jQuery.post("/admin/ky/doSaveSubmission.json", jQuery("#saveForm").serialize(), function (data) {
        alert(data.message)
        if (data.code === "0") {
            window.location.href = "/admin/ky/listSubmission.json"
        }
    }, "json")
}


// 删除
function remove(id) {
    if (confirm("是否确定删除?")) {
        jQuery.post("/admin/ky/removeSubmission.json", {"id": id}, function (data) {
            alert(data.message)
            if (data.code === "0") {
                window.location.reload()
            }
        }, "json")
    }
}

// 审批
function auditSubmission(id, audit) {
    var msg;
    if (audit === "1") {
        msg = "是否通过审批"
    }
    if (audit === "2") {
        msg = "是否拒绝审批"
    }
    if (confirm(msg)) {
        jQuery.post("/admin/ky/doAuditSubmission.json", {"id": id, "audit": audit}, function (data) {
            alert(data.message)
            if (data.code === "0") {
                window.location.href = "/admin/ky/listSubmission.json"
            }
        }, "json")
    }
}
// 重置表单
function emptyForm() {
    jQuery("input:text").val('');
    jQuery("select option:first").prop("selected", 'selected');
}