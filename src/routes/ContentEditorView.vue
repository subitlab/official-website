<script setup lang="ts">
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import {
  cloneSiteContent,
  CONTENT_DRAFT_KEY,
  replaceSiteContent,
  type AchievementItem,
  type MemberItem,
  type PhotoItem,
  type PotItem,
  type ProjectItem,
  type SiteContent,
  validateSiteContent,
} from "@/content/siteContent";
import {
  cancelRelease,
  type CmsMe,
  type CmsRole,
  type CmsUser,
  getMe,
  listReleases,
  listUsers,
  loadDraft,
  loginUrl,
  logout,
  publishContent,
  type ReleaseInfo,
  rollbackRelease,
  saveDraft,
  updateUserRole,
  uploadAsset,
} from "@/content/contentApi";

const router = useRouter();
const content = ref<SiteContent>(cloneSiteContent());
const message = ref("正在连接内容服务……");
const importInput = ref<HTMLInputElement | null>(null);
const me = ref<CmsMe | null>(null);
const loading = ref(true);
const serviceError = ref("");
const releases = ref<ReleaseInfo[]>([]);
const users = ref<CmsUser[]>([]);
const publishAt = ref("");
const newUserId = ref<number | null>(null);
const newUserRole = ref<CmsRole>("EDITOR");
const busy = ref(false);

onMounted(initialize);

async function initialize() {
  loading.value = true;
  serviceError.value = "";
  try {
    me.value = await getMe();
    if (!me.value) {
      message.value = "请使用 SSubitO 登录。";
      return;
    }
    if (me.value.role === "NONE") {
      message.value = "账号已识别，但尚未获得网站编辑权限。";
      return;
    }
    const draft = await loadDraft();
    content.value = structuredClone(validateSiteContent(draft.content));
    releases.value = await listReleases();
    if (me.value.role === "ADMIN") users.value = await listUsers();
    message.value = draft.updatedAt
      ? `已载入线上草稿，上次保存于 ${formatTime(draft.updatedAt)}。`
      : "已载入线上草稿。";
  } catch (error) {
    serviceError.value = errorMessage(error);
    message.value = serviceError.value;
  } finally {
    loading.value = false;
  }
}

function addProject() {
  content.value.projects.push({title: "新项目", subtitle: "项目简介", linkLabel: "了解更多 >", href: "https://", image: "/projects/ssubito.png", imageAlt: "新项目"});
}

function addAchievement() {
  content.value.achievements.push({type: "running", title: "新成就", image: "/invitation.png", href: "https://github.com/subitlab", description: "成就简介"});
}

function addMember() {
  content.value.join.members.push({image: "/cps.jpg", name: "新社员", description: "个人简介"});
}

function addPot() {
  content.value.submore.pots.push({image: "/pot_mk1.png", title: "铁锅", subtitle: "届次与社长"});
}

function addPhoto() {
  content.value.submore.photos.push({image: "/submore_photo.jpg", description: "照片说明"});
}

function addQuote() {
  content.value.submore.quotes.push({text: "想说的话", author: "署名"});
}

function removeItem<T>(items: T[], index: number) {
  items.splice(index, 1);
}

function moveItem<T>(items: T[], index: number, direction: -1 | 1) {
  const target = index + direction;
  if (target < 0 || target >= items.length) return;
  [items[index], items[target]] = [items[target], items[index]];
}

async function setImage(event: Event, item: ProjectItem | AchievementItem | MemberItem | PotItem | PhotoItem, field: "image" | "secondaryImage" = "image") {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (!file || !me.value) return;
  busy.value = true;
  try {
    const uploaded = await uploadAsset(file, me.value.csrfToken);
    if (field === "secondaryImage") (item as ProjectItem).secondaryImage = uploaded.url;
    else item.image = uploaded.url;
    message.value = `图片已上传：${file.name}`;
  } catch (error) {
    message.value = errorMessage(error);
  } finally {
    busy.value = false;
    (event.target as HTMLInputElement).value = "";
  }
}

