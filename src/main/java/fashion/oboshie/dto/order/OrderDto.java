package fashion.oboshie.dto.order;

import fashion.oboshie.models.AppUser;
import fashion.oboshie.models.Order;
import javax.validation.constraints.NotNull;

public class OrderDto {
    private long id;
    private @NotNull AppUser appUser;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(order.getId());
        //this.setUserId(order.getUserId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }
    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
