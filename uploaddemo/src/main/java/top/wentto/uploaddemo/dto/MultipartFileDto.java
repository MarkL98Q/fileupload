package top.wentto.uploaddemo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liquanfang
 * @date 2019-04-20
 */
@Data
public class MultipartFileDto {

    //总分片数量
    private int totalChunks;
    //当前为第几块分片
    private int chunkNumber;
    //当前分片大小
    private long currentChunkSize = 0L;
    //文件名
    private String filename;
    //分片对象
    private MultipartFile file;
    // MD5
    private String identifier;
}
