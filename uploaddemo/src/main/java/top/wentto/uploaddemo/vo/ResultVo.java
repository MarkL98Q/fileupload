package top.wentto.uploaddemo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author liquanfang
 * @date 2019-04-20
 */
@Data
@Builder
public class ResultVo<T> {
    private Boolean skipUpload;
    private List uploaded;
    private String url;
}
