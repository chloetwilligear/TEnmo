package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {

	float returnBalance(int userId);

	void addBalance(int userId, float transferAmount);

	void deleteBalance(int userId, float transferAmount);

}
