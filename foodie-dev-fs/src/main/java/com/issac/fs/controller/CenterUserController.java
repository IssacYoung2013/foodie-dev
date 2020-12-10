package com.issac.fs.controller;

import com.issac.fs.resource.FileResource;
import com.issac.fs.service.FdfsService;
import com.issac.pojo.Users;
import com.issac.service.center.CenterUserService;
import com.issac.util.CookieUtils;
import com.issac.util.JSONResult;
import com.issac.util.JsonUtils;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: ywy
 * @date: 2020-05-31
 * @desc:
 */
@RestController
@RequestMapping("fdfs")
public class CenterUserController extends BaseController {

    @Resource
    FdfsService fdfsService;

    @Resource
    FileResource fileResource;

    @Resource
    CenterUserService centerUserService;


    @PostMapping("uploadFace")
    public JSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = null;
        // 定义头像保存地址
        if (file != null) {
            // 获得上传文件名称
            String fileName = file.getOriginalFilename();
            if (StringUtils.isNotBlank(fileName)) {
                // 文件重命名
                String[] fileNameArr = fileName.split("\\.");
                String suffix = fileNameArr[fileNameArr.length - 1];
                if (!suffix.equalsIgnoreCase("png") &&
                        !suffix.equalsIgnoreCase("jpg") &&
                        !suffix.equalsIgnoreCase("jpeg")) {
                    return JSONResult.errorMsg("图片格式不正确");
                }
                path = fdfsService.upload(file, suffix);
            }
        } else {
            return JSONResult.errorMsg("文件不能为空！");
        }
        if (StringUtils.isBlank(path)) {
            return JSONResult.errorMsg("上传头像失败");
        }
        // 浏览器存在缓存，需要加上时间戳后缀，保证更新的图片及时刷新
        String finalUserFaceUrl = fileResource.getHost() + path;
        Users users = centerUserService.updateUserFace(userId, finalUserFaceUrl);
//        setNullProperty(users);
        // 增加令牌token，整合redis 分布式会话
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(convertUsersVO(users)), true);
        return JSONResult.ok();
    }
}
