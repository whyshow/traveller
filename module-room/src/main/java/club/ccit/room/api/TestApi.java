package club.ccit.room.api;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import club.ccit.room.model.TestDataModel;

/**
 * FileName: RecordApi
 *
 * @author: 张帅威
 * Date: 2022/2/23 8:52 上午
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
     * @param record
     * @return
     */
    @Insert
    long insertData(TestDataModel record);

    /**
     * 插入多行数据
     * @param record
     * @return
     */
    @Insert
    List<Long> insertData(TestDataModel... record);

    /**
     * 更新一条数据
     * @param record
     */
    @Update
    void updateData(TestDataModel record);

    /**
     * 删除一行数据
     * @param record
     */
    @Delete
    void deleteData(TestDataModel record);

}
