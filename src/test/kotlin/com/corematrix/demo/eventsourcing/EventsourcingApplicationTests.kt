package com.corematrix.demo.eventsourcing

import com.corematrix.demo.eventsourcing.tenant.Tenant
import com.corematrix.demo.eventsourcing.tenant.TenantCreated
import com.corematrix.demo.eventsourcing.tenant.TenantId
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class EventsourcingApplicationTests {

	private var fixture : FixtureConfiguration<Tenant>? = null

	@Before
	fun setup() {
		fixture = AggregateTestFixture<Tenant>(Tenant::class.java)
	}

	@Test
	fun firstTest() {
		val tenantId = TenantId.new()
		val name = "some name"

		fixture!!.given()
			.`when`(TenantCreated(tenantId, name))
			.expectSuccessfulHandlerExecution()
			.expectEvents(TenantCreated(tenantId, name))
	}

    @Test
    fun secondTest() {
        val tenantId = TenantId.new()
        val name = "some name"

        val fixture = AggregateTestFixture<Tenant>(Tenant::class.java)

        fixture.given()
            .`when`(TenantCreated(tenantId, name))
            .expectSuccessfulHandlerExecution()
            .expectEvents(TenantCreated(tenantId, name))
    }
}
