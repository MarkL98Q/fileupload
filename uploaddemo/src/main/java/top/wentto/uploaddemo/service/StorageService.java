package top.wentto.uploaddemo.service;

import top.wentto.uploaddemo.dto.MultipartFileDto;

import java.io.IOException;

/**
 * @author liquanfang
 * @date 2019-04-20
 */
public interface StorageService {

    /**
     * 删除全部数据
     */
    void deleteAll();

    /**
     * 初始化方法
     */
    void init();



    void uploadFileRandomAccessFile(MultipartFileDto param) throws IOException;
}
