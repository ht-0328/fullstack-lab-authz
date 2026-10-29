package com.example.fullstacklab.authz

enum class UserType { CUSTOMER, STAFF }

/** 認可を判定される主体。BFF が検証済み JWT から組み立てて渡す */
data class Subject(
    val id: String,
    val userType: UserType,
    val roles: Set<String> = emptySet(),
)

/** 操作対象。ownerId が null の場合は「特定の所有者に限らない（全件）」を意味する */
data class Resource(
    val type: String,
    val ownerId: String? = null,
)

data class DecisionRequest(
    val subject: Subject,
    val action: String,
    val resource: Resource,
)

data class DecisionResponse(
    val allowed: Boolean,
    val reason: String,
)
