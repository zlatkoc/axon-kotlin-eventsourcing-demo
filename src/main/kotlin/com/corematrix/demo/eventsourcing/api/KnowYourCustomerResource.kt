package com.corematrix.demo.eventsourcing.api

import com.corematrix.demo.eventsourcing.CreateProfileCommand
import com.corematrix.demo.eventsourcing.profile.ProfileId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kyc")
class KnowYourCustomerResource(private val commands: CommandGateway) {
    @PostMapping
    fun signUp(@RequestBody msisdn: String) {
        Assert.hasLength(msisdn) { "MSISDN missing" }
        commands.send<CreateProfileCommand>(CreateProfileCommand(ProfileId(), msisdn))
    }
}
