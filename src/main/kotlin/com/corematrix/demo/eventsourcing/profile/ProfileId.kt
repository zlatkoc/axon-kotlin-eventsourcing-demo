package com.corematrix.demo.eventsourcing.profile

import java.util.*
import kotlin.math.absoluteValue

data class ProfileId(
        val id: String = Random().nextInt().absoluteValue.toString()) {
    override fun toString() =  id
}
