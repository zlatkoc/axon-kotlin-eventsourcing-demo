package com.corematrix.demo.eventsourcing.api

import com.corematrix.demo.eventsourcing.tenant.*
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/tenants")
class TenantResource(private val gateway: CommandGateway) {

    @PostMapping
    fun createTenant(@RequestBody name: String): CompletableFuture<CreateTenantCommand> =
            gateway.send<CreateTenantCommand>(
                    CreateTenantCommand(TenantId.new(), name, TenantStatus.ACTIVE))

    @GetMapping("{id}")
    fun getById(@PathVariable("id") id: String) {
    }

    @PutMapping("/{id}/activate")
    fun activate(@PathVariable("id") id: String) {
        gateway.send<ActivateTenantCommand>(ActivateTenantCommand(TenantId(id)))
    }

    @PutMapping("/{id}/suspend")
    fun suspend(@PathVariable("id") id: String) {
        gateway.send<SuspendTenantCommand>(SuspendTenantCommand(TenantId(id)))
    }

    @GetMapping
    fun list() {
    }
}
