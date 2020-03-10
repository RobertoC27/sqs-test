package gt.com.celera;

import java.util.HashMap;
import java.util.List;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SetQueueAttributesRequest;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        SqsClient sqsClient = SqsClient.builder().build();
        System.out.println("Hello World!");
        String prefix = "que";
        ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder().build();
        ListQueuesResponse listQueuesResponse = sqsClient.listQueues(listQueuesRequest);
        for (String url : listQueuesResponse.queueUrls()) {
            System.out.println(url);
        }

        // Enable long polling when creating a queue
        HashMap<QueueAttributeName, String> attributes = new HashMap<QueueAttributeName, String>();
        attributes.put(QueueAttributeName.RECEIVE_MESSAGE_WAIT_TIME_SECONDS, "20");

        String queueUrl = "https://sqs.us-east-2.amazonaws.com/374384368868/hsm-poc.fifo";

        // Enable long polling on an existing queue
        System.out.println("\nReceive messages");
        SetQueueAttributesRequest setAttrsRequest = SetQueueAttributesRequest.builder().queueUrl(queueUrl)
                .attributes(attributes).build();

        sqsClient.setQueueAttributes(setAttrsRequest);

        // Enable long polling on a message receipt
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl).waitTimeSeconds(20)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();
        System.out.println("now gonna print messages");
        for (Message m : messages) {
            System.out.println("\n" +m.body());
        }

    }
}
