package csl.individual.community.common.service;

import csl.individual.community.common.mappers.BaseMapperInfo;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.xml.ws.WebServiceException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Administrator
 * @Date 2019/11/1 10:11
 */
public abstract class BaseService<T> implements IBaseService<T> {
    @Autowired
    protected BaseMapperInfo<T> baseMapperInfo;
    protected Class<T> modelClass;
    // 线程池
    protected static ExecutorService executorService = Executors.newFixedThreadPool(30);
    @SuppressWarnings("unchecked")
    public BaseService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public Integer insert(T model) {
        return baseMapperInfo.insertSelective(model);
    }

    @Override
    public Integer deleteById(String id) {
        return baseMapperInfo.deleteByPrimaryKey(id);
    }

    @Override
    public Integer deleteByIds(String ids) {
        return baseMapperInfo.deleteByIds(ids);
    }

    @Override
    public Integer update(T model) {
        return baseMapperInfo.updateByPrimaryKeySelective(model);
    }

    @Override
    public T selectById(String id) {
        return baseMapperInfo.selectByPrimaryKey(id);
    }

    @Override
    public T selectBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return baseMapperInfo.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> selectListBy(String fieldName, Object value)  {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return baseMapperInfo.select(model);
        } catch (ReflectiveOperationException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> selectListByIds(String ids) {
        return baseMapperInfo.selectByIds(ids);
    }

    @Override
    public List<T> selectByCondition(Condition condition) {
        return baseMapperInfo.selectByCondition(condition);
    }

    @Override
    public List<T> selectAll() {
        return baseMapperInfo.selectAll();
    }

    @Override
    public List<T> select(T record){
        return baseMapperInfo.select(record);
    }

    @Override
    public T selectOne(T recoed){
        return baseMapperInfo.selectOne(recoed);
    }
    @Override
    public int selectCountByExample(Example example) {
        return baseMapperInfo.selectCountByExample(example);
    }

    @Override
    public List<T> selectByExample(Example example) {
        return baseMapperInfo.selectByExample(example);
    }

    @Override
    public int deleteByExample(Example example) {
        return baseMapperInfo.deleteByExample(example);
    }

    @Override
    public int updateByExample(T t, Example example) {
        return baseMapperInfo.updateByExample(t, example);
    }

    @Override
    public int updateByExampleSelective(T t, Example example) {
        return baseMapperInfo.updateByExampleSelective(t, example);
    }

    @Override
    public T selectOneByExample(Example example) {
        return baseMapperInfo.selectOneByExample(example);
    }
    @Override
    public List<T> selectByExampleAndRowBounds(Example example, RowBounds rowBounds) {
        return baseMapperInfo.selectByExampleAndRowBounds(example, rowBounds);
    }
    public int selectCount(T t){
        return baseMapperInfo.selectCount(t);
    }
}