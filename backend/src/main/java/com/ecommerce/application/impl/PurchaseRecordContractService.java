package com.ecommerce.application.impl;

import com.ecommerce.application.IPurchaseRecordContractService;

import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IPurchaseRepository;
import com.ecommerce.domain.repository.entity.CommonUtil;
import com.ecommerce.domain.repository.entity.CryptoUtil;
import com.ecommerce.domain.repository.entity.Record;
import com.ecommerce.domain.wrapper.PurchaseRecordContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * PurchaseRecordContractService
 * 작성, 배포한 PurchaseRecord.sol 스마트 컨트랙트에서 정보를 조회한다.
 */
@Service
public class PurchaseRecordContractService implements IPurchaseRecordContractService {
    private static final Logger log = LoggerFactory.getLogger(PurchaseRecordContractService.class);

    @Value("${eth.purchase.record.contract}")
    private String PURCHASE_CONTRACT_ADDRESS;

    @Value("${eth.admin.address}")
    private String ADMIN_ADDRESS;

    @Value("${eth.admin.wallet.filename}")
    private String WALLET_RESOURCE;

    @Value("${eth.encrypted.password}")
    private String PASSWORD;

    private PurchaseRecordContract purchaseRecordContract;
    private ContractGasProvider contractGasProvider = new DefaultGasProvider();
    private Credentials credentials;

    @Autowired
    private Web3j web3j;

    private IPurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseRecordContractService(IPurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    private void getCredentials(){
        if(this.credentials != null) return;
        CryptoUtil cryptoUtil = CryptoUtil.of(ADMIN_ADDRESS);
        this.credentials = CommonUtil.getCredential(WALLET_RESOURCE, PASSWORD);
    }

    private void loadContract() {
        if(this.credentials == null)
            getCredentials();
        this.purchaseRecordContract = PurchaseRecordContract.load(PURCHASE_CONTRACT_ADDRESS, web3j, this.credentials, contractGasProvider);
    }

    private List<Record> fetchRecordAsync(final String ca) throws InterruptedException, ExecutionException {
        CompletableFuture<BigInteger> completableFuture = this.purchaseRecordContract.getRecordNumber(ca).sendAsync();
        BigInteger numberOfRecords = completableFuture.get();
        List<Record> records = new ArrayList<>();
        int i = 0 ;
        while (i < numberOfRecords.intValue()) {
            CompletableFuture<Tuple3<BigInteger, BigInteger, String>> recordCompletableFuture
                    = this.purchaseRecordContract.getRecord(ca, BigInteger.valueOf(i)).sendAsync();
            Tuple3<BigInteger, BigInteger, String> record = recordCompletableFuture.get();
            Record r = Record.getInstance(record);
            records.add(r);
            i++;
        }
        return records;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3
     * 구매 이력 조회
     */
    @Override
    public List<Record> getHistory(String contractAddress) {
        try {
            if(this.purchaseRecordContract == null) {
                loadContract();
            }

            log.debug(contractAddress);
            return this.fetchRecordAsync(contractAddress);
        } catch(Exception e) {
            log.debug(e.getMessage());
            throw new ApplicationException(e.getMessage());
        }
    }
}
