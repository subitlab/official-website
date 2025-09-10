<script setup lang="ts">

import Content from "@/components/Content.vue";
import {onMounted, ref} from "vue";
import Typed from 'typed.js';
import hljsVuePlugin from '@highlightjs/vue-plugin';
import 'highlight.js/scss/github-dark.scss';
import Separator from "@/components/Separator.vue";
import RotatingView from "@/components/RotatingView.vue";
import Explore5 from "@/components/achievements/Explore5.vue";
import SSubITO from "@/components/achievements/SSubITO.vue";
import YouthWrite from "@/components/achievements/YouthWrite.vue";
const highlightjs = hljsVuePlugin.component;
const type = ref<HTMLDivElement | null>(null);
onMounted(() => {
  new Typed(type.value, {
    strings: ['这里是北大附中的 <span class="textGradient1">{技术爱好者}</span> 聚集地, <br/>让每一个爱好者都能充分 <span class="textGradient2">&lt;&#8288;施展拳脚&#8288;&gt;</span>.'],
    typeSpeed: 50,
    showCursor: false
  });
});

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
  <div class="block blockTitle">
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
  <div class="block block1">
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
  <div class="block block2">
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
  <div class="block block3">
    <span class="no">0x03</span>
    <span class="title">我们的项目</span>
    <div class="main">
      <RotatingView :components="[Explore5, SSubITO, YouthWrite]"/>
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
.blockTitle {
  min-height: 100vh;
  background-color: #161616;
  color: white;

  .horizontalAlign {
    flex-direction: row;
    padding: 6vw 4vw 4vw;
    width: 92vw;
    @media (max-width: 1023px) {
      padding-top: 3vw;
      flex-direction: column;
      text-align: center;
    }
    .titleLeft {
      display: block;
      max-width: 44vw;
      word-break: keep-all;
      margin-left: 0;
      margin-right: 2vw;
      @media (max-width: 1023px) {
        max-width: 100%;
      }
      h1 {
        font-family: 'JetBrains Mono', monospace;
        font-size: 80px;
        font-weight: 700;
        line-height: 1.5em;
        margin: 0;

        &.blue {
          color: #2990EB;
        }

        @media (max-width: 767px) {
          font-size: 50px;
        }
      }
      .subtitle {
        margin-top: 100px;

        font-size: 30px;
        font-weight: 600;
        line-height: 1.5em;

        @media (max-width: 767px) {
          font-size: 20px;
        }
      }
    }
    .titleRight {
      margin-left: auto;
      font-size: 12px;
      @media (max-width: 1023px) {
        align-self: center;
        margin-right: auto;
        margin-top: 4em;
        text-align: left;
      }
      @media (max-width: 767px) {
        font-size: 10px;
      }
    }
  }
}
.block0 {
  flex-direction: row;
  .left {
    display: flex;
    flex-direction: column;
    padding: 80px 6vw;
    .title {
      font-size: 40px;
      font-weight: 700;
    }
    .content {
      font-size: 20px;
      margin-bottom: 2em;
    }
  }
  .right {
    display: flex;
    flex-direction: column;
    padding: 80px 4vw;
    .photo {
      width: 40vw;
    }
    .note {
      color: #848D97;
      text-align: center;
      font-size: 20px;
    }
  }
}
.block1 {
  background: #F7F9FC;

  flex-direction: column;

  .title {
    font-size: 60px;
    font-weight: 700;
    margin: 80px 6vw;
  }

  .main {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    width: calc(100% - 12vw);
    padding: 0 6vw 80px;

    .frame {
      flex-grow: 1;
      width: 300px;

      &.rightMost {
        flex-grow: 0;
      }

      .iconBox {
        width: 300px;
        height: 300px;
        background-color: #D7EBFF;

        display: flex;
        flex-direction: column;
        justify-content: center;

        img {
          height: 150px;
        }
      }

      .description {
        display: flex;
        flex-direction: column;
        width: 240px;
        height: 120px;
        padding: 30px;
        background-color: white;

        .descNote {
          font-size: 12px;
          color: #42425A;
        }
        .descTitle {
          font-size: 30px;
          font-weight: 700;
          margin-bottom: 15px;
        }
        .descInfo {
          font-size: 15px;
        }
      }
    }
  }
}
.block2 {
  background-color: #000000;
  color: white;

  flex-direction: column;
  .segment {
    display: flex;
    flex-direction: row;
    .left {
      display: flex;
      flex-direction: column;
      padding: 80px 5vw;
      .title {
        font-size: 60px;
        font-weight: 700;
      }
      .content {
        font-size: 20px;
        margin-bottom: 2em;
      }
      .photo {
        width: 40vw;
      }
      .note {
        color: #848D97;
        text-align: center;
        font-size: 20px;
      }
    }
    .right {
      display: flex;
      flex-direction: column;
      padding: 80px 5vw;
      &.flexRow {
        flex-direction: row;
        gap: 40px;
        padding-top: 160px;
      }
      .card {
        align-content: center;
        min-width: 180px;
        min-height: 30px;
        padding: 20px;
        border-radius: 20px;
        border: 2px solid rgba(1, 113, 212, 0.20);
        background: white;
        color: #4B5563;

        .title {
          font-size: 18px;
          font-weight: 700;
          color: black;
        }
        .bold {
          font-weight: 700;
        }
      }
      .photo {
        width: 40vw;
      }
      .note {
        color: #848D97;
        text-align: center;
        font-size: 20px;
      }
    }
  }
}
.block3 {
  flex-direction: column;

  .title {
    font-size: 60px;
    font-weight: 700;
    margin: 80px 6vw;
  }

  .main {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    width: 100%;
    padding: 0 0 80px;
  }
}
.block {
  position: relative;
  width: 100%;
  max-width: 100%;

  display: flex;
  z-index: 0;

  .no {
    position: absolute;
    top: 0;
    left: 0;
    z-index: -1;
    margin: 10px;
    font-family: "JetBrains Mono", monospace;
    font-size: 100px;
    font-weight: 400;
    color: #9CA3AF4D;
  }
}
</style>