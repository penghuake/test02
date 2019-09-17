package cn.ysys13.tensquare.dao;

import cn.ysys13.tensquare.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query("select p from Problem p where id in( select problemid from Pl where labelid=?1 ) order by replytime desc")
    public Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);

    @Query("select p from Problem p where id in( select problemid from Pl where labelid=?1 ) order by reply desc")
    Page<Problem> findHotlistByLabelId(String label, Pageable pageable);

    @Query("select p from Problem p where id in( select problemid from Pl where labelid=?1 ) and p.reply=0 order by createtime desc")
    Page<Problem> findWaitlistByLabelId(String label, Pageable pageable);
}
