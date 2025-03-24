package org.customer.constants;

public final class AppConstants {

    private AppConstants() {}


  public static final String GROUP_ID = "group_id";

  public static final double THRESHOLD_D = 0.0;

  public static final int THRESHOLD = 0;

  public static final String  ACCOUNT_TOPIC_NAME = "topic-account";

  public static final String  TRANSACTION_TOPIC_NAME = "topic-transaction";

  public static final String DATE_FORMAT = "yyyyMMddHHmmss";

  public static  final Integer FACTORY_CONCURRENCY_COUNT = 3;

  public static  final int MAX_RETRY_ATTEMPT = 3;

  public static  final long IDLE_BETWEEN_POLLS = 200000;

  public static  final int MAX_POLL_INTERVAL_MS_CONFIG = 600000;

  public static  final int SESSION_TIMEOUT_MS_CONFIG = 50000;

  public static  final int DEFAULT_API_TIMEOUT_MS_CONFIG = 7000000;


  public static  final int MAX_POLL_RECORDS_CONFIG = 100;

  public static  final String GROUP_INSTANCE_ID_CONFIG = "kafka-streams-instance-1";

  public static  final int MAX_PARTITION_FETCH_BYTES_CONFIG = 10000;

  public static  final int FETCH_MAX_BYTES_CONFIG = 30000;

  public static  final long CACHE_TTL = 60000;

  public static final String BOOTSTRAP_ADDRESS = "localhost:9092";

  public static final String CONSUMER_GROUP_ID = "my-group" ;



}