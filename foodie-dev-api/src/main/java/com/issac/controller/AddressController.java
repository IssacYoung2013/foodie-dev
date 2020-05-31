package com.issac.controller;

import com.issac.pojo.UserAddress;
import com.issac.pojo.bo.AddressBO;
import com.issac.service.AddressService;
import com.issac.util.JSONResult;
import com.issac.util.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@RestController
@RequestMapping("address")
@Api(value = "地址相关", tags = {"地址相关"})
public class AddressController {

    @Resource
    AddressService addressService;

    /**
     * 1. 查询用户所有收货地址
     * 2. 新增
     * 3. 删除
     * 4. 修改
     * 5. 设置默认
     */
    @PostMapping("/list")
    @ApiOperation(value = "根据userId查询收货列表")
    public JSONResult list(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        List<UserAddress> addressList = addressService.queryAll(userId);
        return JSONResult.ok(addressList);
    }

    @PostMapping("/add")
    @ApiOperation(value = "用户新增地址", httpMethod = "POST")
    public JSONResult add(@RequestBody AddressBO addressBO) {
        JSONResult jsonResult = checkAddress(addressBO);
        if (!jsonResult.isOK()) {
            return jsonResult;
        }
        addressService.addNewUserAddress(addressBO);
        return JSONResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "用户修改地址", httpMethod = "POST")
    public JSONResult update(@RequestBody AddressBO addressBO) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return JSONResult.errorMsg("修改地址错误：addressId不能为空");
        }
        JSONResult jsonResult = checkAddress(addressBO);
        if (!jsonResult.isOK()) {
            return jsonResult;
        }
        addressService.updateUserAddress(addressBO);
        return JSONResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "用户删除地址", httpMethod = "POST")
    public JSONResult delete(@RequestParam String userId,
                             @RequestParam String addressId) {
        if (StringUtils.isBlank(addressId) ||
                StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        addressService.removeUserAddress(userId, addressId);
        return JSONResult.ok();
    }

    @PostMapping("/setDefault")
    @ApiOperation(value = "用户设置默认地址", httpMethod = "POST")
    public JSONResult setDefault(@RequestParam String userId,
                             @RequestParam String addressId) {
        if (StringUtils.isBlank(addressId) ||
                StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        addressService.setDefaultAddress(userId, addressId);
        return JSONResult.ok();
    }

    private JSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() > 12) {
            return JSONResult.errorMsg("收货人姓名不能太长");
        }
        if (!MobileEmailUtils.checkMobileIsOk(mobile)) {
            return JSONResult.errorMsg("收货人手机号格式不正确");
        }
        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String detail = addressBO.getDetail();
        String district = addressBO.getDistrict();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(detail)) {
            return JSONResult.errorMsg("收货地址信息不能为空");
        }
        return JSONResult.ok();
    }
}
