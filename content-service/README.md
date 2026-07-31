# SubIT Website Content Service

独立的 Ktor 内容服务。它负责 SSubitO 登录、官网编辑权限、草稿、发布版本、定时发布、回滚和图片上传；普通访客直接从 Nginx 读取已发布 JSON 和图片。

## 权限

- `NONE`：无后台权限；不存在于权限表的用户默认也是 `NONE`。
- `EDITOR`：编辑、上传、保存草稿、立即/定时发布、取消计划及回滚。
- `ADMIN`：拥有 `EDITOR` 的全部能力，并可管理其他用户的角色。

## 本地构建

服务生成 Java 17 字节码：

```bash
./gradlew test shadowJar
```

产物位于 `build/libs/SubITWebsiteContentService.jar`。

## 服务器部署

完整步骤见 [`DEPLOYMENT.md`](DEPLOYMENT.md)。服务启动时会自动创建以 `website_cms_` 开头的数据表。
