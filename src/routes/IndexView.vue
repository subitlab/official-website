<script setup lang="ts">

import Content from "@/components/Content.vue";
import {onBeforeUnmount, onMounted, ref} from "vue";
import Typed from 'typed.js';
import hljsVuePlugin from '@highlightjs/vue-plugin';
import 'highlight.js/scss/github-dark.scss';
import Separator from "@/components/Separator.vue";
import RotatingView from "@/components/RotatingView.vue";
import {useSiteContent} from "@/content/siteContent";
const highlightjs = hljsVuePlugin.component;
const siteContent = useSiteContent();
const type = ref<HTMLDivElement | null>(null);
let typed: Typed | undefined;
onMounted(() => {
  typed = new Typed(type.value, {
    strings: ['这里是北大附中的 <span class="textGradient1">{技术爱好者}</span> 聚集地, <br/>让每一个爱好者都能充分 <span class="textGradient2">&lt;&#8288;施展拳脚&#8288;&gt;</span>.'],
    typeSpeed: 50,
    fadeOut: true,
    fadeOutDelay: 3000,
    fadeOutClass: "",
    startDelay: 0,
    loop: true,
    showCursor: false
  });
});

onBeforeUnmount(() => typed?.destroy());

const code =
    "class SubIT {\n" +
    "    // 核心属性\n" +
    "    name = \"SubIT\"\n" +
    "    school = \"北京大学附属中学\"\n" +
    "    \n" +
    "\n" +
    "    // 社团使命\n" +
    "    mission = [\"创造\", \"助力\", \"放送\"]\n" +
    "    vision = \"让每一个爱好者都能充分施展拳脚\"\n" +
    "\n" +
    "\n" +
    "    // 社团结构\n" +
    "    groups = [\n" +
    "        new TechnologyAndHelpGroup(),\n" +
    "        new PlatformAndContentGroup()\n" +
    "    ]\n" +
    "    \n" +
    "\n" +
    "    // 加入我们\n" +
    "    function join(member) {\n" +
    "        if (member.isTechEnthusiast || member.isMediaCreative) {\n" +
    "            onboard(member)\n" +
    "        } else {\n" +
    "            continueLearning()\n" +
    "        }\n" +
    "    }\n" +
    "}"


</script>

