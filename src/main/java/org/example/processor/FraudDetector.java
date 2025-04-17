package org.example.processor;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.model.Alert;
import org.example.model.Transaction;
import org.example.util.Constants;

public class FraudDetector extends KeyedProcessFunction<String, Transaction, Alert> {
    private static final long serialVersionUID = 1L;
    private transient ValueState<Double> lastAmount;

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<Double> descriptor = new ValueStateDescriptor<>(
                "last-amount",
                Double.class
        );
        lastAmount = getRuntimeContext().getState(descriptor);
    }

    @Override
    public void processElement(
            Transaction transaction,
            Context context,
            Collector<Alert> collector) throws Exception {

        Double previousAmount = lastAmount.value();
        if (previousAmount != null && transaction.getAmount() > Constants.FRAUD_THRESHOLD) {
            Alert alert = new Alert(
                    transaction.getAccountId(),
                    "Suspicious amount detected: " + transaction.getAmount()
            );
            collector.collect(alert);
        }

        lastAmount.update(transaction.getAmount());
    }
}