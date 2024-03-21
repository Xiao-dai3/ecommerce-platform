package org.ecommerceplatform.controller;

import org.ecommerceplatform.controller.viewobject.ItemVO;
import org.ecommerceplatform.dataobject.ItemDO;
import org.ecommerceplatform.error.BusinessException;
import org.ecommerceplatform.response.CommonReturnType;
import org.ecommerceplatform.service.ItemService;
import org.ecommerceplatform.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController("/item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", originPatterns = "*")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "description")String description,
                                       @RequestParam(name = "price")BigDecimal price,
                                       @RequestParam(name = "stock")Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVO);
    }

    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem() {
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }
    @PostMapping("/find")
    public CommonReturnType find(@RequestParam(name = "title")String title){
        List<ItemModel> itemModelList = itemService.find(title);

        return CommonReturnType.create(itemModelList);
    }
    @PutMapping("/modify")
    public CommonReturnType modifyItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "description")String description,
                                       @RequestParam(name = "price")BigDecimal price,
                                       @RequestParam(name = "stock")Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModelForReturn = itemService.modifyItem(itemModel);
        ItemVO itemVO = convertFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVO);

    }

    @PutMapping("/delete")
    public CommonReturnType deleteItem(@RequestParam(name = "title")String title) throws BusinessException {
        List<ItemModel> itemModelList = itemService.find(title);
        if (itemModelList == null || itemModelList.size() == 0) {
            return CommonReturnType.create("商品不存在", "fail");
        }
        int res = itemService.delete(itemModelList);
        return CommonReturnType.create(res);

    }

    private ItemVO convertFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        return itemVO;
    }
}
