package com.corematrix.demo.eventsourcing

import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Configuration {
    @Bean
    fun eventStore(): EventStorageEngine {
        return InMemoryEventStorageEngine()
    }
}