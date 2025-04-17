package org.example;

        import org.apache.flink.api.common.eventtime.WatermarkStrategy;
        import org.apache.flink.connector.kafka.source.KafkaSource;
        import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
        import org.apache.flink.streaming.api.datastream.DataStream;
        import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
        import org.example.model.Alert;
        import org.example.model.Transaction;
        import org.example.processor.FraudDetector;
        import org.example.serialization.TransactionDeserializer;

        public class FraudDetectionApp {
            public static void main(String[] args) throws Exception {
                StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

                KafkaSource<Transaction> source = KafkaSource.<Transaction>builder()
                        .setBootstrapServers("localhost:9092")
                        .setTopics("transactions")
                        .setGroupId("fraud-detector")
                        .setStartingOffsets(OffsetsInitializer.earliest())
                        .setValueOnlyDeserializer(new TransactionDeserializer())
                        .build();

                DataStream<Transaction> transactions = env
                        .fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

                DataStream<Alert> alerts = transactions
                        .keyBy(Transaction::getAccountId)
                        .process(new FraudDetector());

                transactions.print("Transaction");
                alerts.print("Alert");

                env.execute("Fraud Detection");
            }
        }