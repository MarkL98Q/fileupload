package top.wentto.uploaddemo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wentto.uploaddemo.dto.MultipartFileDto;
import top.wentto.uploaddemo.service.StorageService;
import top.wentto.uploaddemo.utils.FileUtils;
import top.wentto.uploaddemo.vo.ResultVo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liquanfang
 * @date 2019-04-20
 */
@Controller
@Slf4j(topic = "fileupload")
@RequestMapping("/index")
public class FileUploadController {


    @Autowired
    private StorageService storageService;

    @Value("${top.upload.dir}")
    private String finalDirPath;

    /**
     * 秒传判断，断点判断
     *
     * @return
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
    @ResponseBody
    public Object checkFileMd5(MultipartFileDto param) throws IOException {
        if (!FileUtils.isUploadLog(param, finalDirPath)) {
            return ResultVo.builder().skipUpload(false).build();
        }

        if (FileUtils.isUploadTrue(param, finalDirPath)) {
            return ResultVo.builder().skipUpload(true).url(FileUtils.getFile(param, finalDirPath)).build();
        } else {
            if (!FileUtils.isUploadConfigLog(param, finalDirPath)) {
                return ResultVo.builder().skipUpload(false).build();
            }
            File confFile = new File(FileUtils.getFileConfig(param, finalDirPath));
            byte[] completeList = org.apache.commons.io.FileUtils.readFileToByteArray(confFile);
            List<Integer> chunkList = new LinkedList<>();
            for (int i = 1; i < completeList.length; i++) {
                if (completeList[i] == Byte.MAX_VALUE) {
                    chunkList.add(i);
                }
            }
            return ResultVo.builder().skipUpload(false).uploaded(chunkList).build();
        }
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.OPTIONS)
    public ResponseEntity fileUpload() {
        return ResponseEntity.ok().build();
    }
    /**
     * 上传文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity fileUpload(MultipartFileDto param, HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            log.info("上传文件start。");
            try {
                storageService.uploadFileRandomAccessFile(param);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("文件上传失败。{}", param.toString());
            }
            log.info("上传文件end。");
        }
        return ResponseEntity.ok().body("上传成功。");
    }
}
