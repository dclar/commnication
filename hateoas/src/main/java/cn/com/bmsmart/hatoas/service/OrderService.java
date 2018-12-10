package cn.com.bmsmart.hatoas.service;

import cn.com.bmsmart.hatoas.entity.Order;

import java.util.List;

/**
 * Description:
 *
 * @author dclar
 */
public interface OrderService {

    List<Order> getAllOrdersForCustomer(String customerId);

    Order getOrderByIdForCustomer(String customerId, String orderId);
}
