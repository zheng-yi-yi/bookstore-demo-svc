package com.bookstore.springboot.modules.order;

/**
 * 订单状态枚举
 */
public enum OrderStatus {
    /**
     * 待支付
     */
    PENDING,
    /**
     * 已支付
     */
    PAID,
    /**
     * 已完成
     */
    COMPLETED,
    /**
     * 已取消
     */
    CANCELLED
}
