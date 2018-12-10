package cn.com.bmsmart.hatoas.entity;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

/**
 * Description:
 *
 * @author dclar
 */
@Data
public class Order extends ResourceSupport {

    private String orderId;
    private double price;
    private int quantity;

    public Order() {
        super();
    }

    public Order(final String orderId, final double price, final int quantity) {
        super();
        this.orderId = orderId;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", price=" + price + ", quantity=" + quantity + "]";
    }


}
