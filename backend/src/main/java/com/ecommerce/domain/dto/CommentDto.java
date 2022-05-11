package com.ecommerce.domain.dto;

import io.swagger.annotations.ApiParam;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long commentId;

    @ApiParam(value = "댓글 내용")
    private String content;

    @ApiParam(value = "댓글 작성자")
    private String username;

    @ApiParam(value = "댓글 작성 시각")
    private LocalDateTime createdAt;

    @ApiParam(value = "댓글 해당 캠페인 id")
    private Long campaignId;
}
