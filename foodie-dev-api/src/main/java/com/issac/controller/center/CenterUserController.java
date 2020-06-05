package com.issac.controller.center;

import com.issac.controller.BaseController;
import com.issac.pojo.Users;
import com.issac.pojo.bo.center.CenterUserBO;
import com.issac.resource.FileUpload;
import com.issac.service.center.CenterUserService;
import com.issac.util.CookieUtils;
import com.issac.util.DateUtil;
import com.issac.util.JSONResult;
import com.issac.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ywy
 * @date: 2020-05-31
 * @desc:
 */
@RestController
@RequestMapping("userInfo")
@Api(value = "用户信息相关接口", tags = "用户信息相关接口")
public class CenterUserController extends BaseController {

    @Resource
    CenterUserService centerUserService;

    @Resource
    FileUpload fileUpload;

    @ApiOperation(value = "用户头像修改", notes = "用户头像修改", httpMethod = "POST")
    @PostMapping("uploadFace")
    public JSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {
        // 定义头像保存地址
        String fileSpace = fileUpload.getImageUserFaceLocation();
        // 在路径上为每个用户增加用户id，用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;
        // 开始文件上传
        if (file != null) {
            FileOutputStream outputStream = null;
            try {
                // 获得上传文件名称
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    // 文件重命名
                    String[] fileNameArr = fileName.split("\\.");
                    String suffix = fileNameArr[fileNameArr.length - 1];
                    if (!suffix.equalsIgnoreCase("png") &&
                            !suffix.equalsIgnoreCase("png") &&
                            !suffix.equalsIgnoreCase("png")) {
                        return JSONResult.errorMsg("图片格式不正确");
                    }
                    // face-{userId}.png
                    String newFileName = "face-" + userId + "." + suffix;
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;
                    uploadPathPrefix += ("/" + newFileName);
                    File outFile = new File(finalFacePath);
                    if (!outFile.getParentFile().exists()) {
                        // 创建文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    // 文件输出保存到目录
                    outputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return JSONResult.errorMsg("文件不能为空！");
        }
        // 更新用户头像到数据库

        // 浏览器存在缓存，需要加上时间戳后缀，保证更新的图片及时刷新
        String finalUserFaceUrl = fileUpload.getImageServerUrl() + uploadPathPrefix
                + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        Users users = centerUserService.updateUserFace(userId, finalUserFaceUrl);
        setNullProperty(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        //todo 后续要改，增加令牌token，整合redis 分布式会话
        return JSONResult.ok();
    }

    @ApiOperation(value = "保存用户信息", notes = "保存用户信息", httpMethod = "POST")
    @PostMapping("update")
    public JSONResult updat(@RequestParam @ApiParam(name = "userId", value = "用户id", required = true) String userId,
                            @RequestBody @Valid CenterUserBO centerUserBO,
                            BindingResult result,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        if (result.hasErrors()) {
            Map<String, String> errorMsgs = getErrors(result);
            return JSONResult.errorMap(errorMsgs);
        }
        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        setNullProperty(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        //todo 后续要改，增加令牌token，整合redis 分布式会话
        return JSONResult.ok(users);
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String errorField = fieldError.getField();
            String errorMsg = fieldError.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }

    private Users setNullProperty(Users users) {
        users.setPassword(null);
        users.setRealname(null);
        users.setMobile(null);
        users.setEmail(null);
        users.setCreatedTime(null);
        users.setUpdatedTime(null);
        users.setBirthday(null);
        return users;
    }
}
