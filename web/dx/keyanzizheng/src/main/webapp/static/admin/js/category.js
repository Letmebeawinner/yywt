// 提交表单
function searchForm() {
    jQuery("#listCategory").submit();
}

// 重置表单
function emptyForm() {
    jQuery("input:text").val('');
    jQuery("select option:first").prop("selected", 'selected');
}

// 删除
function remove(id) {
    if (confirm("是否确定删除?")) {
        jQuery.post("/admin/ky/removeCategory.json", {"id": id}, function (data) {
            alert(data.message)
            if (data.code === "0") {
                window.location.reload()
            }
        }, "json")
    }
}

// 保存
function saveForm() {
    var sort = jQuery("#sort").val()
    if (jQuery("#resultFormId").val().length < 1) {
        alert("请选择成果形式")
        return
    }
    if (jQuery("#name").val().length < 1) {
        alert("请填写成果类型名称")
        return
    }

    // 非必填
    if (sort.length > 1) {
        if (isNaN(sort)) {
            alert("排序值错误, 请输入数字")
            return
        }
    }

    jQuery.post("/admin/ky/doSaveCategory.json", jQuery("#saveForm").serialize(), function (data) {
        alert(data.message)
        if (data.code === "0") {
            window.location.href = "/admin/ky/listCategory.json"
        }
    }, "json")
}

// 重置表单
function resetData() {
    jQuery("input:text").val('');
    jQuery("select option:first").prop("selected", 'selected');
}

// 修改
function updateForm() {
    var sort = jQuery("#sort").val()
    if (jQuery("#resultFormId").val().length < 1) {
        alert("请选择成果形式")
        return
    }
    if (jQuery("#name").val().length < 1) {
        alert("请填写成果类型名称")
        return
    }

    // 非必填
    if (sort.length > 1) {
        if (isNaN(sort)) {
            alert("排序值错误, 请输入数字")
            return
        }
    }

    jQuery.post("/admin/ky/doUpdateCategory.json", jQuery("#updateForm").serialize(), function (data) {
        alert(data.message)
        if (data.code === "0") {
            window.location.href = "/admin/ky/listCategory.json"
        }
    }, "json")
}