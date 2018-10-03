package com.corematrix.demo.eventsourcing

import org.axonframework.spring.config.EnableAxon
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = [
    "com.corematrix.demo.eventsourcing",
    "com.corematrix.demo.eventsourcing.tenant",
    "com.corematrix.demo.eventsourcing.logging",
    "com.corematrix.demo.eventsourcing.api"
])
@EnableAxon
class EventsourcingApplication

fun main(args: Array<String>) {
    val ctx = runApplication<EventsourcingApplication>(*args)

    println("Registered beans:")
    ctx.beanDefinitionNames.sorted().forEach { println(it) }
}