async function persistDraft() {
  if (!me.value) return false;
  busy.value = true;
  try {
    await saveDraft(content.value, me.value.csrfToken);
    message.value = "线上草稿已保存；访客仍会看到当前已发布版本。";
    return true;
  } catch (error) {
    message.value = errorMessage(error);
    return false;
  } finally {
    busy.value = false;
  }
}

async function savePreview(path = "/") {
  if (!await persistDraft()) return;
  localStorage.setItem(CONTENT_DRAFT_KEY, JSON.stringify(content.value));
  replaceSiteContent(content.value);
  void router.push(path);
}

function clearPreview() {
  localStorage.removeItem(CONTENT_DRAFT_KEY);
  message.value = "本机预览已清除；线上草稿没有改变。";
}

function downloadJson() {
  const blob = new Blob([JSON.stringify(content.value, null, 2)], {type: "application/json"});
  const url = URL.createObjectURL(blob);
  const link = document.createElement("a");
  link.href = url;
  link.download = "site-content.json";
  link.click();
  URL.revokeObjectURL(url);
  message.value = "内容包已导出，可用于备份或迁移。";
}

async function importJson(event: Event) {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (!file) return;
  try {
    const parsed: unknown = JSON.parse(await file.text());
    content.value = structuredClone(validateSiteContent(parsed));
    message.value = `已载入 ${file.name}，请先预览再导出。`;
  } catch (error) {
    message.value = error instanceof Error ? error.message : "无法读取内容文件。";
  } finally {
    (event.target as HTMLInputElement).value = "";
  }
}

async function publishNow() {
  if (!me.value) return;
  if (!window.confirm("立即发布后，所有访客刷新页面都会看到这份内容。确定继续吗？")) return;
  busy.value = true;
  try {
    await saveDraft(content.value, me.value.csrfToken);
    const result = await publishContent(content.value, me.value.csrfToken);
    localStorage.removeItem(CONTENT_DRAFT_KEY);
    replaceSiteContent(content.value);
    releases.value = await listReleases();
    message.value = `版本 ${shortId(result.release.id)} 已发布，访客无需重新加载网站包即可读取。`;
  } catch (error) {
    message.value = errorMessage(error);
  } finally {
    busy.value = false;
  }
}

async function schedulePublish() {
  if (!me.value || !publishAt.value) return;
  const scheduled = new Date(publishAt.value);
  if (Number.isNaN(scheduled.valueOf()) || scheduled <= new Date()) {
    message.value = "定时发布时间必须晚于当前时间。";
    return;
  }
  busy.value = true;
  try {
    await saveDraft(content.value, me.value.csrfToken);
    const result = await publishContent(content.value, me.value.csrfToken, scheduled.toISOString());
    releases.value = await listReleases();
    publishAt.value = "";
    message.value = `版本 ${shortId(result.release.id)} 已安排于 ${formatTime(result.release.scheduledAt)} 发布。`;
  } catch (error) {
    message.value = errorMessage(error);
  } finally {
    busy.value = false;
  }
}

async function rollback(item: ReleaseInfo) {
  if (!me.value) return;
  if (!window.confirm(`确定将版本 ${shortId(item.id)} 的内容重新发布为最新版本吗？`)) return;
  busy.value = true;
  try {
    const result = await rollbackRelease(item.id, me.value.csrfToken);
    releases.value = await listReleases();
    message.value = `已从 ${shortId(item.id)} 创建并发布回滚版本 ${shortId(result.release.id)}。`;
  } catch (error) {
    message.value = errorMessage(error);
  } finally {
    busy.value = false;
  }
}

async function cancelSchedule(item: ReleaseInfo) {
  if (!me.value) return;
  if (!window.confirm(`确定取消计划版本 ${shortId(item.id)} 吗？`)) return;
  busy.value = true;
  try {
    await cancelRelease(item.id, me.value.csrfToken);
    releases.value = await listReleases();
    message.value = `已取消计划版本 ${shortId(item.id)}。`;
  } catch (error) {
    message.value = errorMessage(error);
  } finally {
    busy.value = false;
  }
}

