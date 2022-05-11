package com.ecommerce.domain.repository.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
public class BloodCardPostReq {
    private String bloodCardNumber;
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate userBirth;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate donationDate;
    private String donationType;
    private String bloodHouse;
    private String userGender;
}
