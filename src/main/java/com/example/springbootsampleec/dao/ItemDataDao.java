package com.example.springbootsampleec.dao;

import java.io.Serializable;
import java.util.List;

import com.example.springbootsampleec.entities.Item;

public interface ItemDataDao extends Serializable {
	
	public List<Item> search(String name, String description);

}