async function setRole(userId: number, role: CmsRole) {
  if (!me.value || me.value.role !== "ADMIN") return;
  busy.value = true;
  try {
    await updateUserRole(userId, role, me.value.csrfToken);
    users.value = await listUsers();
    message.value = `用户 #${userId} 的权限已更新为 ${roleLabel(role)}。`;
  } catch (error) {
    message.value = errorMessage(error);
  } finally {
    busy.value = false;
  }
}

async function addUser() {
  if (newUserId.value == null) return;
  await setRole(newUserId.value, newUserRole.value);
  newUserId.value = null;
}

async function refreshReleases() {
  releases.value = await listReleases();
}

function onRoleChange(userId: number, event: Event) {
  void setRole(userId, (event.target as HTMLSelectElement).value as CmsRole);
}

async function signOut() {
  if (me.value) await logout(me.value.csrfToken).catch(() => undefined);
  me.value = null;
  window.location.href = "/";
}

function formatTime(value?: string) {
  return value ? new Intl.DateTimeFormat("zh-CN", {dateStyle: "medium", timeStyle: "short"}).format(new Date(value)) : "—";
}
function shortId(value: string) { return value.slice(0, 8); }
function roleLabel(role: CmsRole) { return ({NONE: "无权限", EDITOR: "编辑者", ADMIN: "管理员"})[role]; }
function statusLabel(status: ReleaseInfo["status"]) {
  return ({SCHEDULED: "计划发布", PUBLISHED: "当前版本", SUPERSEDED: "历史版本", CANCELLED: "已取消"})[status];
}
function errorMessage(error: unknown) { return error instanceof Error ? error.message : "操作失败，请稍后重试。"; }
</script>

