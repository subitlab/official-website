<script setup lang="ts">
import {onBeforeUnmount, onMounted, ref} from "vue";

const aboutRoot = ref<HTMLElement | null>(null);
const cleanup: Array<() => void> = [];

onMounted(() => {
  const root = aboutRoot.value;
  if (!root) return;

  const reducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        (entry.target as HTMLElement).classList.add("in");
        observer.unobserve(entry.target);
      }
    });
  }, {threshold: .14, rootMargin: "0px 0px -48px 0px"});

  root.querySelectorAll<HTMLElement>(".reveal, .wipe").forEach((element) => {
    const stagger = element.closest<HTMLElement>("[data-stagger]");
    if (stagger && stagger !== element) {
      const peers = Array.from(stagger.querySelectorAll<HTMLElement>(".reveal, .wipe"));
      const index = peers.indexOf(element);
      const delay = Number.parseInt(stagger.dataset.stagger ?? "0", 10);
      if (index > 0 && delay) element.style.transitionDelay = `${index * delay}ms`;
    }
    observer.observe(element);
  });
  cleanup.push(() => observer.disconnect());

  const progress = root.querySelector<HTMLElement>(".progress");
  const parallaxElements = Array.from(root.querySelectorAll<HTMLElement>("[data-parallax]"));
  let animationFrame = 0;
  const updateScrollEffects = () => {
    animationFrame = 0;
    if (progress) {
      const maximum = document.documentElement.scrollHeight - window.innerHeight;
      progress.style.width = `${maximum > 0 ? window.scrollY / maximum * 100 : 0}%`;
    }
    if (!reducedMotion) {
      parallaxElements.forEach((element) => {
        const bounds = element.getBoundingClientRect();
        if (bounds.bottom < -120 || bounds.top > window.innerHeight + 120) return;
        const speed = Number.parseFloat(element.dataset.parallax ?? "0");
        element.style.transform = `translateY(${((bounds.top + bounds.height / 2 - window.innerHeight / 2) * -speed).toFixed(1)}px)`;
      });
    }
  };
  const onScroll = () => {
    if (!animationFrame) animationFrame = window.requestAnimationFrame(updateScrollEffects);
  };
  window.addEventListener("scroll", onScroll, {passive: true});
  window.addEventListener("resize", onScroll, {passive: true});
  updateScrollEffects();
  cleanup.push(() => {
    window.removeEventListener("scroll", onScroll);
    window.removeEventListener("resize", onScroll);
    if (animationFrame) window.cancelAnimationFrame(animationFrame);
  });

  if (!reducedMotion && window.matchMedia("(pointer: fine)").matches) {
    const cards = root.querySelectorAll<HTMLElement>(".biz-card");
    const tilt = (event: MouseEvent) => {
      const card = event.currentTarget as HTMLElement;
      const bounds = card.getBoundingClientRect();
      const rotateX = ((event.clientY - bounds.top) / bounds.height - .5) * -5;
      const rotateY = ((event.clientX - bounds.left) / bounds.width - .5) * 6;
      card.style.transform = `perspective(900px) translateY(-5px) rotateX(${rotateX.toFixed(2)}deg) rotateY(${rotateY.toFixed(2)}deg)`;
    };
    const resetTilt = (event: MouseEvent) => { (event.currentTarget as HTMLElement).style.transform = ""; };
    cards.forEach((card) => {
      card.addEventListener("mousemove", tilt);
      card.addEventListener("mouseleave", resetTilt);
    });
    cleanup.push(() => cards.forEach((card) => {
      card.removeEventListener("mousemove", tilt);
      card.removeEventListener("mouseleave", resetTilt);
    }));
  }
});

onBeforeUnmount(() => cleanup.splice(0).forEach((dispose) => dispose()));
</script>

