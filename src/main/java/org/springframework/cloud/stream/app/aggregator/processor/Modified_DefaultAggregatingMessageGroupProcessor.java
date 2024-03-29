//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cloud.stream.app.aggregator.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.support.MutableMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.Assert;

public class Modified_DefaultAggregatingMessageGroupProcessor extends AbstractAggregatingMessageGroupProcessor {
    public Modified_DefaultAggregatingMessageGroupProcessor() {
    }

    protected final Object aggregatePayloads(MessageGroup group, Map<String, Object> headers) {
         Collection<Message<?>> messages = group.getMessages();
         Assert.notEmpty(messages, this.getClass().getSimpleName() + " cannot process empty message groups");

        List<Object> payloads = new ArrayList(messages.size());
        for (Message<?> message : messages) {
            if (message.getPayload() instanceof byte[]) {
                String contentType = message.getHeaders().containsKey(MessageHeaders.CONTENT_TYPE)
                        ? message.getHeaders().get(MessageHeaders.CONTENT_TYPE).toString()
                        : BindingProperties.DEFAULT_CONTENT_TYPE.toString();
                if (contentType.contains("text") || contentType.contains("json") || contentType.contains("x-spring-tuple")) {
                    message = new MutableMessage<>(new String(((byte[]) message.getPayload())), message.getHeaders());
                }
                payloads.add(message.getPayload());
            }
        }
        return payloads;
    }
}
