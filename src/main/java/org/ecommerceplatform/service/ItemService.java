package org.ecommerceplatform.service;

import org.ecommerceplatform.dataobject.ItemDO;
import org.ecommerceplatform.error.BusinessException;
import org.ecommerceplatform.service.model.ItemModel;

import java.util.List;

public interface ItemService {
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    List<ItemModel> listItem();
    ItemModel getItemById(Integer id);
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;

    ItemModel modifyItem(ItemModel itemModel) throws BusinessException;
    List<ItemModel> find(String title);
    int delete(List<ItemModel> itemModelList);
}
