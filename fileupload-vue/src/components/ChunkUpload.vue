<template>
  <uploader
    :options="options"
    class="uploader-example"
    @file-added="onFileAdded"
    @file-success="onFileSuccess"
    @file-progress="onFileProgress"
    @file-error="onFileError"
    :autoStart="false"
    ref="uploader"
   
    :file-status-text="statusText"
  >
    <uploader-unsupport></uploader-unsupport>
    <uploader-btn :attrs="attrs">选择文件</uploader-btn>
   
    <uploader-list></uploader-list>
  </uploader>
</template>

<script>
import SparkMD5 from "spark-md5";

export default {
  data() {
    return {
      options: {
        target: "http://localhost:8080/index/fileUpload",
        chunkSize: 5 * 1024 * 1024,
        fileParameterName: "file",
        maxChunkRetries: 3,
        testChunks: true, //是否开启服务器分片校验
        // 服务器分片校验函数，秒传及断点续传基础
        checkChunkUploadedByResponse: function(chunk, message) {
          let objMessage = JSON.parse(message);
          if (objMessage.skipUpload) {
            return true;
          }
          return (objMessage.uploaded || []).indexOf(chunk.offset + 1) >= 0;
        }
      },
      attrs: {
          accept: 'application/x-zip,application/x-zip-compressed'
        },
        statusText: {
          success: '成功了',
          error: '出错了',
          uploading: '上传中',
          paused: '暂停中',
          waiting: '等待中'
        }
    };
  },
  //   props:["accept"],
  computed: {
    uploader() {
      return this.$refs.uploader.uploader;
    }
  },
  methods: {
    onFileAdded(file) {
      this.panelShow = true;
      this.computeMD5(file);
    },
    onFileProgress(rootFile, file, chunk) {
      console.log(
        `上传中 ${file.name}，chunk：${chunk.startByte /
          1024 /
          1024} ~ ${chunk.endByte / 1024 / 1024}`
      );
    },
    onFileSuccess(rootFile, file, response, chunk) {
      // let res = JSON.parse(response);
      // 服务器自定义的错误，这种错误是Uploader无法拦截的
      // if (!res.result) {
      //   this.$message({ message: res.message, type: "error" });
      //   return;
      // }

      console.log("上传成功");
    },
    onFileError(rootFile, file, response, chunk) {
      this.$message({
        message: response,
        type: "error"
      });
    },
    error(msg) {
      console.log("error:" + msg);
      // this.$notify({
      //     title: this.$t('c.false'),
      //     message: msg,
      //     type: 'error',
      //     duration: 2000
      // })
    },
    /**
     * 计算md5，实现断点续传及秒传
     * @param file
     */
    computeMD5(file) {
      let fileReader = new FileReader();
      let time = new Date().getTime();
      let md5 = "";
      file.pause();

      fileReader.readAsArrayBuffer(file.file);
      fileReader.onload = e => {
        if (file.size != e.target.result.byteLength) {
          this.error(
            "Browser reported success but could not read the file until the end."
          );
          return;
        }
        md5 = SparkMD5.ArrayBuffer.hash(e.target.result);
        // 添加额外的参数
        this.uploader.opts.query = {
          ...this.params
        };
        console.log(JSON.stringify(this.params));
        console.log(
          `MD5计算完毕：${file.id} ${
            file.name
          } MD5：${md5} 用时：${new Date().getTime() - time} ms`
        );
        file.uniqueIdentifier = md5;
        file.resume();
      };
      fileReader.onerror = function() {
        this.error(
          "FileReader onerror was triggered, maybe the browser aborted due to high memory usage."
        );
      };
    }
  }
};
</script>

<style>
.uploader-example {
  width: 880px;
  padding: 15px;
  margin: 40px auto 0;
  font-size: 12px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
}
.uploader-example .uploader-btn {
  margin-right: 4px;
}
.uploader-example .uploader-list {
  max-height: 440px;
  overflow: auto;
  overflow-x: hidden;
  overflow-y: auto;
}
</style>