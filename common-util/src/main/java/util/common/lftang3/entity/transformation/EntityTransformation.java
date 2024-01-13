package util.common.lftang3.entity.transformation;

import util.common.lftang3.entity.mapping.FieldMapping;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;

/**
 * @author ：lftang3
 * @date ：Created in 2022/7/20 13:51
 * @description：实体类转换,实体类字段名必须一样，getset方法必须有
 * @modified By：
 * @version: 1.0$
 */
public class EntityTransformation {

    private EntityTransformation() {
    }

    /**
     * 设置值
     *
     * @param fieldName 字段名
     * @param toObj     目标对象
     * @param valObj    传入值
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void setValue(String fieldName, Object toObj, Object valObj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, toObj.getClass());
        Method writeMethod = propertyDescriptor.getWriteMethod();
        writeMethod.invoke(toObj, valObj);
    }

    /**
     * @param fieldName 字段名
     * @param dataObj   数据
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static Object getValue(String fieldName, Object dataObj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, dataObj.getClass());
        Method writeMethod = propertyDescriptor.getReadMethod();
        return writeMethod.invoke(dataObj);
    }

    /**
     * 是否是基本数据类型的包装类
     *
     * @param type 字段类型
     * @return true/false
     * @throws Exception
     */
    private static boolean isPackingBasicType(Type type) {
        return type == String.class || type == Integer.class || type == Double.class ||
                type == Short.class || type == Character.class || type == Byte.class || type == Float.class || type == Boolean.class;
    }

    /**
     * 是否能转自定义对象
     * true :能转
     * false:不能
     *
     * @param genericType
     * @return
     */
    private static boolean isObjectCustom(Type genericType) {
        String typeName = genericType.getTypeName();
        try {
            final Class<?> aClass = Class.forName(typeName);
            aClass.newInstance();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 处理类型
     *
     * @param genericType
     * @return
     */
    private static boolean handlerDataType(Type genericType) {
        //基本类型包装类型、String
        if (isPackingBasicType(genericType)) {
            return true;
        }
        //对象类型
        if (isObjectCustom(genericType)) {
            return false;
        }
        //集合
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        Type[] types = parameterizedType.getActualTypeArguments();
        boolean flag = true;
        for (Type type : types) {
            //能转class类
            if (type instanceof Class && (type == Object.class || isPackingBasicType(type))) {
                continue;
            }
            flag = handlerDataType(type);
        }
        return flag;
    }

    /**
     * 实体类转换，只处理字段为基本类型
     * 根据setget方法转换类型，字段必须一样
     *
     * @param obj   数据源
     * @param clazz 目标类型
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T transformEntity(Object obj, Class<T> clazz) {
        //返回对象
        Object result = null;
        try {
            result = clazz.newInstance();
            //获取值
            Class<?> objClazz = obj.getClass();
            Field[] declaredFields = objClazz.getDeclaredFields();
            //获取字段的值
            for (Field declaredField : declaredFields) {
                String name = declaredField.getName();
                Object value = getValue(name, obj);
                //获取声明的字段类型的class
                Class<?> type = declaredField.getType();
                //基本数据类型
                if (type.isPrimitive() || type.isEnum()) {
                    setValue(name, result, value);
                    continue;
                }
                //获取
                Type genericType = declaredField.getGenericType();
                if (handlerDataType(genericType)) {
                    setValue(name, result, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz.cast(result);
    }

    /***
     * @Author log
     * @Description // 通过指定注解转换类型
     * @Date 22:24 2022/8/20
     * @Param [obj, clazz]
     * @return T
     **/
    public static <T> T transformEntityByAnnotation(Object obj, Class<T> clazz) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        final T result = clazz.newInstance();
        for (Field declaredField : declaredFields) {
            if (!declaredField.isAnnotationPresent(FieldMapping.class) && declaredField.getAnnotation(FieldMapping.class).filterField()) {
                continue;
            }
            // 字段类型
            final Class<?> type = declaredField.getType();
            // 类型判断
            if (type.isPrimitive() || type.isEnum() || handlerDataType(declaredField.getGenericType())) {
                setObjectValue(result, clazz, obj, declaredField);
            }
        }
        return result;
    }

    /***
     * @Author log
     * @Description //根据字段获取值
     * @Date 22:36 2022/8/20
     * @Param [result, clazz, obj, field]
     * @return void
     **/
    private static <T> void setObjectValue(T result, Class<T> clazz, Object obj, Field field) throws IllegalAccessException, NoSuchFieldException {
        field.setAccessible(true);
        Object object = field.get(obj);
        final String fieldName = field.getAnnotation(FieldMapping.class).fieldName();
        Field resultField = clazz.getDeclaredField(fieldName);
        resultField.setAccessible(true);
        resultField.set(result, object);
    }

}
