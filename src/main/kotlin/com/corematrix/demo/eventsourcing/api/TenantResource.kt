package com.corematrix.demo.eventsourcing.api

import com.corematrix.demo.eventsourcing.tenant.*
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventsourcing.EventSourcingHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture
import javax.persistence.Entity
import javax.persistence.Id

@RestController
@RequestMapping("/tenants")
class TenantResource(private val gateway: CommandGateway,
                     private val tenantRepository: TenantQueryObjectRepository) {

    @PostMapping
    fun createTenant(@RequestBody name: String): CompletableFuture<CreateTenantCommand> =
            gateway.send<CreateTenantCommand>(
                    CreateTenantCommand(TenantId.new(), name, TenantStatus.ACTIVE))

    @GetMapping("{id}")
    fun getById(@PathVariable("id") id: String) = tenantRepository.getOne(id)

    @PutMapping("/{id}/activate")
    fun activate(@PathVariable("id") id: String) {
        gateway.send<ActivateTenantCommand>(ActivateTenantCommand(TenantId(id)))
    }

    @PutMapping("/{id}/suspend")
    fun suspend(@PathVariable("id") id: String) {
        gateway.send<SuspendTenantCommand>(SuspendTenantCommand(TenantId(id)))
    }

    @GetMapping
    fun list(): List<TenantQueryObject> {
        println("Listing all tenants")
        return tenantRepository.findAll().toList()
    }
}

@Component
class TenantQueryObjectUpdater(private val repository: TenantQueryObjectRepository) {
    @EventSourcingHandler
    fun on(e: TenantCreated) {
        repository.save(TenantQueryObject(e.id.id, e.name, e.status))
    }

    @EventSourcingHandler
    fun on(e: TenantSuspended) {
        val tenant = repository.getOne(e.id.id).copy(status = TenantStatus.SUSPENDED)
        repository.save(tenant)
    }

    @EventSourcingHandler
    fun on(e: TenantActivated) {
        val tenant = repository.getOne(e.id.id).copy(status = TenantStatus.ACTIVE)
        repository.save(tenant)
    }
}

@Entity
data class TenantQueryObject(
        @Id
        val tenantId: String,
        val name: String,
        val status: TenantStatus) {
}

@Repository
interface TenantQueryObjectRepository : JpaRepository<TenantQueryObject, String>
