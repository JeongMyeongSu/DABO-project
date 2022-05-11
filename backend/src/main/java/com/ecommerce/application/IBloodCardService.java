package com.ecommerce.application;

import com.ecommerce.domain.repository.entity.BloodCard;
import com.ecommerce.domain.repository.entity.TransactionBloodCardHistory;
import com.ecommerce.domain.repository.request.BloodCardDonationReq;
import com.ecommerce.domain.repository.request.BloodCardPostReq;
import com.ecommerce.domain.repository.response.BloodCardDonationRes;

import java.util.List;

public interface IBloodCardService {
    BloodCard createBloodCard(BloodCardPostReq bloodCardPostReq, Long userId);
    List<BloodCard> getBloodCard(Long userId);
    TransactionBloodCardHistory bloodCardDonation(BloodCardDonationReq bloodCardDonationReq, Long userId, String userName);
    List<TransactionBloodCardHistory> bloodCardDonationSend(Long userId);
    List<TransactionBloodCardHistory> bloodCardDonationReceive(Long userId);

}
