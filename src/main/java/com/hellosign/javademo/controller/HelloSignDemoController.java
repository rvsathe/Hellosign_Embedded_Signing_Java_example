package com.hellosign.javademo.controller;

import com.hellosign.sdk.resource.EmbeddedRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hellosign.javademo.service.HelloSignService;
import com.hellosign.sdk.HelloSignException;
import com.hellosign.sdk.resource.Event;

@RestController
@RequestMapping("/api")
public class HelloSignDemoController {
	private final Logger logger = LoggerFactory.getLogger(HelloSignDemoController.class);

	@Autowired
	HelloSignService helloSignService;

	@PostMapping("/hellosign/embeddedsign")
	public String embeddedSignatureRequest() throws HelloSignException {
		return helloSignService.sendEmbeddedSignatureRequest();
	}

	@PostMapping(value = "/hellosign/webhook")
	public String webhook(@RequestParam String json) throws HelloSignException, JSONException {
		JSONObject jsonObject = new JSONObject(json);
		Event event = new Event(jsonObject);

		boolean validRequest = event.isValid("a2faee0f49b83377213dfe2cb47b3eee84c40ad4cf7cbf9e43ca81b9876d0941");

		if (validRequest) {

			switch (event.getTypeString()) {
				case "signature_request_signed":
					logger.info("Signature Request Signed");
					break;
				case "signature_request_sent":
					logger.info("Signature Request Sent");
					break;
				default:
					break;
			}
		}
		return "Hello API Event Received";
	}

}
