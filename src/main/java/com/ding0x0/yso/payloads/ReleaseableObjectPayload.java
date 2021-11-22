package com.ding0x0.yso.payloads;


/**
 * @author mbechler
 *
 */
public interface ReleaseableObjectPayload<T> extends ObjectPayload<T> {

    void release( T obj ) throws Exception;
}
