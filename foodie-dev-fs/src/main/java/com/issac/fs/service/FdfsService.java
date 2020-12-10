package com.issac.fs.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: ywy
 * @date: 2020-11-17
 * @desc:
 */
public interface FdfsService {
    /**
     * 上传
     * @return
     * @param file
     * @param fileExtName
     * @throws Exception
     */
    String upload(MultipartFile file, String fileExtName) throws Exception;
}
