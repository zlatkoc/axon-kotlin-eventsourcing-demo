package com.corematrix.demo.eventsourcing.tenant

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.util.Assert

@Aggregate
class Tenant {

    @AggregateIdentifier
    private var tenantId: TenantId? = null
    private var name: String? = null
    private var status: TenantStatus? = null

    constructor()

    @CommandHandler
    constructor(cmd: CreateTenantCommand) : this() {
        Assert.hasLength(cmd.name) { "Empty tenant name" }

        apply(TenantCreated(cmd.id, cmd.name, cmd.status))
    }

    @CommandHandler
    fun handle(cmd: SuspendTenantCommand) {
        if (status == TenantStatus.ACTIVE) {
            apply(TenantSuspended(cmd.id))
        }
    }

    @CommandHandler
    fun handle(cmd: ActivateTenantCommand) {
        if (status == TenantStatus.SUSPENDED) {
            apply(TenantActivated(cmd.id))
        }
    }

    @EventSourcingHandler
    fun on(evt: TenantCreated) {
        tenantId = evt.id
        name = evt.name
        status = evt.status
    }

    @EventSourcingHandler
    fun on(evt: TenantSuspended) {
        status = TenantStatus.SUSPENDED
    }

    @EventSourcingHandler
    fun on(evt: TenantActivated) {
        status = TenantStatus.ACTIVE
    }
}