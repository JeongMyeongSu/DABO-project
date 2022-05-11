package com.ecommerce.application.impl;

import com.ecommerce.application.IBloodCardService;
import com.ecommerce.domain.repository.entity.BloodCard;
import com.ecommerce.domain.repository.entity.TransactionBloodCardHistory;
import com.ecommerce.domain.repository.request.BloodCardDonationReq;
import com.ecommerce.domain.repository.request.BloodCardPostReq;
import com.ecommerce.domain.repository.response.BloodCardDonationRes;
import com.ecommerce.infrastructure.repository.BloodCardRepository;
import com.ecommerce.infrastructure.repository.TransactionBloodCardHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodCardService implements IBloodCardService {
    @Autowired
    BloodCardRepository bloodCardRepository;

    @Autowired
    TransactionBloodCardHistoryRepository transactionBloodCardHistoryRepository;

    @Override
    public BloodCard createBloodCard(BloodCardPostReq bloodCardPostReq, Long userId) {
        BloodCard bloodCard = new BloodCard();
        bloodCard.setUserId(userId);
        bloodCard.setBloodCardNumber(bloodCardPostReq.getBloodCardNumber());
        bloodCard.setUserName(bloodCardPostReq.getUserName());
        bloodCard.setUserBirth(bloodCardPostReq.getUserBirth());
        bloodCard.setDonationDate(bloodCardPostReq.getDonationDate());
        bloodCard.setDonationType(bloodCardPostReq.getDonationType());
        bloodCard.setBloodHouse(bloodCardPostReq.getBloodHouse());
        bloodCard.setUserGender(bloodCardPostReq.getUserGender());

        return bloodCardRepository.save(bloodCard);
    }

    @Override
    public List<BloodCard> getBloodCard(Long userId) {
        return bloodCardRepository.findByUserId(userId);
    }

    @Override
    public TransactionBloodCardHistory bloodCardDonation(BloodCardDonationReq bloodCardDonationReq, Long userId, String userName) {
        TransactionBloodCardHistory transactionBloodCardHistory = new TransactionBloodCardHistory();
        Long bloodCardId = bloodCardDonationReq.getBloodCardId();
        Long TransactionCardToId = bloodCardDonationReq.getTransactionCardToId();
        transactionBloodCardHistory.setTransactionCardFromId(userId);
        transactionBloodCardHistory.setTransactionCardToId(bloodCardDonationReq.getTransactionCardToId());
        transactionBloodCardHistory.setUserName(userName);
        transactionBloodCardHistory.setBloodCardId(bloodCardDonationReq.getBloodCardId());
        transactionBloodCardHistory.setTransactionCardMessage(bloodCardDonationReq.getTransactionCardMessage());
        bloodCardRepository.bloodCardDonation(bloodCardId, TransactionCardToId);
        return transactionBloodCardHistoryRepository.save(transactionBloodCardHistory);

    }

    @Override
    public List<TransactionBloodCardHistory> bloodCardDonationSend(Long userId) {
        return transactionBloodCardHistoryRepository.findBySend(userId);
    }

    @Override
    public List<TransactionBloodCardHistory> bloodCardDonationReceive(Long userId) {
        return transactionBloodCardHistoryRepository.findByReceive(userId);
    }
}