<template>
  <div ref="aboutRoot" class="about-page" id="about-top">
    <div class="progress" aria-hidden="true"></div>

    <section class="hero">
      <div class="container">
        <div class="hero-grid">
          <div>
            <div class="hero-badges">
              <span class="badge badge-primary badge-mono">ABOUT US / VOL.21</span>
              <span class="badge badge-neutral">学业管理中心 / 中心社团</span>
            </div>
            <h1>
              <span class="line"><span>用技术引领，</span></span>
              <span class="line"><span>和<em>想做事的人</em>一起。</span></span>
            </h1>
            <p class="hero-sub">SubIT 成立于 2018 年 12 月，是隶属于北大附中学业管理中心的中心社团。我们协助联系、解决全校师生在各信息化系统使用中遇到的困难与技术问题。</p>
            <div class="hero-actions">
              <a class="button primary" href="#business">看看我们在做什么 <span aria-hidden="true">→</span></a>
              <a class="text-link" href="#future">还有更多 <span class="arr" aria-hidden="true">→</span></a>
            </div>
          </div>

          <div class="terminal-wrap">
            <div class="hero-number" data-parallax=".1" aria-hidden="true">21</div>
            <div class="terminal">
              <span class="terminal-tag">EST. 2018.12</span>
              <div class="terminal-bar">
                <i class="terminal-dot red"></i><i class="terminal-dot yellow"></i><i class="terminal-dot blue"></i>
                <span class="terminal-title">subit@west-wing / support.sh</span>
                <span class="terminal-status"><i></i>响应组在线</span>
              </div>
              <div class="terminal-body">
                <p><b>subit@west-wing:~$</b> ./support --now</p>
                <p><span>[ O365 ]</span> 使用疑难工单 <strong>×1</strong> <em>→ 已接单</em></p>
                <p><span>[ 西楼屏 ]</span> 宣传屏幕系统 <em>● 运行正常</em></p>
                <p><span>[ 新生课 ]</span> 信息化入学教育 <mark>… 备课中</mark></p>
                <p><em>✓</em> 大神已就位，问题即将解决<i class="cursor"></i></p>
              </div>
            </div>
          </div>
        </div>

        <div class="stat-strip reveal" data-stagger="90">
          <div class="stat"><strong>2018<small>.12</small></strong><span><b>FOUNDED</b>社团成立</span></div>
          <div class="stat"><strong>数百<small>人次</small></strong><span><b>SUPPORT</b>各平台技术支持</span></div>
          <div class="stat"><strong>4<small> 大</small></strong><span><b>BUSINESS</b>核心业务在线</span></div>
          <div class="stat"><strong>2<small> 层</small></strong><span><b>WEST WING</b>西楼社团活动室</span></div>
        </div>
      </div>
    </section>

    <div class="effect-gap"><div class="container"><span class="wipe"></span></div></div>

    <section class="section" id="origin">
      <div class="container">
        <span class="section-label reveal">01 / Origin / 起源</span>
        <h2 class="section-title reveal">一切，始于一次<span class="highlight">「大神之招募」</span></h2>
        <div class="origin-grid">
          <div class="origin-prose reveal">
            <p><strong>2018 年 12 月</strong>，在旧博雅学院与孙玉磊老师的引导下，SubIT 正式创建。</p>
            <p>当时，Office 365 在我校全面铺开。系统复杂，又持续更新，使用疑难层出不穷，要让<strong>学生做老师</strong>，来解决这个最直接的问题。</p>
            <p>于是，以帮助全校师生解决 O365 使用中遇到的困扰为初心，便有了第一次「大神之招募」。答疑的起点，是一间办公室里举起来的一只手；后来，举起来的手越来越多。</p>
            <blockquote>「以帮助全校师生解决 O365 使用困扰为初心。」<small>这句话，写在我们最开始的地方</small></blockquote>
          </div>
          <ol class="timeline" data-stagger="130">
            <li class="hot reveal"><b>2018.12</b><h3>SubIT 成立</h3><p>由旧博雅学院与孙玉磊老师引导创建，挂靠北大附中学业管理中心。</p></li>
            <li class="reveal"><b>O365 时代</b><h3>「大神」驻场答疑</h3><p>Office 365 全校铺开，学生大神冲在使用支持第一线。</p></li>
            <li class="reveal"><b>后来</b><h3>从答疑，走向更多现场</h3><p>宣传屏幕运营、新生入学教育、教育集团成员校信息化建设，业务越走越宽。</p></li>
            <li class="reveal"><b>VOL.21 / 此刻</b><h3>新一学年，一起奋进</h3><p>一届届优秀社员接力，技术只是起点，初心直至此刻。</p></li>
          </ol>
        </div>
      </div>
    </section>

    <section class="section section-subtle" id="business">
      <div class="container">
        <div class="business-head">
          <div><span class="section-label reveal">02 / What We Do / 核心业务</span><h2 class="section-title reveal">四件事，我们认真做</h2></div>
          <p class="section-desc reveal">从日常答疑到系统运营，从突发响应到课程建设，学校信息化的第一线，一直有 SubIT 的位置。</p>
        </div>
        <div class="business-grid" data-stagger="110">
          <article class="biz-card reveal"><span class="card-index">01 / O365</span><div class="biz-icon">☁</div><h3><small>Office 365 Operations</small>O365 系统运维</h3><p>账号、权限、协同工具与各项云端服务，持续维护全校 Office 365 的稳定运行，让复杂的系统对每个人都简单一点。</p><div class="tags"><span>账号与权限</span><span>云端协同</span><span>日常巡检</span></div></article>
          <article class="biz-card reveal"><span class="card-index">02 / SUPPORT</span><div class="biz-icon">?</div><h3><small>Helpdesk &amp; Incident Response</small>答疑支持与突发响应</h3><p>面向全校师生的信息化系统答疑通道；遇到突发事件，第一时间响应、定位、协同解决，把故障时间压到最短。</p><div class="tags"><span>全校答疑</span><span>突发事件</span><span>快速响应</span></div></article>
          <article class="biz-card reveal"><span class="card-index">03 / SCREEN</span><div class="biz-icon">▣</div><h3><small>West-Wing Display Network</small>西楼宣传屏幕系统运营</h3><p>运营西楼宣传屏幕系统：内容排期、上线发布、设备维护，让信息准时、准确地出现在每一位路过的同学眼前。</p><div class="tags"><span>内容排期</span><span>设备维护</span><span>信息发布</span></div></article>
          <article class="biz-card featured reveal"><span class="card-index">04 / ONBOARDING</span><div class="biz-icon">⌁</div><h3><small>Freshman IT Curriculum</small>高中新生信息化入学教育</h3><p>从零建设并实施新生信息化入学教育课程培训体系，把高中三年要用到的信息化工具与素养，浓缩成技术第一课。</p><div class="tags"><span>课程体系</span><span>从零建设</span><span>一届又一届</span></div></article>
        </div>
      </div>
    </section>

    <section class="section structure" id="structure">
      <div class="container center-head">
        <span class="section-label reveal">03 / Structure / 社团架构</span>
        <h2 class="section-title reveal">两个事业群，一起把事做成</h2>
        <p class="section-desc reveal">技术支持和宣传外联共同进步。我们的社团结构十分简单，日常氛围十分欢脱。</p>
        <div class="org">
          <div class="org-root reveal">SubIT <i>/</i> 中心社团<small>北大附中学业管理中心</small></div>
          <div class="org-line"></div>
          <div class="org-branches" data-stagger="160">
            <article class="org-card reveal"><strong>THG</strong><h3>技术与支持事业群</h3><small>Technology &amp; Help Group</small><p><span>O365 系统运维</span><span>信息化答疑支持</span><span>突发事件响应</span><span>技术方案攻坚</span></p></article>
            <article class="org-card reveal"><strong>PCG</strong><h3>平台与内容事业群</h3><small>Platform &amp; Content Group</small><p><span>西楼屏幕运营</span><span>宣传与内容</span><span>对外联系</span><span>课程策划支持</span></p></article>
          </div>
          <p class="org-note reveal">技术支持 <b>×</b> 宣传外联，共同进步。结构简单，氛围欢脱。</p>
        </div>
      </div>
    </section>

    <section class="crew" id="crew" aria-label="社团合照">
      <div class="crew-ghost" data-parallax=".08" aria-hidden="true">CREW</div>
      <div class="container">
        <div class="center-head"><span class="section-label reveal">Interlude / 一张合照</span><h2 class="section-title reveal">一些<span class="highlight">想做事的人</span>，都在这里</h2></div>
        <figure class="crew-frame reveal"><span>SUBIT CREW</span><img src="@/assets/photo.png" alt="SubIT 社团合照"><figcaption><small>THE CREW / VOL.21</small>「快乐分锅，快乐干活」</figcaption></figure>
        <p class="crew-hint reveal">// 结构十分简单，日常氛围十分欢脱</p>
      </div>
    </section>

    <section class="section" id="west-wing">
      <div class="container"><div class="west-panel reveal"><div class="west-grid"><div><span class="section-label">04 / West Wing / 西楼</span><h2>在西楼，<br>我们有<em>两层</em>活动室。</h2><p>这是 SubIT 的据点：可以讨论新方案，可以休息片刻，也可以组团学习。门通常开着，欢迎随时来串门，哪怕只是来连个 Wi-Fi。</p></div><div class="floors" data-stagger="140"><div class="floor reveal"><strong>F1</strong><span>讨论新方案<small>BRAINSTORM &amp; BUILD</small></span></div><div class="floor reveal"><strong>F2</strong><span>休息片刻 / 组团学习<small>CHILL &amp; STUDY TOGETHER</small></span></div></div></div></div></div>
    </section>

    <div class="slogan" aria-label="社团口号"><div>快乐分锅，快乐干活！<i>■</i>快乐分锅，快乐干活！<i>■</i>快乐分锅，快乐干活！<i>■</i>快乐分锅，快乐干活！<i>■</i></div></div>

    <section class="section future" id="future">
      <div class="container"><div class="future-head"><span class="section-label reveal">05 / What's Next / 还有更多</span><h2 class="reveal">让未来，<em>无限可能。</em></h2></div><div class="future-prose"><p class="reveal">技术，对这个年轻社团而言，永远只是起点。</p><p class="reveal">三年来，我们从零建设并实施了我校的新生信息化入学教育课程培训体系，在各平台完成了数百人次的技术支持，共同参与了北大附中教育集团多个成员校的信息化建设。</p></div><blockquote class="manifesto reveal"><span>答疑解惑，说到底是一种服务，解决的是现有问题。这是起因，但无疑不是全部，甚至，不是重心。</span><br><strong>用技术引领，和一些想做事的人一起，才是。</strong></blockquote><div class="future-stats" data-stagger="90"><div class="fstat reveal"><strong>3<small> 年+</small></strong><span><b>YEARS</b>从零建设课程培训体系</span></div><div class="fstat reveal"><strong>数百<small> 人次</small></strong><span><b>PEOPLE HELPED</b>各平台技术支持</span></div><div class="fstat reveal"><strong>多<small> 所</small></strong><span><b>GROUP SCHOOLS</b>集团成员校信息化建设</span></div><div class="fstat reveal"><strong>∞</strong><span><b>POSSIBILITIES</b>资源、社员与无限可能</span></div></div><div class="future-actions reveal"><RouterLink class="button primary" to="/join">新一学年，一起奋进</RouterLink><a class="text-link light" href="#about-top">大神之招募，等你入伙 <span class="arr">→</span></a></div><p class="future-sign reveal">&gt; 这个初心，直至此刻。拥有丰富校内外资源和一届届优秀社员的 SubIT，拥有无限的可能。</p></div>
    </section>
  </div>
