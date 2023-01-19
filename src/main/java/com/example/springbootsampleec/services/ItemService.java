package com.example.springbootsampleec.services;
 
import com.example.springbootsampleec.dao.impl.ItemDataDaoImpl;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
 
import java.util.List;
 
public interface ItemService {
    // 投稿一覧の取得
    List<Item> findAll();
    // ID を指定して投稿を取得
    Optional<Item> findById(long id);
    
    static // 検索
     List<Item> search(String name, String description){
    	List<Item> result = new ArrayList<Item>();
    	//すべてブランクだった場合は全件検索する
//        if ("".equals(name) && "".equals(description)){
//            result = ItemRepository.findAll();
//        }
//        else {
            //上記以外の場合、BookDataDaoImplのメソッドを呼び出す
//            result = ItemDataDaoImpl.search(name, description);
//        }
        return result;
        }
//    Optional<Item> findBynameLike(String name);
//    Optional<Item> findByNameContaining(String name);
//    List<Item> findByNameContaining(String name);
//    Optional<List<Item>> findByNameContaining(String name);
    
    
    // 商品情報を更新
    void updateItem(long id, String name, int price, int stock, String description);
    // 削除
    void delete(long id);
    // 投稿の登録
    void register(String name, int price, int stock, String description, MultipartFile image);
	
//    Optional<Item> findAllByNameContaining(String name);
    List<Item> findAllByNameContaining(String name);
    List<Item> findAllByDescriptionContaining(String description);
//	List<Item> search(String name, String description);
    
    // ブックマーク処理
    void toggleLike(User user, long item_id);
	
	
}