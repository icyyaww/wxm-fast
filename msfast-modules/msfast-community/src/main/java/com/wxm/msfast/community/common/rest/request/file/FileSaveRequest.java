package com.wxm.msfast.community.common.rest.request.file;

import com.wxm.msfast.base.file.annotation.FileSave;
import lombok.Data;

@Data
public class FileSaveRequest {

    @FileSave
    private String fileUrl;
}
