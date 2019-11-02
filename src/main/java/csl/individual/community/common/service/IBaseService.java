package csl.individual.community.common.service;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2019/11/1 10:14
 */
public interface IBaseService<T>  {
    /**
     * @param model
     * @Description: 持久化
     * @Reutrn Integer
     */
    Integer insert(T model);

    /**
     * @param id
     * @Description: 通过主鍵刪除
     * @Reutrn Integer
     */
    Integer deleteById(String id);

    /**
     * @param ids
     * @Description: 批量刪除 eg：ids -> “1,2,3,4”
     * @Reutrn Integer
     */
    Integer deleteByIds(String ids);

    /**
     * @param model
     * @Description: 更新
     * @Reutrn Integer
     */
    Integer update(T model);

    /**
     * @param id
     * @Description: 通过ID查找
     * @Reutrn T
     */
    T selectById(String id);

    /**
     * @param fieldName
     * @param value
     * @throws TooManyResultsException
     * @Description: 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @Reutrn T
     */
    T selectBy(String fieldName, Object value) throws TooManyResultsException;

    /**
     * @param fieldName javabean定义的属性名，不是数据库里的属性名
     * @param value
     * @Description: 通过Model中某个成员变量名称（非数据表中column的名称）查找
     * @Reutrn List<T>
     */
    List<T> selectListBy(String fieldName, Object value);

    /**
     * @param ids
     * @Description: 通过多个ID查找//eg：ids -> “1,2,3,4”
     * @Reutrn List<T>
     */
    List<T> selectListByIds(String ids);

    /**
     * @param condition
     * @Description: 根据条件查找
     * @Reutrn List<T>
     */
    List<T> selectByCondition(Condition condition);

    /**
     * @Description: 获取所有
     * @Reutrn List<T>
     */
    List<T> selectAll();

    /**
     * @param record
     * @return List<T>
     * @Description: 根据实体中的属性值进行查询，查询条件使用等号
     */
    List<T> select(T record);

    /**
     * @param record
     * @return T
     * @Description: 根据实体中的属性值进行查询，查询条件使用等号
     */
    T selectOne(T record);


    /**
     * 根据模板和行号查询
     *
     * @param example
     * @param rowBounds
     * @return
     */
    public List<T> selectByExampleAndRowBounds(Example example, RowBounds rowBounds);

    /**
     * 根据模板删除
     *
     * @param example
     * @return
     */
    public int deleteByExample(Example example);
    /**
     * 根据模板更新，全字段更新
     *
     * @param example
     * @return
     */
    public int updateByExample(T t, Example example);

    /**
     * 根据模板更新选定子段
     *
     * @param t
     * @param example
     * @return
     */
    public int updateByExampleSelective(T t, Example example);
    /**
     * 根据模板查询条数
     *
     * @param example
     * @return
     */
    public int selectCountByExample(Example example);
    /**
     * 根据模板查询
     *
     * @param example
     * @return
     */
    public List<T> selectByExample(Example example);
    /**
     * 根据模板查询数据
     *
     * @param example
     * @return
     */
    public T selectOneByExample(Example example);
    /**
     * 查找数量
     *
     * @param t
     * @return
     */
    public int selectCount(T t);
}
