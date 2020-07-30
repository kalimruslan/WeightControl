package ru.ruslan.weighttracker.data.repository

import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.data.datasource.localdb.ProfileLocalDBDataSource
import ru.ruslan.weighttracker.data.datasource.sharedpreferences.ProfilePreferencesDataSource
import ru.ruslan.weighttracker.data.repository.mapper.*
import ru.ruslan.weighttracker.domain.repository.ProfileLocalRepository
import ru.ruslan.weighttracker.domain.model.PhotoDataEntity
import ru.ruslan.weighttracker.domain.model.profile.PhotoEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity

class ProfileLocalRepositoryImpl(private val localDataSource: ProfileLocalDBDataSource,
                                 private val profilePreferences: ProfilePreferencesDataSource) :
    ProfileLocalRepository {

    override suspend fun saveWeight(weightEntity: WeightEntity) {
        localDataSource.saveWeight(WeightEntityToLocalMapper.map(weightEntity))
    }

    override suspend fun savePhotoData(photoEntity: PhotoEntity): Result<Int> {
        val photoId = localDataSource.savePhotoData(PhotoEntityToLocalMapper.map(photoEntity))

        return if (photoId.resultType == ResultType.SUCCESS)
            Result.success(photoId.data)
        else
            Result.error(photoId.error)
    }

    override suspend fun createProfile(profileEntity: ProfileEntity): Result<Int> {
        val profileId = localDataSource.insertProfile(ProfileEntityToLocalMapper.map(profileEntity))

        return if (profileId.resultType == ResultType.SUCCESS)
            Result.success(profileId.data)
        else
            Result.error(profileId.error)
    }

    override suspend fun editProfile(profileId: Int, profileEntity: ProfileEntity) {
        localDataSource.editProfile(profileId, ProfileEntityToLocalMapper.map(profileEntity))
    }

    override suspend fun getProfileData(userId: Int): Result<ProfileEntity> {
        val result = localDataSource.getProfile(userId = userId)

        return if (result.resultType == ResultType.SUCCESS)
            Result.success(ProfileLocalToEntityMapper.map(result.data))
        else
            Result.error(result.error)
    }

    override suspend fun getPhotoData(userId: Int): PhotoDataEntity {
        var photoDataEntity: PhotoDataEntity = PhotoDataEntity()
        val photoResult = localDataSource.getLastPhotoData(true, userId)
        if (photoResult.resultType == ResultType.SUCCESS) {
            val photoObj = photoResult.data
            val weightResult = localDataSource.getWeightByPhoto(photoObj?.id!!)
            if (weightResult.resultType == ResultType.SUCCESS) {
                val weightObj = weightResult.data
                photoDataEntity = PhotoDataEntity(
                    photoDate = photoObj.photoDate,
                    photoPath = photoObj.photoUrl,
                    weightOnPhoto = weightObj?.weight!!
                )
            }
        }

        return photoDataEntity
    }

    override suspend fun getPhotoDataByPhotoId(photoId: Int): PhotoDataEntity {
        var photoDataEntity: PhotoDataEntity = PhotoDataEntity()
        val weight = localDataSource.getWeightByPhoto(photoId)
        if (weight.resultType == ResultType.SUCCESS) {
            val photo = localDataSource.getPhotoById(photoId)
            if (photo.resultType == ResultType.SUCCESS) {
                val photoObj = photo.data
                if (photoObj != null) {
                    photoDataEntity = PhotoDataEntity(
                        photoDate = photoObj.photoDate,
                        photoPath = photoObj.photoUrl,
                        weightOnPhoto = weight.data?.weight!!)
                }
            }
        }

        return photoDataEntity
    }

    override suspend fun getAllWeightsForUser(profileId: Int): Result<List<WeightEntity>> {
        var result = localDataSource.getAllWeights(profileId)
        return if (result.resultType == ResultType.SUCCESS)
            Result.success(WeightLocalToWeightEntity.map(result.data))
        else
            Result.error(result.error)
    }

    override fun deleteWeight(photoId: Int, photoDate: String?, weightOnPhoto: String?) {
        localDataSource.deleteWeight(photoId, photoDate, weightOnPhoto)
    }

    override suspend fun getAllProfileData(): Result<List<ProfileEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun clearProfile(userId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllProfiles() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun storeProfileId(profileId: Int) {
        profilePreferences.storeProfileId(profileId)
    }

    override fun retrieveProfileId() =
        profilePreferences.retrieveProfileId()

    override fun storeWeightMeasure(unit: String) {
        profilePreferences.storeWeightMeasure(unit)
    }

    override fun retrieveWeightMeasure(): String? =
        profilePreferences.retrieveWeightMeasure()

    override fun storeHeightMeasure(unit: String) {
        profilePreferences.storeHeightMeasure(unit)
    }

    override fun retrieveHeightMeasure(): String? = profilePreferences.retrieveHeightMeasure()
}