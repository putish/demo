package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Catergory;
import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.dao.CatergoryDao;
import cn.zucc.demo.dao.MovieDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.ShowStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.mapping.ResultMapping;
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

    @Autowired
    private MovieDao movieDao;
    /**
     * 分类列表
     * @param tId 影院id 影院id
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
     * @param tId 影院id   影院id
     * @return
     */
    @Override
    @Transactional
    public Catergory addCatergory(String cName, Long tId) {
        Catergory one=catergoryDao.findByCName(cName);
        if (one.getTId()==tId &&one.getDeleteFlag()== DeleteFlagEnum.UN_DELETE.getValue()) {//判断影片名称是否重复
            throw new TheaterException(ResultMapping.REPEAT_CATERGORY);
        }else {
            Catergory catergory = new Catergory();
            catergory.setCName(cName);
            catergory.setTId(tId);
            catergory.setDeleteFlag(0);
            return catergoryDao.save(catergory);
        }
    }

    /**
     * 删除分类
     *
     * @param cId 分类id
     * @param tId 影院id 影院id
     * @return
     */
    @Override
    @Transactional
    public boolean deteleCatergory(Long cId, Long tId) {
       Catergory catergory=this.findById(cId,tId);
       if(catergory!=null){
           List<Movie> list=movieDao.findList(tId, ShowStateEnum.IN_SHOW.getValue(),null);
           for (Movie movie:list){
               if (cId==null||movie.getFicId()==cId||movie.getSecId()==cId||movie.getTId()==cId) {
                    throw new TheaterException(ResultMapping.CIN_USE);
               }
            }
           catergory.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());
           catergoryDao.save(catergory);
           return true;
       }
       else {
           throw new TheaterException(ResultMapping.NO_CATERGORY);
       }
    }

    /**
     * 分类详情
     *
     * @param cId 分类id
     * @param tId 影院id 影院id
     * @return
     */
    @Override
    public Catergory catergoryDetail(Long cId, Long tId) {

        return catergoryDao.findOne(cId);
    }

    /**
     * 编辑分类
     *
     * @param cId   分类id
     * @param cName 分类名称
     * @param tId 影院id   影院id
     * @return
     */
    @Override
    @Transactional
    public boolean editCatergory(Long cId, String cName, Long tId) {
        Catergory one = catergoryDao.findByCName(cName);
        if (one.getTId() == tId && one.getDeleteFlag() == DeleteFlagEnum.UN_DELETE.getValue()) {
            Catergory catergory=catergoryDao.findOne(cId);
            if (catergory != null) {
                catergory.setCName(cName);
                catergoryDao.save(catergory);
                return true;
            } else {
                throw new TheaterException(ResultMapping.NO_CATERGORY);
            }
        }else {
            throw new TheaterException(ResultMapping.REPEAT_CATERGORY);
        }
    }

    /**
     * 根据id查找
     *
     * @param cId 分类id
     * @param tId 影院id 影院id
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
