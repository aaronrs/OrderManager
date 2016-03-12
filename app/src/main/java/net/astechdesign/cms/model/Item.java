package net.astechdesign.cms.model;

public class Item {
    public final String name;
    public final String price;
    public final String quantity;

    public Item(String name, String price, String quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
