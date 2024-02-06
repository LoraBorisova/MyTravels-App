package com.example.mytravels;

import androidx.appcompat.app.AppCompatActivity;

// Destination class to use in ListViewImage
public class Destination {

    // Variables for name, price and image of destination
    String hotelName;
    double price;
    int image;

    // Constructor
    public Destination(String name, double price, int image){
        this.hotelName= name;
        this.price = price;
        this.image = image;
    }

    // Getters and setters
    public void setName(String name) {
        this.hotelName = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return hotelName;
    }

    public double getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
