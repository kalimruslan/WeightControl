package ru.ruslan.weighttracker.data.datasource.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ruslan.weighttracker.data.datasource.localdb.model.PhotoLocal
import ru.ruslan.weighttracker.data.datasource.localdb.model.WeightLocal

@Dao
interface PhotoLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePhoto(photoLocal: PhotoLocal): Long

    @Query("SELECT * FROM photos WHERE profile_id = :userId")
    fun getPhotoLocal(userId: Int): PhotoLocal?

    @Query("SELECT * FROM photos WHERE profile_id = :userId ORDER BY id DESC LIMIT 1")
    fun getLastPhotoLocal(userId: Int): PhotoLocal?

    @Query("SELECT * FROM photos WHERE id = :photoId")
    fun getPhotoById(photoId: Int): PhotoLocal?
}