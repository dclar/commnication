package cn.com.bmsmart.hatoas.entity;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;

/**
 * Description:
 * <p>
 * 如果需要实现Hateoas的功能，需要继承{@link ResourceSupport}
 *
 * @author dclar
 */
@Data
public class Customer extends ResourceSupport {

    private String customerId;
    private String customerName;
    private String companyName;
    private Map<String, Order> orders;

    public Customer() {
        super();
    }

    public Customer(final String customerId, final String customerName, final String companyName) {
        super();
        this.customerId = customerId;
        this.customerName = customerName;
        this.companyName = companyName;
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    public void setOrders(final Map<String, Order> orders) {
        this.orders = orders;
    }
}
