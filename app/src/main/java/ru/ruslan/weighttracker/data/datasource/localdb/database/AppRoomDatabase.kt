package ru.ruslan.weighttracker.data.datasource.localdb.database

import android.content.Context
import android.provider.SyncStateContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.ruslan.weighttracker.data.datasource.localdb.dao.ProfileLocalDao
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.utils.Constants

@Database(entities = [ProfileLocal::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun profileLocalDao(): ProfileLocalDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            if (INSTANCE != null)
                return INSTANCE as AppRoomDatabase
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    Constants.DB_NAME
                ).build()
                INSTANCE = instance
                return INSTANCE as AppRoomDatabase
            }
        }
    }
}