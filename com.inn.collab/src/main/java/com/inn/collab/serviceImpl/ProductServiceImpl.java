package com.inn.collab.serviceImpl;

import com.inn.collab.JWT.JwtFilter;
import com.inn.collab.POJO.Category;
import com.inn.collab.POJO.Product;
import com.inn.collab.constents.CollabConstents;
import com.inn.collab.dao.ProductDao;
import com.inn.collab.service.ProductService;
import com.inn.collab.utils.CollabUtils;
import com.inn.collab.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if (validateProductMap(requestMap, false)){
                    productDao.save(getProductFromMap(requestMap,false));
                    return CollabUtils.getResponseEntity("Product Added Successfully.", HttpStatus.OK);
                }
                return CollabUtils.getResponseEntity(CollabConstents.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else
                return CollabUtils.getResponseEntity(CollabConstents.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
             if (jwtFilter.isAdmin()){
                 if (validateProductMap(requestMap, true)){
                     Optional<Product>optional=productDao.findById(Integer.parseInt(requestMap.get("id")));
                     if (!optional.isEmpty()){
                         Product product = getProductFromMap(requestMap,true);
                         product.setStatus(optional.get().getStatus());
                         productDao.save(product);
                         return CollabUtils.getResponseEntity("Product Updated Successfully.",HttpStatus.OK);
                     }
                     else {
                         return CollabUtils.getResponseEntity("Product id does not exist.",HttpStatus.OK);
                     }
                 }else {
                     return CollabUtils.getResponseEntity(CollabConstents.INVALID_DATA,HttpStatus.BAD_REQUEST);
                 }
             }else {
                 return CollabUtils.getResponseEntity(CollabConstents.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
             }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if (jwtFilter.isAdmin()){
                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()){
                    productDao.deleteById(id);
                    return CollabUtils.getResponseEntity("Product Deleted Successfully.", HttpStatus.OK);
                }
                return CollabUtils.getResponseEntity("Product id does not exist.", HttpStatus.OK);
            }else {
                return CollabUtils.getResponseEntity(CollabConstents.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()){
                    productDao.updateProductStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    return CollabUtils.getResponseEntity("Product Status Updated Successfully.", HttpStatus.OK);
                }
                return CollabUtils.getResponseEntity("Product id does not exist.",HttpStatus.OK);
            }else {
                return CollabUtils.getResponseEntity(CollabConstents.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductByCategory(id),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductById(id), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
