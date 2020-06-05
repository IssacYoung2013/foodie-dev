package com.issac.service.center;

import com.issac.pojo.Users;
import com.issac.pojo.bo.center.CenterUserBO;

/**
 * @author: ywy
 * @date: 2020-05-31
 * @desc:
 */
public interface CenterUserService {

    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBO
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     * @param userId
     * @param faceUrl
     * @return
     */
    Users updateUserFace(String userId, String faceUrl);

}