<template>
  <div class="editor-page">
    <section v-if="loading" class="access-card">
      <span class="eyebrow">SUBIT CONTENT STUDIO</span>
      <h1>正在连接内容服务</h1>
      <p>正在确认 SSubitO 登录状态并载入线上草稿。</p>
    </section>
    <section v-else-if="!me" class="access-card">
      <span class="eyebrow">SUBIT CONTENT STUDIO</span>
      <h1>登录后编辑网站</h1>
      <p>{{ serviceError || '内容后台使用 SSubitO 识别社团成员身份。' }}</p>
      <a class="login-button" :href="loginUrl">使用 SSubitO 登录</a>
    </section>
    <section v-else-if="me.role === 'NONE'" class="access-card">
      <span class="eyebrow">SIGNED IN AS #{{ me.userId }}</span>
      <h1>尚未获得编辑权限</h1>
      <p>你已以 {{ me.username }} 登录。请让网站管理员将你的角色设为 Editor 或 Admin。</p>
      <button type="button" @click="signOut">退出登录</button>
    </section>
    <template v-else>
    <header class="editor-hero">
      <div>
        <span class="eyebrow">SUBIT CONTENT STUDIO</span>
        <h1>网站内容编辑器</h1>
        <p>编辑和上传内容无需修改代码或重新打包；保存草稿不会影响访客，发布后静态官网会自动读取新版本。</p>
      </div>
      <div class="status" aria-live="polite">
        <strong>{{ me.username }}</strong> · {{ roleLabel(me.role) }}<br/>
        {{ message }}
      </div>
    </header>

    <nav class="toolbar" aria-label="内容编辑操作">
      <button type="button" :disabled="busy" @click="persistDraft">保存草稿</button>
      <button type="button" :disabled="busy" @click="savePreview('/')">预览首页</button>
      <button type="button" class="primary" :disabled="busy" @click="publishNow">立即发布</button>
      <label class="schedule-field">定时发布<input v-model="publishAt" type="datetime-local"/></label>
      <button type="button" :disabled="busy || !publishAt" @click="schedulePublish">安排发布</button>
      <button type="button" @click="importInput?.click()">导入内容包</button>
      <button type="button" @click="downloadJson">导出内容包</button>
      <button type="button" @click="clearPreview">清除本机预览</button>
      <button type="button" class="danger" @click="signOut">退出</button>
      <input ref="importInput" class="visually-hidden" type="file" accept="application/json,.json" @change="importJson"/>
    </nav>

    <main>
      <section class="editor-section">
        <div class="section-heading">
          <div><span>01</span><h2>首页项目轮播</h2><p>拖动顺序由上下移动按钮控制；首页会按此顺序自动轮播。</p></div>
          <button type="button" @click="addProject">＋ 添加项目</button>
        </div>
        <article v-for="(item, index) in content.projects" :key="index" class="edit-card project-card">
          <div class="card-index">{{ String(index + 1).padStart(2, '0') }}</div>
          <div class="image-editor">
            <img :src="item.image" :alt="item.imageAlt"/>
            <label>替换主图<input type="file" accept="image/*" @change="setImage($event, item)"/></label>
          </div>
          <div class="fields">
            <label>项目名称<input v-model="item.title"/></label>
            <label>一句话介绍<input v-model="item.subtitle"/></label>
            <div class="field-row"><label>按钮文字<input v-model="item.linkLabel"/></label><label>跳转链接<input v-model="item.href" type="url"/></label></div>
            <label>主图路径<input v-model="item.image"/></label>
            <label>图片替代文字<input v-model="item.imageAlt"/></label>
            <label>手机图（可选）<input v-model="item.secondaryImage" placeholder="没有则留空"/></label>
          </div>
          <div class="card-actions">
            <button type="button" :disabled="index === 0" @click="moveItem(content.projects, index, -1)">上移</button>
            <button type="button" :disabled="index === content.projects.length - 1" @click="moveItem(content.projects, index, 1)">下移</button>
            <button type="button" class="danger" @click="removeItem(content.projects, index)">删除</button>
          </div>
        </article>
      </section>

      <section class="editor-section">
        <div class="section-heading">
          <div><span>02</span><h2>项目与成就</h2><p>建议把最新内容放在最前；卡片会自动适配不同屏幕宽度。</p></div>
          <button type="button" @click="addAchievement">＋ 添加成就</button>
        </div>
        <article v-for="(item, index) in content.achievements" :key="index" class="edit-card compact-card">
          <div class="image-editor small"><img :src="item.image" alt=""/><label>替换图片<input type="file" accept="image/*" @change="setImage($event, item)"/></label></div>
          <div class="fields">
            <div class="field-row"><label>状态<select v-model="item.type"><option value="event">活动类成就</option><option value="running">运行中</option><option value="archived">已存档</option></select></label><label>标题<input v-model="item.title"/></label></div>
            <label>描述<textarea v-model="item.description" rows="2"/></label>
            <div class="field-row"><label>图片路径<input v-model="item.image"/></label><label>项目链接<input v-model="item.href"/></label></div>
          </div>
          <div class="card-actions inline"><button type="button" :disabled="index === 0" @click="moveItem(content.achievements, index, -1)">↑</button><button type="button" :disabled="index === content.achievements.length - 1" @click="moveItem(content.achievements, index, 1)">↓</button><button type="button" class="danger" @click="removeItem(content.achievements, index)">删除</button></div>
        </article>
      </section>

      <section class="editor-section">
        <div class="section-heading">
          <div><span>03</span><h2>招新与社员</h2><p>更新招新说明、届次和社员卡片。</p></div>
          <button type="button" @click="addMember">＋ 添加社员</button>
        </div>
        <div class="edit-card text-card">
          <div class="fields"><label>招新说明<textarea v-model="content.join.intro" rows="4"/></label><div class="field-row"><label>招新资料链接<input v-model="content.join.recruitmentLink"/></label><label>社员区标题<input v-model="content.join.membersTitle"/></label></div></div>
        </div>
        <div class="member-edit-grid">
          <article v-for="(item, index) in content.join.members" :key="index" class="edit-card member-edit-card">
            <div class="image-editor small"><img :src="item.image" alt=""/><label>修改 Demo 图片<input type="file" accept="image/*" @change="setImage($event, item)"/></label></div>
            <div class="fields"><label>姓名/职务<input v-model="item.name"/></label><label>简介<input v-model="item.description"/></label><label>Demo 图片路径<input v-model="item.image"/></label></div>
            <button type="button" class="danger delete-only" @click="removeItem(content.join.members, index)">删除</button>
          </article>
        </div>
      </section>

      <section class="editor-section">
        <div class="section-heading">
          <div><span>04</span><h2>SubMore 社团文化</h2><p>铁锅、影相、词云和成员语录均可独立轮换。</p></div>
          <button type="button" @click="savePreview('/submore')">预览 SubMore</button>
        </div>
        <h3>铁锅纪念馆 <button type="button" @click="addPot">＋ 添加</button></h3>
        <article v-for="(item, index) in content.submore.pots" :key="index" class="edit-card compact-card">
          <div class="image-editor small"><img :src="item.image" alt=""/><label>替换图片<input type="file" accept="image/*" @change="setImage($event, item)"/></label></div>
          <div class="fields"><div class="field-row"><label>名称<input v-model="item.title"/></label><label>届次/社长<input v-model="item.subtitle"/></label></div><label>图片路径<input v-model="item.image"/></label></div>
          <button type="button" class="danger delete-only" @click="removeItem(content.submore.pots, index)">删除</button>
        </article>
        <h3>珍藏影相 <button type="button" @click="addPhoto">＋ 添加</button></h3>
        <div class="member-edit-grid">
          <article v-for="(item, index) in content.submore.photos" :key="index" class="edit-card member-edit-card">
            <div class="image-editor small"><img :src="item.image" alt=""/><label>替换图片<input type="file" accept="image/*" @change="setImage($event, item)"/></label></div>
            <div class="fields"><label>说明<input v-model="item.description"/></label><label>图片路径<input v-model="item.image"/></label></div>
            <button type="button" class="danger delete-only" @click="removeItem(content.submore.photos, index)">删除</button>
          </article>
        </div>
        <h3>词云与语录 <button type="button" @click="addQuote">＋ 添加语录</button></h3>
        <div class="edit-card text-card"><div class="fields"><label>词云图片路径<input v-model="content.submore.wordcloud"/></label><label>页面导语<textarea v-model="content.submore.intro" rows="2"/></label></div></div>
        <article v-for="(item, index) in content.submore.quotes" :key="index" class="edit-card quote-edit-card">
          <div class="fields"><label>语录<textarea v-model="item.text" rows="2"/></label><label>署名<input v-model="item.author"/></label></div>
          <button type="button" class="danger delete-only" @click="removeItem(content.submore.quotes, index)">删除</button>
        </article>
      </section>

      <section class="editor-section">
        <div class="section-heading">
          <div><span>05</span><h2>发布历史</h2><p>每次发布都会生成不可变版本，可以随时回滚；计划版本可在生效前取消。</p></div>
          <button type="button" :disabled="busy" @click="refreshReleases">刷新</button>
        </div>
        <div class="release-list">
          <article v-for="item in releases" :key="item.id" class="release-row">
            <div><strong>{{ shortId(item.id) }}</strong><span :class="['release-status', item.status.toLowerCase()]">{{ statusLabel(item.status) }}</span></div>
            <span>{{ formatTime(item.publishedAt || item.scheduledAt) }}</span>
            <span>操作人 #{{ item.publishedBy }}</span>
            <div class="release-actions">
              <button v-if="item.status !== 'CANCELLED'" type="button" :disabled="busy || item.status === 'PUBLISHED'" @click="rollback(item)">回滚到此版本</button>
              <button v-if="item.status === 'SCHEDULED'" type="button" class="danger" :disabled="busy" @click="cancelSchedule(item)">取消计划</button>
            </div>
          </article>
        </div>
      </section>

      <section v-if="me.role === 'ADMIN'" class="editor-section">
        <div class="section-heading">
          <div><span>06</span><h2>成员权限</h2><p>未列出的 SSubitO 用户默认为 None；Editor 可以编辑和发布，Admin 还可以管理成员。</p></div>
        </div>
        <div class="permission-add">
          <label>SSubitO 用户 ID<input v-model.number="newUserId" type="number" min="1" placeholder="例如 123"/></label>
          <label>角色<select v-model="newUserRole"><option value="EDITOR">Editor</option><option value="ADMIN">Admin</option></select></label>
          <button type="button" :disabled="busy || !newUserId" @click="addUser">添加或更新</button>
        </div>
        <div class="permission-list">
          <article v-for="user in users" :key="user.userId" class="permission-row">
            <div><strong>{{ user.username || `用户 #${user.userId}` }}</strong><span>{{ user.email || `SSubitO #${user.userId}` }}</span></div>
            <select :value="user.role" :disabled="busy || user.userId === me.userId" @change="onRoleChange(user.userId, $event)">
              <option value="NONE">None</option><option value="EDITOR">Editor</option><option value="ADMIN">Admin</option>
            </select>
          </article>
        </div>
      </section>
    </main>
    </template>
  </div>
