package ru.ruslan.weighttracker.data.repository

import ru.ruslan.weighttracker.home.domain.HomeRepository
import ru.ruslan.weighttracker.data.datasource.localdb.model.ProfileLocal

class LocalHomeRepositoryImpl() : HomeRepository {
    override fun saveWeight(userId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun savePhotoData(userId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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