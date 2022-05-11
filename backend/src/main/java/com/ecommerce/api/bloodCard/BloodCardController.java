package com.ecommerce.api.bloodCard;

import com.ecommerce.application.impl.BloodCardService;
import com.ecommerce.config.auth.SsafyUserDetails;
import com.ecommerce.domain.repository.entity.BloodCard;
import com.ecommerce.domain.repository.entity.TransactionBloodCardHistory;
import com.ecommerce.domain.repository.request.BloodCardDonationReq;
import com.ecommerce.domain.repository.request.BloodCardPostReq;
import com.ecommerce.domain.repository.response.BaseResponseBody;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bc")
public class BloodCardController {
    @Autowired
    BloodCardService bloodCardService;

    @PostMapping("/create")
    @ApiOperation(value = "전자 헌혈증 생성", notes = "전자 헌혈증 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BloodCardPostReq.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<BloodCard> createBloodCard(@ApiIgnore Authentication authentication, @RequestBody BloodCardPostReq bloodCardPostReq){
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        Long userId = userDetails.getUser().getUserId();
        BloodCard bloodCard = bloodCardService.createBloodCard(bloodCardPostReq, userId);
        System.out.println("전자헌혈증 생성");
        if(bloodCard != null){
            return ResponseEntity.status(200).body(bloodCard);
        }
        return ResponseEntity.status(500).body(null);
    }

    @GetMapping("/search")
    @ApiOperation(value = "전자 헌혈증 조회", notes = "전자 헌혈증 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BloodCardPostReq.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<List<BloodCard>> getBloodCard(@ApiIgnore Authentication authentication){
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        Long userId = userDetails.getUser().getUserId();
        List<BloodCard> bloodCard = bloodCardService.getBloodCard(userId);
        if(bloodCard != null){
            System.out.println("전자헌혈증 조회 성공" + bloodCard);
            return ResponseEntity.status(200).body(bloodCard);
        }
        return ResponseEntity.status(500).body(null);
    }

    @PostMapping("/donation")
    @ApiOperation(value = "전자 헌혈증 기부", notes = "전자 헌혈증 기부")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BloodCardPostReq.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<TransactionBloodCardHistory> bloodCardDonation(@ApiIgnore Authentication authentication, @RequestBody BloodCardDonationReq bloodCardDonationReq){
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        Long userId = userDetails.getUser().getUserId();
        String userName = userDetails.getUser().getNickname();
        TransactionBloodCardHistory transactionBloodCardHistory = bloodCardService.bloodCardDonation(bloodCardDonationReq, userId, userName);
        if(transactionBloodCardHistory != null){
            return ResponseEntity.status(200).body(transactionBloodCardHistory);
        }
        return ResponseEntity.status(500).body(null);
    }

    @GetMapping("/transactionHistory/send")
    @ApiOperation(value = "전자 헌혈증 기부내역 조회", notes = "전자 헌혈증 기부내역 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BloodCardPostReq.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<List<TransactionBloodCardHistory>> bloodCardDonationSend(@ApiIgnore Authentication authentication){
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        Long userId = userDetails.getUser().getUserId();
        List<TransactionBloodCardHistory> transactionBloodCardHistory = bloodCardService.bloodCardDonationSend(userId);
        if(transactionBloodCardHistory != null){
            return ResponseEntity.status(200).body(transactionBloodCardHistory);
        }
        return ResponseEntity.status(500).body(null);
    }

    @GetMapping("/transactionHistory/receive")
    @ApiOperation(value = "전자 헌혈증 기부받은 내역 조회", notes = "전자 헌혈증 기부받은 내역 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BloodCardPostReq.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<List<TransactionBloodCardHistory>> bloodCardDonationReceive(@ApiIgnore Authentication authentication){
        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        Long userId = userDetails.getUser().getUserId();
        List<TransactionBloodCardHistory> transactionBloodCardHistory = bloodCardService.bloodCardDonationReceive(userId);
        if(transactionBloodCardHistory != null){
            return ResponseEntity.status(200).body(transactionBloodCardHistory);
        }
        return ResponseEntity.status(500).body(null);
    }

}