</template>

<style scoped lang="scss">
.about-page { color: var(--text-primary); overflow: hidden; }
.container { width: min(100%, var(--content-width)); margin: 0 auto; padding: 0 var(--page-gutter); box-sizing: border-box; }
.progress { position: fixed; z-index: 10000; top: 64px; left: 0; width: 0; height: 3px; background: var(--brand-blue); transition: width .08s linear; }
.hero { padding: 88px 0 64px; background: linear-gradient(rgba(20, 123, 209, .12) 1px, transparent 1px) 0 0 / 100% 56px, linear-gradient(90deg, rgba(20, 123, 209, .12) 1px, transparent 1px) 0 0 / 56px 100%; }
.hero-grid { display: grid; grid-template-columns: minmax(0, 1.12fr) minmax(0, .88fr); gap: 64px; align-items: center; }
.hero-badges, .hero-actions, .tags, .future-actions { display: flex; align-items: center; flex-wrap: wrap; gap: 10px; }
.badge, .section-label, .card-index, .terminal-body, .terminal-title, .terminal-status, .terminal-tag, .timeline > li > b, .org-root small, .org-card > small, .crew-frame figcaption small, .crew-hint, .floor small, .stat span b, .fstat span b, .future-sign { font-family: "JetBrains Mono", monospace; }
.badge { padding: 4px 10px; font-size: 12px; font-weight: 600; border-radius: 2px; }
.badge-primary { background: var(--brand-blue); color: white; }
.badge-neutral { background: var(--surface-subtle); color: var(--text-secondary); }
.hero h1 { margin: 28px 0 0; font-size: clamp(40px, 6.2vw, 76px); line-height: 1.2; letter-spacing: -.04em; }
.hero h1 .line { display: block; overflow: hidden; }.hero h1 .line + .line { margin-top: .1em; }.hero h1 .line span { display: block; transform: translateY(110%); animation: rise .9s cubic-bezier(.22,.61,.36,1) forwards; }.hero h1 .line:nth-child(2) span { animation-delay: .12s; }
.hero h1 em, .future h2 em, .west-panel h2 em { position: relative; z-index: 0; font-style: normal; white-space: nowrap; }.hero h1 em::after { position: absolute; z-index: -1; right: 0; bottom: .06em; left: 0; height: .16em; content: ""; background: var(--brand-blue); transform: scaleX(0); transform-origin: left; animation: wipe-in .7s .7s cubic-bezier(.22,.61,.36,1) forwards; }
.hero-sub, .section-desc { max-width: 640px; margin: 28px 0 0; color: var(--text-secondary); font-size: 17px; line-height: 1.8; }.hero-actions { margin-top: 36px; gap: 24px; }
.button { display: inline-flex; align-items: center; justify-content: center; gap: 8px; box-sizing: border-box; min-height: 48px; padding: 0 28px; border: 1px solid transparent; border-radius: 4px; font-weight: 650; text-decoration: none; transition: background-color .15s, box-shadow .15s, transform .15s; }.button.primary { background: var(--brand-blue); color: white; }.button:hover { background: var(--brand-blue); box-shadow: 0 6px 20px -6px rgba(20, 123, 209, .55); filter: brightness(.88); }.button:active { transform: translateY(1px); }
.text-link { display: inline-flex; gap: 8px; color: var(--brand-blue); font-weight: 650; text-decoration: none; }.text-link .arr { transition: transform .2s; }.text-link:hover .arr { transform: translateX(5px); }
.terminal-wrap { position: relative; }.hero-number { position: absolute; z-index: -1; top: -44px; right: -2%; color: transparent; font-size: 260px; font-weight: 700; line-height: 1; -webkit-text-stroke: 2px rgba(20, 123, 209, .2); }.terminal { position: relative; overflow: hidden; border: 1px solid #161616; border-radius: 8px; background: #161616; box-shadow: 0 16px 40px -12px rgba(13, 20, 28, .25); color: white; }.terminal-tag { position: absolute; z-index: 1; top: -16px; right: -10px; padding: 8px 14px; border-radius: 2px; background: var(--brand-blue); color: white; font-size: 12px; font-weight: 700; transform: rotate(4deg); }.terminal-bar { display: flex; align-items: center; gap: 8px; padding: 14px 18px; border-bottom: 1px solid rgba(255, 255, 255, .12); }.terminal-dot { width: 11px; height: 11px; border-radius: 50%; }.terminal-dot.red { background: rgba(255,255,255,.85); }.terminal-dot.yellow { background: color-mix(in srgb, var(--brand-blue) 55%, white); }.terminal-dot.blue { background: var(--brand-blue); }.terminal-title { margin-left: 8px; color: rgba(255, 255, 255, .55); font-size: 12px; }.terminal-status { display: inline-flex; align-items: center; gap: 7px; margin-left: auto; color: color-mix(in srgb, var(--brand-blue) 30%, white); font-size: 11px; white-space: nowrap; }.terminal-status i { width: 8px; height: 8px; border-radius: 50%; background: var(--brand-blue); box-shadow: 0 0 0 0 rgba(20, 123, 209, .6); animation: pulse 1.8s infinite; }.terminal-body { padding: 24px 22px 26px; color: rgba(255, 255, 255, .86); font-size: 13px; line-height: 1.9; }.terminal-body p { margin: 0; opacity: 0; animation: terminal-in .5s forwards; }.terminal-body p:nth-child(1) { animation-delay: .7s; }.terminal-body p:nth-child(2) { animation-delay: 1.05s; }.terminal-body p:nth-child(3) { animation-delay: 1.35s; }.terminal-body p:nth-child(4) { animation-delay: 1.65s; }.terminal-body p:nth-child(5) { animation-delay: 1.95s; }.terminal-body b, .terminal-body strong, .terminal-body em { color: color-mix(in srgb, var(--brand-blue) 30%, white); font-style: normal; }.terminal-body span { color: rgba(255,255,255,.48); }.terminal-body mark { background: transparent; color: color-mix(in srgb, var(--brand-blue) 55%, white); }.cursor { display: inline-block; width: 8px; height: 16px; margin-left: 3px; background: var(--brand-blue); vertical-align: -2px; animation: blink 1.1s steps(1) infinite; }
.stat-strip { display: grid; grid-template-columns: repeat(4, 1fr); margin-top: 72px; border-top: 1px solid rgba(20, 123, 209, .22); }.stat { padding: 28px 28px 28px 0; border-right: 1px solid rgba(20, 123, 209, .22); }.stat:not(:first-child) { padding-left: 28px; }.stat:last-child { padding-right: 0; border-right: 0; }.stat strong, .fstat strong { display: block; font-size: 34px; line-height: 1; letter-spacing: -.03em; }.stat small, .fstat small { color: var(--text-secondary); font-size: 18px; }.stat span, .fstat span { display: block; margin-top: 9px; color: var(--text-secondary); font-size: 13px; }.stat span b, .fstat span b { display: block; margin-bottom: 3px; color: var(--text-secondary); font-size: 11px; font-weight: 500; letter-spacing: .06em; }
.effect-gap { padding: 44px 0; }.wipe { display: block; height: 2px; background: linear-gradient(90deg, transparent, var(--brand-blue) 18%, rgba(20, 123, 209, .35) 52%, transparent); transform: scaleX(0); transform-origin: left; }.wipe.in { animation: wipe 1.1s cubic-bezier(.7,0,.2,1) forwards; }
.section { padding: 112px 0; }.section-subtle { background: var(--surface-subtle); }.section-label { display: inline-flex; align-items: center; gap: 10px; color: var(--text-secondary); font-size: 12px; font-weight: 500; letter-spacing: .1em; text-transform: uppercase; }.section-label::before { width: 10px; height: 10px; border-radius: 2px; content: ""; background: var(--brand-blue); }.section-title { margin: 20px 0 16px; font-size: clamp(30px, 4vw, 44px); line-height: 1.18; letter-spacing: -.02em; }.highlight { padding: 0 2px; background: linear-gradient(transparent 62%, rgba(20, 123, 209, .35) 62%); }
.origin-grid { display: grid; grid-template-columns: minmax(0, 1.05fr) minmax(0, .95fr); gap: 72px; margin-top: 56px; }.origin-prose p { margin: 0 0 22px; color: var(--text-secondary); font-size: 16.5px; line-height: 1.9; }.origin-prose strong { color: var(--text-primary); font-weight: 650; }.origin-prose blockquote { margin: 8px 0 0; padding: 24px 28px; border-left: 4px solid var(--brand-blue); border-radius: 0 8px 8px 0; background: white; box-shadow: 0 1px 2px rgba(13, 20, 28, .06); font-size: 19px; font-weight: 650; line-height: 1.6; }.origin-prose blockquote small { display: block; margin-top: 8px; color: var(--text-secondary); font-size: 14px; font-weight: 400; }.timeline { position: relative; margin: 0; padding-left: 38px; list-style: none; }.timeline::before { position: absolute; top: 8px; bottom: 8px; left: 7px; width: 2px; content: ""; background: rgba(20, 123, 209, .2); }.timeline li { position: relative; padding: 0 0 36px 8px; }.timeline li:last-child { padding-bottom: 0; }.timeline li::before { position: absolute; top: 6px; left: -30px; width: 10px; height: 10px; border: 3px solid color-mix(in srgb, var(--brand-blue) 55%, white); border-radius: 50%; content: ""; background: white; }.timeline li.hot::before { border-color: var(--brand-blue); background: var(--brand-blue); box-shadow: 0 0 0 5px rgba(20, 123, 209, .18); }.timeline h3 { margin: 5px 0 6px; font-size: 18px; }.timeline p { margin: 0; color: var(--text-secondary); font-size: 14.5px; line-height: 1.7; }.timeline > li > b { font-size: 12.5px; letter-spacing: .04em; }
.business-head { display: flex; align-items: flex-end; justify-content: space-between; gap: 32px; margin-bottom: 48px; }.business-head .section-desc { max-width: 520px; margin-bottom: 4px; }.business-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 24px; }.biz-card { position: relative; overflow: hidden; padding: 36px 32px 32px; border: 1px solid rgba(20, 123, 209, .2); border-radius: 8px; background: white; box-shadow: 0 1px 2px rgba(13, 20, 28, .06); transition: box-shadow .2s, transform .2s, border-color .2s; }.biz-card:hover { border-color: var(--brand-blue); box-shadow: 0 8px 24px -8px rgba(13, 20, 28, .16); transform: translateY(-4px); }.card-index { color: var(--text-secondary); font-size: 13px; letter-spacing: .1em; }.biz-icon { display: grid; width: 46px; height: 46px; margin: 20px 0 22px; border-radius: 4px; place-items: center; background: rgba(20, 123, 209, .12); color: var(--brand-blue); font-size: 24px; font-weight: 700; }.biz-card h3 { margin: 0 0 10px; font-size: 22px; line-height: 1.3; }.biz-card h3 small { display: block; margin-bottom: 8px; color: var(--brand-blue); font-family: "JetBrains Mono", monospace; font-size: 11px; font-weight: 500; letter-spacing: .08em; text-transform: uppercase; }.biz-card p { margin: 0; color: var(--text-secondary); font-size: 15px; line-height: 1.75; }.tags { margin-top: 20px; gap: 8px; }.tags span { padding: 3px 9px; border-radius: 2px; background: var(--surface-subtle); color: var(--text-secondary); font-family: "JetBrains Mono", monospace; font-size: 11px; }.biz-card.featured { border-color: #161616; background: #161616; color: white; }.biz-card.featured::after { position: absolute; right: -60px; bottom: -60px; width: 180px; height: 180px; border-radius: 50%; content: ""; background: radial-gradient(circle, rgba(20, 123, 209, .35), transparent 70%); }.biz-card.featured .card-index, .biz-card.featured p { color: rgba(255, 255, 255, .7); }.biz-card.featured .biz-icon { background: var(--brand-blue); color: white; }.biz-card.featured h3 small { color: #b9e1ff; }.biz-card.featured .tags span { background: rgba(255,255,255,.1); color: rgba(255,255,255,.75); }
.structure { border-top: 1px solid rgba(20, 123, 209, .18); border-bottom: 1px solid rgba(20, 123, 209, .18); }.center-head { text-align: center; }.center-head .section-label { justify-content: center; }.center-head .section-desc { margin-right: auto; margin-left: auto; }.org { margin-top: 56px; }.org-root { display: inline-flex; flex-wrap: wrap; justify-content: center; gap: 12px; padding: 16px 32px; border-radius: 8px; background: #161616; color: white; font-size: 20px; font-weight: 700; }.org-root i { color: var(--brand-blue); font-style: normal; }.org-root small { display: block; flex-basis: 100%; color: rgba(255,255,255,.55); font-size: 11px; font-weight: 400; letter-spacing: .08em; }.org-line { width: 2px; height: 36px; margin: 0 auto; background: rgba(20, 123, 209, .3); }.org-branches { display: grid; grid-template-columns: repeat(2, 1fr); gap: 28px; text-align: left; }.org-card { padding: 36px 32px; border: 2px solid rgba(20, 123, 209, .2); border-radius: 8px; background: white; transition: border-color .2s, box-shadow .2s, transform .2s; }.org-card:hover { border-color: var(--brand-blue); box-shadow: 0 8px 24px -8px rgba(13,20,28,.16); transform: translateY(-4px); }.org-card > strong { display: block; color: var(--brand-blue); font-size: 44px; line-height: 1; }.org-card h3 { margin: 12px 0 4px; font-size: 21px; }.org-card > small { color: var(--text-secondary); font-size: 11.5px; letter-spacing: .06em; }.org-card p { display: flex; flex-wrap: wrap; gap: 8px; margin: 18px 0 0; }.org-card p span { padding: 6px 12px; border: 1px solid rgba(20, 123, 209, .2); border-radius: 2px; font-size: 13.5px; }.org-note { margin: 40px 0 0; color: var(--text-secondary); }.org-note b { color: var(--text-primary); }
.crew { position: relative; overflow: hidden; padding: 140px 0; background: radial-gradient(ellipse 70% 60% at 50% 0%, white 0%, var(--surface-subtle) 70%); }.crew-ghost { position: absolute; top: 50%; left: 50%; color: transparent; font-size: clamp(160px, 30vw, 380px); font-weight: 700; line-height: 1; transform: translate(-50%, -50%); -webkit-text-stroke: 2px rgba(20, 123, 209, .12); user-select: none; }.crew-frame { position: relative; width: 400px; max-width: 100%; margin: 56px auto 0; }.crew-frame > span { position: absolute; z-index: 1; top: -16px; right: -14px; padding: 8px 14px; border-radius: 2px; background: var(--brand-blue); color: white; font-family: "JetBrains Mono", monospace; font-size: 12px; font-weight: 700; letter-spacing: .08em; transform: rotate(4deg); }.crew-frame img { display: block; width: 100%; aspect-ratio: 4 / 3; border: 1px solid rgba(20, 123, 209, .2); border-radius: 8px; object-fit: cover; box-shadow: 0 16px 40px -12px rgba(13,20,28,.25); transition: transform .5s, box-shadow .5s; }.crew-frame:hover img { box-shadow: 0 30px 70px -24px rgba(13,20,28,.35); transform: rotate(.6deg) scale(1.02); }.crew-frame figcaption { display: flex; flex-direction: column; gap: 6px; margin-top: 26px; font-size: 19px; font-weight: 650; }.crew-frame figcaption small { color: var(--brand-blue); font-size: 11px; letter-spacing: .18em; }.crew-hint { margin: 36px 0 0; color: var(--text-secondary); font-size: 12px; letter-spacing: .04em; }
.west-panel { position: relative; overflow: hidden; padding: 88px 72px; border-radius: 12px; background: #161616; color: white; text-align: left; }.west-panel::before { position: absolute; inset: 0; content: ""; background-image: linear-gradient(rgba(255,255,255,.06) 1px, transparent 1px), linear-gradient(90deg, rgba(255,255,255,.06) 1px, transparent 1px); background-size: 48px 48px; mask-image: radial-gradient(ellipse at 80% 0%, #000 0%, transparent 65%); }.west-grid { position: relative; display: grid; grid-template-columns: minmax(0, 1.15fr) minmax(0, .85fr); gap: 56px; align-items: center; }.west-panel .section-label { color: color-mix(in srgb, var(--brand-blue) 30%, white); }.west-panel h2 { margin: 24px 0 0; font-size: clamp(30px, 3.6vw, 46px); line-height: 1.22; }.west-panel h2 em { color: var(--brand-blue); }.west-panel p { max-width: 460px; margin: 18px 0 0; color: rgba(255,255,255,.7); line-height: 1.85; }.floors { display: flex; flex-direction: column; gap: 14px; }.floor { display: flex; align-items: center; gap: 20px; padding: 22px 26px; border: 1px solid rgba(255,255,255,.2); border-radius: 8px; background: rgba(255,255,255,.03); transition: border-color .2s, background .2s, transform .2s; }.floor:hover { border-color: var(--brand-blue); background: rgba(20,123,209,.12); transform: translateX(6px); }.floor > strong { min-width: 52px; color: var(--brand-blue); font-size: 34px; }.floor > span { font-size: 18px; font-weight: 650; }.floor small { display: block; margin-top: 3px; color: rgba(255,255,255,.5); font-size: 11px; font-weight: 400; letter-spacing: .08em; }
.slogan { overflow: hidden; border-top: 3px solid #161616; border-bottom: 3px solid #161616; background: var(--brand-blue); color: white; }.slogan > div { width: max-content; padding: 26px 0; font-size: clamp(26px, 3.4vw, 44px); font-weight: 700; white-space: nowrap; animation: marquee 22s linear infinite; }.slogan:hover > div { animation-play-state: paused; }.slogan i { margin: 0 36px; font-size: .5em; font-style: normal; vertical-align: middle; }
.future { position: relative; overflow: hidden; background: #161616; color: white; text-align: left; }.future::before { position: absolute; top: -200px; right: -140px; width: 520px; height: 520px; border-radius: 50%; content: ""; background: radial-gradient(circle, rgba(20,123,209,.22), transparent 68%); }.future .container { position: relative; }.future .section-label { color: color-mix(in srgb, var(--brand-blue) 30%, white); }.future h2 { margin: 22px 0 0; font-size: clamp(36px, 5.4vw, 68px); line-height: 1.12; letter-spacing: -.02em; }.future h2 em { color: var(--brand-blue); }.future-prose { display: grid; grid-template-columns: repeat(2, 1fr); gap: 28px 56px; margin-top: 56px; }.future-prose p { margin: 0; color: rgba(255,255,255,.72); line-height: 1.9; }.future-prose p:first-child { color: white; font-size: 19px; font-weight: 500; }.manifesto { margin: 48px 0 0; padding: 40px 44px; border: 1px solid rgba(255,255,255,.16); border-left: 4px solid var(--brand-blue); border-radius: 0 8px 8px 0; background: rgba(255,255,255,.03); font-size: clamp(19px, 2.2vw, 26px); font-weight: 600; line-height: 1.7; }.manifesto span { color: rgba(255,255,255,.6); font-weight: 500; }.manifesto strong { color: var(--brand-blue); }.future-stats { display: grid; grid-template-columns: repeat(4, 1fr); gap: 24px; margin-top: 64px; }.fstat { padding: 28px 24px; border: 1px solid rgba(255,255,255,.16); border-radius: 8px; background: rgba(255,255,255,.03); transition: border-color .2s, transform .2s; }.fstat:hover { border-color: var(--brand-blue); transform: translateY(-4px); }.fstat strong { color: var(--brand-blue); font-size: 40px; }.fstat small, .fstat span, .fstat span b { color: rgba(255,255,255,.6); }.fstat span b { color: rgba(255,255,255,.45); }.future-actions { margin-top: 64px; gap: 28px; }.text-link.light { color: color-mix(in srgb, var(--brand-blue) 30%, white); }.future-sign { margin: 44px 0 0; color: rgba(255,255,255,.45); font-size: 13px; }
.reveal { opacity: 0; transform: translateY(30px); transition: opacity .8s cubic-bezier(.22,.61,.36,1), transform .8s cubic-bezier(.22,.61,.36,1); }.reveal.in { opacity: 1; transform: none; }
@keyframes rise { to { transform: translateY(0); } } @keyframes wipe-in { to { transform: scaleX(1); } } @keyframes wipe { to { transform: scaleX(1); } } @keyframes pulse { 70% { box-shadow: 0 0 0 9px rgba(20,123,209,0); } 100% { box-shadow: 0 0 0 0 rgba(20,123,209,0); } } @keyframes terminal-in { from { opacity: 0; transform: translateX(-8px); } to { opacity: 1; transform: none; } } @keyframes blink { 50% { opacity: 0; } } @keyframes marquee { to { transform: translateX(-50%); } }
@media (max-width: 1023px) { .section { padding: 84px 0; }.hero { padding: 72px 0 56px; }.hero-grid, .origin-grid, .west-grid, .future-prose { grid-template-columns: 1fr; }.hero-grid { gap: 56px; }.business-head { align-items: flex-start; flex-direction: column; }.business-head .section-desc { max-width: 640px; }.stat-strip, .future-stats { grid-template-columns: repeat(2, 1fr); }.stat { padding: 24px 0 !important; border-bottom: 1px solid rgba(20,123,209,.22); }.stat:nth-child(odd) { padding-right: 20px !important; }.stat:nth-child(even) { padding-left: 20px !important; border-right: 0; }.west-panel { padding: 56px 32px; }.future-prose { gap: 20px; } }
@media (max-width: 760px) { .container { padding-right: 20px; padding-left: 20px; }.progress { top: 64px; }.hero { padding-top: 56px; }.hero h1 { font-size: clamp(40px, 12vw, 56px); }.hero-actions { align-items: stretch; flex-direction: column; }.hero-actions .button, .hero-actions .text-link { justify-content: center; }.hero-number { top: -30px; font-size: 170px; }.terminal-title { overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }.terminal-status { display: none; }.effect-gap { padding: 28px 0; }.business-grid, .org-branches { grid-template-columns: 1fr; }.biz-card { padding: 30px 24px 24px; }.org-line { display: none; }.crew { padding: 96px 0; }.crew-frame { margin-top: 40px; }.crew-frame > span { top: -13px; right: 2px; }.west-panel { padding: 44px 24px; }.floor { padding: 20px; }.floor > strong { min-width: 38px; font-size: 28px; }.slogan > div { padding: 20px 0; }.manifesto { padding: 28px 24px; }.future-stats { gap: 14px; }.fstat { padding: 22px 18px; }.future-actions { align-items: stretch; flex-direction: column; }.future-actions .button, .future-actions .text-link { justify-content: center; } }
@media (prefers-reduced-motion: reduce) { *, *::before, *::after { animation-duration: .01ms !important; animation-iteration-count: 1 !important; transition-duration: .01ms !important; }.reveal { opacity: 1; transform: none; } }
</style>
