package com.example.springbootsampleec.services.impl;
 
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
// gradle で追加
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.repositories.ItemRepository;
import com.example.springbootsampleec.services.ItemService;

 
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    
    @Autowired
    private Environment environment; // 環境変数を使えるように。
 
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
 
    @Transactional(readOnly = true)
    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Item> findById(long id) {
        return itemRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    @Override
    public void updateItem(long id, String name, int price, int stock, String description) {
        Item item =  findById(id).orElseThrow();
        item.setName(name);
        item.setPrice(price);
        item.setStock(stock);
        item.setDescription(description);
        itemRepository.saveAndFlush(item);
    }
    
    @Transactional
    @Override
    public void delete(long id) {
        Item item =  findById(id).orElseThrow();
        itemRepository.delete(item);
    }
 
    @Transactional
    @Override
    public void register(String name, int price, int stock, String description, List<MultipartFile> image) {
        if (((MultipartFile) image).getOriginalFilename().isEmpty()) {
            throw new RuntimeException("ファイルが設定されていません");
        }
        // 拡張子取得
        String extension = FilenameUtils.getExtension(((MultipartFile) image).getOriginalFilename());
        // ランダムなファイル名を設定
        String randomFileName = RandomStringUtils.randomAlphanumeric(20) + "." + extension;
        uploadImage((MultipartFile) image, randomFileName);
     
        // Item エンティティの生成
        Item item = new Item(null, name, price, stock, description, randomFileName, null, null);
 
        // Item を保存
        itemRepository.saveAndFlush(item);
    }
 
    private void uploadImage(MultipartFile image, String fileName) {
        // 保存先のパスを作成
        Path filePath = Paths.get(environment.getProperty("sample.images.imagedir") + fileName);
        try {
            // ファイルをバイト列に変換して書き込み
            byte[] bytes  = ((MultipartFile) image).getBytes();
            OutputStream stream = Files.newOutputStream(filePath);
            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

}