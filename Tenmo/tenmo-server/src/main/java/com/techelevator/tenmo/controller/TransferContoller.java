package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;


@RestController
@RequestMapping("/users")
public class TransferContoller {
	
	
	private TransferDAO transferDao;
	
	public TransferContoller(TransferDAO transferDao) {
		this.transferDao = transferDao;
	}
	
	@RequestMapping(path= "{userId}/transfer", method=RequestMethod.GET)
	public List<Transfer> listTransfer(@PathVariable("userId") int userId) {
		return transferDao.listTransfer(userId);
	}
	
	@RequestMapping(path= "{userId}/transfer/pending", method=RequestMethod.GET)
	public List<Transfer> listPendingTransfer(@PathVariable("userId") int userId) {
		return transferDao.listPendingTransfer(userId);
	}
	
	@RequestMapping(path= "/transfer", method=RequestMethod.POST)
	public void addTransfer(@RequestBody Transfer transfer) {
		transferDao.createTransfer(transfer.getTypeId(), transfer.getTransferstatus(), transfer.getAccountFrom(), transfer.getAccountTo(),transfer.getAmount());
	}
	
	@RequestMapping(path= "{userId}/transfer/{transferId}", method=RequestMethod.GET)
	public Transfer getTransfer(@PathVariable("userId") int userId, @PathVariable("transferId") int transferId) {
		return transferDao.getTransferById(transferId);
	}

	@RequestMapping(path= "/transfer/{transferId}/update", method=RequestMethod.PUT)
	public void updateTransfer(@PathVariable("transferId") int transferId, @RequestBody Transfer transfer) {
		transferDao.updateTransfer(transfer.getTransferstatus(), transferId);
	}
}
