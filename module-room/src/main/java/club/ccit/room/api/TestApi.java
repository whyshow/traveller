package club.ccit.room.api;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import club.ccit.room.model.TestDataModel;

/**
 * FileName: TestApi
 *
 * @author: 张帅威
 * Description: 数据库操作接口
 * Version:
 */
@Dao
public interface TestApi {

    /**
     * 查询全部数据
     * @return 返回查询到的数据
     */
    @Query("SELECT * FROM db_testData")
    List<TestDataModel> queryAll();

    /**
     * 插入一行数据
     * @param dataModel
     * @return
     */
    @Insert
    long insertData(TestDataModel dataModel);

    /**
     * 插入多行数据
     * @param dataModel
     * @return
     */
    @Insert
    List<Long> insertData(TestDataModel... dataModel);

    /**
     * 更新一条数据
     * @param dataModel
     */
    @Update
    void updateData(TestDataModel dataModel);

    /**
     * 删除一行数据
     * @param dataModel
     */
    @Delete
    void deleteData(TestDataModel dataModel);

}
