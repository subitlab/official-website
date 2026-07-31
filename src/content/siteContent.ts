import {reactive, readonly} from "vue";

export interface ProjectItem {
  title: string;
  subtitle: string;
  linkLabel: string;
  href: string;
  image: string;
  secondaryImage?: string;
  imageAlt: string;
}

export interface AchievementItem {
  type: "event" | "archived" | "running";
  title: string;
  image: string;
  href: string;
  description: string;
}

export interface MemberItem {
  image: string;
  name: string;
  description: string;
}

export interface PotItem { image: string; title: string; subtitle: string }
export interface PhotoItem { image: string; description: string }
export interface QuoteItem { text: string; author: string }

export interface SiteContent {
  version: 1;
  projects: ProjectItem[];
  achievements: AchievementItem[];
  join: {
    intro: string;
    recruitmentLink: string;
    membersTitle: string;
    members: MemberItem[];
  };
  submore: {
    intro: string;
    pots: PotItem[];
    photos: PhotoItem[];
    wordcloud: string;
    quotes: QuoteItem[];
  };
}

export const CONTENT_DRAFT_KEY = "subit-site-content-draft-v1";

export const defaultSiteContent: SiteContent = {
  version: 1,
  projects: [
    {title: "西楼大屏管理系统", subtitle: "这块屏幕为你所有。", linkLabel: "实时状态 >", href: "https://screen.subit.org.cn/", image: "/projects/screen.png", imageAlt: "西楼大屏管理系统"},
    {title: "SSubITO", subtitle: "一个应用，登录所有 SubIT 社团开发的平台。", linkLabel: "通过 SSO 登录 >", href: "https://ssubito.subit.org.cn/", image: "/projects/ssubito.png", imageAlt: "SSubITO"},
    {title: "创意写作工坊", subtitle: "我们把对写作的爱藏在袖子里。", linkLabel: "开始书写 >", href: "https://youthwrite.subit.org.cn/", image: "/projects/youthwrite1.png", secondaryImage: "/projects/youthwrite2.png", imageAlt: "创意写作工坊"},
  ],
  achievements: Array.from({length: 6}, (_, index) => ({
    type: (["event", "archived", "running"] as const)[index % 3],
    title: "2020毕业典礼·邀请函生成器",
    image: "/invitation.png",
    href: "https://github.com/subitlab",
    description: "这是一段描述这是一段描述这是一段描述这是一段描述这是一段描述",
  })),
  join: {
    intro: "我们欢迎每一位对我们感兴趣的北大附中学生的加入！如果你希望成为我们的一员，SubIT 每年会在春季和秋季各进行一次招新，春季招新通常在每学年下半学期的 3 月至 4 月之间，秋季招新则在上半学期的 8 月至 10 月期间进行。",
    recruitmentLink: "https://pkuschool.yuque.com/subit",
    membersTitle: "27 届社员",
    members: Array.from({length: 12}, (_, index) => ({
      image: "/cps.jpg",
      name: index === 0 ? "社长｜CPS1" : index === 1 ? "副社长｜CPS2" : `CPS${index + 1}`,
      description: "CPSCPSCPSCPS",
    })),
  },
  submore: {
    intro: "在 SubIT，除了常规的社团活动，我们还有各种奇妙的社团文化 ……",
    pots: [
      {image: "/pot_mk1.png", title: "铁锅 Mk.1", subtitle: "2021届社长｜刘语辰"},
      {image: "/pot_mk2.png", title: "铁锅 Mk.2", subtitle: "2022届社长｜何天阳"},
      {image: "/pot_mk3.jpg", title: "铁锅 Mk.3", subtitle: "2023届社长｜刘宇宸"},
      {image: "/pot_mk1.png", title: "铁锅 Mk.4", subtitle: "2024届社长｜魏子峰"},
      {image: "/pot_mk1.png", title: "铁锅 Mk.5", subtitle: "2021届社长｜刘语辰"},
    ],
    photos: Array.from({length: 4}, () => ({image: "/submore_photo.jpg", description: "社活一角｜脆皮四绘于 2025 年夏"})),
    wordcloud: "/wordcloud.png",
    quotes: Array.from({length: 6}, () => ({text: "赛博旺证遗民这辈子也就这样了，鉴定为屁股决定上限", author: "YJN"})),
  },
};

function copyContent(value: SiteContent): SiteContent {
  const copied = JSON.parse(JSON.stringify(value)) as SiteContent;
  copied.join.members = copied.join.members.map((member) => {
    const {presentation: _legacyPresentation, ...currentMember} = member as MemberItem & {presentation?: unknown};
    return currentMember;
  });
  return copied;
}

const contentState = reactive<SiteContent>(copyContent(defaultSiteContent));
let hasLoaded = false;

function isSiteContent(value: unknown): value is SiteContent {
  if (!value || typeof value !== "object") return false;
  const candidate = value as Partial<SiteContent>;
  return candidate.version === 1 && Array.isArray(candidate.projects) && Array.isArray(candidate.achievements)
    && Array.isArray(candidate.join?.members) && Array.isArray(candidate.submore?.pots);
}

export function replaceSiteContent(next: SiteContent) {
  Object.assign(contentState, copyContent(next));
}

export async function loadSiteContent() {
  if (hasLoaded) return;
  hasLoaded = true;

  const draft = localStorage.getItem(CONTENT_DRAFT_KEY);
  if (draft) {
    try {
      const parsed: unknown = JSON.parse(draft);
      if (isSiteContent(parsed)) {
        replaceSiteContent(parsed);
        return;
      }
    } catch {
      localStorage.removeItem(CONTENT_DRAFT_KEY);
    }
  }

  try {
    const manifestResponse = await fetch("/content/current.json", {cache: "no-cache"});
    if (manifestResponse.ok && manifestResponse.headers.get("content-type")?.includes("application/json")) {
      const manifest = await manifestResponse.json() as {contentUrl?: string};
      if (manifest.contentUrl) {
        const releaseResponse = await fetch(manifest.contentUrl, {cache: "force-cache"});
        if (releaseResponse.ok) {
          const release: unknown = await releaseResponse.json();
          if (isSiteContent(release)) {
            replaceSiteContent(release);
            return;
          }
        }
      }
    }
  } catch {
    // Before the content service is deployed, the version manifest does not exist.
  }

  try {
    const response = await fetch("/content/site-content.json", {cache: "no-cache"});
    if (!response.ok) return;
    const parsed: unknown = await response.json();
    if (isSiteContent(parsed)) replaceSiteContent(parsed);
  } catch {
    // The bundled defaults keep the site usable when the content file is unavailable.
  }
}

export function useSiteContent() {
  return readonly(contentState);
}

export function cloneSiteContent() {
  return copyContent(contentState);
}

export function validateSiteContent(value: unknown): SiteContent {
  if (!isSiteContent(value)) throw new Error("内容文件格式不正确或版本不受支持。");
  return value;
}
