package com.corematrix.demo.eventsourcing.tenant

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.eventhandling.EventHandler
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.context.annotation.Bean

@Aggregate
open class Tenant {

    @AggregateIdentifier private var tenantId: TenantId? = null
    private var name: String? = null
    private var status: TenantStatus? = null

    constructor()

    @CommandHandler
    constructor(cmd: CreateTenantCommand): this() {
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

    @EventHandler
    fun on(evt: TenantCreated) {
        tenantId = evt.id
        name = evt.name
        status = evt.status
    }

    @EventHandler
    fun on(evt: TenantSuspended) {
        status = TenantStatus.SUSPENDED
    }

    @EventHandler
    fun on(evt: TenantActivated) {
        status = TenantStatus.ACTIVE
    }
}