package com.hellosign.javademo.service;

import com.hellosign.sdk.resource.support.Signature;
import org.springframework.stereotype.Service;
import com.hellosign.sdk.HelloSignClient;
import com.hellosign.sdk.HelloSignException;
import com.hellosign.sdk.resource.EmbeddedRequest;
import com.hellosign.sdk.resource.EmbeddedResponse;
import com.hellosign.sdk.resource.SignatureRequest;
import com.hellosign.sdk.resource.TemplateSignatureRequest;

@Service
public class HelloSignService {

	public String sendEmbeddedSignatureRequest() throws HelloSignException {

		TemplateSignatureRequest request = new TemplateSignatureRequest();
		request.setTemplateId("10a9cf98b0e0eba0ce276a9ab026abb19d59e6ab");
		request.setSubject("Please review and Sign");
		request.setMessage("Please review and Sign");
		request.setSigner("Client", "testmailbox2021@gmail.com","Test User");
		request.setTestMode(true); // Only if account is not an Enterprise account.

		String clientId = "5e36f64b9f82a48cb6db34fb3483f7dc";
		EmbeddedRequest embedReq = new EmbeddedRequest(clientId, request);
		//Create a HelloSign Client
		HelloSignClient helloSignclient = new HelloSignClient(
				"a2faee0f49b83377213dfe2cb47b3eee84c40ad4cf7cbf9e43ca81b9876d0941");
		//create new Embedded Signature Request
		SignatureRequest newRequest = (SignatureRequest) helloSignclient.createEmbeddedRequest(embedReq);

		Signature signature = newRequest.getSignature("testmailbox2021@gmail.com","Test User");
		String signatureId =  signature.getId(); //"<SIGNATURE_ID_YOU_GET_FROM_EMBEDDED_SIGNING_REQ_RESPONSE>";
		EmbeddedResponse response = helloSignclient.getEmbeddedSignUrl(signatureId);
		String url = response.getSignUrl();

		return url;
	}

}
