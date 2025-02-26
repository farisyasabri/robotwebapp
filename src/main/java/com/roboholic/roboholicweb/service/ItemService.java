package com.roboholic.roboholicweb.service;

import java.util.List;

import com.roboholic.roboholicweb.entity.Item;

public interface ItemService {
    public Long addItem(Item product);
    public void deleteItem(long item_id);
    public List<Item> searchItemByName(String search); //UC5
    public Item getItembyId(long item_id); //UC5
    public List<Item> getAllItems();
    // public List<Item> getItemsName();
    public Long updateItem(Item product, Long item_id);
    public List<Item> filterItemsByPrice(int from, int to); //UC5
    // public Item viewItem();
    // public static void saveItem(Item item) {
    // }
}
