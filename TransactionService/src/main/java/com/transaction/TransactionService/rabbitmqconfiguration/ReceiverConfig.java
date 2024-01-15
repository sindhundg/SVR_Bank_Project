package com.transaction.TransactionService.rabbitmqconfiguration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceiverConfig {
    private String exchangeName = "TransactionExchange";
    private String queueName = "TransactionQueue";

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue registerQueue(){
        return new Queue(queueName,true);
    }
    @Bean
    Binding bindingQueuewithExc(DirectExchange exch, Queue qu){
        return BindingBuilder.bind(registerQueue()).to(exch).with("transactionKey");
    }

    @Bean
    public Jackson2JsonMessageConverter produceConversion(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory confactory){
        RabbitTemplate rt = new RabbitTemplate(confactory);
        rt.setMessageConverter(produceConversion());
        return rt;
    }
}
