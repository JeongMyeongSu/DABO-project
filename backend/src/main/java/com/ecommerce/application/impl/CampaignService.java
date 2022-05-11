package com.ecommerce.application.impl;

import com.ecommerce.application.ICampaignService;
import com.ecommerce.config.auth.PrincipalDetails;
import com.ecommerce.config.media.GlobalConfig;
import com.ecommerce.domain.dto.CampaignDto;
import com.ecommerce.domain.dto.CommentDto;
import com.ecommerce.domain.repository.ICampaignRepository;
import com.ecommerce.domain.repository.ICommentRepository;
import com.ecommerce.domain.repository.entity.Campaign;
import com.ecommerce.domain.repository.entity.Comment;
import com.ecommerce.domain.repository.entity.DABOUser;
import com.ecommerce.infrastructure.repository.DABOUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CampaignService implements ICampaignService {

    private final ICampaignRepository campaignRepository;
    private final ICommentRepository commentRepository;
    private final DABOUserRepository userRepository;
    private final GlobalConfig config;

    private final CommonService commonService;
    // 캠페인 등록
    @Transactional
    @Override
    public ResponseEntity<?> create(CampaignDto campaign) {

        MultipartFile multipartFile = campaign.getMedia();

        // 파일 없음 || 파일이 비어있음
//        if(multipartFile == null || multipartFile.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

        // 현재 날짜 폴더만들어서 저장
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        String uploadFilePath = config.getUploadFilePath()+currentDate+"/";

        // 랜덤이름 + . 확장자 가져오기
        String postfix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1, multipartFile.getOriginalFilename().length());
        String filename = UUID.randomUUID().toString() + "." + postfix;

        // 폴더 없으면 만들기
        File folder = new File(uploadFilePath);
        if(!folder.isDirectory()){
            folder.mkdirs();
        }

        String pathname = uploadFilePath+filename;
        String resourcePathname = config.getUploadResourcePath()+currentDate+"/"+filename;

        // url 경로 출력
        System.out.println("resourcePathname = " + resourcePathname);

        // 새로 파일 만들기
        File dest = new File(pathname);
        System.out.println(dest);
        try {
            multipartFile.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DABOUser userTemp = commonService.getLoginUser();
        Optional<DABOUser> user = userRepository.findDABOUserByEmail(userTemp.getEmail());
        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Campaign newCampaign = Campaign.builder()
                .user(user.get())
                .title(campaign.getTitle())
                .content(campaign.getContent())
                .target(campaign.getTarget())
                .amount(campaign.getAmount())
                .deadLine(campaign.getDeadLine())
                .mediaUrl(resourcePathname)
                .walletAddress(campaign.getWalletAddress())
                .build();
        campaignRepository.save(newCampaign);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 캠페인 전체 목록 조회
    @Transactional
    public List<CampaignDto> getAllCampaign() {

        List<CampaignDto> list = new ArrayList<>();

        for(Campaign campaign : campaignRepository.findAll()) {

            CampaignDto campaignDto = new CampaignDto();

            campaignDto.setCampaignId(campaign.getCampaignId());
            campaignDto.setTitle(campaign.getTitle());
            campaignDto.setContent(campaign.getContent());
            campaignDto.setTarget(campaign.getTarget());
            campaignDto.setAmount(campaign.getAmount());
            campaignDto.setDeadLine(campaign.getDeadLine());
            campaignDto.setMediaUrl(campaign.getMediaUrl());
            campaignDto.setUsername(campaign.getUser().getNickname());
            campaignDto.setWalletAddress(campaign.getWalletAddress());

            list.add(campaignDto);
        }
        Collections.reverse(list);

        return list;
    }

    // 캠페인 상세 조회
    @Transactional
    public CampaignDto getCampaign(Long campaignId) {

        Optional<Campaign> optCampaign = campaignRepository.findById(campaignId);
        if(!optCampaign.isPresent()) {
            return null;
        }
        Campaign campaign = optCampaign.get();

        CampaignDto campaignDto = new CampaignDto();
        campaignDto.setCampaignId(campaign.getCampaignId());
        campaignDto.setTitle(campaign.getTitle());
        campaignDto.setContent(campaign.getContent());
        campaignDto.setTarget(campaign.getTarget());
        campaignDto.setAmount(campaign.getAmount());
        campaignDto.setDeadLine(campaign.getDeadLine());
        campaignDto.setMediaUrl(campaign.getMediaUrl());
        campaignDto.setUsername(campaign.getUser().getNickname());
        campaignDto.setUserId(campaign.getUser().getUserId());
        campaignDto.setWalletAddress(campaign.getWalletAddress());

        return campaignDto;
    }

    // 캠페인 수정
    @Transactional
    public ResponseEntity<?> updateCampaign(Long campaignId, CampaignDto campaignDto) {

        Optional<Campaign> optCampaign = campaignRepository.findById(campaignId);

        if(!optCampaign.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Campaign campaign = optCampaign.get();
        campaign.setTitle(campaignDto.getTitle());
        campaign.setContent(campaignDto.getContent());
        campaign.setTarget(campaignDto.getTarget());
        campaign.setAmount(campaignDto.getAmount());
        campaign.setDeadLine(campaignDto.getDeadLine());
        campaign.setMediaUrl(campaignDto.getMediaUrl());

        campaignRepository.save(campaign);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 캠페인 삭제
    @Transactional
    public ResponseEntity<?> deleteCampaign(Long campaignId) {

        Optional<Campaign> optCampaign = campaignRepository.findById(campaignId);

        if(!optCampaign.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            campaignRepository.delete(optCampaign.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    // 캠페인 검색
    @Transactional
    public List<CampaignDto> searchCampaign(String keyword) {
        List<Campaign> campaigns = campaignRepository.findByTitleContaining(keyword);
        List<CampaignDto> campaignDtoList = new ArrayList<>();

        if(campaigns.isEmpty())
            return campaignDtoList;

        for(Campaign campaign : campaigns) {

            CampaignDto campaignDto = new CampaignDto();

            campaignDto.setCampaignId(campaign.getCampaignId());
            campaignDto.setTitle(campaign.getTitle());
            campaignDto.setContent(campaign.getContent());
            campaignDto.setTarget(campaign.getTarget());
            campaignDto.setAmount(campaign.getAmount());
            campaignDto.setDeadLine(campaign.getDeadLine());
            campaignDto.setMediaUrl(campaign.getMediaUrl());
            campaignDto.setUsername(campaign.getUser().getNickname());

            campaignDtoList.add(campaignDto);
        }

        return campaignDtoList;
    }

    /* ---------------- 댓글 ----------------*/

    // 댓글 조회
    public List<CommentDto> getComments(Long campaignId) {

        Optional<Campaign> campaign = campaignRepository.findById(campaignId);
        if(!campaign.isPresent()) {
            return null;
        }

        List<CommentDto> list = new ArrayList<>();

        for (Comment comment : commentRepository.findByCampaign(campaign.get())) {
            CommentDto commentDto = new CommentDto();

            commentDto.setCommentId(comment.getCommentId());
            commentDto.setContent(comment.getContent());
            commentDto.setCreatedAt(comment.getCreatedAt());
            commentDto.setUsername(comment.getUser().getNickname());
            commentDto.setCampaignId(comment.getCampaign().getCampaignId());

            list.add(commentDto);
        }
        Collections.reverse(list);
        return list;
    }

    // 댓글 작성
    @Transactional
    public ResponseEntity<?> postComment(CommentDto commentDto) {

        DABOUser userTemp = commonService.getLoginUser();
        Optional<DABOUser> user = userRepository.findDABOUserByEmail(userTemp.getEmail());

        Optional<Campaign> campaign = campaignRepository.findById(commentDto.getCampaignId());

        if(!campaign.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(commentDto.getUsername());
        System.out.println(commentDto.getContent());

        System.out.println(user.get());
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .campaign(campaign.get())
                .user(user.get())
                .build();

        commentRepository.save(comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<?> updateComment(Long commentId, CommentDto commentDto) {

        Optional<Comment> optComment = commentRepository.findById(commentId);

        if(!optComment.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Comment comment = optComment.get();
        comment.setContent(commentDto.getContent());

        commentRepository.save(comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<?> deleteComment(Long commentId) {

        commentRepository.deleteById(commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public List<CampaignDto> getUrgentCampaign() {

        List<CampaignDto> list = new ArrayList<>();

        for(Campaign campaign : campaignRepository.findAll()) {

            CampaignDto campaignDto = new CampaignDto();

            campaignDto.setCampaignId(campaign.getCampaignId());
            campaignDto.setTitle(campaign.getTitle());
            campaignDto.setContent(campaign.getContent());
            campaignDto.setTarget(campaign.getTarget());
            campaignDto.setAmount(campaign.getAmount());
            campaignDto.setDeadLine(campaign.getDeadLine());
            campaignDto.setMediaUrl(campaign.getMediaUrl());
            campaignDto.setUsername(campaign.getUser().getNickname());
            campaignDto.setWalletAddress(campaign.getWalletAddress());
            list.add(campaignDto);
        }
        Collections.sort(list, (o1, o2) -> {
            int a = Integer.parseInt(o1.getDeadLine().substring(3,4)+o1.getDeadLine().substring(5,7)+o1.getDeadLine().substring(8,10));
            int b = Integer.parseInt(o2.getDeadLine().substring(3,4)+o2.getDeadLine().substring(5,7)+o2.getDeadLine().substring(8,10));
            if(a-b<0){
                return -1;
            }
            return 1;
        });

        return list;
    }
}