<template>
  <div id="about" class="block blockTitle">
    <Content class="horizontalAlign">
      <div class="titleLeft">
        <h1>我们是</h1>
        <h1 class="blue">SubIT</h1>
        <div ref="type" class="subtitle"></div>
      </div>
      <div class="titleRight">
        <highlightjs language="javascript" :code="code"/>
      </div>
    </Content>
  </div>
  <Separator color="#161616" background-color="#ffffff"/>
  <div class="block block0">
    <span class="no">0x00</span>
    <div class="left">
      <span class="title">北大附中的中心社团</span>
      <span class="content">SubIT社团，是下属于学业管理中心的中心社团。立志于解决学校各类技术问题，包括Office365维护与新产品的开发。</span>
      <span class="title">技术爱好者的聚集地</span>
      <span class="content">我们立志于成为北大附中技术爱好者的聚集地。以中心社团的身份与资源，让附中每一位计算机爱好者能够得到发挥自身的空间。</span>
    </div>
    <div class="right">
      <img class="photo" src="@/assets/photo.png" alt="合照"/>
      <span class="note">摄于 xxxx 年春</span>
    </div>
  </div>
  <div id="responsibilities" class="block block1">
    <span class="no">0x01</span>
    <span class="title">我们的职责</span>
    <div class="main">
      <div class="frame">
        <div class="iconBox">
          <img src="@/assets/broadcasting.svg" alt="放送"/>
        </div>
        <div class="description">
          <span class="descNote">Broadcasting</span>
          <span class="descTitle">放送</span>
          <span class="descInfo">我们整合学校信息，并使用媒介进行推送。</span>
        </div>
      </div>
      <div class="frame">
        <div class="iconBox">
          <img src="@/assets/creating.svg" alt="创造"/>
        </div>
        <div class="description">
          <span class="descNote">Creating</span>
          <span class="descTitle">创造</span>
          <span class="descInfo">利用手中的资源，结合成员们的奇思妙想，建设新的信息化基础设计。</span>
        </div>
      </div>
      <div class="frame rightMost">
        <div class="iconBox">
          <img src="@/assets/helping.svg" alt="助力"/>
        </div>
        <div class="description">
          <span class="descNote">Helping</span>
          <span class="descTitle">助力</span>
          <span class="descInfo">协助新生进行信息培训，同时提供技术答疑与信息化系统维护。</span>
        </div>
      </div>
    </div>
  </div>
  <Separator color="#F7F9FC" background-color="#000000"/>
  <div id="groups" class="block block2">
    <span class="no">0x02</span>
    <div class="segment">
      <div class="left">
        <span class="title">不止于技术</span>
        <span class="content">在 SubIT，我们不止有技术。在负责技术的技术与支持事业群之外，我们还有负责宣传与外联的平台与内容事业群。这样的架构使我们社团的未来变得更加宽广。</span>
      </div>
      <div class="right flexRow">
        <div class="card">
          <span class="title">THG</span><br/>
          <span class="bold">技术与支持事业群</span><br/>
          <span>开发、测试、调优</span><br/>
          <span>Java, React, C/C++ ...</span>
        </div>
        <div class="card">
          <span class="title">PCG</span><br/>
          <span class="bold">平台与内容事业群</span><br/>
          <span>产品、内容、设计、运营</span><br/>
          <span>Figma, Canvas, 语雀 ...</span>
        </div>
      </div>
    </div>
    <div class="segment">
      <div class="left">
        <img class="photo" src="@/assets/photo.png" alt="合照"/>
        <span class="note">（THG内容）描述描述描述描述</span>
      </div>
      <div class="right">
        <img class="photo" src="@/assets/photo.png" alt="合照"/>
        <span class="note">（PCG内容）描述描述描述描述</span>
      </div>
    </div>
  </div>
  <div id="projects" class="block block3">
    <span class="no">0x03</span>
    <span class="title">我们的项目</span>
    <div class="main">
      <RotatingView :items="siteContent.projects"/>
    </div>
  </div>
</template>

