package com.keyanzizheng.utils;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 序列化对象
 *
 * @author YaoZhen
 * @date 11-01, 14:19, 2017.
 */
public class HessianUtil {
    /**
     * 序列化对象为字节流
     *
     * @param obj 实体对象
     * @return 字节流
     * @throws IOException io异常
     */
    public static byte[] serialize(Object obj) throws IOException {
        if (obj == null) {
            throw new NullPointerException();
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        ho.writeObject(obj);
        return os.toByteArray();
    }

    /**
     * 反系列化字节流为对象
     * <p>
     *     反序列化的对象package路径与源对象不一致时, <br>
     *     会报warn:an unknown class in WebappClassLoader
     * </p>
     * @param by 字节流
     * @return 实体对象
     * @throws IOException io异常
     */
    public static Object deserialize(byte[] by) throws IOException {
        if (by == null) {
            throw new NullPointerException();
        }

        ByteArrayInputStream is = new ByteArrayInputStream(by);
        HessianInput hi = new HessianInput(is);
        return hi.readObject();
    }
}
