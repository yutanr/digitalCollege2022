package com.example.springbootsampleec.repositories;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.example.springbootsampleec.entities.Item;

import java.util.List;
import java.util.Optional;
 
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	Optional<Item> findById(long id);
//	Optional<Item> findBynameLike(String name);

//	Optional<Item> findByNameContaining(String name);
//	Optional<Item> findAllByNameContaining(String name);
	List<Item> findAllByNameContaining(String name);
	List<Item> findAllByDescriptionContaining(String description);
//	Optional<List<Item>> findByNameContaining(String name);

	List<Item> findAllById(long id);
}