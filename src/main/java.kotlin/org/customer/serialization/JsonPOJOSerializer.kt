package org.customer.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Serializer

class JsonPOJOSerializer<T> : Serializer<T?> {
    private val objectMapper = ObjectMapper()

    override fun configure(props: MutableMap<String?, *>?, isKey: Boolean) {}

    override fun serialize(topic: String?, data: T?): ByteArray? {
        if (data == null) return null

        try {
            return objectMapper.writeValueAsBytes(data)
        } catch (e: Exception) {
            throw SerializationException("Error serializing JSON message", e)
        }
    }

    override fun close() {}
}
