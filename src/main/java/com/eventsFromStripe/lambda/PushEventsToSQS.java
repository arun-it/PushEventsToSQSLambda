package com.eventsFromStripe.lambda;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;



/**
 * Hello world!
 *
 */
public class PushEventsToSQS implements RequestHandler<Object, Object>
{
	
	@Autowired QueueMessagingTemplate queueMessagingTemplate;
	
	final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
	


	public Object handleRequest(Object input, Context context) {
		// TODO Auto-generated method stub
		System.out.println(input.toString());
		System.out.println("step-1");
		//dev
	//	String queueUrl = "https://sqs.us-east-1.amazonaws.com/674024469050/stripe-wehooks-queue.fifo";
		String queueUrl = "https://sqs.us-east-1.amazonaws.com/674024469050/preprod-stripe-webhooks.fifo";	
	
		try {
			  SendMessageRequest send_msg_request = new SendMessageRequest()
		                .withQueueUrl(queueUrl)
		                .withMessageBody(input.toString())
		                .withMessageGroupId("test_the_webhooks_from_stripe");
		              //  .withDelaySeconds(5);
		        sqs.sendMessage(send_msg_request);

		        System.out.println("step-2 "+ "events pushed to queue");
		        return 200;
		        
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 300;
		}
		
	}
   
 
}
