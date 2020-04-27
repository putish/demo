package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Catergory;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import java.util.List;


public interface CatergoryDao extends JpaRepository<Catergory, Long> {
//    List<Catergory> findByCName(String cName);

    List<Catergory> findByCIdEqualsAndTIdEquals(Long cId,Long tId);

    List<Catergory> findCatergoryByTIdEqualsAndDeleteFlagEquals(Long tId,Integer deleteFlag);

    Catergory findByCName(String cName);

}
