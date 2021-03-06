package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;

@Component
public class TransferSqlDAO implements TransferDAO{
	
	private JdbcTemplate jdbcTemplate;

    public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	

	@Override
	 public List<Transfer> listTransfer(int userId) {
      List<Transfer> transfers = new ArrayList<>();
      String sql = "SELECT transfer_id, users.username, transfer_type_id, account_from, account_to, amount FROM transfers JOIN accounts ON transfers.account_from = accounts.account_id OR transfers.account_to = accounts.account_id  JOIN users ON accounts.user_id = users.user_id WHERE account_from = ? OR account_to = ? AND transfer_status_id = 2";

      SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
      while(results.next()) {
          Transfer transfer = mapRowToListTransfer(results);
          transfers.add(transfer);
      }

      return transfers;
	}
	
	@Override
	 public List<Transfer> listPendingTransfer(int userId) {
     List<Transfer> transfers = new ArrayList<>();
     String sql = "SELECT transfer_id, account_to, amount FROM transfers  JOIN accounts ON transfers.account_to = accounts.account_id  JOIN users ON accounts.user_id = users.user_id WHERE account_from = ? AND transfer_status_id = 1";

     SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
     while(results.next()) {
         Transfer transfer = mapRowToListPendingTransfer(results);
         transfers.add(transfer);
     }

     return transfers;
	}
	
	@Override
	public void updateTransfer(int transferstatus, int transferId) {
		String insertTransfer = "Update transfers set transfer_status_id = ? where transfer_id = ?;";
		jdbcTemplate.update(insertTransfer, transferstatus, transferId);
	}

	
	@Override
	public void createTransfer(int typeId, int transferstatus, int accountFrom, int accountTo, Float amount) {
		String insertTransfer = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(insertTransfer, typeId, transferstatus, accountFrom, accountTo, amount);
	}

	@Override
	public Transfer getTransferById( int transferId) {
		Transfer transfer = null;
		String sql = "SELECT transfer_id, users.username, transfer_type_id, transfer_status_id, account_to, account_from, amount FROM transfers JOIN accounts ON transfers.account_from = accounts.account_id OR transfers.account_to = accounts.account_id  JOIN users ON accounts.user_id = users.user_id WHERE transfer_id = ?"; 
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
		
		while(results.next()) {
			transfer = mapRowToTransferById(results);
		}
		return transfer;
	}
    
	 private Transfer mapRowToListTransfer(SqlRowSet rs) {
	        Transfer transfer = new Transfer();
	        transfer.setTransferId(rs.getInt("transfer_id"));
	        transfer.setTypeId(rs.getInt("transfer_type_id"));
	        transfer.setUsername(rs.getString("username"));
	        transfer.setAccountFrom(rs.getInt("account_from"));
	        transfer.setAccountTo(rs.getInt("account_to"));
	        transfer.setAmount(rs.getFloat("amount"));
	        return transfer;
	    }
	 
	 private Transfer mapRowToListPendingTransfer(SqlRowSet rs) {
	        Transfer transfer = new Transfer();
	        transfer.setTransferId(rs.getInt("transfer_id"));
	        transfer.setAccountTo(rs.getInt("account_to"));
	        transfer.setAmount(rs.getFloat("amount"));
	        return transfer;
	    }
	 
	 private Transfer mapRowToTransferById(SqlRowSet rs) {
	        Transfer transfer = new Transfer();
	        transfer.setTransferId(rs.getInt("transfer_id"));
	        transfer.setTypeId(rs.getInt("transfer_type_id"));
	        transfer.setTransferstatus(rs.getInt("transfer_status_id"));
	        transfer.setAccountFrom(rs.getInt("account_from"));
	        transfer.setAccountTo(rs.getInt("account_to"));
	        transfer.setAmount(rs.getFloat("amount"));
	        transfer.setUsername(rs.getString("username"));
	        return transfer;
	    }
}
