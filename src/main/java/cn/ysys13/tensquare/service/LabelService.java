package cn.ysys13.tensquare.service;


import cn.ysys13.tensquare.common.util.IdWorker;
import cn.ysys13.tensquare.dao.LabelDao;
import cn.ysys13.tensquare.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签业务逻辑类
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 根据ID查询标签
     * @return
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 增加标签
     * @param label
     */
    public void add(Label label){
        label.setId( idWorker.nextId()+"" );//设置ID
        labelDao.save(label);
    }

    /**
     * 修改标签
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 删除标签
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }


    public List<Label> findSearch(Map searchMap) {

        Specification<Label> specifiation=this.getSpecifiation(searchMap);
        return labelDao.findAll(specifiation);
    }

    private Specification<Label> getSpecifiation(Map searchMap) {
       return new  Specification<Label>(){

           @Override
           public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery,
                                        CriteriaBuilder cb) {
               List<Predicate> predicateList=new ArrayList<>();
               if(searchMap.get("labelname")!=null && !"".equals(searchMap.get("labelname"))){
                   predicateList.add(cb.like( root.get("labelname").as(String.class), "%"+(String)searchMap.get("labelname")+"%"  ) );
               }
               if(searchMap.get("state")!=null && !"".equals(searchMap.get("state"))){
                   predicateList.add(cb.equal( root.get("state").as(String.class), (String)searchMap.get("state") ) );
               }
               if(searchMap.get("recommend")!=null && !"".equals(searchMap.get("recommend"))){
                   predicateList.add(cb.equal( root.get("recommend").as(String.class), (String)searchMap.get("recommend") ) );
               }
               return cb.and( predicateList.toArray( new Predicate[predicateList.size()]) );
           }
       };
    }

    public Page findSearch(Map searchMap, Integer page, Integer size) {
        Specification<Label> specifiation = this.getSpecifiation(searchMap);
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return labelDao.findAll(specifiation,pageRequest);
    }
}