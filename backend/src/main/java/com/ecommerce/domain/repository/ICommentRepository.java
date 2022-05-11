package com.ecommerce.domain.repository;

import com.ecommerce.domain.repository.entity.Campaign;
import com.ecommerce.domain.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCampaign(Campaign campaign);
}
