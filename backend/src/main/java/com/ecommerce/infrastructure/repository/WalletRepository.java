package com.ecommerce.infrastructure.repository;


import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IWalletRepository;
import com.ecommerce.domain.repository.entity.TransactionDonationHistory;
import com.ecommerce.domain.repository.entity.Wallet;
import com.ecommerce.infrastructure.repository.factory.WalletFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WalletRepository implements IWalletRepository
{
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Wallet> list()
	{
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM wallets");
		try {
			return this.jdbcTemplate.query(sbSql.toString(),
							   new Object[]{}, (rs, rowNum) -> WalletFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	/**
	 * 지갑 조회 by id
	 */
	@Override
	public Wallet get(final long ownerId)
	{
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM wallets WHERE owner_id=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] { ownerId }, (rs, rowNum) -> WalletFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	/**
	 * 지갑 조회 by address
	 * @param wAddress
	 * @return
	 */
	@Override
	public Wallet get(final String wAddress)
	{
		StringBuilder sbSql =  new StringBuilder("SELECT * FROM wallets WHERE address=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(),
								new Object[] {wAddress}, (rs, rowNum) -> WalletFactory.create(rs) );
		} catch (EmptyResultDataAccessException e) {
			throw new RepositoryException(e, e.getMessage());
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	/**
	 * 지갑 등록
	 */
	@Override
	public long create(final Wallet wallet)
	{
		try{
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("owner_id", wallet.getOwnerId());
			paramMap.put("address", wallet.getAddress());
			paramMap.put("balance", wallet.getBalance());
			paramMap.put("pay_balance", wallet.getPayBalance());
			paramMap.put("receiving_count", 0);
			paramMap.put("cash", 0);

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("wallets")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			return newId.longValue();

		}catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	/**
	 * 지갑 잔액 동기화
	 */
	@Override
	public int updateBalance(String wAddress, BigDecimal balance,BigDecimal payBalance, int cash) {
		StringBuilder sbSql =  new StringBuilder("UPDATE wallets ");
		sbSql.append("SET balance=?, cash=?, pay_balance=? ");
		sbSql.append("where address=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
					new Object[] {balance,payBalance, cash, wAddress});
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	/**
	 * 이더 충전 요청 횟수 업데이트
	 */
	@Override
	public int updateRequestNo(final String wAddress){
		StringBuilder sbSql =  new StringBuilder("UPDATE wallets SET receiving_count = receiving_count + 1 ");
		sbSql.append("WHERE address=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(),
			                                new Object[] {wAddress});
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}


}
