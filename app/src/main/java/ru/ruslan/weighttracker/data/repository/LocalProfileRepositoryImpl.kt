package ru.ruslan.weighttracker.data.repository

import ru.ruslan.weightracker.core.datatype.Result
import ru.ruslan.weightracker.core.datatype.ResultType
import ru.ruslan.weighttracker.data.datasource.localdb.ProfileLocalDBDataSource
import ru.ruslan.weighttracker.domain.repository.ProfileRepository
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.repository.mapper.PhotoEntityToLocalMapper
import ru.ruslan.weighttracker.data.repository.mapper.ProfileEntityToLocalMapper
import ru.ruslan.weighttracker.data.repository.mapper.WeightEntityToLocalMapper
import ru.ruslan.weighttracker.domain.model.profile.PhotoEntity
import ru.ruslan.weighttracker.domain.model.profile.ProfileEntity
import ru.ruslan.weighttracker.domain.model.profile.WeightEntity
import javax.inject.Inject

class LocalProfileRepositoryImpl (private val localDataSource: ProfileLocalDBDataSource) : ProfileRepository {

    override suspend fun saveWeight(weightEntity: WeightEntity) {
        localDataSource.saveWeight(WeightEntityToLocalMapper.map(weightEntity))
    }

    override suspend fun savePhotoData(photoEntity: PhotoEntity) {
        localDataSource.savePhotoData(PhotoEntityToLocalMapper.map(photoEntity))
    }

    override suspend fun createProfile(profileEntity: ProfileEntity): Result<Int> {
        val profileId = localDataSource.insertProfile(ProfileEntityToLocalMapper.map(profileEntity))

        return if (profileId.resultType == ResultType.SUCCESS)
            Result.success(profileId.data)
        else
            Result.error(profileId.error)
    }

    override suspend fun getProfileData(userId: String): ProfileLocal {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getAllProfileData(): List<ProfileLocal> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearProfile(userId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllProfiles() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}