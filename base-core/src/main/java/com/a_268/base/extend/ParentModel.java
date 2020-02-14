package com.a_268.base.extend;

import com.a_268.base.core.IEntity;
import java.lang.annotation.*;

/**
 * 如果实体上某个属性的值引用其他实体中的Id，需要在该属性的get方法中加上此注解。
 * 如 ParentnModel(Sample.class) ，其中Sample就是对应实体
  * @Package com.haixue.core.extend
  * @ClassName: ParentnModel
  * @author yantao
  *
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Inherited
public @interface ParentModel {
	public Class<? extends IEntity> value();
}
