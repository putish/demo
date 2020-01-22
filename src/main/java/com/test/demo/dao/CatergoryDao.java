package com.test.demo.dao;

import com.test.demo.bean.Catergory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatergoryDao extends JpaRepository<Catergory, String> {
    List<Catergory> findByCName(String cName);
}
