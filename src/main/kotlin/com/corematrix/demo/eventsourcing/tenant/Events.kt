package com.corematrix.demo.eventsourcing.tenant

data class TenantCreated(val id: TenantId, val name: String)

data class TenantSuspended(val id: TenantId)

data class TenantActivated(val id: TenantId)
