package com.corematrix.demo.eventsourcing.logging

import com.corematrix.demo.eventsourcing.tenant.TenantActivated
import com.corematrix.demo.eventsourcing.tenant.TenantCreated
import com.corematrix.demo.eventsourcing.tenant.TenantSuspended
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class Logger {

    @EventHandler
    fun handle(e: TenantCreated) {
        println("event received: $e")
    }

    @EventHandler
    fun handle(e: TenantSuspended) {
        println("event received: $e")
    }

    @EventHandler
    fun handle(e: TenantActivated) {
        println("event received: $e")
    }
}