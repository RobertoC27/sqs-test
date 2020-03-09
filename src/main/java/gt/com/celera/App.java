package gt.com.celera;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        SqsClient sqsClient = SqsClient.builder()
                .build();
        System.out.println("Hello World!");
        String prefix = "que";
        ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder().build();
        ListQueuesResponse listQueuesResponse = sqsClient.listQueues(listQueuesRequest);
        for (String url : listQueuesResponse.queueUrls()) {
            System.out.println(url);
        }
    }
}
