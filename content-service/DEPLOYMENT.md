# 部署说明

这套配置依据现有 SSubitO 代码确定的运行环境编写：Ktor/JVM、PostgreSQL、Nginx，以及位于 `https://ssubito.subit.org.cn` 的 SSO 前端。以下命令需要由拥有服务器权限的维护者执行。

## 1. 在 SSubitO 注册服务

用 SSubitO 管理界面创建“SubIT 官网内容后台”服务：

- 未授权权限：`NONE`
- 已授权权限：`BASIC`
- 取消授权后的权限：`NONE`

服务审核通过后记录服务 ID，并由服务所有者获取 Service Token。Service Token 只能写入服务器环境文件，禁止进入 Git 或浏览器代码。

## 2. 创建数据库

```sql
CREATE USER subit_website_content WITH PASSWORD '替换为随机密码';
CREATE DATABASE subit_website_content OWNER subit_website_content;
```

应用第一次启动时会自动创建表和索引。

## 3. 安装文件

```bash
sudo useradd --system --home /var/lib/subit-website-content --shell /usr/sbin/nologin subit-website
sudo install -d -o subit-website -g www-data -m 0750 /opt/subit-website-content
sudo install -d -o subit-website -g www-data -m 0750 /var/lib/subit-website-content/public
sudo install -d -o root -g root -m 0755 /etc/subit
sudo install -o subit-website -g www-data -m 0640 build/libs/SubITWebsiteContentService.jar /opt/subit-website-content/
sudo install -o subit-website -g www-data -m 0640 ../public/content/site-content.json /opt/subit-website-content/
```

复制 `deploy/website-content.env.example` 到 `/etc/subit/website-content.env`，填写数据库密码、SSO Service ID、Service Token、Session Secret，以及首位管理员的 SSubitO 用户 ID。建议用 `openssl rand -base64 48` 生成 Session Secret。

`BOOTSTRAP_ADMIN_IDS` 中的用户每次启动都会确保为 `ADMIN`，用于紧急恢复后台权限。

## 4. 安装 systemd 服务

```bash
sudo install -m 0644 deploy/subit-website-content.service /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable --now subit-website-content
curl --fail http://127.0.0.1:8091/health
```

## 5. 配置 Nginx

把 `deploy/nginx-content.conf` 中的 location 块加入现有 `subit.org.cn` server 块，然后执行：

```bash
sudo nginx -t
sudo systemctl reload nginx
```

Nginx 会直接提供 `/content/current.json`、不可变版本文件和图片；只有 `/content-api/` 会进入 Ktor 服务。

## 6. 首次发布

1. 打开 `https://subit.org.cn/content-editor`。
2. 使用 `BOOTSTRAP_ADMIN_IDS` 中的账号通过 SSubitO 登录并授权。
3. 确认初始草稿内容后点击“立即发布”。
4. 检查 `https://subit.org.cn/content/current.json`。
5. 使用无痕窗口确认官网能读取新版本。

## 更新与回滚服务程序

```bash
./gradlew test shadowJar
sudo systemctl stop subit-website-content
sudo cp /opt/subit-website-content/SubITWebsiteContentService.jar /opt/subit-website-content/SubITWebsiteContentService.jar.previous
sudo install -o subit-website -g www-data -m 0640 build/libs/SubITWebsiteContentService.jar /opt/subit-website-content/
sudo systemctl start subit-website-content
curl --fail http://127.0.0.1:8091/health
```

如果健康检查失败，将 `.previous` 文件复制回来并重启服务。内容版本和图片位于 `/var/lib/subit-website-content/public`，数据库及该目录都应纳入备份。
