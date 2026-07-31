# SubIT Website

SubIT 官网，使用 Vue 3、TypeScript 和 Vite 构建。

## 本地运行

```bash
yarn
yarn dev
```

生产构建检查：

```bash
yarn build
```

## 不改代码更新周期性内容

部署内容服务后，设计人员打开 `/content-editor`，使用 SSubitO 登录即可在线编辑、上传图片、预览、立即或定时发布，并从历史版本回滚。发布只会生成新的版本化 JSON 和图片，不会重新构建官网。

权限分为 `None`、`Editor` 和 `Admin`。Editor 可以编辑和发布；Admin 还可以通过编辑器管理成员权限。

独立 Ktor 服务的代码、环境配置、Nginx 配置和服务器安装步骤位于 [`content-service`](content-service/README.md)。`public/content/site-content.json` 只作为首次启动与内容服务不可用时的内置降级内容。

## 内容结构

- `projects`：首页项目轮播
- `achievements`：项目成就页卡片
- `join`：招新说明与社员卡片
- `submore`：铁锅、照片、词云与语录

内容包目前使用 `version: 1`。若以后调整字段结构，应同步更新 `src/content/siteContent.ts` 中的类型与校验逻辑。
