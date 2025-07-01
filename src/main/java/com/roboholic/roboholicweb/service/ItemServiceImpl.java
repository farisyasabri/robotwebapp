package com.roboholic.roboholicweb.service;
import java.util.ArrayList;
import java.util.List;
// import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
    
    private static final Logger logger = LoggerFactory.getLogger(FAQServiceImpl.class);
    @Autowired
    private ItemRepository itemrepo;

    @Override
    public Long addItem(Item product){
        try {
            long item_id = ((Item) itemrepo.save(product)).getItemID();
            return item_id;
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while adding new product", e);
            throw new RuntimeException("Failed to add new product due to data integrity violation");
        } catch (Exception e) {
            logger.error("Unexpected error while adding new product", e);
            throw new RuntimeException("Failed to add new product");
        }
        
    }

    @Override
    public void deleteItem(long item_id) {
        try{
            if (!itemrepo.existsById(item_id)) {
                throw new RuntimeException("Product not found with id: " + item_id);
            }
            itemrepo.deleteById(item_id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Product not found with id: " + item_id, e);
            throw new RuntimeException("Product not found with id: " + item_id);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting product", e);
            throw new RuntimeException("Failed to delete product");
        }
        
        // throw new UnsupportedOperationException("Unimplemented method 'deleteItem'");
    }

    // @Override
    // public Item getItembyId(long item_id) {
    //     Optional<Item> optional = itemrepo.findById(item_id);
    //     Item product = null;
    //     if (optional.isPresent()){
    //         product = optional.get();
    //     } else{
    //         throw new RuntimeException(" product is not found for id:: "+ item_id);
    //     }
    //     return product;
    // }
    @Override
    public Item getItembyId(long item_id) {

        try{
            return itemrepo.findByIdWithImages(item_id)
                .orElseThrow(() -> new RuntimeException("Product not found for id: " + item_id));
        } catch (Exception e) {
            logger.error("Error fetching product with id: " + item_id, e);
            throw new RuntimeException("Error fetching product");
        }
        
    }

    @Override
    public List<Item> getAllItems(){
        try{
            List<Item> items = new ArrayList<Item>();
            itemrepo.findAll().forEach(items::add);
            return items;
        } catch (Exception e) {
            logger.error("Error fetching all products", e);
            throw new RuntimeException("Error fetching products");
        }

        
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
        try{
            Optional<Item> itemoptional = itemrepo.findById(itemID);
            if(itemoptional.isPresent()){
                Item item =itemoptional.get();
                item.setItemName(product.getItemName());
                item.setItemDescription(product.getItemDescription());
                item.setItemPrice(product.getItemPrice());
                item.setItemStock(product.getItemStock());
                // item.setImageUrls(product.getImageUrls());
                // Only update images if new ones were provided
                if (product.getImageUrls() != null) {
                    item.setImageUrls(product.getImageUrls());
                }
                itemrepo.save(item);
                }
            return itemID;
        } catch (Exception e) {
            logger.error("Error updating product with id: " + itemID, e);
            throw new RuntimeException("Error updating product");
        }
    
    }      

    // @Override
    // public List<Item> searchItemByName(String name){
    //     return itemrepo.getItemsName();
    // }

        @Override
    public List<Item> searchItemByName(String name) {
        try{
            return itemrepo.findByItemNameContaining(name);   
        } catch (Exception e) {
            logger.error("Error searching product(s) by name: " + name, e);
            throw new RuntimeException("Error searching product(s)");
        }
    }
    
    @Override
    public List<Item> searchItemsByPrice(Double minPrice, Double maxPrice){
        try{
            return itemrepo.findByItemPriceBetween(minPrice, maxPrice);
        } catch (Exception e) {
            logger.error("Error searching product(s) within price range " + minPrice + " - " + maxPrice, e);
            throw new RuntimeException("Error searching product(s)");
        }
    }     
}
