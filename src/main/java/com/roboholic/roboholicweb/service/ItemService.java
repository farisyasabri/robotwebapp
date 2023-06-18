package com.roboholic.roboholicweb.service;

import java.util.List;

import com.roboholic.roboholicweb.entity.Item;

public interface ItemService {
    public Long addItem(Item product);
    public void deleteItem(long item_id);
    public List<Item> searchItemByName(String search);
    public Item searchItembyId(long item_id);
    public List<Item> getAllItems();
    public List<Item> getItemsName();
    // public Item viewItem();
    // public static void saveItem(Item item) {
    // }
}
