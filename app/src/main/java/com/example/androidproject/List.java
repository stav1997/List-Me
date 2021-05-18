/* Assignment: final
 *   Campus: Ashdod
 *   Author: Stav Shlomovich, ID: 316132802
 * */
package com.example.androidproject;
/**
 * The class List.
 * this class represent each list.
 */
public class List {
    private String listName;
    private int listId;
    private String state;
    private int count;

    /**
     * Instantiates a new List.
     *
     * @param listName the list name
     * @param listId   the list id
     * @param state    the state
     */
    public List(String listName, int listId, String state) {
        this.listName = listName;
        this.listId = listId;
        this.state = state;
        this.count=0;
    }

    /**
     * Gets list name.
     *
     * @return the list name
     */
    public String getListName() {
        return listName;
    }

    /**
     * Sets list name.
     *
     * @param listName the list name
     */
    public void setListName(String listName) {
        this.listName = listName;
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
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
    public void setCount(int count) {
        this.count = count;
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
