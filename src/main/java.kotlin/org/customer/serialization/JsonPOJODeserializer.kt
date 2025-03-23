package org.customer.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.customer.model.TransactionRecord

class JsonPOJODeserializer<T> : Deserializer<T?> {
    private val objectMapper = ObjectMapper()

    private var tClass: Class<T?>? = null

    override fun configure(props: MutableMap<String?, *>?, isKey: Boolean) {
        tClass = TransactionRecord::class.java as Class<T?>
    }

    override fun deserialize(topic: String?, bytes: ByteArray?): T? {
        if (bytes == null) return null

        val data: T?
        try {
            data = objectMapper.readValue(bytes, tClass)
        } catch (e: Exception) {
            throw SerializationException(e)
        }

        return data
    }

    override fun close() {
    }
}