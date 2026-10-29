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
 * 
 * @param allowed 
 * @param reason 判定理由（ログと学習用。利用者には見せない）
 */
data class DecisionResponse(

    @param:JsonProperty("allowed", required = true)
    @get:JsonProperty("allowed", required = true) val allowed: kotlin.Boolean,

    @param:JsonProperty("reason", required = true)
    @get:JsonProperty("reason", required = true) val reason: kotlin.String
) {

}

