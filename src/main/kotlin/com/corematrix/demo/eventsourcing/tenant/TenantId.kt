package com.corematrix.demo.eventsourcing.tenant

import java.util.Random
import kotlin.math.absoluteValue

class TenantId(val id: String) {
    companion object {
        fun new(): TenantId = TenantId(Random().nextInt().absoluteValue.toString())
    }

    override fun toString(): String {
        return id
    }
}