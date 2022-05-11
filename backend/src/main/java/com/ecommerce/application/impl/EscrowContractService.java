package com.ecommerce.application.impl;

import com.ecommerce.application.IEscrowContractService;
import com.ecommerce.application.IPurchaseService;

import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.entity.CommonUtil;
import com.ecommerce.domain.repository.entity.CryptoUtil;
import com.ecommerce.domain.repository.entity.Purchase;
import com.ecommerce.domain.repository.entity.PurchaseState;
import com.ecommerce.domain.wrapper.EscrowContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class EscrowContractService implements IEscrowContractService {

    private static final Logger log = LoggerFactory.getLogger(EscrowContractService.class);

    private IPurchaseService purchaseService;

    @Value("${eth.admin.address}")
    private String ADMIN_ADDRESS;

    @Value("${eth.admin.wallet.filename}")
    private String WALLET_RESOURCE;

    @Value("${eth.encrypted.password}")
    private String PASSWORD;

    private EscrowContract escrowContract;
    private ContractGasProvider contractGasProvider = new DefaultGasProvider();
    private Credentials credentials;

    @Autowired
    private Web3j web3j;

    @Autowired
    public EscrowContractService(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    private void getCredentials(){
        if(this.credentials != null) return;
        CryptoUtil cryptoUtil = CryptoUtil.of(ADMIN_ADDRESS);
        this.credentials = CommonUtil.getCredential(WALLET_RESOURCE, PASSWORD);
    }

    private void loadContract(String ca) {
        if(this.credentials == null)
            getCredentials();
        this.escrowContract = EscrowContract.load(ca, web3j, this.credentials, contractGasProvider);
    }

    /**
     * 입금 상태 조회 - checkDeposit
     * @return
     */
    @Override
    public Purchase checkDeposit(int pid) {
        Purchase p = this.purchaseService.getByPurchaseId(pid);
        if(p == null ) {
            throw new ApplicationException("");
        }
        loadContract(p.getContractAddress());
        try {
            CompletableFuture<TransactionReceipt> future = this.escrowContract.checkDeposit().sendAsync();
            TransactionReceipt receipt = future.get();
            log.debug("escrow checkDeposit() receipt's status: " + receipt.isStatusOK());
            CompletableFuture<BigInteger> stateFuture = this.escrowContract.state().sendAsync();
            BigInteger state = stateFuture.get();
            if(state.equals(BigInteger.ONE)) {
                Purchase purchaseUpdated = this.purchaseService.updateState(pid, PurchaseState.P.toString());
                return purchaseUpdated;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new ApplicationException(e.getMessage());
        }
        return null;
    }
}
