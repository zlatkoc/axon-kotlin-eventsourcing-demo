package com.corematrix.demo.eventsourcing.logging

import com.corematrix.demo.eventsourcing.tenant.TenantActivated
import com.corematrix.demo.eventsourcing.tenant.TenantCreated
import com.corematrix.demo.eventsourcing.tenant.TenantSuspended
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
open class Logger {

    @EventHandler
    fun handle(cmd: TenantCreated) {
        println("event received: " + cmd.toString())
    }

    @EventHandler
    fun handle(cmd: TenantSuspended) {
        println("event received: " + cmd.toString())
    }

    @EventHandler
    fun handle(cmd: TenantActivated) {
        println("event received: " + cmd.toString())
    }
}