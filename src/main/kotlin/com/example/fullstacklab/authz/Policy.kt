package com.example.fullstacklab.authz

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 社員ロールと許可する操作の対応。ロール名は IdP（Okta のグループ）側で管理し、
 * 「そのロールで何ができるか」はこの認可 API 側で管理する、という分担にしている。
 */
@ConfigurationProperties("authz")
data class PolicyProperties(
    val staffRolePermissions: Map<String, Set<String>> = emptyMap(),
    val customerActions: Set<String> = emptySet(),
)

/**
 * 認可ポリシー。
 * - 社員: ロールに付与された操作なら、所有者に関係なく許可する（RBAC）
 * - 一般利用者: 許可された操作のうち、自分が所有するリソースに対してだけ許可する（所有者ベース）
 * どれにも当てはまらなければ拒否する（既定で拒否）。
 */
@Component
class Policy(private val props: PolicyProperties) {

    fun decide(request: DecisionRequest): DecisionResponse {
        val (subject, action, resource) = request
        return when (subject.userType) {
            UserType.STAFF -> {
                val granted = subject.roles.flatMap { props.staffRolePermissions[it].orEmpty() }.toSet()
                if (action in granted) allow("staff role grants $action")
                else deny("no staff role grants $action (roles=${subject.roles})")
            }
            UserType.CUSTOMER -> when {
                action !in props.customerActions -> deny("customers cannot $action")
                action == "task:create" -> allow("customers can create their own tasks")
                resource.ownerId == subject.id -> allow("customer owns the resource")
                else -> deny("customer can only $action own resources")
            }
        }
    }

    private fun allow(reason: String) = DecisionResponse(true, reason)
    private fun deny(reason: String) = DecisionResponse(false, reason)
}
