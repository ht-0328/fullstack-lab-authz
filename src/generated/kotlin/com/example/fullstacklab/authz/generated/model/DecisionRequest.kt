package com.example.fullstacklab.authz.generated.model

import java.util.Objects
import com.example.fullstacklab.authz.generated.model.Resource
import com.example.fullstacklab.authz.generated.model.Subject
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
 * 
 * @param subject 
 * @param action 「対象:操作」の形式
 * @param resource 
 */
data class DecisionRequest(

    @field:Valid
    @param:JsonProperty("subject", required = true)
    @get:JsonProperty("subject", required = true) val subject: Subject,

    @param:JsonProperty("action", required = true)
    @get:JsonProperty("action", required = true) val action: kotlin.String,

    @field:Valid
    @param:JsonProperty("resource", required = true)
    @get:JsonProperty("resource", required = true) val resource: Resource
) {

}

