package com.example.razwanul1407;

public class model {
    String name,price,productid,purl;

    public model() {
    }

    public model(String name, String price, String productid, String purl) {
        this.name = name;
        this.price = price;
        this.productid = productid;
        this.purl = purl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
