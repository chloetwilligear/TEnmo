package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	List<Transfer> listTransfer(int userId);

	void createTransfer(int typeId, int transferstatus, int accountFrom, int accountTo, Float amount);

	Transfer getTransferById(int transferId);

	List<Transfer> listPendingTransfer(int userId);

	void updateTransfer(int transferstatus, int transferId);

}
