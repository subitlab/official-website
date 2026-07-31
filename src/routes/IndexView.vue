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
  position: relative;
  z-index: 0;
  display: flex;
  width: 100%;
  max-width: 100%;

  .no {
    position: absolute;
    z-index: -1;
    top: clamp(32px, 3vw, 48px);
    left: 0;
    margin: 10px;
    color: rgba(156, 163, 175, .3);
    font: 400 100px/1 "JetBrains Mono", monospace;
  }
}

.blockTitle {
  min-height: 600px;
  height: clamp(600px, calc(100svh - 65px), 657px);
  background: #161616;
  color: white;

  .horizontalAlign {
    box-sizing: border-box;
    align-items: center;
    flex-direction: row;
    gap: clamp(28px, 3vw, 45px);
    width: 100%;
    max-width: 1488px;
    min-height: inherit;
    padding: 33px clamp(24px, 6.45vw, 96px);
  }

  .titleLeft {
    width: min(44vw, 640px);
    max-width: 640px;
    margin: 0;
    word-break: keep-all;

    h1 {
      margin: 0;
      font: 700 clamp(64px, 6vw, 90px)/1.12 "JetBrains Mono", monospace;

      &.blue { color: #2990eb; font-size: clamp(82px, 8vw, 120px); }
    }

    .subtitle {
      min-height: 120px;
      margin-top: clamp(70px, 9vw, 135px);
      font-size: clamp(24px, 2.22vw, 33px);
      font-weight: 600;
      line-height: 1.82;
    }
  }

  .titleRight {
    box-sizing: border-box;
    flex: 0 1 611px;
    width: min(42vw, 611px);
    height: min(39.72vw, 591px);
    min-height: 500px;
    max-width: 100%;
    margin-left: auto;
    overflow: hidden;
    border-radius: 16px;
    background: #000;
    font-size: 14px;

    :deep(pre) {
      box-sizing: border-box;
      width: 100%;
      height: 100%;
      margin: 0;
      padding: 24px;
      overflow: hidden;
      border-radius: 16px;
      font-size: inherit;
      line-height: 20px;
    }
  }
}

.block0 {
  box-sizing: border-box;
  justify-content: center;
  gap: clamp(40px, 11vw, 164px);
  min-height: 776px;
  padding: 0 clamp(24px, 5vw, 74px);

  .left {
    display: flex;
    flex-direction: column;
    width: min(40vw, 554px);
    padding: 145px 0 40px;

    .title { font-size: clamp(36px, 3.36vw, 50px); font-weight: 700; line-height: 1.3; }
    .content { margin: 22px 0 36px; font-size: clamp(18px, 2.01vw, 30px); line-height: 1.12; }
  }

  .right {
    display: flex;
    flex-direction: column;
    width: min(38vw, 535px);
    padding: 200px 0 40px;

    .photo { width: 100%; border-radius: 8px; box-shadow: 2px 2px 4px 1px rgba(0,0,0,.25); }
    .note { color: #848d97; text-align: center; font-size: clamp(18px, 2.01vw, 30px); }
  }
}

.block1 {
  min-height: 677px;
  margin-top: -23px;
  flex-direction: column;
  background: #f7f9fc;

  > .title {
    width: min(calc(100% - 48px), 1206px);
    margin: 82px auto 41px;
    font-size: clamp(38px, 3.23vw, 48px);
    font-weight: 700;
    line-height: 59px;
  }

  .main {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 70px;
    width: min(calc(100% - 48px), 1034px);
    margin: 0 auto;
    padding: 0 0 39px;

    .frame {
      width: 298px;

      .iconBox {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 298px;
        height: 298px;
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
        width: 298px;
        height: 158px;
        padding: 10px 20px;
        background: white;

        .descNote { color: #42425a; font-size: 14px; }
        .descTitle { margin-bottom: 15px; font-size: 28px; font-weight: 700; }
        .descInfo { font-size: 16px; line-height: 1.92; }
      }
    }
  }
}

.block2 {
  min-height: 936px;
  flex-direction: column;
  background: #000;
  color: white;

  .segment {
    display: flex;

    .left, .right { display: flex; flex-direction: column; padding: 0; }
    .left .title { font-size: 48px; font-weight: 700; }
    .left .content { margin-top: 20px; font-size: 22px; line-height: 33px; }
    .photo { width: 100%; border-radius: 8px; }
    .note { color: #9ea6ad; text-align: center; font-size: 30px; }

    .right.flexRow { flex-direction: row; gap: 24px; padding-top: 25px; }
    .card {
      box-sizing: border-box;
      width: 248px;
      min-width: 0;
      height: 160px;
      padding: 24px;
      border: 2px solid rgba(1, 113, 212, .2);
      border-radius: 8px;
      background: white;
      color: #4b5563;

      .title { color: #0a0f1a; font-size: 20px; font-weight: 700; }
      .bold { font-weight: 700; }
    }
  }

  .segment:first-of-type {
    box-sizing: border-box;
    gap: 81px;
    width: min(calc(100% - 48px), 1152px);
    min-height: 400px;
    margin: 0 auto;
    padding-top: 118px;

    > .left, > .right { width: 520px; }
  }

  .segment:last-of-type {
    gap: 117px;
    width: min(calc(100% - 48px), 1121px);
    margin: 0 auto;

    > .left, > .right { width: 502px; }
  }
}

.block3 {
  min-height: 836px;
  flex-direction: column;

  > .title {
    width: min(calc(100% - 48px), 1152px);
    margin: 82px auto 14px;
    font-size: clamp(38px, 3.23vw, 48px);
    font-weight: 700;
    line-height: 59px;
  }

  .main { display: flex; width: 100%; padding: 0; }
}

@media (max-width: 1023px) {
  .blockTitle {
    .horizontalAlign { justify-content: center; flex-direction: column; padding: 54px 24px; text-align: center; }
    .titleLeft { width: 100%; max-width: 100%; }
    .titleLeft .subtitle { margin-top: 70px; }
    .titleRight {
      align-self: center;
      width: 100%;
      max-width: calc(100vw - 32px);
      height: auto;
      min-height: 0;
      margin: 32px auto 0;
      overflow-x: auto;
      text-align: left;

      :deep(pre) { min-width: 405px; height: auto; }
    }
  }
}

@media (max-width: 850px) {
  .block .no { font-size: 70px; }
  .block0 {
    min-height: 0;
    flex-direction: column;
    gap: 0;
    padding: 0;

    .left { box-sizing: border-box; width: 100%; padding: 100px 24px 10px; }
    .right { box-sizing: border-box; width: 100%; padding: 30px 24px 64px; }
  }
  .block1 {
    > .title { margin: 64px 24px 36px; font-size: 42px; }
    .main { box-sizing: border-box; gap: 28px; padding: 0 20px 64px; }
    .main .frame { width: 100%; max-width: 298px; }
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
    .left .title { font-size: 42px; }
    .right.flexRow { flex-direction: column; gap: 18px; padding: 20px 24px 50px; }
    .card { width: 100%; }
  }
  .block3 > .title { width: auto; margin: 64px 24px 24px; font-size: 42px; }
}

@media (min-width: 851px) and (max-width: 1120px) {
  .block1 .main { gap: 24px; }
  .block1 .main .frame { width: min(29vw, 298px); }
  .block1 .main .frame .iconBox, .block1 .main .frame .description { width: 100%; }
  .block2 .segment:first-of-type { gap: 40px; }
  .block2 .segment:last-of-type { gap: 48px; }
}

@media (max-height: 560px) and (min-width: 1024px) {
  .blockTitle { min-height: 600px; }
}
</style>
