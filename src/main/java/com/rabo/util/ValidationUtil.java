package com.rabo.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rabo.controller.RaboStatementProcessController;
import com.rabo.model.Transaction;

/**
 * Utility Class for validations
 * @author Venkatadri Bungatavula
 * Initial version: Aug 12, 2020
 * 
 */

public class ValidationUtil {
	private static final Logger logger = LogManager.getLogger(ValidationUtil.class);

	/**
	 * Method to check duplication transaction reference.
	 * If duplicate reference found it returns duplication transactions
	 * 
	 * @param referenceList
	 * @return Set<Transaction>
	 */
	public Set<Transaction> checkForDuplicateTransaction(Transaction [] transactions) {
		logger.info("ValidationUtil::checkForDuplicateTransaction - start");
		Stream<Transaction> transactionStream = Arrays.stream(transactions);
		Set<Long> referenceSet = new HashSet<>();
		return transactionStream.filter(transaction -> !referenceSet.add(transaction.getReference())).collect(Collectors.toSet());
	}
	
	/**
	 * Method to validate End Balance in transactions
	 * Start Balance +/- Mutation = End Balance
	 * If End Balance does not matches above condition returns Invalid Transactions
	 * 
	 * @param transactions
	 * @return Set<Transaction>
	 */
	public Set<Transaction> checkForInValidEndBalance(Transaction [] transactions) {
		logger.info("ValidationUtil::checkForInValidEndBalance - start");
		Stream<Transaction> transactionStream = Arrays.stream(transactions);
		DecimalFormat df = new DecimalFormat("#.##");
		return transactionStream.filter(transaction -> !(transaction.getEndBalance() == Double.valueOf(df.format(transaction.getStartBalance()+transaction.getMutation())))).collect(Collectors.toSet());
	}
	
	public boolean validateBadRequest(Transaction [] transactions) {
		logger.info("ValidationUtil::validateBadRequest - start");
		Stream<Transaction> transactionStream = Arrays.stream(transactions);
		return transactionStream.anyMatch(transaction -> (transaction.getReference() == 0
				|| transaction.getAccountNumber() == null || transaction.getAccountNumber().isEmpty()
				));
	}

}
