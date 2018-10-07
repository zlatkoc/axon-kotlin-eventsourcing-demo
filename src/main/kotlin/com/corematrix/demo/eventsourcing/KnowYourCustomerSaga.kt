package com.corematrix.demo.eventsourcing

import com.corematrix.demo.eventsourcing.profile.ProfileId
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventhandling.saga.EndSaga
import org.axonframework.eventhandling.saga.SagaEventHandler
import org.axonframework.eventhandling.saga.SagaLifecycle
import org.axonframework.eventhandling.saga.StartSaga
import org.axonframework.eventhandling.scheduling.EventScheduler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.eventhandling.scheduling.java.SimpleEventSchedulerFactoryBean
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.spring.stereotype.Saga
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import java.io.Serializable

@Saga
class KnowYourCustomerSaga {

    @Autowired
    @Transient
    private lateinit var commands: CommandGateway

    @Autowired
    @Transient
    private lateinit var scheduler: EventScheduler

    private companion object {
        val LOG: Logger = LoggerFactory.getLogger(KnowYourCustomerSaga::class.java)
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "msisdn")
    fun on(e: ProfileCreated) {
        LOG.info("New profile created")

        commands.send<SendSmsCommand>(SendSmsCommand(e.profileId, e.msisdn))
    }

    @SagaEventHandler(associationProperty = "msisdn")
    fun on(e: SmsSent) {
        LOG.info("Sms has been sent")

        // start timer - must receive response within n sec
    }

    @SagaEventHandler(associationProperty = "msisdn")
    fun on(e: VerificationCodeInvalid) {

    }

    @EndSaga
    @SagaEventHandler(associationProperty = "msisdn")
    fun on(e: VerificationCodeAccepted) {
    }
}

data class SendSmsCommand(@TargetAggregateIdentifier val profileId: ProfileId, val msisdn: String) : Serializable

data class SmsSent(val profileId: ProfileId, val msisdn: String)

data class VerificationCodeAccepted(
        val profileId: ProfileId, val msisdn: String, val code: String)

data class VerificationCodeInvalid(val profileId: ProfileId, val msisdn: String)


@Aggregate
class Profile {
    @AggregateIdentifier
    private lateinit var profileId: ProfileId
    private lateinit var msisdn: String
    private var status: ProfileStatus = ProfileStatus.UNKNOWN

    enum class ProfileStatus {
        UNKNOWN, VERIFIED
    }

    private companion object {
        val LOG : Logger = LoggerFactory.getLogger(Profile::class.java)
    }

    constructor()

    @CommandHandler
    constructor(cmd: CreateProfileCommand) : this() {
        Assert.hasLength(cmd.msisdn) { "MSISDN missing" }

        AggregateLifecycle.apply(ProfileCreated(cmd.profileId, cmd.msisdn))
    }

    @CommandHandler
    fun handle(cmd: SendSmsCommand) {
        LOG.info("Sending sms...")

        AggregateLifecycle.apply(SmsSent(cmd.profileId, cmd.msisdn))
    }

    @EventSourcingHandler
    fun on(e: ProfileCreated) {
        profileId = e.profileId
        msisdn = e.msisdn
    }

    @EventSourcingHandler
    fun on(e: ProfileVerified) {
        status = ProfileStatus.VERIFIED
    }
}

data class CreateProfileCommand(@TargetAggregateIdentifier val profileId: ProfileId, val msisdn: String) : Serializable

data class ProfileCreated(val profileId: ProfileId, val msisdn: String)

data class ProfileVerified(val profileId: ProfileId)