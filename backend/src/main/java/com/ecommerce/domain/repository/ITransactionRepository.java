package com.ecommerce.domain.repository;

import com.ecommerce.domain.repository.entity.Transaction;

import java.util.List;

public interface ITransactionRepository {
    List<Transaction> list();
    Transaction get(String hash);
    List<Transaction> getByAddress(String address);
    long add(Transaction tx);
}
