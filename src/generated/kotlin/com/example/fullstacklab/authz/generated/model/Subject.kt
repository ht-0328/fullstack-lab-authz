package com.example.fullstacklab.authz.generated.model

import java.util.Objects
import com.example.fullstacklab.authz.generated.model.UserType
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
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
 * 認可を判定される主体。BFF が検証済み JWT から組み立てる
 * @param id IdP の sub
 * @param userType 
 * @param roles 社員のロール（Okta のグループ名）。一般利用者は空
 */
data class Subject(

    @param:JsonProperty("id", required = true)
    @get:JsonProperty("id", required = true) val id: kotlin.String,

    @field:Valid
    @param:JsonProperty("userType", required = true)
    @get:JsonProperty("userType", required = true) val userType: UserType,

    @param:JsonProperty("roles")
    @get:JsonProperty("roles") val roles: kotlin.collections.List<kotlin.String>? = null
) {

}

