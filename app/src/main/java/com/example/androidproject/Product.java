/* Assignment: final
 *   Campus: Ashdod
 *   Author: Stav Shlomovich, ID: 316132802
 * */
package com.example.androidproject;
/**
 * The class Product.
 * this class represent the products on the lists.
 */
public class Product {
    private int id;
    private String productName;
    private String quantity;
    private String price;
    private String note;
    private String state;
    private int listId;

    /**
     * Instantiates a new Product.
     *
     * @param id          the id
     * @param productName the product name
     * @param quantity    the quantity
     * @param price       the price
     * @param note        the note
     * @param state       the state
     * @param listId      the list id
     */
    public Product(int id, String productName, String quantity, String price, String note, String state, int listId) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
        this.state = state;
        this.listId = listId;
    }

    /**
     * Is state string.
     *
     * @return the string
     */
    public String isState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets list id.
     *
     * @return the list id
     */
    public int getListId() {
        return listId;
    }

    /**
     * Sets list id.
     *
     * @param listId the list id
     */
    public void setListId(int listId) {
        this.listId = listId;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets product name.
     *
     * @param productName the product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets note.
     *
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets note.
     *
     * @param note the note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public boolean getState() {
        if (state.matches("true"))
            return true;
        return false;
    }
}
