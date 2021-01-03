package com.issac.service.impl;

import com.issac.enums.OrderStatusEnum;
import com.issac.enums.YesOrNo;
import com.issac.mapper.OrderItemsMapper;
import com.issac.mapper.OrderStatusMapper;
import com.issac.mapper.OrdersMapper;
import com.issac.pojo.*;
import com.issac.pojo.bo.ShopCartItemBO;
import com.issac.pojo.bo.SubmitOrderBO;
import com.issac.pojo.vo.MerchantOrdersVO;
import com.issac.pojo.vo.OrderVO;
import com.issac.service.AddressService;
import com.issac.service.ItemService;
import com.issac.service.OrderService;
import com.issac.util.DateUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-24
 * @desc:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    AddressService addressService;

    @Resource
    OrdersMapper ordersMapper;

    @Resource
    OrderItemsMapper orderItemsMapper;

    @Resource
    OrderStatusMapper orderStatusMapper;

    @Autowired
    Sid sid;

    @Resource
    ItemService itemService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderVO createOrder(List<ShopCartItemBO> shopCartList,
                               SubmitOrderBO submitOrderBO) {

        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮
        Integer postAmount = 0;
        // 1. 新订单数据保存
        String orderId = sid.nextShort();
        UserAddress userAddress = addressService.queryUserAddress(userId, addressId);
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);

        newOrder.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " "
                + userAddress.getDistrict() + " " + userAddress.getDetail());
        newOrder.setReceiverName(userAddress.getReceiver());
        newOrder.setReceiverMobile(userAddress.getMobile());

        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setTotalAmount(0);
        newOrder.setRealPayAmount(0);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());
        ordersMapper.insert(newOrder);

        // 2. 循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        // 商品原价
        Integer totalAmount = 0;
        Integer payAmount = 0;
        List<ShopCartItemBO> toBeRemovedShopCartList = new ArrayList<>();
        for (String itemSpecId : itemSpecIdArr) {
            ShopCartItemBO shopCart = getBuyCountsFromShopCart(shopCartList, itemSpecId);
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecId);
            // TODO 整合redis后，商品数量从redis中获取
            toBeRemovedShopCartList.add(shopCart);
            int buyCounts = shopCart.getBuyCounts();
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            payAmount += itemsSpec.getPriceDiscount() * buyCounts;

            // 根据商品id获得商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items items = itemService.queryItemById(itemId);
            String url = itemService.queryItemsMainImgById(itemId);

            OrderItems subOrderItem = new OrderItems();
            String subOrderId = sid.nextShort();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemName(items.getItemName());
            subOrderItem.setItemImg(url);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);

            // 用户提交订单后，扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(payAmount);
        newOrder.setUserId(null);
        ordersMapper.updateByPrimaryKeySelective(newOrder);

        // 3. 保存订单状态表
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCreatedTime(new Date());
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatusMapper.insert(orderStatus);

        // 4. 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(payAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        orderVO.setToBeRemovedShopcartList(toBeRemovedShopCartList);
        return orderVO;
    }

    /**
     * 从redis中获取商品
     * @param shopCartList
     * @param specId
     * @return
     */
    private ShopCartItemBO getBuyCountsFromShopCart(List<ShopCartItemBO> shopCartList,
                                                    String specId) {
        for (ShopCartItemBO sc : shopCartList) {
            if (sc.getSpecId().equals(specId)) {
                return sc;
            }
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Orders queryOrdersByOrderId(String orderId) {
        return ordersMapper.selectByPrimaryKey(orderId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void closeOrder() {
        // 查询所有未付款订单，判断时间是否超时（2小时）
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> list = orderStatusMapper.select(queryOrder);
        for (OrderStatus orderStatus : list) {
            Date createdTime = orderStatus.getCreatedTime();
            int days = DateUtil.daysBetween(createdTime, new Date());
            if (days >= 1) {
                doClose(orderStatus.getOrderId());
            }
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void doClose(String orderId) {
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }
}
