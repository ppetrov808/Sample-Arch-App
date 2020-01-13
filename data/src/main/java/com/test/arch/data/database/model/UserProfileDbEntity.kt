package com.test.arch.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.arch.domain.model.AddressEntity
import com.test.arch.domain.model.SexEntity
import com.test.arch.domain.model.StatusEntity
import com.test.arch.domain.model.profile.UserProfileEntity

@Entity(tableName = "profile")
class UserProfileDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "database_id")
    val databaseId: Long,
    @Embedded(prefix = "address_")
    val address: AddressDbEntity?,
    @ColumnInfo(name = "birthday")
    val birthday: String?,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "imgUrl")
    val imgUrl: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "roles")
    val roles: List<String>,
    @ColumnInfo(name = "sex")
    val sex: SexEntity?,
    @ColumnInfo(name = "language")
    val language: String?,
    @ColumnInfo(name = "status")
    val status: StatusEntity?

) {
    companion object {
        private const val SINGLE_ENTITY_ID = 0L
    }

    constructor(entity: UserProfileEntity) : this(
        SINGLE_ENTITY_ID,
        entity.address?.let { AddressDbEntity(it) },
        entity.birthday,
        entity.email,
        entity.firstName,
        entity.id,
        entity.imgUrl,
        entity.lastName,
        entity.roles,
        entity.sex,
        entity.language,
        entity.status

    )

    fun toEntity() = UserProfileEntity(
        address?.let { AddressEntity(it.city, it.country, it.street, it.to ?: "", it.zip) },
        birthday,
        email,
        firstName,
        id,
        imgUrl,
        lastName,
        language,
        roles,
        sex,
        status
    )
}