package com.corematrix.demo.eventsourcing.tenant

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.eventhandling.EventHandler
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
open class Tenant {

    enum class Status {
        ACTIVE, SUSPENDED
    }

    @AggregateIdentifier private var tenantId: TenantId? = null
    private var name: String? = null
    private var status: Status? = null

    constructor()

    @CommandHandler
    constructor(cmd: CreateTenantCommand): this() {
        apply(TenantCreated(cmd.id, cmd.name))
    }

    @CommandHandler
    fun handle(cmd: SuspendTenantCommand) {
        if (status == Status.ACTIVE) {
            apply(TenantSuspended(cmd.id))
        }
    }

    @CommandHandler
    fun handle(cmd: ActivateTenantCommand) {
        if (status == Status.SUSPENDED) {
            apply(TenantActivated(cmd.id))
        }
    }

    @EventHandler
    fun on(evt: TenantCreated) {
        tenantId = evt.id
        name = evt.name
        status = Status.ACTIVE
    }

    @EventHandler
    fun on(evt: TenantSuspended) {
        status = Status.SUSPENDED
    }

    @EventHandler
    fun on(evt: TenantActivated) {
        status = Status.ACTIVE
    }
}