package cn.com.bmsmart.hatoas.service;

import cn.com.bmsmart.hatoas.entity.Customer;

import java.util.List;

/**
 * Description:
 *
 * @author dclar
 */
public interface CustomerService {

    Customer getCustomerDetail(String customerId);

    List<Customer> allCustomers();
}
