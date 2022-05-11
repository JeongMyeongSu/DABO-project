package com.ecommerce.domain.repository;

import com.ecommerce.domain.repository.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICampaignRepository  extends JpaRepository<Campaign, Long> {
    List<Campaign> findByTitleContaining(String keyword);
}
