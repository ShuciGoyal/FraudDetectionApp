package org.example.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.example.model.Transaction;
import java.io.IOException;

public class TransactionDeserializer implements DeserializationSchema<Transaction> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Transaction deserialize(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, Transaction.class);
    }

    @Override
    public boolean isEndOfStream(Transaction transaction) {
        return false;
    }

    @Override
    public TypeInformation<Transaction> getProducedType() {
        return TypeInformation.of(Transaction.class);
    }
}