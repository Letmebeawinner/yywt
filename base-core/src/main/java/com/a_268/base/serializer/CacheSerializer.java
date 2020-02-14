package com.a_268.base.serializer;

import java.io.IOException;

/**
 *缓存数据序列化接口 */
public interface CacheSerializer {

    public String name();

    /**
     * 把要缓存的数据对象序列化 byte[]
     * @param obj 要序列化的数据对象
     * @return 返回序列化后的byte[]数组
     * @throws IOException
     */
    public byte[] serialize(Object obj) throws IOException;

    /**
     * 把序列化过后得到的byte[]进行反序列化，使得到对应的数据对象
     * @param bytes 要序列化的 byte[]
     * @return 返回反序列后的数据对象
     * @throws IOException
     */
    public Object deserialize(byte[] bytes) throws IOException;

}
