package com.example.razwanul1407;

public class UserHelperClass {
    String phone,address;
    public UserHelperClass(){

    }
    public UserHelperClass(String phone,String address)
    {
           this.phone = phone;
           this.address=address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
