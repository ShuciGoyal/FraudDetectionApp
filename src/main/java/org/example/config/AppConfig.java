package org.example.config;

public class AppConfig {
    private AppConfig() {}

    public static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String KAFKA_TOPIC = "transactions";
    public static final String CONSUMER_GROUP = "fraud-detector";
}