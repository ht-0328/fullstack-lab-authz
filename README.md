# fullstack-lab-authz

認可 API（Kotlin + Spring Boot）。[fullstack-lab](https://github.com/ht-0328/fullstack-lab) の一部で、BFF から「この主体はこの操作をしてよいか」を問い合わせられる内部サービスです。全体の構成は fullstack-lab の README を参照してください。

## 契約が正

[openapi/authz.yaml](openapi/authz.yaml) が契約です。次を生成し、生成コードは git で管理します（手で直さず、仕様を直して生成し直します）。

| 生成物 | 使う側 |
|---|---|
| コントローラーのインターフェースと DTO（`src/generated`） | [DecisionController](src/main/kotlin/com/example/fullstacklab/authz/DecisionController.kt) が `DecisionsApi` を実装する |
| クライアント `com.example.fullstacklab:authz-client`（[client/](client/)、生成物は `client/src/generated`） | BFF。Maven リポジトリ経由でバージョン指定で依存する |

仕様を変えたら `./gradlew generate` で生成し直し、生成物も同じコミットに含めます（ホストに JDK が無ければ fullstack-lab の `./scripts/generate.sh`）。`docker build --target test .` はテストの前に生成し直して差分がないことを確かめるので（`check-generated` ステージ）、生成し忘れや手で直した生成コードは CI で落ちます。

契約を変えるときは `openapi/authz.yaml` の `info.version` と [gradle.properties](gradle.properties) の `version` を同時に上げます。

## 判定ルール

- 社員：ロール（IdP のグループ名）に許可された操作なら、所有者に関係なく許可します。ロールと操作の対応は [application.yml](src/main/resources/application.yml) にあります
- 一般利用者：許可された操作のうち、自分が所有するリソースに対してだけ許可します
- どれにも当てはまらなければ拒否します（既定で拒否）

ロジックは [Policy.kt](src/main/kotlin/com/example/fullstacklab/authz/Policy.kt)、テストは [PolicyTest.kt](src/test/kotlin/com/example/fullstacklab/authz/PolicyTest.kt) にあります。

## テスト

| 種類 | 実行 | 確認すること |
|---|---|---|
| 単体テスト | `docker build --target test .` | `PolicyTest`：所有者・ロール・既定で拒否 |
| API テスト（Karate） | `docker compose -f compose.test.yaml build && docker compose -f compose.test.yaml run --rm karate` | [karate/features/decisions.feature](karate/features/decisions.feature)：契約どおりの判定（利用者の種別 × 操作 × 所有者の組み合わせ、契約にない入力は 400） |

Karate は [compose.test.yaml](compose.test.yaml) でこのサービスだけを立てて実行します。実行環境は [karate/Dockerfile](karate/Dockerfile) で、ホストに JDK は不要です。終わったら `docker compose -f compose.test.yaml down -v` で片付けます。

## コマンド

```bash
docker build --target test .      # 生成 + 単体テスト
./gradlew :client:publish         # クライアントを publish（環境変数は api リポジトリと同じ）
```
