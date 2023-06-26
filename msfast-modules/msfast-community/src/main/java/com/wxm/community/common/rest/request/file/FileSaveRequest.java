package com.wxm.community.common.rest.request.file;

import com.wxm.base.file.annotation.FileSave;
import lombok.Data;

@Data
public class FileSaveRequest {

    @FileSave
    private String fileUrl;
}
