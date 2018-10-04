package com.corematrix.demo.eventsourcing

import com.corematrix.demo.eventsourcing.tenant.TenantCreated
import com.rabbitmq.client.Channel
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.serialization.Serializer
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
class Configuration {
    @Bean
    fun eventStorageEngine(): EventStorageEngine {
        return InMemoryEventStorageEngine()
    }

    @Bean
    fun exchange(): Exchange =
            ExchangeBuilder.fanoutExchange("TenantEvents").build()

    @Bean
    fun queue(): Queue = QueueBuilder.durable("TenantEvents").build()

    @Bean
    fun binding(): Binding =
            BindingBuilder.bind(queue()).to(exchange()).with("*").noargs()

    /**
     * Automatically creates all queueing element as declared above
     * (queue, exchange, binding)
     */
    @Autowired
    fun configure(admin: AmqpAdmin) {
        with(admin) {
            declareExchange(exchange())
            declareQueue(queue())
            declareBinding(binding())
        }
    }

    @Bean
    fun tenantEvents(serializer: Serializer): SpringAMQPMessageSource =
            object : SpringAMQPMessageSource(DefaultAMQPMessageConverter(serializer)) {
                @RabbitListener(queues = ["TenantEvents"])
                override fun onMessage(message: Message?, channel: Channel?) {
                    super.onMessage(message, channel)
                }
            }
}

@Component
class QueueEventHandler {
    @EventHandler
    fun on(e: TenantCreated) {
        println("Created tenant with id ${e.id}")
    }
}