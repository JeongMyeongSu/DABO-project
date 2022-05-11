package com.ecommerce.application.impl;


import com.ecommerce.domain.repository.IEthInfoRepository;
import com.ecommerce.domain.repository.ITransactionRepository;
import com.ecommerce.domain.repository.entity.EthInfo;
import com.ecommerce.domain.repository.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Sub PJT Ⅲ 추가 과제
 * EthBlockListeningService
 * 이더리움 네트워크의 새로 생성된 블록 정보로부터
 * 트랜잭션을 동기화하는 기능
 */
@Service
public class EthBlockListeningService
{
	private static final Logger log = LoggerFactory.getLogger(EthBlockListeningService.class);

	private BigInteger latestBlockHeight = BigInteger.valueOf(0);

	private Web3j web3j;
	private IEthInfoRepository ethInfoRepository;
	private ITransactionRepository transactionRepository;

	@Value("${spring.web3j.client-address}")
	private String ethUrl;

	@Autowired
	public EthBlockListeningService(Web3j web3j,
									IEthInfoRepository ethInfoRepository,
									ITransactionRepository transactionRepository)
	{
		this.web3j = web3j;
		this.ethInfoRepository = ethInfoRepository;
		this.transactionRepository = transactionRepository;
	}

	/**
	 * 완료된 트랜잭션을 가져온다.
	 */
	private void getTransactions(final BigInteger currentBlockNumber)
			throws ExecutionException, InterruptedException
	{
		log.info("Txs fetching start from Block # (" + currentBlockNumber + ") to (" + latestBlockHeight+ ")");
		for(long i = currentBlockNumber.longValue(); i > latestBlockHeight.longValue(); i--) {
			EthBlock.Block block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(i)), true).sendAsync().get().getBlock();
			block.getTransactions().forEach(tx -> {
				this.transactionRepository.add(new Transaction(tx));
			});
		}
		latestBlockHeight = currentBlockNumber;
//		log.info("Txs fetched Block # (" + latestBlockHeight + ") from " + this.ethUrl);

		this.ethInfoRepository.put(this.ethUrl, latestBlockHeight.toString());
	}

	/**
	 * 구축한 이더리움 네트워크로부터 신규 생성된 블록을 동기화한다.
	 */
	@PostConstruct
	public void listen()
	{
		log.info("Try to subscribe new block");

		web3j.blockFlowable(true).subscribe(ethBlock -> {
			EthBlock.Block b = ethBlock.getBlock();
			log.info("New Block: blockNumber={}, blockHash={}, timestamp={}, difficulty={}, " +
					         "gasLimit={}, gasUsed={}, nonce={}, txs={}",
			         b.getNumber(),
			         b.getHash(),
			         b.getTimestamp(),
			         b.getDifficulty(),
			         b.getGasLimit(),
			         b.getGasUsed(),
			         b.getNonce(),
			         b.getTransactions().size());
			try {
				EthInfo ethInfo = this.ethInfoRepository.get(ethUrl);
				if (ethInfo != null && ethInfo.getLatestBlockNumber() != null) {
					latestBlockHeight = new BigInteger(ethInfo.getLatestBlockNumber());
					log.info(ethInfo.getEthUrl());
				}
				this.getTransactions(b.getNumber());
//				log.info("New Block Subscribed");
			} catch (ExecutionException | InterruptedException e) {
				log.info("Block Subscription Failed");
				log.error(e.toString());
			}
		},Throwable::printStackTrace);
	}
}
