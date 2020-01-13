package com.test.arch.data.utils

import com.test.arch.domain.model.AddressEntity
import com.test.arch.domain.model.SexEntity
import com.test.arch.domain.model.StatusEntity
import com.test.arch.domain.model.profile.UserProfileEntity
import io.reactivex.Single

object MockDataTools {

    fun mockProfile(): Single<UserProfileEntity> {
        return Single.fromCallable { getMockProfile() }
    }

    private fun getMockProfile(): UserProfileEntity {
        return UserProfileEntity(
            AddressEntity.emptyAddress,
            "",
            "test@test.com",
            "Test",
            123L,
            "https://i0.wp.com/www.jodyrobbins.com/wp-content/uploads/2019/11/mountain-winter-reflection-on-lake.jpg",
            "Test",
            "English",
            listOf(),
            SexEntity.MALE,
            StatusEntity.ACTIVE
        )
    }
}