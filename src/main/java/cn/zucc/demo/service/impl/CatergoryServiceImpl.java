package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Catergory;
import cn.zucc.demo.dao.CatergoryDao;
import cn.zucc.demo.service.CatergoryService;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 14:10
 **/
@Service
public class CatergoryServiceImpl implements CatergoryService {

    @Autowired
    private CatergoryDao catergoryDao;
    /**
     * 分类列表
     * @param tId 影院id
     * @return
     */
    @Override
    public List<Catergory> findList(Long tId) {
        List<Catergory> list=catergoryDao.findCatergoryByTIdEqualsAndDeleteFlagEquals(tId,1);
        return list;
    }

    /**
     * 添加分类
     *
     * @param cName 分类名称
     * @param tId   影院id
     * @return
     */
    @Override
    @Transactional
    public Catergory addCatergory(String cName, Long tId) {
        Catergory catergory=new Catergory();
        catergory.setCName(cName);
        catergory.setTId(tId);
        catergory.setDeleteFlag(0);
        return catergoryDao.save(catergory);
    }

    /**
     * 删除分类
     *
     * @param cId 分类id
     * @param tId 影院id
     * @return
     */
    @Override
    @Transactional
    public boolean deteleCatergory(Long cId, Long tId) {
       Catergory catergory=this.findById(cId,tId);
       if(catergory!=null){
           catergory.setDeleteFlag(1);
           catergoryDao.save(catergory);
           return true;
       }
       else {
           return  false;
       }
    }

    /**
     * 分类详情
     *
     * @param cId 分类id
     * @param tId 影院id
     * @return
     */
    @Override
    public Catergory catergoryDetail(Long cId, Long tId) {

        return this.findById(cId,tId);
    }

    /**
     * 编辑分类
     *
     * @param cId   分类id
     * @param cName 分类名称
     * @param tId   影院id
     * @return
     */
    @Override
    @Transactional
    public boolean editCatergory(Long cId, String cName, Long tId) {
        Catergory catergory=this.findById(cId,tId);
        if(catergory!=null){
            catergory.setCName(cName);
            catergoryDao.save(catergory);
            return true;
        }
        else {
            return  false;
        }
    }

    /**
     * 根据id查找
     *
     * @param cId 分类id
     * @param tId 影院id
     * @return
     */
    @Override
    public Catergory findById(Long cId, Long tId) {
        List<Catergory> list=catergoryDao.findByCIdEqualsAndTIdEquals(cId,tId);
        if (list.size()!=0) {
            Catergory catergory = list.get(0);
            if (catergory.getDeleteFlag() == 0) {
                catergory.setDeleteFlag(1);
                catergoryDao.save(catergory);
                return catergory;
            }
        }
        return null;
    }
}
