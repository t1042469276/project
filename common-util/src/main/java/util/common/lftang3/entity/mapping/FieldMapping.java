package util.common.lftang3.entity.mapping;

import java.lang.annotation.*;

/**
 * @Description: 字段映射注解
 * @author: log
 * @date: 2022.08.20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface FieldMapping {

    /**
     * 映射字段
     *
     * @return
     */
    String fieldName();

    /**
     * 字段描述
     *
     * @return
     */
    String description() default "";

    /**
     * 过滤字段
     *
     * @return
     */
    boolean filterField() default false;
}
