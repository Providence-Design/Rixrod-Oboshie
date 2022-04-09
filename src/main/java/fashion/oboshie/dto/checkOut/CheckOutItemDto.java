package fashion.oboshie.dto.checkOut;

import fashion.oboshie.models.AppUser;

public class CheckOutItemDto {

    private String productName;
    private int  quantity;
    private double price;
    private long productId;
    private AppUser appUser;

    public CheckOutItemDto() {}

    public CheckOutItemDto(String productName, int quantity, double price, long productId, AppUser appUser) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.appUser = appUser;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice(){return price;}

    public AppUser getAppUser() {
        return appUser;
    }

    public void setUserId(AppUser appUser) {
        this.appUser = appUser;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long id) {
        this.productId = id;
    }

}
