package club.ccit.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import club.ccit.room.api.TestApi;
import club.ccit.room.model.TestDataModel;


/**
 * FileName: RoomDataBase
 *
 * @author: 张帅威
 * Date: 2022/2/23 9:15 上午
 * Description: 数据库操作基类
 * Version:
 */
@Database(entities = {TestDataModel.class},version = 1,exportSchema  = false)
public abstract class AbstractRoomData extends RoomDatabase {
    private static String db_name = "db_testData";
    private static AbstractRoomData roomDataBase;
    /**
     * 获取room 操作接口
     * @return
     */
    public abstract TestApi getTestApi();

    /**
     * 单例实例化操作类
     * @param context
     * @return
     */
    public static AbstractRoomData getRoomDataBase(Context context) {
        if (roomDataBase == null){
            synchronized (AbstractRoomData.class){
                if (roomDataBase == null){
                    roomDataBase =  Room.databaseBuilder(context, AbstractRoomData.class,db_name)
                            .allowMainThreadQueries()
                            .addMigrations(AbstractRoomData.updateMigration)
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // 数据库创建
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    // 数据库打开
                                }
                            })
                            .build();

                }
            }
        }
        return roomDataBase;
    }

    static final Migration updateMigration = new Migration(1,1) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 需要执行的语句
        }
    };
}
