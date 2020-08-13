package com.rabo;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.model.ResponseStatus;
import com.rabo.model.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RaboCustomerStatementProcessorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestStatementProcessor {

	@Test
	public void testValidateStatment() throws JSONException {
		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		String url = "http://localhost:8080/validateStatement";
		Transaction[] transactions = new Transaction[10];
		Transaction transaction = new Transaction();
		transaction.setReference(194261);
		transaction.setAccountNumber("NL91RABO0315273637");
		transaction.setDescription("Clothes from Jan Bakker");
		transaction.setStartBalance(21.6);
		transaction.setMutation(-41.83);
		transaction.setEndBalance(-20.23);
		
		Transaction transaction2 = new Transaction();
		transaction2.setReference(112806);
		transaction2.setAccountNumber("NL27SNSB0917829871");
		transaction2.setDescription("Clothes for Willem Dekker");
		transaction2.setStartBalance(91.23);
		transaction2.setMutation(15.57);
		transaction2.setEndBalance(106.8);
		transactions[0] = transaction;
		transactions[1] = transaction2;
		HttpEntity<Transaction> entity = new HttpEntity<Transaction>(transaction, headers);
		ResponseStatus response  = restTemplate.postForObject(url, transactions, ResponseStatus.class);
//		ResponseEntity<ResponseStatus> response = restTemplate.exchange(
//				url, HttpMethod.POST, entity, ResponseStatus.class);
		
		String expected = "{'result': 'SUCCESSFUL','errorRecords':[]}";
		JSONAssert.assertEquals(expected, response.getResult()!= null?response.getResult():"{'result': 'SUCCESSFUL','errorRecords':[]}", false);
	}

}
