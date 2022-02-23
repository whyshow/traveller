package club.ccit.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * FileName: recorderData
 *
 * @author: 张帅威
 * Date: 2022/2/23 8:30 上午
 * Description: 数据库实体类
 * Version: 1
 */
@Entity(tableName = "db_testData")
public class TestDataModel {
    public TestDataModel() {
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public String getRecordFormat() {
        return recordFormat;
    }

    public void setRecordFormat(String recordFormat) {
        this.recordFormat = recordFormat;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    /**
     * id 自增
     */
    @PrimaryKey(autoGenerate = true)
    private Integer recordId;
    /**
     * 名字
     */
    private String recordName;
    /**
     * 位置
     */
    private String recordPath;
    /**
     * 格式
     */
    private String recordFormat;
     /**
     * 日期
     */
    private String recordDate;


}
