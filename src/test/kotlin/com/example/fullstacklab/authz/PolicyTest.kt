package com.example.fullstacklab.authz

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PolicyTest {
    private val policy = Policy(
        PolicyProperties(
            staffRolePermissions = mapOf(
                "task-viewer" to setOf("task:list", "task:read"),
                "task-admin" to setOf("task:list", "task:read", "task:create"),
            ),
            customerActions = setOf("task:list", "task:read", "task:create"),
        ),
    )

    private val alice = Subject("alice-id", UserType.CUSTOMER)
    private fun decide(subject: Subject, action: String, ownerId: String? = null) =
        policy.decide(DecisionRequest(subject, action, Resource("task", ownerId))).allowed

    @Test
    fun `customer can access only own tasks`() {
        assertTrue(decide(alice, "task:read", ownerId = "alice-id"))
        assertFalse(decide(alice, "task:read", ownerId = "bob-id"))
        assertTrue(decide(alice, "task:list", ownerId = "alice-id"))
        assertFalse(decide(alice, "task:list", ownerId = null), "customer must not list all tasks")
        assertTrue(decide(alice, "task:create"))
    }

    @Test
    fun `staff permissions come from roles`() {
        val viewer = Subject("carol-id", UserType.STAFF, setOf("task-viewer"))
        val admin = Subject("dave-id", UserType.STAFF, setOf("task-admin"))
        assertTrue(decide(viewer, "task:list"))
        assertTrue(decide(viewer, "task:read", ownerId = "alice-id"))
        assertFalse(decide(viewer, "task:create"))
        assertTrue(decide(admin, "task:create"))
    }

    @Test
    fun `unknown actions and roles are denied`() {
        assertFalse(decide(Subject("x", UserType.STAFF, setOf("unknown")), "task:list"))
        assertFalse(decide(alice, "task:delete", ownerId = "alice-id"))
    }
}
