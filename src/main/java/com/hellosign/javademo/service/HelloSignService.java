package com.hellosign.javademo.service;

import org.springframework.stereotype.Service;
import com.hellosign.sdk.HelloSignClient;
import com.hellosign.sdk.HelloSignException;
import com.hellosign.sdk.resource.EmbeddedRequest;
import com.hellosign.sdk.resource.EmbeddedResponse;
import com.hellosign.sdk.resource.SignatureRequest;
import com.hellosign.sdk.resource.TemplateSignatureRequest;

@Service
public class HelloSignService {

	public String send() throws HelloSignException {
		HelloSignClient client = new HelloSignClient(
				"<YOUR_API_TOKE_HERE>>");
		String signatureId = "<SIGNATURE_ID_YOU_GET_FROM_EMBEDDED_SIGNING_REQ_RESPONSE>";
		EmbeddedResponse response = client.getEmbeddedSignUrl(signatureId);
		String url = response.getSignUrl();

		return url;
	}

	public String createEmbeddedSigningRequest() throws HelloSignException {

		TemplateSignatureRequest request = new TemplateSignatureRequest();
		request.setTemplateId("<TEMPLATE_ID_YOU_CREATED_FROM_THE HELLOSIGN_UI>");
		request.setSubject("Example Email Subject");
		request.setMessage("Example Email Message");
		request.setSigner("Client", "<EMAIL_RECIPIENT>", "<RECIPIENT_NAME>");
		request.setTestMode(true); // Only if account is not an Enterprise account.

		String clientId = "<CLIENT_ID_FROM_CLIENT_APP_CREATED_IN_HELLOSIGN>";
		EmbeddedRequest embedReq = new EmbeddedRequest(clientId, request);
		HelloSignClient helloSignclient = new HelloSignClient(
				"<YOUR_API_TOKEN>");

		SignatureRequest newRequest = (SignatureRequest) helloSignclient.createEmbeddedRequest(embedReq);

		return newRequest.toString();
	}

}
