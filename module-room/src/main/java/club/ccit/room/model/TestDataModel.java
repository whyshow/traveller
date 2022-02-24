package club.ccit.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * FileName: TestDataModel
 *
 * @author: 张帅威
 * Description: 数据库实体类
 * Version: 1
 */
@Entity(tableName = "db_testData")
public class TestDataModel {
    public TestDataModel() {
    }

    /**
     * id 自增
     */
    @PrimaryKey(autoGenerate = true)
    private Integer testId;
    /**
     * 名字
     */
    private String testName;
    /**
     * 年龄
     */
    private String testAge;

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestAge() {
        return testAge;
    }

    public void setTestAge(String testAge) {
        this.testAge = testAge;
    }
}
