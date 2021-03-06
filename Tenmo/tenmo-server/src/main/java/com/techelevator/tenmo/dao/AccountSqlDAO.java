package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountSqlDAO implements AccountDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	@Override
	public float returnBalance(int userId) {
		
	float balance = jdbcTemplate.queryForObject("SELECT ac.balance FROM users u JOIN accounts ac ON u.user_id = ac.user_id WHERE u.user_id = ?", float.class, userId);
	
	return balance;
	}

	@Override
	public void addBalance(int userId, float transferAmount) {
		
		String insertAccount = "update accounts set balance = ? where user_id = ?";
        jdbcTemplate.update(insertAccount, (returnBalance(userId) + transferAmount), userId);
	}
	
	@Override
	public void deleteBalance(int userId, float transferAmount) {
		
		String insertAccount = "update accounts set balance = ? where user_id = ?";
        jdbcTemplate.update(insertAccount, (returnBalance(userId) - transferAmount), userId);
	}

}
