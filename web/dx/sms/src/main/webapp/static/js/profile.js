/**
 * 增加配置属性
 * @param em
 */
function changeName(em){
    var label = jQuery(em).parent('label');
    var span = jQuery(label).next('span');
    var arr = jQuery(span).children();
    if(em.value==null || jQuery.trim(em.value)==''){
        arr[0].name='';
    }else{
        arr[0].name='configName_'+em.value;
    }
}

/**
 * 删除配置属性
 * @param em
 */
function removeConfigProperty(em){
    if(confirm('确认要删除该配置属性？')){
        jQuery(em).parent().parent().remove();
    }
}
/**
 * 配置属性的Key=Value表单属性
 */
function addElement(){
    var context = '<p class="new-config"><label>属性name:<input class="center configName" onchange="changeName(this)" style="width:70px;" title="属性name" type="text" value=""/></label><span class="field">属性value:<input style="width:62%;" type="text" title="属性value" class="configValue" value=""/><a class="btn btn2" href="javascript:void(0);" onclick="removeConfigProperty(this)">'
        +'<i class="fa fa-trash-o c-999 fa-fw fsize16 ml5"></i><span>删除配置属性</span></a></span></p>';
    jQuery(context).insertBefore("#but-p-from");
}

/**
 * 保存表单数据
 */
function saveProfile(){
    var arr=jQuery("#addorupdate").serialize();

       jQuery.ajax({
           url:'/admin/sms/profile/addOrUpdateProfile.json',
           type:'POST',
           data:arr,
           dataType:'json',
           success:function(result){
                if(result.code==0){
                    window.location.href="/admin/sms/profile/profileList.json";
                }else{
                    alert(result.message);
                }
           }
       });
}

/**
 * 删除配置
 */
function deleteProfile(id){
    if(window.confirm("确定删除吗?")){
        jQuery.ajax({
            url:'/admin/sms/profile/deleteProfile.json',
            type:'POST',
            data:{"id":id},
            dataType:'json',
            success:function (result) {
                if(result.code=='0'){
                    id='#profile_'+id;
                    jQuery(id).remove();
                    alert("删除成功");
                }
            }
        });
    }

}
function cleanform(){
    jQuery(".reset").click();
}
