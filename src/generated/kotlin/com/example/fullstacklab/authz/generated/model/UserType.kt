package com.example.fullstacklab.authz.generated.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import jakarta.validation.Valid

/**
 * CUSTOMER = 一般利用者（マイページ）、STAFF = 社員
 * Values: CUSTOMER,STAFF
 */
enum class UserType(@get:JsonValue val value: kotlin.String) {

    CUSTOMER("CUSTOMER"),
    STAFF("STAFF");

    companion object {
        @JvmStatic
        @JsonCreator
        fun forValue(value: kotlin.String): UserType {
            return values().firstOrNull{ it.value == value }
                ?: throw IllegalArgumentException("Unexpected value '$value' for enum 'UserType'")
        }
    }
}
