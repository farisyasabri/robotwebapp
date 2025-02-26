package com.roboholic.roboholicweb.service;
import java.util.ArrayList;
import java.util.List;
// import java.util.Objects;
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
    public Item getItembyId(long item_id) {
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
    public List<Item> getAllItems(){
        List<Item> items = new ArrayList<Item>();
        itemrepo.findAll().forEach(items::add);
        return items;
    }

    // @Override
    // public List<Item> getItemsName(){
    //     return itemrepo.getItemsName();
    // }
    
    @Override
    public Long updateItem(Item product, Long itemID){
        // Item item = itemrepo.findById(itemID).get();

        // //update item name
        // if(!"".equalsIgnoreCase(item.getItemName())){
        //     item.setItemName(item.getItemName());
        // }

        //     itemrepo.save(product);
        //     return (long) item.getItemID();

    Optional<Item> itemoptional = itemrepo.findById(itemID);
    if(itemoptional.isPresent()){
        Item item =itemoptional.get();
        item.setItemName(product.getItemName());
        item.setItemDescription(product.getItemDescription());
        item.setItemPrice(product.getItemPrice());
        item.setItemStock(product.getItemStock());
        itemrepo.save(item);
        }
    return itemID;
    }      

    // @Override
    // public List<Item> searchItemByName(String name){
    //     return itemrepo.getItemsName();
    // }

        @Override
    public List<Item> searchItemByName(String name) {
        return itemrepo.findByItemNameContaining(name);
    }
    
    @Override
    public List<Item> searchItemsByPrice(Double minPrice, Double maxPrice){
        return itemrepo.findByItemPriceBetween(minPrice, maxPrice);
    }     
}
