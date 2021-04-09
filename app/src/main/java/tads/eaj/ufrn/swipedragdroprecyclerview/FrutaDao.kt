package tads.eaj.ufrn.swipedragdroprecyclerview

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FrutaDao {

    @Insert
    fun add(f: Fruta): Long
    @Query("SELECT * FROM Fruta")
    fun listAll(): MutableList<Fruta>
    @Delete
    fun remove(f: Fruta): Int
    @Insert
    fun inserirAll(vararg l: Fruta): LongArray
    @Query("DELETE FROM Fruta")
    fun deleteAll(): Int
}