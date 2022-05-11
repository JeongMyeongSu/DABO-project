package com.ecommerce.domain.dto;

import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDto {

    private Long campaignId;

    @ApiParam(value="캠페인 제목")
    private String title;

    @ApiParam(value="캠페인 내용")
    private String content;

    @ApiParam(value="캠페인 목표")
    private String target;

    @ApiParam(value="다보 목표")
    private int amount;

    @ApiParam(value="마감 기한")
    private String deadLine;

    @ApiParam(value="첨부 파일")
    private MultipartFile media;

    @ApiParam(value="첨부 파일 저장 경로")
    private String mediaUrl;

    @ApiParam(value="캠페인 작성자")
    private String username;

    @ApiParam(value="지갑 주소")
    private String walletAddress;

    @ApiParam(value = "작성자 ID")
    private Long userId;
}