</template>

<style scoped lang="scss">
.editor-page { width: min(1180px, calc(100% - 40px)); margin: 0 auto; padding: 48px 0 100px; color: #0d141c; }
.access-card { max-width: 680px; margin: 12vh auto; padding: 42px; border: 1px solid #dbe4ed; border-radius: 18px; background: white; box-shadow: 0 18px 50px rgba(13,20,28,.08); }
.access-card h1 { margin: 12px 0; }.access-card p { margin: 0 0 24px; color: #526779; }
.login-button { display: inline-block; padding: 11px 18px; border-radius: 9px; background: #0066cc; color: white; text-decoration: none; font-weight: 600; }
.editor-hero { display: grid; grid-template-columns: 1.5fr 1fr; gap: 40px; align-items: end; padding: 28px 0 36px; }
.eyebrow { color: #0066cc; font: 700 12px/1 "JetBrains Mono", monospace; letter-spacing: .14em; }
h1 { margin: 8px 0; font-size: clamp(34px, 5vw, 62px); letter-spacing: -.04em; }
.editor-hero p { max-width: 640px; margin: 0; color: #526779; font-size: 17px; }
.status { padding: 18px; border: 1px solid #dbe4ed; border-radius: 12px; background: #f7f9fc; color: #34495e; }
.toolbar { position: sticky; z-index: 20; top: 65px; display: flex; gap: 10px; padding: 12px; border: 1px solid #dbe4ed; border-radius: 14px; background: rgba(255,255,255,.94); box-shadow: 0 8px 30px rgba(13,20,28,.08); backdrop-filter: blur(14px); }
.schedule-field { display: flex; align-items: center; gap: 7px; padding-left: 8px; color: #536779; white-space: nowrap; font-size: 13px; }
.schedule-field input { padding: 7px; border: 1px solid #ccd7e2; border-radius: 7px; }
button, label { font: inherit; }
button { padding: 9px 14px; border: 1px solid #ccd7e2; border-radius: 8px; background: white; color: #0d141c; cursor: pointer; }
button:hover:not(:disabled) { background: #edf4fa; }
button:disabled { opacity: .38; cursor: not-allowed; }
button.primary { border-color: #0066cc; background: #0066cc; color: white; }
button.danger { color: #b42318; }
.visually-hidden { position: absolute; width: 1px; height: 1px; overflow: hidden; clip: rect(0 0 0 0); }
.editor-section { padding: 70px 0 0; }
.section-heading { display: flex; align-items: flex-end; justify-content: space-between; gap: 24px; margin-bottom: 20px; }
.section-heading span { color: #90a1b2; font: 700 14px/1 "JetBrains Mono", monospace; }
.section-heading h2 { margin: 7px 0 2px; font-size: 30px; }
.section-heading p { margin: 0; color: #66788a; }
.editor-section > h3 { display: flex; align-items: center; justify-content: space-between; margin: 38px 0 12px; }
.edit-card { position: relative; display: grid; grid-template-columns: 150px minmax(0, 1fr) auto; gap: 22px; align-items: start; margin-bottom: 12px; padding: 18px; border: 1px solid #dbe4ed; border-radius: 14px; background: white; }
.card-index { position: absolute; top: 10px; left: 10px; z-index: 2; padding: 4px 6px; border-radius: 6px; background: rgba(13,20,28,.75); color: white; font: 12px/1 "JetBrains Mono", monospace; }
.image-editor { position: relative; overflow: hidden; border-radius: 10px; background: #f1f4f7; }
.image-editor img { display: block; width: 100%; height: 120px; object-fit: contain; }
.image-editor.small img { height: 96px; object-fit: cover; }
.image-editor img.rotate { transform: rotate(180deg); }.image-editor img.sideways { transform: rotate(90deg); }.image-editor img.blue { filter: hue-rotate(175deg) saturate(1.2); }.image-editor img.green { filter: hue-rotate(65deg) saturate(1.25); }
.image-editor label { display: block; padding: 8px; color: #0066cc; text-align: center; cursor: pointer; font-size: 13px; }
.image-editor input[type=file] { position: absolute; width: 1px; height: 1px; opacity: 0; }
.fields { display: grid; gap: 10px; min-width: 0; }
.fields label { display: grid; gap: 5px; color: #536779; font-size: 12px; font-weight: 600; }
.fields input, .fields textarea, .fields select { box-sizing: border-box; width: 100%; min-width: 0; padding: 9px 10px; border: 1px solid #ccd7e2; border-radius: 7px; background: white; color: #0d141c; font: 14px/1.4 inherit; resize: vertical; }
.fields input:focus, .fields textarea:focus, .fields select:focus { border-color: #2990eb; outline: 3px solid rgba(41,144,235,.13); }
.field-row { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; }
.card-actions { display: grid; gap: 7px; }.card-actions.inline { grid-template-columns: repeat(3, auto); }.delete-only { align-self: end; }
.compact-card { grid-template-columns: 120px minmax(0, 1fr) auto; }
.text-card { grid-template-columns: 1fr; }
.member-edit-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 12px; }
.member-edit-card { grid-template-columns: 92px minmax(0, 1fr); }.member-edit-card .delete-only { grid-column: 1 / -1; justify-self: end; }
.quote-edit-card { grid-template-columns: minmax(0, 1fr) auto; }
.release-list, .permission-list { display: grid; gap: 9px; }
.release-row { display: grid; grid-template-columns: 1.2fr 1fr .7fr auto; gap: 18px; align-items: center; padding: 14px 16px; border: 1px solid #dbe4ed; border-radius: 10px; background: white; color: #536779; font-size: 14px; }
.release-row strong { margin-right: 10px; color: #0d141c; font-family: "JetBrains Mono", monospace; }
.release-status { padding: 3px 7px; border-radius: 999px; background: #edf1f5; font-size: 11px; }.release-status.published { background: #dcfce7; color: #166534; }.release-status.scheduled { background: #dbeafe; color: #1d4ed8; }.release-status.cancelled { color: #8b97a3; }
.release-actions { display: flex; justify-content: flex-end; gap: 6px; }
.permission-add { display: grid; grid-template-columns: 1fr 1fr auto; gap: 12px; align-items: end; margin-bottom: 14px; padding: 16px; border-radius: 12px; background: #f7f9fc; }
.permission-add label { display: grid; gap: 5px; color: #536779; font-size: 12px; font-weight: 600; }
.permission-add input, .permission-add select, .permission-row select { padding: 9px 10px; border: 1px solid #ccd7e2; border-radius: 7px; background: white; }
.permission-row { display: flex; align-items: center; justify-content: space-between; padding: 13px 16px; border: 1px solid #dbe4ed; border-radius: 10px; background: white; }
.permission-row div { display: grid; gap: 3px; }.permission-row span { color: #66788a; font-size: 12px; }

@media (max-width: 760px) {
  .editor-page { width: min(100% - 28px, 1180px); padding-top: 20px; }
  .editor-hero { grid-template-columns: 1fr; gap: 18px; }
  .toolbar { top: 65px; overflow-x: auto; white-space: nowrap; }
  .section-heading { align-items: flex-start; flex-direction: column; }
  .edit-card, .compact-card { grid-template-columns: 1fr; }
  .image-editor img, .image-editor.small img { height: 180px; }
  .card-actions { grid-template-columns: repeat(3, 1fr); }
  .field-row { grid-template-columns: 1fr; }
  .access-card { margin: 8vh auto; padding: 28px 22px; }
  .release-row { grid-template-columns: 1fr; gap: 7px; }
  .release-actions { justify-content: flex-start; }
  .permission-add { grid-template-columns: 1fr; }
}
</style>
