package com.ecommerce.domain.repository.response;

import lombok.Data;

@Data
public class BloodCardDonationRes {
    private long transactionCardFromId;
    private long transactionCardToId;
    private long bloodCardId;
    private String userName;
    private String transactionCardMessage;
}
