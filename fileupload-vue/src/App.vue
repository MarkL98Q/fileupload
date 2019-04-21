<template>
  <div id="app">
    <chunkupload :accept="accept"></chunkupload>
  </div>
</template>

<script>
import chunkupload from "@/components/ChunkUpload";

export default {
  name: "app",
  data() {
    return {
      accept: {
        // image: ["gif", "jpg", "jpeg", "png", "bmp", "webp"],
        // video: ["mp4", "m3u8", "rmvb", "avi", "swf", "3gp", "mkv", "flv"],
        // audio: ["mp3", "wav", "wma", "ogg", "aac", "flac"],
        document: [
          "zip",
        ]
      }
      // options: {
      //   target: "http://localhost:8080/index/fileUpload", // 目标上传 URL
      //   chunkSize: 5 * 1024 * 1024, //分块大小
      //   fileParameterName: "file", //上传文件时文件的参数名，默认file
      //   maxChunkRetries: 3, //最大自动失败重试上传次数
      //   testChunks: true, //是否开启服务器分片校验
      //   // 服务器分片校验函数，秒传及断点续传基础
      //   checkChunkUploadedByResponse: function(chunk, message) {
      //     let objMessage = JSON.parse(message);
      //     if (objMessage.skipUpload) {
      //       return true;
      //     }

      //     return (objMessage.uploaded || []).indexOf(chunk.offset + 1) >= 0;
      //   }
      // },
      // attrs: {
      //   accept: "image/*"
      // },
      // query() {}
    };
  },
  components: {
    chunkupload
  }
};
</script>

<style>
</style>