package tads.eaj.ufrn.swipedragdroprecyclerview

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Fruta::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun frutaDao(): FrutaDao
}