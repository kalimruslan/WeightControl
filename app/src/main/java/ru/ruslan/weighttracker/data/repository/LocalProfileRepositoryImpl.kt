package ru.ruslan.weighttracker.data.repository

import ru.ruslan.weighttracker.data.datasource.localdb.ProfileLocalDBDataSource
import ru.ruslan.weighttracker.home.domain.ProfileRepository
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal
import ru.ruslan.weighttracker.data.repository.mapper.EntityToLocalMapper
import ru.ruslan.weighttracker.home.domain.model.ProfileEntity

class LocalProfileRepositoryImpl(private val localDataSource: ProfileLocalDBDataSource) : ProfileRepository {

    override suspend fun saveWeight(profileEntity: ProfileEntity) {
        localDataSource.saveWeight(EntityToLocalMapper.map(profileEntity))
    }

    override suspend fun savePhotoData(profileEntity: ProfileEntity) {
        localDataSource.savePhotoData(EntityToLocalMapper.map(profileEntity))
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