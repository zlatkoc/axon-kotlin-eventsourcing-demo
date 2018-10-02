package com.corematrix.demo.eventsourcing.tenant

import org.axonframework.commandhandling.TargetAggregateIdentifier

data class CreateTenantCommand(
    @TargetAggregateIdentifier val id: TenantId,
    val name: String,
    val status: TenantStatus)

data class SuspendTenantCommand(
    @TargetAggregateIdentifier val id: TenantId)

data class ActivateTenantCommand(
    @TargetAggregateIdentifier val id: TenantId)
