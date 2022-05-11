package com.ecommerce.domain.repository.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class TransactionDonationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="id", example="1")
    private long id;

    private LocalDate created_at = LocalDate.now();
    @ApiModelProperty(name="state", example="충전")
    private String state;
    @ApiModelProperty(name="amount", example="10000")
    private long amount;
    @ApiModelProperty(name="transactionDonationFromAddress", example="0x")
    private String transactionDonationFromAddress;
    @ApiModelProperty(name="transactionDonationToAddress", example="0x")
    private String transactionDonationToAddress;
    @ApiModelProperty(name="contractAddress", example="0x")
    private String contractAddress;
}
