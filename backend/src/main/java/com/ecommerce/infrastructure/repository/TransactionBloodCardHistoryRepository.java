package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.repository.entity.TransactionBloodCardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionBloodCardHistoryRepository extends JpaRepository<TransactionBloodCardHistory, Long> {
    @Query(value = "SELECT *from transaction_blood_card_history where transaction_card_from_id = :userId", nativeQuery = true)
    List<TransactionBloodCardHistory> findBySend(Long userId);

    @Query(value = "SELECT *from transaction_blood_card_history where transaction_card_to_id = :userId", nativeQuery = true)
    List<TransactionBloodCardHistory> findByReceive(Long userId);
}
