package cn.zucc.demo.service;

import cn.zucc.demo.bean.Catergory;

import java.util.List;

public interface CatergoryService {
    /**
     * 分类列表
     * @param tId 影院id 影院id
     * @return
     */
    List<Catergory> findList(Long tId);

    /**
     * 添加分类
     * @param cName 分类名称
     * @param tId 影院id 影院id
     * @return
     */
    Catergory addCatergory(String cName,Long tId);

    /**
     * 删除分类
     * @param cId 分类id
     * @param tId 影院id 影院id
     * @return
     */
    boolean deteleCatergory(Long cId,Long tId);

    /**
     * 分类详情
     * @param cId 分类id
     * @param tId 影院id 影院id
     * @return
     */
    Catergory catergoryDetail(Long cId,Long tId);

    /**
     * 编辑分类
     * @param cId 分类id
     * @param cName 分类名称
     * @param tId 影院id 影院id
     * @return
     */
    boolean editCatergory(Long cId, String cName,Long tId);

    /**
     * 根据id查找
     * @param cId 分类id
     * @param tId 影院id 影院id
     * @return
     */
    Catergory findById(Long cId,Long tId);


}
