package com.roboholic.roboholicweb.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
    
    @Autowired
    private ItemRepository itemrepo;

    @Override
    public Long addItem(Item product){
        long item_id = ((Item) itemrepo.save(product)).getItemID();
        return item_id;
    }

    @Override
    public void deleteItem(long item_id) {
        itemrepo.deleteById(item_id);
        // throw new UnsupportedOperationException("Unimplemented method 'deleteItem'");
    }

    @Override
    public Item searchItembyId(long item_id) {
        Optional<Item> optional = itemrepo.findById(item_id);
        Item product = null;
        if (optional.isPresent()){
            product = optional.get();
        } else{
            throw new RuntimeException(" product is not found for id:: "+ item_id);
        }
        return product;
    }

    @Override
    public List<Item> searchItemByName(String itemName) {
        return itemrepo.findByItemNameContaining(itemName);
    }

    @Override
    public List<Item> getAllItems(){
        return itemrepo.findAll();
    }

    @Override
    public List<Item> getItemsName(){
        return itemrepo.getItemsName();
    }
    
}
