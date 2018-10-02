package com.corematrix.demo.eventsourcing.tenant

data class TenantCreated(val id: TenantId, val name: String, val status: TenantStatus)

data class TenantSuspended(val id: TenantId)

data class TenantActivated(val id: TenantId)
