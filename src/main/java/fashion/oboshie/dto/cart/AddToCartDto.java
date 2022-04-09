package fashion.oboshie.dto.cart;

import fashion.oboshie.models.Cart;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AddToCartDto {
    private long id;
    private @NotNull long productId;
    private @NotNull Integer quantity;

//    public AddToCartDto() {
//    }
//
//
//
//    @Override
//    public String toString() {
//        return "CartDto{" +
//                "id=" + id +
//                ", productId=" + productId +
//                ", quantity=" + quantity +
//                ",";
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//
//    public long getProductId() {
//        return productId;
//    }
//
//    public void setProductId(long productId) {
//        this.productId = productId;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
}
