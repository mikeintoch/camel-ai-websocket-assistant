import java.util.ArrayList;
import java.util.List;

import org.apache.camel.BindToRegistry;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import jakarta.inject.Inject;

public class Bindings extends RouteBuilder{

    @Inject
    SessionManager sessionManager;

    @Override
    public void configure() throws Exception {
        // Routes are loading in yaml files.
    }


    @BindToRegistry(lazy=true)
    public static Processor createChatMessage(){

        return new Processor() {
            public void process(Exchange exchange) throws Exception{

                String payload = exchange.getMessage().getBody(String.class);
                List<ChatMessage> messages = new ArrayList<>();

                String systemMessage = """
                    You are a intelligent chatbot assistant answering to customer request.
                    """;

                messages.add(new SystemMessage(systemMessage));


                messages.add(new UserMessage(payload));

                exchange.getIn().setBody(messages);
            }
        };
    }
}