package com.sixeco.order.module.redis.service;

import java.util.List;
import java.util.Set;

/**
 * Redis服务
 *
 * @author: Zhanghe
 * @date: 2019-04-04
 */
public interface RedisService {

    boolean set(final String key , Object value);

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    boolean set(final String key , Object value , Long expireTime);

    /**
     * 批量删除对应的value
     * @param keys
     */
    void remove(final String... keys);

    /**
     * 批量删除key
     * @param pattern
     */
    void removePattern(final String pattern);
    /**
     * 删除对应的value
     * @param key
     */
    void remove(final String key);
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    boolean exists(final String key);
    /**
     * 读取缓存
     * @param key
     * @return
     */
    Object get(final String key);
    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    void hmSet(String key, Object hashKey, Object value);

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    Object hmGet(String key, Object hashKey);

    /**
     * 列表添加
     * @param k
     * @param v
     */
    void lPush(String k,Object v);
    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    List<Object> lRange(String k, long l, long l1);

    /**
     * 集合添加
     * @param key
     * @param value
     */
    void add(String key,Object value);

    /**
     * 集合获取
     * @param key
     * @return
     */
    Set<Object> setMembers(String key);
    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    void zAdd(String key,Object value,double scoure);

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    Set<Object> rangeByScore(String key, double scoure, double scoure1);
}
