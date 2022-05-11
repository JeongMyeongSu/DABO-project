package com.ecommerce.api;

import com.ecommerce.application.ICampaignService;
import com.ecommerce.domain.dto.CampaignDto;
import com.ecommerce.domain.dto.CommentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("캠페인 관련 기능")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/donationBoard")
public class CampaignController {

    public static final Logger logger = LoggerFactory.getLogger(CampaignController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private ICampaignService campaignService;

    @Autowired
    public CampaignController(ICampaignService campaignService){
        this.campaignService = campaignService;
    }

    // 캠페인 등록
    @ApiOperation(value = "캠페인 등록", notes = "캠페인 등록")
    @PostMapping(value = "/createBoard")
    public ResponseEntity<?> createBoard(@ModelAttribute CampaignDto campaign) throws Exception {
        logger.info("createBoard - 호출");
        System.out.println(campaign.getDeadLine());
        return campaignService.create(campaign);
    }

    // 캠페인 조회
    @ApiOperation(value = "캠페인 전체 조회", notes = "캠페인 전체 조회")
    @GetMapping(value = "/listBoard")
    public List<CampaignDto> listBoard() {
        logger.info("listBoard - 호출");

        return campaignService.getAllCampaign();
    }

    // 캠페인 조회(시간 적게 남은 순서)
    @ApiOperation(value = "캠페인 전체 조회", notes = "캠페인 전체 조회")
    @GetMapping(value = "/urgentBoard")
    public List<CampaignDto> urgentBoard() {
        logger.info("urgentBoard - 호출");

        return campaignService.getUrgentCampaign();
    }

    // 캠페인 상세 조회
    @ApiOperation(value = "캠페인 상세 조회", notes = "캠페인 상세 조회")
    @GetMapping(value = "/detailBoard/{campaign_id}")
    public CampaignDto getBoard(@PathVariable("campaign_id") Long campaignId) {
        logger.info("getBoard - 호출");

        return campaignService.getCampaign(campaignId);
    }

    // 캠페인 수정
    @ApiOperation(value = "캠페인 수정", notes = "캠페인 수정")
    @PutMapping(value = "/detailBoard/{campaign_id}")
    public ResponseEntity<?> updateBoard(@PathVariable("campaign_id") Long campaignId, @RequestBody CampaignDto campaignDto){
        logger.info("updateBoard - 호출");
        System.out.println("아 왜 안찍히냐고");
        System.out.println(campaignDto.getTitle());
        System.out.println(campaignDto.getContent());
        return campaignService.updateCampaign(campaignId,campaignDto);
    }

    // 캠페인 삭제
    @ApiOperation(value = "캠페인 삭제", notes = "캠페인 삭제")
    @DeleteMapping("/detailBoard/{campaign_id}")
    public ResponseEntity<?> deleteCampaign(@PathVariable("campaign_id") Long campaignId){
        return campaignService.deleteCampaign(campaignId);
    }

    // 캠페인 검색
    @ApiOperation(value = "캠페인 검색", notes = "캠페인 검색")
    @GetMapping("/search")
    public List<CampaignDto> searchCampaign(@RequestParam("keyword") String keyword){
        return campaignService.searchCampaign(keyword);
    }

    /* ---------------- 댓글 ----------------*/

    // 댓글 조회
    @ApiOperation(value = "캠페인 댓글 조회", notes = "캠페인에 등록된 댓글을 조회합니다.")
    @GetMapping("/detailBoard/{campaign_id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable("campaign_id") Long campaignId) {
        return ResponseEntity.ok(campaignService.getComments(campaignId));
    }

    // 댓글 작성
    @ApiOperation(value = "캠페인 댓글 작성", notes = "캠페인에 댓글을 작성하여 추가합니다.")
    @PostMapping("/detailBoard/{campaign_id}/comments")
    public ResponseEntity<?> postComment(@RequestBody CommentDto commentDto) {
        return campaignService.postComment(commentDto);
    }

    // 댓글 수정
    @ApiOperation(value = "캠페인 댓글 수정", notes = "캠페인에 댓글을 수정합니다.")
    @PutMapping("/detailBoard/{campaign_id}/comments/{comment_id}")
    public ResponseEntity<?> updateComment(@PathVariable("comment_id") Long commentId, @RequestBody CommentDto commentDto) {
        return campaignService.updateComment(commentId, commentDto);
    }

    // 댓글 삭제
    @ApiOperation(value = "캠페인 댓글 삭제", notes = "캠페인에 댓글을 삭제합니다.")
    @DeleteMapping("/detailBoard/{campaign_id}/comments/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable("comment_id") Long commentId) {
        return campaignService.deleteComment(commentId);
    }
}
