package com.ecommerce.domain.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Entity
public class BloodCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bloodCardId;

    private long userId;
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
