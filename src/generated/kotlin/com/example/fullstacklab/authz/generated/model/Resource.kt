package com.example.fullstacklab.authz.generated.model

import java.util.Objects
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
 * 操作対象。ownerId が null のときは「特定の所有者に限らない（全件）」を意味する
 * @param type 
 * @param ownerId 
 */
data class Resource(

    @param:JsonProperty("type", required = true)
    @get:JsonProperty("type", required = true) val type: kotlin.String,

    @param:JsonProperty("ownerId")
    @get:JsonProperty("ownerId") val ownerId: kotlin.String? = null
) {

}

