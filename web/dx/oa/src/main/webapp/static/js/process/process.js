//subject ztree start
var subject_setting = {
    view: {
        showLine: true,
        showIcon: true,
        selectedMulti: false,
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: 'id',
            pIdKey: 'parentId',
            rootPid: ''
        },
        key: {
            name: 'name',
            title: 'name'
        }
    },
    callback: {
        onClick: subject_treeOnclick
    }
};

//切换专业时操作
function subject_treeOnclick(e, treeId, treeNode) {
    hideSubjectMenu();
    jQuery("#subjectId").val(treeNode.id);
    jQuery("#subjectNameBtn").val(treeNode.name);
}

function showSubjectMenu() {
    var cityObj = jQuery("#subjectNameBtn");
    var cityOffset = jQuery("#subjectNameBtn").offset();
    jQuery("#subjectmenuContent").css({
         // left: cityOffset.left + "px",
         // top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    jQuery("body").bind("mousedown", onBodyDown);
}
function hideSubjectMenu() {
    jQuery("#subjectmenuContent").fadeOut("fast");
    jQuery("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    if (!(event.target.id == "subjectNameBtn" || event.target.id == "subjectmenuContent" || jQuery(event.target).parents("#subjectmenuContent").length > 0)) {
        hideSubjectMenu();
    }
}

// 分类
function addFormSubmit() {
    if(!jQuery("#subjectNameBtn").val()){
        alert("请选择分类");
        return;
    }
    var data = jQuery("#processCategory").serialize();
    jQuery.ajax({
        url:"/admin/oa/category/set.json",
        data: data,
        type: "post",
        dataType: "json",
        async: false,
        cache : false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/oa/process/list.json";
            } else {
                alert(result.message);
            }
        }
    });
}

function updateFormSubmit() {
    if(!jQuery("#name").val()){
        alert("请添加封条名称");
        return;
    }
    if(!jQuery("#paperTypeId").val()){
        alert("请选择封条类别");
        return;
    }
    if(!jQuery("#paperFunctionId").val()){
        alert("请选择封条用途");
        return;
    }
    if(!jQuery("#subjectNameBtn").val()){
        alert("请选择发行部门");
        return;
    }
    var data = jQuery("#updatePaper").serialize();
    jQuery.ajax({
        url:"/admin/oa/updatePaper.json",
        data: data,
        type: "post",
        dataType: "json",
        async: false,
        cache : false,
        success: function (result) {
            if (result.code == "0") {
                alert(result.message);
                window.location.href = "/admin/oa/queryAllPaper.json";
            } else {
                alert(result.message);
            }
        }
    });
}

// 重置
function resetData(){
    jQuery(".longinput").val("");
}