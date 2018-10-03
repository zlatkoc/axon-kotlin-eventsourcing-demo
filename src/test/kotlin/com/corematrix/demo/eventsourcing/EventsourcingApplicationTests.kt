package com.corematrix.demo.eventsourcing

import com.corematrix.demo.eventsourcing.tenant.CreateTenantCommand
import com.corematrix.demo.eventsourcing.tenant.Tenant
import com.corematrix.demo.eventsourcing.tenant.TenantCreated
import com.corematrix.demo.eventsourcing.tenant.TenantId
import com.corematrix.demo.eventsourcing.tenant.TenantStatus
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.Before
import org.junit.Test

class EventsourcingApplicationTests {

    private lateinit var fixture: FixtureConfiguration<Tenant>

    @Before
    fun setup() {
        fixture = AggregateTestFixture(Tenant::class.java)
    }

    @Test
    fun firstTest() {
        val tenantId = TenantId.new()
        val name = "some name"
        val status = TenantStatus.ACTIVE

        fixture.givenNoPriorActivity()
                .`when`(CreateTenantCommand(tenantId, name, status))
                .expectSuccessfulHandlerExecution()
                .expectEvents(TenantCreated(tenantId, name, status))
    }

    @Test
    fun secondTest() {
        val tenantId = TenantId.new()
        val name = "some name"
        val status = TenantStatus.ACTIVE

        val fixture = AggregateTestFixture(Tenant::class.java)

        fixture.givenNoPriorActivity()
                .`when`(CreateTenantCommand(tenantId, name, status))
                .expectSuccessfulHandlerExecution()
                .expectEvents(TenantCreated(tenantId, name, status))
    }
}
