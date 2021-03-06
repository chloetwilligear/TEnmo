package com.techelevator.tenmo;

import org.junit.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.AccountSqlDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.User;

@SpringBootTest

public class UserSqlDaoIntegerationTest extends DAOIntegrationTest {
	
	
	private AccountDAO accountDao;
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(getDataSource());
		accountDao = new AccountSqlDAO( jdbcTemplate );
	}
	
	@Test
	public void getUserBalance() {
		float result = accountDao.returnBalance(1);
		Assert.assertEquals(1000, result, 1);
	}
		
	
	
}
