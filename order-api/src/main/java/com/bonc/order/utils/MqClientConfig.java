package com.bonc.order.utils;  
  
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqClientConfig{
	 	@Autowired
	    ConnectionFactory connectionFactory;

	    final static String queueName = "orders-response";
//	 	final static String queueName = "channels-response";
	    @Bean
	    public Queue responseQueue() {
	        return new Queue(queueName);
	    }
	    @Bean
	    TopicExchange exchange() {
	        return new TopicExchange("syn");
	    }
		//交换器和路由绑定
	    @Bean
	    Binding binding(Queue queue, TopicExchange exchange) {
	        return BindingBuilder.bind(queue).to(exchange).with(queueName);
	    }
	    @Bean
	    public RabbitTemplate amqpTemplate() {
	        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	        rabbitTemplate.setReplyQueue(responseQueue());
	        rabbitTemplate.setReplyTimeout(20000);
	        return rabbitTemplate;
	    }
	    @Bean
	    public SimpleMessageListenerContainer clientMessageListenerContainer(){
	        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	        container.setConnectionFactory(connectionFactory);
	        container.setQueues(responseQueue());
	        container.setMessageListener(amqpTemplate());
	        return container;
	    }
}  