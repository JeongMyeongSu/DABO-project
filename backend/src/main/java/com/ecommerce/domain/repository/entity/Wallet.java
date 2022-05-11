package com.ecommerce.domain.repository.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Wallet {
	@ApiModelProperty(name="id", example="1")
	private long id;
	@ApiModelProperty(name="ownerId", example="1")
	private long ownerId;
	@ApiModelProperty(name="ownerId's address", example="0x3a2Ea4Db5CbEcCAc3738449a7D3b7bF4Eb6E2d06")
	private String address;
	@ApiModelProperty(name="ETH balance", example="0")
	private BigDecimal balance = BigDecimal.valueOf(0); // Eth balance
	@ApiModelProperty(name="KR balance", example="0")
	private BigDecimal payBalance = BigDecimal.valueOf(0); // Payment Service Balance
	@ApiModelProperty(name="receivingCount", example="1") //나중에 없어도 되는 칼럼 -> 거래 내역으로 뺄 것
	private int receivingCount = 0;
	@ApiModelProperty(name="cash", example="1")
	private int cash = 0; //dabo Token

	public boolean canRequestEth(){
		return this.receivingCount < 10;
	}
}
