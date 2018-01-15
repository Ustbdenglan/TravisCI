package com.roslibrary.ros.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by lhw on 2018/1/14.
 * 序列化和反序列化工具
 */

public class SerializationUtils {
    /**
     * 将指定对象序列化为byte数组.
     * @param object  指定被序列化的对象
     * @return 序列化后的byte数组
     */

    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("序列化对象失败: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }

    /**
     * 反序列化byte数组为对象格式.
     * @param  bytes 指定被反序列化的bytes数组
     * @return 反序列化后的对象
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("反序列化对象失败", ex);
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException("反序列化对象失败", ex);
        }
    }
}
