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
  image?: string;
  imageAlt?: string;
  monogram?: string;
  href?: string;
  linkLabel?: string;
  description: string;
}

export interface MemberItem {
  image: string;
  name: string;
  description: string;
}

export interface MemberEntry {
  name: string;
  house: string;
}

export interface MemberSession {
  name: string;
  members: MemberEntry[];
}

export interface MemberSection {
  title: string;
  collapsible?: boolean;
  sessions: MemberSession[];
}

export interface PotItem { image: string; title: string; subtitle: string }
export interface PhotoItem { image: string; description: string }
export interface QuoteItem { text: string; author: string }
export interface MemberProfile {
  name: string;
  cohort: string;
  role: string;
  description: string;
  image?: string;
}

export interface SiteContent {
  version: 1;
  projects: ProjectItem[];
  achievements: AchievementItem[];
  join: {
    intro: string;
    recruitmentLink: string;
    membersTitle: string;
    members: MemberItem[];
    memberSections: MemberSection[];
  };
  submore: {
    intro: string;
    pots: PotItem[];
    photos: PhotoItem[];
    wordcloud: string;
    quotes: QuoteItem[];
    memberProfiles?: MemberProfile[];
  };
}

export const CONTENT_DRAFT_KEY = "subit-site-content-draft-v1";