<style lang="scss">
.textGradient1 {
  background: linear-gradient(90deg, #0171D4, #4A9FE0);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
.textGradient2 {
  background: linear-gradient(90deg, #FF6A6A, #FF9B9B);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
</style>

<style scoped lang="scss">
.block {
  --section-index-top: 42px;

  position: relative;
  z-index: 0;
  display: flex;
  width: 100%;
  max-width: 100%;

  .no {
    position: absolute;
    z-index: -1;
    top: var(--section-index-top);
    left: max(0px, calc((100% - var(--shell-width)) / 2));
    margin: 10px;
    color: rgba(156, 163, 175, .3);
    font: 400 clamp(64px, 6vw, 80px)/1 "JetBrains Mono", monospace;
  }
}

.blockTitle {
  min-height: 560px;
  height: clamp(560px, calc(100svh - 64px), 620px);
  background: #161616;
  color: white;

  .horizontalAlign {
    box-sizing: border-box;
    align-items: center;
    flex-direction: row;
    gap: clamp(48px, 6vw, 84px);
    width: 100%;
    max-width: var(--content-width);
    min-height: inherit;
    padding: 48px var(--page-gutter);
  }

  .titleLeft {
    width: min(42vw, 480px);
    max-width: 480px;
    margin: 0;
    word-break: keep-all;

    h1 {
      margin: 0;
      font: 700 clamp(52px, 5vw, 66px)/1.08 "JetBrains Mono", monospace;
      letter-spacing: .015em;

      &.blue { color: #2990eb; font-size: clamp(66px, 6.5vw, 88px); }

      + h1 { margin-top: 8px; }
    }

    .subtitle {
      min-height: 88px;
      margin-top: clamp(50px, 6vw, 72px);
      font-size: clamp(20px, 1.9vw, 25px);
      font-weight: 600;
      line-height: 1.65;
    }
  }

  .titleRight {
    box-sizing: border-box;
    flex: 0 1 520px;
    width: min(43vw, 520px);
    height: min(40.65vw, 520px);
    min-height: 500px;
    max-width: 100%;
    margin-left: auto;
    overflow: hidden;
    border: 1px solid rgba(255, 255, 255, .1);
    border-radius: 12px;
    background: #000;
    font-size: 11px;

    :deep(pre) {
      box-sizing: border-box;
      width: 100%;
      height: 100%;
      margin: 0;
      padding: 16px;
      overflow: hidden;
      border-radius: 16px;
      font-size: inherit;
      line-height: 16px;
    }
  }
}

.block0 {
  --section-index-top: 86px;

  box-sizing: border-box;
  align-items: center;
  justify-content: center;
  gap: 64px;
  min-height: 604px;
  padding: 44px max(24px, calc((100% - var(--section-content-width)) / 2)) 0;

  .left {
    display: flex;
    flex-direction: column;
    width: min(44vw, 492px);
    padding: 64px 0 0;

    .title { font-size: clamp(32px, 3vw, 40px); font-weight: 700; line-height: 1.25; letter-spacing: -.025em; }
    .content { margin: 18px 0 32px; color: #3d4650; font-size: clamp(17px, 1.45vw, 19px); line-height: 1.7; }
  }

  .right {
    display: flex;
    flex-direction: column;
    width: min(42vw, 500px);
    padding: 64px 0 0;

    .photo { width: 100%; border-radius: 12px; box-shadow: 0 18px 44px rgba(13,20,28,.16); }
    .note { margin-top: 12px; color: #6b7280; text-align: center; font-size: 15px; }
  }
}

.block1 {
  min-height: 650px;
  margin-top: -14px;
  flex-direction: column;
  background: #f7f9fc;

  > .title {
    width: min(calc(100% - 48px), var(--section-content-width));
    margin: 128px auto 36px;
    font-size: clamp(34px, 3vw, 42px);
    font-weight: 700;
    line-height: 1.2;
  }

  .main {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 36px;
    width: min(calc(100% - 48px), 1000px);
    margin: 0 auto;
    padding: 0 0 39px;

    .frame {
      width: min(30.6%, 310px);
      overflow: hidden;
      border: 1px solid #dfe5ec;
      border-radius: 12px;
      background: white;

      .iconBox {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 100%;
        height: 238px;
        background: #d7ebff;

        > img {
          width: auto;
          height: auto;
          max-width: 58%;
          max-height: 47%;
          object-fit: contain;
        }
      }

      .description {
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        width: 100%;
        height: auto;
        min-height: 160px;
        padding: 22px 24px 24px;
        background: white;

        .descNote { color: #42425a; font-size: 14px; }
        .descTitle { margin-bottom: 14px; font-size: 26px; font-weight: 700; }
        .descInfo { color: #343b44; font-size: 16px; line-height: 1.7; }
      }
    }
  }
}

.block2 {
  --section-index-top: 86px;

  min-height: 0;
  flex-direction: column;
  background: #000;
  color: white;

  .segment {
    display: flex;

    .left, .right { display: flex; flex-direction: column; padding: 0; }
    .left .title { font-size: clamp(34px, 3vw, 42px); font-weight: 700; letter-spacing: -.025em; }
    .left .content { margin-top: 18px; color: #d1d5db; font-size: 19px; line-height: 1.7; }
    .photo { width: 100%; border-radius: 12px; }
    .note { margin-top: 12px; color: #9ea6ad; text-align: center; font-size: 15px; }

    .right.flexRow { flex-direction: row; gap: 20px; padding-top: 16px; }
    .card {
      box-sizing: border-box;
      width: 240px;
      min-width: 0;
      min-height: 158px;
      height: auto;
      padding: 22px;
      border: 1px solid rgba(41, 144, 235, .34);
      border-radius: 10px;
      background: white;
      color: #4b5563;

      .title { color: #0a0f1a; font-size: 20px; font-weight: 700; }
      .bold { font-weight: 700; }
    }
  }

  .segment:first-of-type {
    box-sizing: border-box;
    gap: 72px;
    width: min(calc(100% - 48px), var(--section-content-width));
    min-height: 0;
    margin: 0 auto;
    padding-top: 172px;

    > .left, > .right { width: 492px; }
  }

  .segment:last-of-type {
    gap: 64px;
    width: min(calc(100% - 48px), var(--section-content-width));
    margin: 40px auto 0;
    padding-bottom: 88px;

    > .left, > .right { width: 496px; }
  }
}

.block3 {
  min-height: 730px;
  flex-direction: column;

  > .title {
    width: min(calc(100% - 48px), var(--section-content-width));
    margin: 128px auto 18px;
    font-size: clamp(34px, 3vw, 42px);
    font-weight: 700;
    line-height: 1.2;
  }

  .main { display: flex; width: 100%; padding: 0; }
}

@media (max-width: 1023px) {
  .blockTitle {
    height: auto;
    min-height: 0;

    .horizontalAlign { justify-content: center; flex-direction: column; padding: 54px 24px 68px; text-align: center; }
    .titleLeft { width: 100%; max-width: 100%; }
    .titleLeft .subtitle { margin-top: 42px; }
    .titleRight {
      align-self: center;
      width: 100%;
      max-width: calc(100vw - 32px);
      height: auto;
      min-height: 0;
      margin: 24px auto 0;
      overflow-x: auto;
      text-align: left;

      :deep(pre) { min-width: 405px; height: auto; }
    }
  }
}

@media (max-width: 560px) {
  .blockTitle {
    .horizontalAlign { padding: 44px 20px 56px; }
    .titleLeft h1 { font-size: 46px; }
    .titleLeft h1.blue { font-size: 60px; }
    .titleLeft .subtitle { min-height: 76px; margin-top: 34px; font-size: 18px; line-height: 1.6; }
    .titleRight {
      max-width: calc(100vw - 24px);
      :deep(pre) { min-width: 390px; padding: 16px; font-size: 11px; line-height: 16px; }
    }
  }
}

@media (max-width: 850px) {
  .block0, .block2 { --section-index-top: 42px; }
  .block .no { font-size: 58px; }
  .block0 {
    min-height: 0;
    flex-direction: column;
    gap: 0;
    padding: 0;

    .left { box-sizing: border-box; width: 100%; padding: 88px 24px 8px; }
    .right { box-sizing: border-box; width: 100%; padding: 28px 24px 64px; }
  }
  .block1 {
    > .title { margin: 70px 24px 32px; font-size: 36px; }
    .main { box-sizing: border-box; gap: 28px; padding: 0 20px 64px; }
    .main .frame { width: 100%; max-width: 320px; }
    .main .frame .iconBox, .main .frame .description { width: 100%; }
  }
  .block2 .segment, .block2 .segment:first-of-type, .block2 .segment:last-of-type {
    box-sizing: border-box;
    width: 100%;
    min-height: 0;
    flex-direction: column;
    gap: 0;
    padding: 0;

    > .left, > .right { box-sizing: border-box; width: 100%; padding: 60px 24px 12px; }
    .left .title { font-size: 36px; }
    .right.flexRow { flex-direction: column; gap: 18px; padding: 20px 24px 50px; }
    .card { width: 100%; }
  }
  .block2 .segment:last-of-type { margin: 0; }
  .block3 > .title { width: auto; margin: 70px 24px 24px; font-size: 36px; }
}

@media (min-width: 851px) and (max-width: 1120px) {
  .block1 .main { gap: 24px; }
  .block1 .main .frame { width: min(29vw, 298px); }
  .block1 .main .frame .iconBox, .block1 .main .frame .description { width: 100%; }
  .block2 .segment:first-of-type { gap: 40px; }
  .block2 .segment:last-of-type { gap: 48px; }
}

@media (min-width: 1600px) and (min-aspect-ratio: 2/1) {
  .blockTitle {
    height: clamp(680px, calc(100svh - 64px), 760px);

    .horizontalAlign { gap: clamp(96px, 7vw, 136px); }
  }

  .block0 {
    gap: 96px;
    min-height: 680px;

    .left, .right { width: 552px; }
  }

  .block1 {
    min-height: 700px;

    .main {
      width: min(calc(100% - 96px), var(--section-content-width));

      .frame {
        width: calc((100% - 72px) / 3);
        max-width: none;
      }
    }
  }

  .block2 {
    .segment:first-of-type {
      gap: 96px;

      > .left, > .right { width: 552px; }
    }

    .segment:last-of-type {
      gap: 64px;

      > .left, > .right { width: 568px; }
    }
  }

  .block3 { min-height: 780px; }
}

@media (max-height: 560px) and (min-width: 1024px) {
  .blockTitle { min-height: 600px; }
}
</style>
