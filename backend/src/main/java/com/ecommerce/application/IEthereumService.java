package com.ecommerce.application;

import com.ecommerce.domain.repository.entity.Address;
import java.math.BigInteger;

public interface IEthereumService {
    String requestEth(String address);
    BigInteger getBalance(String address);

    Address getAddress(String address);
}
