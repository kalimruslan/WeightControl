package ru.ruslan.weighttracker.data.datasource.localdb.database

import android.content.Context
import android.provider.SyncStateContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.ruslan.weighttracker.data.datasource.localdb.dao.PhotoLocalDao
import ru.ruslan.weighttracker.data.datasource.localdb.dao.ProfileLocalDao
import ru.ruslan.weighttracker.data.datasource.localdb.dao.WeightLocalDao
import ru.ruslan.weighttracker.data.datasource.localdb.model.PhotoLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal
import ru.ruslan.weighttracker.data.utils.Constants

@Database(
    entities = [ProfileLocal::class, PhotoLocal::class, WeightLocal::class],
    version = 3,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun profileLocalDao(): ProfileLocalDao
    abstract fun weightLocalDao(): WeightLocalDao
    abstract fun photoLocalDao(): PhotoLocalDao
}