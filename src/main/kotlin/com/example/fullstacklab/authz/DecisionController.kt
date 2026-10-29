package com.example.fullstacklab.authz

import com.example.fullstacklab.authz.generated.api.DecisionsApi
import com.example.fullstacklab.authz.generated.model.DecisionRequest as DecisionRequestDto
import com.example.fullstacklab.authz.generated.model.DecisionResponse as DecisionResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/** 認可 API。openapi/authz.yaml から生成した DecisionsApi を実装する。内部ネットワーク専用 */
@RestController
class DecisionController(private val policy: Policy) : DecisionsApi {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun decide(decisionRequest: DecisionRequestDto): ResponseEntity<DecisionResponseDto> {
        val request = decisionRequest.toDomain()
        val decision = policy.decide(request)
        log.info(
            "decision subject={} type={} action={} owner={} -> allowed={} ({})",
            request.subject.id, request.subject.userType, request.action, request.resource.ownerId,
            decision.allowed, decision.reason,
        )
        return ResponseEntity.ok(DecisionResponseDto(allowed = decision.allowed, reason = decision.reason))
    }

    private fun DecisionRequestDto.toDomain() = DecisionRequest(
        subject = Subject(
            id = subject.id,
            userType = UserType.valueOf(subject.userType.value),
            roles = subject.roles.orEmpty().toSet(),
        ),
        action = action,
        resource = Resource(type = resource.type, ownerId = resource.ownerId),
    )
}
