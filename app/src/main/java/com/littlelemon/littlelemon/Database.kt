package com.littlelemon.littlelemon

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import kotlinx.serialization.SerialName
import androidx.room.RoomDatabase

@Dao
interface MenuDao {
    @Query("SELECT * from MenuItems")
    fun getAll(): LiveData<List<MenuItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(menuItems: List<MenuItemEntity>)

    @Query("SELECT COUNT(*) FROM MenuItems")
    fun count(): Int
}




@Database (
    entities = [MenuItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


@Entity(tableName = "MenuItems")
data class MenuItemEntity (
    @PrimaryKey
    val id : Int,
    val title : String,
    val price: Int,
    val image: String,
    val category: String,
    val description: String
)