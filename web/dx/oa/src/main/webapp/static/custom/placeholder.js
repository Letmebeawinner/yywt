//placeholder兼容IE方法
function placeholderFun() {
    //判断浏览器是否支持placeholder属性
  supportPlaceholder='placeholder'in document.createElement('input');

  //当浏览器不支持placeholder属性时，调用placeholder函数
  if(!supportPlaceholder){
    $("input").not("input[type='password']").each(//把input绑定事件 排除password框  
          function(){  
              if($(this).val()=="" && $(this).attr("placeholder")!=""){  
                  $(this).val($(this).attr("placeholder"));  
                  $(this).focus(function(){  
                      if($(this).val()==$(this).attr("placeholder")) $(this).val("");  
                  });  
                  $(this).blur(function(){  
                      if($(this).val()=="") $(this).val($(this).attr("placeholder"));  
                  });  
              }  
      });  
      //对password框的特殊处理
      var pwdField    = $("input[type=password]");
      pwdField.each(function() {
          var _this = $(this),
              index = _this.index(),
              pwdVal = _this.attr('placeholder');
          _this.after('<input id="pwdPlaceholder'+index+'" type="text" value='+pwdVal+' autocomplete="off" />');
          var pwdFieldColn = _this.next();  
          pwdFieldColn.show();  
          _this.hide();
            
          pwdFieldColn.focus(function(){  
              pwdFieldColn.hide();  
              _this.show();  
              _this.focus();  
          });  
            
          _this.blur(function(){  
              if(_this.val() == '') {  
                  pwdFieldColn.show();  
                  _this.hide();  
              }  
          });
      });
  }
}