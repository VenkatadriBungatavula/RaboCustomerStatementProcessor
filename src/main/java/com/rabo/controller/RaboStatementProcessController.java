package com.rabo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.model.ErrorRecord;
import com.rabo.model.ResponseStatus;
import com.rabo.model.Transaction;
import com.rabo.util.ValidationUtil;

/**
 * This is Controller which handles the requests and sends the Response Status based on output
 * 
 * @author Venkatadri Bungatavula Initial version: Aug 12, 2020
 * 
 */

@RestController
public class RaboStatementProcessController {
	
	private static final Logger logger = LogManager.getLogger(RaboStatementProcessController.class);
	ValidationUtil validateUtil;
	ResponseStatus status;

	@PostMapping("/validateStatement")
	public ResponseEntity<ResponseStatus> validateStatement(@RequestBody @Valid Transaction[] transactionList)
			throws Exception {
		logger.info("RaboStatementProcessController::validateStatement - start");
		
		try {
			if (transactionList == null || transactionList.length == 0) {
				status = new ResponseStatus();
				List<ErrorRecord> errorList = new ArrayList<>();
				ErrorRecord error = new ErrorRecord();
				status.setResult("BAD_REQUEST");
				status.setErrorRecords(errorList.toArray(new ErrorRecord[errorList.size()]));
				logger.info("RaboStatementProcessController::validateStatement - end");
				return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
			} else {

				boolean duplicate = false;
				boolean invalidBalance = false;
				// check for valid transaction- unique references
				validateUtil = new ValidationUtil();

				if (validateUtil.validateBadRequest(transactionList)) {
					logger.info("Validation Failed: Bad Request");
					status = new ResponseStatus();
					List<ErrorRecord> errorList = new ArrayList<>();
					ErrorRecord error = new ErrorRecord();
					status.setResult("BAD_REQUEST");
					status.setErrorRecords(errorList.toArray(new ErrorRecord[errorList.size()]));
					logger.info("RaboStatementProcessController::validateStatement - end");
					return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
				}

				Set<Transaction> duplicateTransactions = validateUtil.checkForDuplicateTransaction(transactionList);
				
				if (duplicateTransactions != null && duplicateTransactions.size() > 0) {
					duplicate = true;
					logger.debug("Duplicate Transactions found");
				}
					

				// check for valid transaction- valid End Balance
				Set<Transaction> invalidEndBalTransactions = validateUtil.checkForInValidEndBalance(transactionList);

				if (invalidEndBalTransactions != null && invalidEndBalTransactions.size() > 0) {
					invalidBalance = true;
					logger.debug("Invalid End Balance Transactions found");
				}

				// When there are no duplicate reference and correct end balance
				if (!duplicate && !invalidBalance) {
					status = new ResponseStatus();
					List<ErrorRecord> errorList = new ArrayList<>();
					ErrorRecord error = new ErrorRecord();
					status.setResult("SUCCESSFUL");
					status.setErrorRecords(errorList.toArray(new ErrorRecord[errorList.size()]));
					logger.info("RaboStatementProcessController::validateStatement - end");
					return new ResponseEntity<ResponseStatus>(status, HttpStatus.OK);
				}

				// When there are duplicate reference and correct balance
				if (duplicate && !invalidBalance) {
					status = new ResponseStatus();
					List<ErrorRecord> errorList = new ArrayList<>();
					duplicateTransactions.forEach(t -> {
						errorList.add(new ErrorRecord(t.getReference(), t.getAccountNumber()));
					});
					status.setResult("DUPLICATE_REFERENCE");
					status.setErrorRecords(errorList.toArray(new ErrorRecord[errorList.size()]));
					logger.info("RaboStatementProcessController::validateStatement - end");
					return new ResponseEntity<ResponseStatus>(status, HttpStatus.OK);
				}
				// When there are no duplicate reference and In correct balance
				if (!duplicate && invalidBalance) {
					status = new ResponseStatus();
					List<ErrorRecord> errorList = new ArrayList<>();
					invalidEndBalTransactions.forEach(t -> {
						errorList.add(new ErrorRecord(t.getReference(), t.getAccountNumber()));
					});
					status.setResult("INCORRECT_END_BALANCE");
					status.setErrorRecords(errorList.toArray(new ErrorRecord[errorList.size()]));
					logger.info("RaboStatementProcessController::validateStatement - end");
					return new ResponseEntity<ResponseStatus>(status, HttpStatus.OK);
				}
				// When there are duplicate reference and In correct balance
				if (duplicate && invalidBalance) {
					status = new ResponseStatus();
					List<ErrorRecord> errorList = new ArrayList<>();
					duplicateTransactions.forEach(t -> {
						errorList.add(new ErrorRecord(t.getReference(), t.getAccountNumber()));
					});
					invalidEndBalTransactions.forEach(t -> {
						errorList.add(new ErrorRecord(t.getReference(), t.getAccountNumber()));
					});
					status.setResult("DUPLICATE_REFERENCE _INCORRECT_END_BALANCE");
					status.setErrorRecords(errorList.toArray(new ErrorRecord[errorList.size()]));
					logger.info("RaboStatementProcessController::validateStatement - end");
					return new ResponseEntity<ResponseStatus>(status, HttpStatus.OK);
				} else {
					status = new ResponseStatus();
					List<ErrorRecord> errorList = new ArrayList<>();
					ErrorRecord error = new ErrorRecord();
					status.setResult("INTERNAL_SERVER_ERROR");
					status.setErrorRecords(errorList.toArray(new ErrorRecord[errorList.size()]));
					logger.info("RaboStatementProcessController::validateStatement - end");
					return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
		} catch (Exception e) {
			throw new Exception();
		}
	}

}
