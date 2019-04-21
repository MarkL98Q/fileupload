package top.wentto.uploaddemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import top.wentto.uploaddemo.dto.MultipartFileDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author liquanfang
 * @date 2019-04-20
 */
@Slf4j(topic = "fileupload")
public class FileUtils {
    public static String getFileMD5(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }



    /**
     * 是否有上传记录
     * @param params
     * @return
     */
    public static Boolean isUploadLog(MultipartFileDto params, String rootPath) {
        File file = new File(rootPath + params.getIdentifier());
        return file.exists();
    }

    public static Boolean isUploadConfigLog(MultipartFileDto params, String rootPath) {
        File file = new File(getFileConfig(params, rootPath));
        return file.exists();
    }



    /**
     * 检查文件是否上传完成
     * @param params
     * @param rootPath
     * @return
     */
    public static boolean isUploadTrue(MultipartFileDto params, String rootPath) {
        File file = new File(getFile(params, rootPath));
        return file.exists();
        // todo 已经校验文件md5来判断文件是否上传正确，但是前后端md5可能不一致，暂不校验
//        try {
//            String md5 = getFileMD5(file);
//            log.debug("文件"+params.getFilename()+"md5:%s",md5);
//            if (md5.equals(params.getIdentifier())) {
//                return true;
//            }
//        }catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
    }
    public static String getFileConfig(MultipartFileDto params, String rootPath) {
        return rootPath + params.getIdentifier() + "/" + params.getFilename() +".conf";
    }
    public static String getFile(MultipartFileDto params, String rootPath) {
        return rootPath + params.getIdentifier() + "/" + params.getFilename();
    }
}
