package com.test.arch.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.arch.domain.model.SexEntity
import com.test.arch.domain.model.StatusEntity

class DataTypeConverter {

    companion object {

        private val GSON = Gson()

        private val rolesStringListType = object : TypeToken<List<String>>() {}.type
    }

    @TypeConverter
    fun stringToRolesStringList(data: String?): List<String> {
        if (data == null) {
            return emptyList()
        }
        return GSON.fromJson(data, rolesStringListType)
    }

    @TypeConverter
    fun rolesStringListToString(someObjects: List<String>): String {
        return GSON.toJson(someObjects)
    }

    @TypeConverter
    fun intToSexEntity(data: Int?): SexEntity? {
        if (data == null) {
            return null
        }
        return SexEntity.values()[data]
    }

    @TypeConverter
    fun sexEntityToInt(sexEntity: SexEntity?): Int? {
        return sexEntity?.ordinal
    }

    @TypeConverter
    fun intToStatusEntity(data: Int?): StatusEntity? {
        if (data == null) {
            return null
        }
        return StatusEntity.values()[data]
    }

    @TypeConverter
    fun statusEntityToInt(statusEntity: StatusEntity?): Int? {
        return statusEntity?.ordinal
    }
}