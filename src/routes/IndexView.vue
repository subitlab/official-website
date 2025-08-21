<script setup lang="ts">

import Content from "@/components/Content.vue";
import {onMounted, ref} from "vue";
import Typed from 'typed.js';
import hljsVuePlugin from '@highlightjs/vue-plugin';
import 'highlight.js/scss/github-dark.scss';
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
  width: 100%;
  max-width: 100%;
}
.blockTitle {
  min-height: 100vh;
  background-color: #161616;
  color: white;

  .horizontalAlign {
    flex-direction: row;
    padding: 4vw;
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
        font-size: 10px;
      }
      @media (max-width: 767px) {
        font-size: 9px;
      }
    }
  }
}
</style>