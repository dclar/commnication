package cn.com.bmsmart.hatoas.controller;

import cn.com.bmsmart.hatoas.entity.Customer;
import cn.com.bmsmart.hatoas.entity.Order;
import cn.com.bmsmart.hatoas.service.CustomerService;
import cn.com.bmsmart.hatoas.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Description:
 *
 * @author dclar
 */
@RestController
@RequestMapping(value = "/customers")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    /**
     * 某一用户的具体信息
     *
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable final String customerId) {
        return customerService.getCustomerDetail(customerId);
    }

    /**
     * 某一用户的某一order的具体信息
     *
     * @param customerId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/{customerId}/{orderId}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable final String customerId, @PathVariable final String orderId) {
        return orderService.getOrderByIdForCustomer(customerId, orderId);
    }

    /**
     * 某一客户的所有的orders
     *
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/{customerId}/orders", method = RequestMethod.GET, produces = {"application/hal+json"})
    public Resources<Order> getOrdersForCustomer(@PathVariable final String customerId) {
        final List<Order> orders = orderService.getAllOrdersForCustomer(customerId);
        for (final Order order : orders) {
            final Link selfLink = linkTo(methodOn(CustomerController.class).getOrderById(customerId, order.getOrderId())).withSelfRel();
            order.add(selfLink);
        }

        Link link = linkTo(methodOn(CustomerController.class).getOrdersForCustomer(customerId)).withSelfRel();
        Resources<Order> result = new Resources<>(orders, link);
        return result;
    }

    /**
     * 主入口，获取所有的用户信息
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {"application/hal+json"})
    public Resources<Customer> getAllCustomers() {

        // 查询所有的客户
        final List<Customer> allCustomers = customerService.allCustomers();

        // 循环客户信息
        for (final Customer customer : allCustomers) {

            // 获取客户ID
            String customerId = customer.getCustomerId();

            // 使用slash方法增加customerID作为path的参数 即在 localhost:8889/hateoas/customers/ 的基础上增加{customerId}
            // 从而变成 localhost:8889/hateoas/customers/{customerId} 的RESTful接口
            Link selfLink = linkTo(CustomerController.class).slash(customerId).withSelfRel();

            // 由于我们的Entity都是继承 ResourceSupport 所以可以直接Add Link
            customer.add(selfLink);

            if (orderService.getAllOrdersForCustomer(customerId).size() > 0) {

                // 定义"allOrders"的link，并且使用linkTO来绑定当前方法中的RESTful接口
                // ⚠️⚠️所谓的allOrders是当前用户的所有的orders⚠️⚠️
                final Link ordersLink = linkTo(methodOn(CustomerController.class).getOrdersForCustomer(customerId)).withRel("allOrders");
                customer.add(ordersLink);
            }
        }

        // 绑定context path的根结点的RESTful接口
        Link link = linkTo(CustomerController.class).withSelfRel();

        // 所有内容设置到Resource中进行返回
        Resources<Customer> result = new Resources<>(allCustomers, link);
        return result;
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET, produces = {"application/hal+json"})
    public List<Customer> getDefaultAllCustomers() {

        // 查询所有的客户
        final List<Customer> allCustomers = customerService.allCustomers();

        return allCustomers;
    }

}
