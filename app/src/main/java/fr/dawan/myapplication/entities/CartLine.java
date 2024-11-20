package fr.dawan.myapplication.entities;

import java.util.Objects;

public class CartLine {

    private Product product;
    private int qty;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public CartLine(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    public CartLine() {
    }

    public double getTotal(){
        return qty * product.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartLine)) return false;
        CartLine cartLine = (CartLine) o;
        return qty == cartLine.qty && Objects.equals(product, cartLine.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, qty);
    }
}
