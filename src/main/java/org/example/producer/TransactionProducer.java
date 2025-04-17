package org.example.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class TransactionProducer {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        ObjectMapper mapper = new ObjectMapper();

        String csvFile = "creditcard.csv";
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;

        // Skip header
        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Transaction transaction = new Transaction(
                "account" + values[0],  // Use first column as account ID
                Double.parseDouble(values[29]),  // Amount is in last column
                System.currentTimeMillis()
            );

            String jsonTransaction = mapper.writeValueAsString(transaction);
            producer.send(new ProducerRecord<>("transactions", jsonTransaction));
            Thread.sleep(1000); // Simulate real-time data
        }

        producer.close();
    }
}