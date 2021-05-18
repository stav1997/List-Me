/* Assignment: final
 *   Campus: Ashdod
 *   Author: Stav Shlomovich, ID: 316132802
 * */
package com.example.androidproject;
/**
 * The class Amount.
 * this class represent for every list, the amount of products she has.
 */
public class Amount {
    /**
     * The Id.
     */
    int id;
    /**
     * The Num.
     */
    int num;

    /**
     * Instantiates a new Amount.
     *
     * @param id  the id
     * @param num the num
     */
    public Amount(int id, int num) {
        this.id = id;
        this.num = num;
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
     * Gets num.
     *
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * Sets num.
     *
     * @param num the num
     */
    public void setNum(int num) {
        this.num = num;
    }
}
