package com.ecommerce.domain.repository.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode()
@EntityListeners(value = AuditingEntityListener.class)
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_c_id")
    private DABOUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_c_id")
    private Campaign campaign;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
