package com.ecommerce.application.impl;

import com.ecommerce.application.ICashContractService;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.entity.CommonUtil;
import com.ecommerce.domain.repository.entity.CryptoUtil;
import com.ecommerce.domain.wrapper.CashContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
public class CashContractService implements ICashContractService {
    private static final Logger log = LoggerFactory.getLogger(CashContractService.class);

    @Value("${eth.erc20.contract}")
    private String ERC20_TOKEN_CONTRACT;

    @Value("${eth.admin.address}")
    private String ADMIN_ADDRESS;

    @Value("${eth.admin.wallet.filename}")
    private String WALLET_RESOURCE;

    @Value("${eth.encrypted.password}")
    private String PASSWORD;

    private CashContract cashContract;
    private ContractGasProvider contractGasProvider = new DefaultGasProvider();
    private Credentials credentials;

    @Autowired
    private Web3j web3j;

    private void getCredentials(){
        if(this.credentials != null) return;
        CryptoUtil cryptoUtil = CryptoUtil.of(ADMIN_ADDRESS);
        this.credentials = CommonUtil.getCredential(WALLET_RESOURCE, PASSWORD);
    }

    private void loadContract() {
        if(this.credentials == null)
            getCredentials();
        this.cashContract = CashContract.load(ERC20_TOKEN_CONTRACT, web3j, this.credentials, contractGasProvider);
    }

    @Override
    public int getBalance(String eoa) {
        try {
            if(this.cashContract == null)
                loadContract();

            log.debug(eoa);
            return this.cashContract.balanceOf(eoa).send().intValue();
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }
}
