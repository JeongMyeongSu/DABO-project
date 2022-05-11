package com.ecommerce.domain.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class TransactionBloodCardHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionCardHistoryId;

    private long transactionCardFromId;
    private long transactionCardToId;
    private long bloodCardId;
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionCardDate = LocalDate.now();
    private String transactionCardMessage;

}
