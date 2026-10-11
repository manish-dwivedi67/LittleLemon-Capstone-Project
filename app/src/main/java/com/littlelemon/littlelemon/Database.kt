package com.littlelemon.littlelemon

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.serialization.SerialName
import androidx.room.RoomDatabase

@Dao
interface MenuDao {
    @Query("SELECT * from MenuItems")
    fun getAll(): LiveData<List<MenuItems>>

    @Insert
    fun insertAll(vararg menuItems: MenuItems)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItems) == 0")
    fun isEmpty(): Boolean
}




@Database (
    entities = [MenuItems::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun menuDao(): MenuDao
}


@Entity
data class MenuItems (
    @PrimaryKey
    val id : Int,
    val title : String,
    val price: Int,
    val image: String,
    val category: String
)