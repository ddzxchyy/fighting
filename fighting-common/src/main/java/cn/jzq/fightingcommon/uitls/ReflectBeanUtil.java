package cn.jzq.fightingcommon.uitls;

import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author by jzq
 * @date 2020/1/13
 */
public class ReflectBeanUtil {

    public static <T> T getEmptyBean(Class<T> clazz) {
        T obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                Type type = field.getGenericType();
                if (type.getTypeName().contains("ist")) {
                    System.out.println(type.getTypeName());
                }
                String methodName = "set" + StrUtil.upperFirst(field.getName());
                if (Integer.class.equals(type)) {
                    Method method = clazz.getMethod(methodName, Integer.class);
                    method.invoke(obj, 0);
                } else if (Long.class.equals(type)) {
                    Method method = clazz.getDeclaredMethod(methodName, Long.class);
                    method.invoke(obj, 0L);
                } else if (Date.class.equals(type)) {
                    Method method = clazz.getDeclaredMethod(methodName, Date.class);
                    method.invoke(obj, new Date());
                } else if (String.class.equals(type)) {
                    Method method = clazz.getDeclaredMethod(methodName, String.class);
                    method.invoke(obj, "");
                } else if (Double.class.equals(type)) {
                    Method method = clazz.getDeclaredMethod(methodName, Double.class);
                    method.invoke(obj, 0.0);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T initBean(Class<T> clazz) {
        T obj = null;
        try {
            Method initMethod = clazz.getMethod("init");
            return (T) initMethod.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getEmptyBean(clazz);
    }

}
