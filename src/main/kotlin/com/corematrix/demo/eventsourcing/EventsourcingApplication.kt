package com.corematrix.demo.eventsourcing

import com.corematrix.demo.eventsourcing.tenant.ActivateTenantCommand
import com.corematrix.demo.eventsourcing.tenant.CreateTenantCommand
import com.corematrix.demo.eventsourcing.tenant.SuspendTenantCommand
import com.corematrix.demo.eventsourcing.tenant.TenantId
import com.corematrix.demo.eventsourcing.tenant.TenantStatus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
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
class EventsourcingApplication

fun main(args: Array<String>) {
    val ctx = runApplication<EventsourcingApplication>(*args)

    // test case

    val commandGateway = ctx.getBean("commandGateway") as CommandGateway
    val tenantId = TenantId.new()
    val tenantName = "some tenant"
    val tenantStatus = TenantStatus.ACTIVE

    commandGateway.sendAndWait<CreateTenantCommand>(
        CreateTenantCommand(tenantId, tenantName, tenantStatus))
    commandGateway.sendAndWait<SuspendTenantCommand>(
        SuspendTenantCommand(tenantId))
    commandGateway.sendAndWait<SuspendTenantCommand>(
        SuspendTenantCommand(tenantId))
    commandGateway.sendAndWait<ActivateTenantCommand>(
        ActivateTenantCommand(tenantId))

    // check stdout, expected something like:
    // event received: TenantCreated(id=2020015930, name=some tenant)
    // event received: TenantSuspended(id=2020015930)
    // event received: TenantActivated(id=2020015930)

    // list events for previously created tenant aggregate
    val eventStore = ctx.getBean("eventStorageEngine") as EventStorageEngine
    val events = eventStore.readEvents(tenantId.toString())

    for (event in events) {
        println(event)
    }
}
