package com.example.springbootsampleec.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springbootsampleec.dao.ItemDataDao;
import com.example.springbootsampleec.entities.Item;
import com.mysql.cj.Query;

@Component
public class ItemDataDaoImpl implements ItemDataDao{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Entityを利用するために必要な機能を提供する
    @Autowired
    private EntityManager entityManager;
    
    public ItemDataDaoImpl() {
        super();
    }
    
    public ItemDataDaoImpl(EntityManager manager) {
        this();
        entityManager = manager;
    }
    
  //Daoクラスで用意したsearchメソッドをオーバーライドする
    @SuppressWarnings("unchecked")
    @Override
    public List<Item> search(String name, String description) {

        //StringBuilderでSQL文を連結する
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT b From Item b WHERE ");

        boolean nameFlg  = false;
        boolean descriptionFlg = false;
        boolean andFlg    = false;
      //nameがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if(!"".equals(name)) {
            sql.append("b.name LIKE :name");
            nameFlg = true;
            andFlg   = true;
        }
        
      //descriptionがブランクではなかった場合、sql変数にappendする
        //フラグをtrueにしとく
        if(!"".equals(description)) {
            if (andFlg) sql.append(" AND ");
            sql.append("b.title LIKE :title");
            descriptionFlg = true;
            andFlg   = true;
        }
        
        /*
        QueryはSQLでデータを問い合わせるためのクエリ文に相当する機能を持つ
        entityManagerのcreateQueryメソッドを使用する
        sql変数を引数に渡す
        */
        Query query = (Query) entityManager.createQuery(sql.toString());

      //上記のif文でtrueになった場合は、各変数に値をセットする
        //今回、あいまい検索したいのでlike句を使用する
        if (nameFlg) ((javax.persistence.Query) query).setParameter("genre", "%" + name + "%");
        if (descriptionFlg) ((javax.persistence.Query) query).setParameter("author", "%" + description + "%");
        
        return ((javax.persistence.Query) query).getResultList();
    
    }

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}