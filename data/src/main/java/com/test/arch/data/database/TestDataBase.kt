package com.test.arch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.arch.data.database.converter.DataTypeConverter
import com.test.arch.data.database.dao.ProfileDao
import com.test.arch.data.database.migration.MigrationConst
import com.test.arch.data.database.model.UserProfileDbEntity

@TypeConverters(DataTypeConverter::class)
@Database(
    entities = arrayOf(UserProfileDbEntity::class),
    version = 4,
    exportSchema = false
)
abstract class TestDataBase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "TestDataBase"

        @Volatile
        private var INSTANCE: TestDataBase? = null

        fun getInstance(context: Context): TestDataBase =
            INSTANCE ?: synchronized(this) { INSTANCE ?: buildDatabase(context).also { INSTANCE = it } }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, TestDataBase::class.java, DATABASE_NAME)
                .addMigrations(MigrationConst.MIGRATION_1_2, MigrationConst.MIGRATION_2_3, MigrationConst.MIGRATION_3_4)
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun profileDao(): ProfileDao
}
