package net.astechdesign.cms.model;

import java.util.Date;

public class Order {

    public final String customer;
    public final Date orderDate;
    public final String invoice;
    public final String product;
    public final float price;
    public final int quantity;
    public final Date deliveryDate;
    public final float cost;

    public Order(String customer, Date orderDate, String invoice, String product, int quantity, float price, Date deliveryDate, float cost) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.deliveryDate = deliveryDate;
        this.cost = cost;
    }
}