export const defaultSiteContent: SiteContent = {
  version: 1,
  projects: [
    {title: "西楼大屏管理系统", subtitle: "让校内通知与活动信息按时出现在西楼屏幕上。", linkLabel: "实时状态 >", href: "https://screen.subit.org.cn/", image: "/projects/screen.png", imageAlt: "西楼大屏管理系统"},
    {title: "SSubITO", subtitle: "注册并登录一次，即可使用接入 SSubITO 的社团项目。", linkLabel: "通过 SSO 登录 >", href: "https://ssubito.subit.org.cn/", image: "/projects/ssubito.png", imageAlt: "SSubITO"},
    {title: "北大附中创意写作官网", subtitle: "发表自己的小说与诗歌，和同学老师交流写作。", linkLabel: "开始书写 >", href: "https://youthwrite.subit.org.cn/", image: "/projects/youthwrite1.png", secondaryImage: "/projects/youthwrite2.png", imageAlt: "北大附中创意写作官网"},
  ],
  achievements: [
    {
      type: "running",
      title: "西楼大屏管理系统",
      image: "/projects/screen.png",
      imageAlt: "西楼大屏管理系统界面",
      href: "https://screen.subit.org.cn/",
      linkLabel: "打开大屏系统 >",
      description: "面向有信息投放需求的校内老师，提供账号管理、内容上传和投放功能。老师可提前安排内容发布，并查看西楼公共屏幕的运行状态。",
    },
    {
      type: "running",
      title: "SSubITO · 单点登录",
      image: "/projects/ssubito.png",
      imageAlt: "SSubITO 登录界面",
      href: "https://ssubito.subit.org.cn/",
      linkLabel: "通过 SSO 登录 >",
      description: "SubIT 项目共用的账户系统。注册并登录一次后，即可使用接入 SSubITO 的项目，省去为每个项目重新注册和登录的步骤。",
    },
    {
      type: "running",
      title: "北大附中创意写作官网",
      image: "/projects/youthwrite1.png",
      imageAlt: "北大附中创意写作官网界面",
      href: "https://youthwrite.subit.org.cn/",
      linkLabel: "进入创意写作工坊 >",
      description: "SubIT 独立开发的校内写作交流网站。师生可以发表小说、诗歌等原创作品，通过点赞、评论、收藏和回复交流，也可以在文章热度排行中发现新作品。",
    },
    {
      type: "running",
      title: "SubIT 官网",
      image: "/projects/subit-website.png",
      imageAlt: "SubIT 官网往期首页",
      href: "https://subit.org.cn/",
      linkLabel: "访问 SubIT 官网 >",
      description: "SubIT 社团的官方网站，集中展示社团历史、项目成果和历届成员，也提供招新信息与技术支持入口。",
    },
    {
      type: "running",
      title: "SubQuiz 答题系统",
      monogram: "Quiz",
      description: "SubIT 开发的答题系统，为答题活动提供线上入口。",
    },
    {
      type: "running",
      title: "技术学考报名系统",
      image: "/projects/tech-exam-registration.jpg",
      imageAlt: "技术学考报名系统登录页",
      description: "服务于北大附中技术学考的校内报名系统，同学可登录系统进行报名。",
    },
    {
      type: "running",
      title: "VR 北大附中",
      monogram: "VR",
      description: "由 SubIT 的 VRFZ 小组自主研发的开放世界校园漫步应用。项目仍在开发中，目标是让师生在桌面设备上漫步虚拟校园，感受校内风光。",
    },
    {
      type: "running",
      title: "SubmIT 问卷收集与管理平台",
      monogram: "SubmIT",
      description: "SubIT 开发的问卷平台，用于收集问卷回复并集中管理。",
    },
    {
      type: "archived",
      title: "2020 毕业典礼 · 邀请函生成器",
      image: "/legacy/achievements/invite.jpg",
      imageAlt: "2020 届毕业典礼邀请函",
      description: "为 2020 届毕业典礼暨成人礼制作的邀请函生成器。输入姓名和书院并上传头像，即可生成一张带有典礼信息和直播入口的专属邀请函。",
    },
    {
      type: "archived",
      title: "北大附中 Adobe 下载中心",
      image: "/legacy/achievements/download-station.jpg",
      imageAlt: "北大附中 Adobe 下载中心页面",
      description: "面向校内师生的软件资源页面，集中提供学校购买的正版 Adobe 软件安装包，并附上安装教程和常见问题解答。",
    },
    {
      type: "archived",
      title: "博雅学院 · 口述史项目官网",
      image: "/legacy/achievements/oral-history.jpg",
      imageAlt: "博雅学院口述史项目网站",
      description: "博雅学院口述史课程的线上展示网站，收录课程采访和相关影像资料，方便读者回看学员的口述史作品。",
    },
    {
      type: "archived",
      title: "SubAI",
      monogram: "AI",
      description: "面向新生的校园问答助手。项目探索使用大语言模型回答新生守则、选课、成绩查询及申诉等常见问题，帮助同学熟悉高中生活。",
    },
    {
      type: "archived",
      title: "银杏拾影",
      monogram: "银杏",
      description: "SubIT 在校庆期间开展的项目，现已存档。",
    },
    {
      type: "archived",
      title: "Explore 5 透明显示器",
      image: "/projects/explore-5.webp",
      imageAlt: "Explore 5 透明 LCD 显示器海报",
      description: "SubIT 参与制作的透明 LCD 显示器，通过透明屏幕呈现画面，保留屏幕后方物体的可见性。",
    },
    {
      type: "archived",
      title: "SubBoard 校园信箱",
      monogram: "SB",
      href: "https://github.com/subitlab/subboard-backend",
      linkLabel: "查看服务端源码 >",
      description: "轻量网页信箱，由前端与 Java 服务组成。项目探索社团内部信息更直接、更轻量的收发方式，也保留着早期成员从零搭起完整前后端的一次尝试。",
    },
    {
      type: "archived",
      title: "SubAV 开源媒体 SDK",
      monogram: "AV",
      href: "https://github.com/subitlab/SubAV",
      linkLabel: "查看 SubAV 源码 >",
      description: "面向社团 VRFZ 游戏项目的开源音视频 SDK，包含自有图像格式 Xiao/OVC、媒体工具与 C++20 核心库，目标是在 Windows/Linux 上提供更轻量的媒体导入导出能力。",
    },
    {
      type: "event",
      title: "信息化入教",
      image: "/projects/it-onboarding.png",
      imageAlt: "SubIT 信息化入教活动现场",
      description: "自 2020 年开展的高中新生信息化入学教育，由 SubIT 为新生提供校园信息化系统使用培训，帮助同学上手日常学习所需的平台和工具。",
    },
    {
      type: "event",
      title: "SubIT 技术课 · 编程应用俱乐部",
      image: "/legacy/achievements/tech-class.jpg",
      imageAlt: "SubIT 技术课课堂合影",
      description: "SubIT 自主设计并授课的零基础编程课，自 2021 年秋季开展。同学从 Python 和海龟绘图入门，再学习 HTML，并在老师的指导下把自己的设计做成个人博客。",
    },
    {
      type: "event",
      title: "2025 校内 CTF 闯关",
      monogram: "CTF",
      href: "https://github.com/subitlab/CTF-Game-2025",
      linkLabel: "查看活动源码 >",
      description: "2025 年校内 CTF 闯关活动使用的问题与运行平台，包含题目、用户和运行文件等模块，以游戏化关卡组织网络安全实践。",
    },
  ],
  join: {
    intro: "欢迎对技术、设计、内容或运营感兴趣的北大附中同学加入 SubIT。社团通常在 3 至 4 月和 8 至 10 月开展春秋两季招新，具体时间和报名方式见招新中转站。在社团里，学长学姐会带新成员上手项目，毕业校友也会分享经验。",
    recruitmentLink: "https://pkuschool.yuque.com/subit",
    membersTitle: "历届社员",
    members: [],
    memberSections: [
      {
        title: "历届社员列表",
        collapsible: true,
        sessions: [
          {
            name: "2029届",
            members: [
              {name: "谌文轩", house: ""},
              {name: "王润青", house: ""},
              {name: "刘语彤", house: ""},
              {name: "于行健", house: ""},
              {name: "张博涵", house: ""},
              {name: "张心羽", house: ""},
              {name: "钱思成", house: ""},
              {name: "杨以诺", house: ""},
              {name: "王子禛", house: ""},
              {name: "杨昊霖", house: ""},
            ],
          },
          {
            name: "2028届",
            members: [
              {name: "石李璐", house: ""},
              {name: "王江彤", house: ""},
              {name: "蒋瑞嘉", house: ""},
              {name: "桂左安芸", house: ""},
              {name: "姜锦融", house: ""},
              {name: "赵祥辰", house: ""},
              {name: "文瑾瑜", house: ""},
              {name: "侍正航", house: ""},
            ],
          },
          {
            name: "2027届",
            members: [
              {name: "黄煦然", house: "社长 | 弘毅"},
              {name: "郝心澄", house: "副社长 | 诚意"},
              {name: "陈朴思", house: "副社长 | 格物"},
              {name: "王思齐", house: "弘毅"},
              {name: "王雨晴", house: "诚意"},
              {name: "李一可", house: "正心"},
              {name: "李思源", house: "诚意"},
              {name: "刘望轩", house: "新民"},
              {name: "杜闻熹", house: "新民"},
              {name: "沈欣怡", house: "格物"},
              {name: "吴宗汉", house: "熙敬"},
              {name: "王士涵", house: "格物"},
              {name: "周宜萱", house: "诚意"},
            ],
          },
          {
            name: "2026届",
            members: [
              {name: "黄若原", house: "社长 | 弘毅"},
              {name: "赵晗犀", house: "副社长 | 正心"},
              {name: "赵伯焘", house: "格物"},
              {name: "徐栩", house: "正心"},
              {name: "唐月钦", house: "正心"},
              {name: "史历新", house: "正心"},
              {name: "李知晓", house: "格物"},
              {name: "杜恒毅", house: "诚意"},
              {name: "申一君", house: "正心"},
              {name: "张奕驰", house: "格物"},
              {name: "汪越嘉", house: "明德"},
              {name: "邵悦晴", house: "致知"},
              {name: "李梓萌", house: "正心"},
              {name: "刘春琴", house: "诚意"},
              {name: "刘宇宸", house: "诚意"},
              {name: "郭耀阳", house: "弘毅"},
              {name: "崔浡然", house: "新民"},
            ],
          },
          {
            name: "2025届",
            members: [
              {name: "孔德宸", house: "社长 | 明德"},
              {name: "王宣懿", house: "副社长 | 诚意"},
              {name: "王雪滢", house: "诚意"},
              {name: "朱舜中", house: "致知"},
              {name: "张皓然", house: "弘毅"},
              {name: "宋易航", house: "明德"},
              {name: "查羽洋", house: "明德"},
              {name: "虞杰宁", house: "致知"},
              {name: "许锦程", house: "至善"},
              {name: "廖士诚", house: "诚意"},
              {name: "来子恒", house: "正心"},
            ],
          },
        ],
      },
      {
        title: "养老院·荣誉社员",
        sessions: [
          {
            name: "2024届",
            members: [
              {name: "魏子峰", house: "社长 | 致知"},
              {name: "李若水", house: "副社长 | 熙敬"},
              {name: "黄尚禹", house: "弘毅"},
              {name: "吴建松", house: "明德"},
              {name: "潘颜紫", house: "熙敬"},
              {name: "王鹏程", house: "诚意"},
              {name: "郑茹伊", house: "正心"},
              {name: "马彰彤", house: "明德"},
              {name: "杨天羽", house: "新民"},
              {name: "吴方雨", house: "熙敬"},
              {name: "郭鹏飞", house: "熙敬"},
            ],
          },
          {
            name: "2023届",
            members: [
              {name: "刘宇宸", house: "社长 | 致知"},
              {name: "张子健", house: "副社长 | 明德"},
              {name: "张景行", house: "弘毅"},
              {name: "夏博扬", house: "弘毅"},
              {name: "李正妍", house: "正心"},
              {name: "李阅天", house: "格物"},
              {name: "麻思齐", house: "弘毅"},
            ],
          },
          {
            name: "2022届",
            members: [
              {name: "何天阳", house: "社长 | 正心"},
              {name: "高幸", house: "副社长 | 格物"},
              {name: "张颜开", house: "副社长 | 致知"},
              {name: "张文思", house: "明德"},
              {name: "沈俊阳", house: "诚意"},
              {name: "陈梓宁", house: "正心"},
              {name: "姚天佾", house: "明德"},
              {name: "刘致嘉", house: "格物"},
            ],
          },
          {
            name: "2021届",
            members: [
              {name: "刘语辰", house: "养老院院长 | 诚意"},
              {name: "盛天成", house: "新民"},
              {name: "陈叙传", house: "明德"},
              {name: "朱文鹤", house: "格物"},
              {name: "吴欣桐", house: "正心"},
            ],
          },
        ],
      },
    ],
  },
  submore: {
    intro: "在 SubIT，技术之外还有一套自己的社团文化：有人负责把系统跑通，有人负责把笑点接住。这里收藏历任社长、社活片段，以及成员们不太正经但很真实的自我介绍。",
    pots: [
      {image: "/pot_mk1.png", title: "铁锅 Mk.1", subtitle: "2021届社长｜刘语辰"},
      {image: "/pot_mk2.png", title: "铁锅 Mk.2", subtitle: "2022届社长｜何天阳"},
      {image: "/pot_mk3.jpg", title: "铁锅 Mk.3", subtitle: "2023届社长｜刘宇宸"},
    ],
    photos: [
      {image: "/legacy/group-2025.jpg", description: "SubIT 2025 届社团合影"},
      {image: "/legacy/group2.jpg", description: "社团合影｜把想做事的人聚在一起"},
      {image: "/legacy/group1.jpg", description: "社团合影｜技术之外，也一起出现"},
      {image: "/legacy/team-working.jpg", description: "社团活动记录｜成员一起工作"},
      {image: "/submore_photo.jpg", description: "社活一角｜脆皮四绘于 2025 年夏"},
    ],
    wordcloud: "/wordcloud.png",
    quotes: [
      {text: "赛博旺证遗民这辈子也就这样了，鉴定为屁股决定上限", author: "YJN"},
    ],
    memberProfiles: [
      {
        name: "张景行",
        cohort: "2023届",
        role: "三角巨神 & 大金牌",
        image: "/legacy/members/zjx.jpg",
        description: "一个目前技术水平停留在主流语言都会写个 Hello World，外加会用 C++ 写各种东西（然而停留在了 C++98/03 & STL）。",
      },
      {
        name: "夏博扬",
        cohort: "2023届",
        role: "技术开发 & 帮助支持",
        image: "/legacy/members/xby.jpg",
        description: "一个想学前端没引路人，想写后端被别人抢的菜鸡 QAQ。",
      },
      {
        name: "何天阳",
        cohort: "2022届",
        role: "技术开发 & 肝帝",
        image: "/legacy/members/hty.jpg",
        description: "一个特别弱弱弱的人，没事写写代码，划划水。有的时候也能排排版（",
      },
    ],
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
