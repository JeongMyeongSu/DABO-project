package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.repository.entity.BloodCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BloodCardRepository extends JpaRepository<BloodCard, Long> {
    @Query(value = "SELECT *FROM blood_card where user_id = :userId", nativeQuery = true)
    List<BloodCard> findByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE blood_card set user_id = :transactionCardToId where blood_card_id = :bloodCardId", nativeQuery = true)
    void bloodCardDonation(Long bloodCardId, Long transactionCardToId);
}
