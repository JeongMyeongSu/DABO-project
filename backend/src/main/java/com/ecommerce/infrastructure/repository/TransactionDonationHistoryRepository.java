package com.ecommerce.infrastructure.repository;


import com.ecommerce.domain.repository.entity.TransactionBloodCardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ecommerce.domain.repository.entity.TransactionDonationHistory;

import java.util.List;


@Repository
public interface TransactionDonationHistoryRepository extends JpaRepository<TransactionDonationHistory, Long> {
    @Query(value = "SELECT *from transaction_donation_history where transaction_donation_from_address = :address", nativeQuery = true)
    List<TransactionDonationHistory> findByAddress(String address);
}
