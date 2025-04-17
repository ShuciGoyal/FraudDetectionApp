//package org.example.util;
//
//import org.example.model.Transaction;
//import java.util.Random;
//
//public class TransactionGenerator {
//    private static final Random random = new Random();
//    private static final String[] ACCOUNT_IDS = {"account1", "account2", "account3", "account4", "account5"};
//
//    public static Transaction generateTransaction() {
//        String accountId = ACCOUNT_IDS[random.nextInt(ACCOUNT_IDS.length)];
//        double amount = 100 + random.nextDouble() * 2000; // Random amount between 100 and 2100
//        return new Transaction(accountId, amount, System.currentTimeMillis());
//    }
//}