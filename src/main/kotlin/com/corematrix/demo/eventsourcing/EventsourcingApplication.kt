package com.corematrix.demo.eventsourcing

import com.corematrix.demo.eventsourcing.tenant.CreateTenantCommand
import com.corematrix.demo.eventsourcing.tenant.SuspendTenantCommand
import com.corematrix.demo.eventsourcing.tenant.TenantId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = [
    "com.corematrix.demo.eventsourcing",
    "com.corematrix.demo.eventsourcing.tenant",
    "com.corematrix.demo.eventsourcing.logging"
])
class EventsourcingApplication

fun main(args: Array<String>) {
    val ctx = runApplication<EventsourcingApplication>(*args)

    // test case

    val commandGateway = ctx.getBean("commandGateway") as CommandGateway
    val tenantId = TenantId.new()
    val tenantName = "some tenant"

    commandGateway.sendAndWait<CreateTenantCommand>(
        CreateTenantCommand(tenantId, tenantName))
    commandGateway.sendAndWait<SuspendTenantCommand>(
        SuspendTenantCommand(tenantId))

    // check stdout
}
