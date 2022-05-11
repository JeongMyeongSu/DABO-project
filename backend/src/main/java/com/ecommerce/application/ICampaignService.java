package com.ecommerce.application;

import com.ecommerce.domain.dto.CampaignDto;
import com.ecommerce.domain.dto.CommentDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ICampaignService {
    ResponseEntity<?> create(CampaignDto campaign) throws IOException;
    List<CampaignDto> getAllCampaign();
    CampaignDto getCampaign(Long campaignId);
    ResponseEntity<?> updateCampaign(Long campaignId, CampaignDto campaignDto);
    ResponseEntity<?> deleteCampaign(Long campaignId);
    List<CampaignDto> searchCampaign(String keyword);
    ResponseEntity<?> postComment(CommentDto commentDto);
    List<CommentDto> getComments(Long campaignId);
    ResponseEntity<?> updateComment(Long commentId, CommentDto commentDto);
    ResponseEntity<?> deleteComment(Long commentId);
    List<CampaignDto> getUrgentCampaign();
}
