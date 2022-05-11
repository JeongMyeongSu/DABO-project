package com.ecommerce.application.impl;

import com.ecommerce.application.ICashContractService;
import com.ecommerce.application.IEthereumService;
import com.ecommerce.application.IWalletService;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.exception.NotFoundException;
import com.ecommerce.domain.repository.entity.Address;
import com.ecommerce.domain.repository.entity.TransactionDonationHistory;
import com.ecommerce.domain.repository.entity.Wallet;
import com.ecommerce.domain.repository.IWalletRepository;
import com.ecommerce.infrastructure.repository.TransactionBloodCardHistoryRepository;
import com.ecommerce.infrastructure.repository.TransactionDonationHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * IWalletService를 implements 하여 구현합니다.
 */
@Service
public class WalletService implements IWalletService
{
	private static final Logger log = LoggerFactory.getLogger(WalletService.class);

	private IWalletRepository walletRepository;
	private IEthereumService ethereumService;
	private ICashContractService cashContractService;

	@Autowired
	TransactionDonationHistoryRepository transactionDonationHistoryRepository;

	@Autowired
	public WalletService(IWalletRepository walletRepository,
						 IEthereumService ethereumService,
						 ICashContractService cashContractService) {
		this.walletRepository = walletRepository;
		this.ethereumService = ethereumService;
		this.cashContractService = cashContractService;
	}

	/**
	 * 지갑 조회
	 * DB에 저장된 지갑주소의 정보와 이더리움의 잔액 정보를 동기화한다.
	 * @param walletAddress
	 * @return Wallet
	 */
	@Override
	public Wallet getAndSyncBalance(final String walletAddress)
	{
		log.debug("getAndSyncBalance Start");
		Wallet wallet = walletRepository.get(walletAddress);
		log.debug(wallet.toString());
		if(wallet == null)
			throw new NotFoundException(walletAddress + " 해당 주소 지갑을 찾을 수 없습니다.");

		// ether balance only
		/*
		BigDecimal currentBalance = new BigDecimal(this.ethereumService.getBalance(walletAddress));
		if(!wallet.getBalance().equals(currentBalance)) {
			wallet = this.syncBalance(walletAddress, currentBalance);
			wallet.setBalance(currentBalance);
		}
		*/

		// ether + cash balance
		Address address = this.ethereumService.getAddress(walletAddress);
		int cashBalance = this.cashContractService.getBalance(walletAddress);
		log.debug("cashBalance : "+cashBalance);
		if(!wallet.getBalance().equals(new BigDecimal(address.getBalance())) || wallet.getCash()!= cashBalance) {
			wallet = syncBalance(walletAddress, new BigDecimal(address.getBalance()),wallet.getPayBalance(), cashBalance);
			wallet.setBalance(new BigDecimal(address.getBalance()));
			wallet.setCash(cashBalance);
		}

		return wallet;
	}

	/**
	 * userId로 지갑을 가져온다.
	 * @param userId
	 * @return
	 */
	@Override
	public Wallet get(final long userId)
	{
		Wallet wallet = this.walletRepository.get(userId);
		if(wallet == null)
			throw new NotFoundException(userId + " 해당 회원의 주소 지갑을 찾을 수 없습니다.");
		return getAndSyncBalance(wallet.getAddress());
	}

	/**
	 * 지갑을 등록한다
	 * @param wallet
	 * @return
	 */
	@Override
	public Wallet register(final Wallet wallet)
	{

		long id = this.walletRepository.create(wallet);
		return this.walletRepository.get(id);
	}

	/**
	 * 지갑을 동기화시킨다.
	 * @param walletAddress
	 * @param balance
	 * @param payBalance
	 * @param cash
	 * @return
	 */
	@Override
	public Wallet syncBalance(final String walletAddress, final BigDecimal balance, final BigDecimal payBalance,final int cash)
	{
		int affected = this.walletRepository.updateBalance(walletAddress, balance,payBalance, cash);
		if(affected == 0)
			throw new ApplicationException("잔액 갱신 처리가 반영되지 않았습니다.");

		return this.walletRepository.get(walletAddress);
	}

	/**
	 * 이더 충전 횟수를 update 한다.
	 * @param walletAddress
	 * @return
	 */
	@Override
	public Wallet updateRequestNo(final String walletAddress)
	{
		int affected = this.walletRepository.updateRequestNo(walletAddress);
		if(affected == 0)
			throw new ApplicationException("충전회수갱신 처리가 반영되지 않았습니다.");

		return this.walletRepository.get(walletAddress);
	}

	/**
	 * [지갑주소]로 이더를 송금한다.
	 * @param walletAddress
	 * @return Wallet
	 */
	@Override
	public Wallet requestEth(String walletAddress) {
		Wallet wallet = this.getAndSyncBalance(walletAddress);
		if (wallet == null || !wallet.canRequestEth()) {
			throw new ApplicationException("[1] 충전할 수 없습니다!");
		}

		try {
			String txHash = this.ethereumService.requestEth(walletAddress);
			if(txHash == null || txHash.equals(""))
				throw new ApplicationException("충전 회수 갱신 트랜잭션을 보낼 수 없습니다!");
			log.info("received txhash: " + txHash);

			this.updateRequestNo(walletAddress);

			return this.getAndSyncBalance(walletAddress);
		}
		catch (Exception e) {
			throw new ApplicationException("[2] 충전할 수 없습니다!");
		}
	}

	/**
	 * 지갑 리스트를 불러온다
	 * @return
	 */
	@Override
	public List<Wallet> list()
	{
		return this.walletRepository.list();
	}

	/**
	 * Donation or Token 충전 시의 기록을 저장한다.
	 * @param transactionDonationHistory
	 * @return
	 */
	@Override
	public TransactionDonationHistory createDonation(TransactionDonationHistory transactionDonationHistory) {
		return transactionDonationHistoryRepository.save(transactionDonationHistory);
	}

	/**
	 * address로 Donation History를 불러온다.
	 * @param address
	 * @return
	 */
	@Override
	public List<TransactionDonationHistory> getDonationList(String address) {
		return transactionDonationHistoryRepository.findByAddress(address);
	}


}
