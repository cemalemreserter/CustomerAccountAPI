package org.customer.constants

object AppConstants {
    const val GROUP_ID: String = "group_id"

    const val THRESHOLD_D: Double = 0.0

    const val THRESHOLD: Int = 0

    const val ACCOUNT_TOPIC_NAME: String = "topic-account"

    const val TRANSACTION_TOPIC_NAME: String = "topic-transaction"

    const val DATE_FORMAT: String = "yyyyMMddHHmmss"

    const val FACTORY_CONCURRENCY_COUNT: Int = 3

    const val MAX_RETRY_ATTEMPT: Int = 3

    const val IDLE_BETWEEN_POLLS: Long = 200000

    const val MAX_POLL_INTERVAL_MS_CONFIG: Int = 600000

    const val SESSION_TIMEOUT_MS_CONFIG: Int = 50000

    const val DEFAULT_API_TIMEOUT_MS_CONFIG: Int = 7000000


    const val MAX_POLL_RECORDS_CONFIG: Int = 100

    const val GROUP_INSTANCE_ID_CONFIG: String = "kafka-streams-instance-1"

    const val MAX_PARTITION_FETCH_BYTES_CONFIG: Int = 10000

    const val FETCH_MAX_BYTES_CONFIG: Int = 30000

    const val CACHE_TTL: Long = 60000

    const val BOOTSTRAP_ADDRESS: String = "localhost:9092"

    const val CONSUMER_GROUP_ID: String = "my-group"
}