Feature: 認可 API（契約 openapi/authz.yaml に沿った判定）

  Background:
    * url baseUrl
    * path 'authz/v1/decisions'

  Scenario Outline: <who> が <action>（対象の所有者: <owner>）→ allowed=<allowed>
    Given request { subject: { id: '<id>', userType: '<type>', roles: <roles> }, action: '<action>', resource: { type: 'task', ownerId: <owner> } }
    When method post
    Then status 200
    And match response == { allowed: <allowed>, reason: '#string' }

    Examples:
      | who                  | id       | type     | roles           | action      | owner      | allowed |
      | 一般利用者（本人）   | alice-id | CUSTOMER | []              | task:read   | 'alice-id' | true    |
      | 一般利用者（他人）   | alice-id | CUSTOMER | []              | task:read   | 'bob-id'   | false   |
      | 一般利用者（全件）   | alice-id | CUSTOMER | []              | task:list   | null       | false   |
      | 一般利用者（登録）   | alice-id | CUSTOMER | []              | task:create | 'alice-id' | true    |
      | 一般利用者（削除）   | alice-id | CUSTOMER | []              | task:delete | 'alice-id' | false   |
      | 一般利用者（完了・本人） | alice-id | CUSTOMER | []          | task:complete | 'alice-id' | true  |
      | 一般利用者（完了・他人） | alice-id | CUSTOMER | []          | task:complete | 'bob-id'   | false |
      | task-viewer の社員   | carol-id | STAFF    | ['task-viewer'] | task:list   | null       | true    |
      | task-viewer の社員   | carol-id | STAFF    | ['task-viewer'] | task:read   | 'alice-id' | true    |
      | task-viewer の社員   | carol-id | STAFF    | ['task-viewer'] | task:create | 'carol-id' | false   |
      | task-admin の社員    | dave-id  | STAFF    | ['task-admin']  | task:create | 'dave-id'  | true    |
      | task-viewer の社員（完了） | carol-id | STAFF | ['task-viewer'] | task:complete | 'alice-id' | false |
      | task-admin の社員（完了）  | dave-id  | STAFF | ['task-admin']  | task:complete | 'alice-id' | true  |
      | 未知のロールの社員   | x-id     | STAFF    | ['unknown']     | task:list   | null       | false   |
      | ロールのない社員     | x-id     | STAFF    | []              | task:list   | null       | false   |

  Scenario Outline: アカウント操作: <who> が account:change-password（対象の所有者: <owner>）→ allowed=<allowed>
    Given request { subject: { id: '<id>', userType: '<type>', roles: <roles> }, action: 'account:change-password', resource: { type: 'account', ownerId: '<owner>' } }
    When method post
    Then status 200
    And match response.allowed == <allowed>

    Examples:
      | who                          | id       | type     | roles          | owner    | allowed |
      | 一般利用者（本人）           | alice-id | CUSTOMER | []             | alice-id | true    |
      | 一般利用者（他人）           | alice-id | CUSTOMER | []             | bob-id   | false   |
      | 社員（Okta 側で管理するため） | dave-id  | STAFF    | ['task-admin'] | dave-id  | false   |

  Scenario: 契約にない userType は 400
    Given request { subject: { id: 'x', userType: 'ROBOT' }, action: 'task:read', resource: { type: 'task' } }
    When method post
    Then status 400

  Scenario: 必須項目（subject）が無ければ 400
    Given request { action: 'task:read', resource: { type: 'task' } }
    When method post
    Then status 400
