package com.mocalovak.cp.di

import android.content.Context
import androidx.room.Room
import com.mocalovak.cp.data.local.AppDatabase
import com.mocalovak.cp.data.local.dao.CharacterDao
import com.mocalovak.cp.data.local.dao.EquipmentDao
import com.mocalovak.cp.data.local.dao.SkillDao
import com.mocalovak.cp.data.local.preferences.PreferenceManager
import com.mocalovak.cp.data.repository.CharacterRepositoryImpl
import com.mocalovak.cp.data.repository.EquipmentRepositoryImpl
import com.mocalovak.cp.data.repository.SkillRepositoryImpl
import com.mocalovak.cp.domain.repository.CharacterRepository
import com.mocalovak.cp.domain.repository.EquipmentRepository
import com.mocalovak.cp.domain.repository.SkillRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "cp_database")
            .fallbackToDestructiveMigration()
            .build()

    //Dao
    @Provides
    fun provideCharacterDao(db:AppDatabase):CharacterDao = db.characterDao()

    @Provides
    fun provideSkillDao(db:AppDatabase):SkillDao = db.skillDao()

    @Provides
    fun provideEquipmentDao(db: AppDatabase): EquipmentDao = db.equipmentDao()

    //Repository
    @Provides
    fun provideCharacterRepository(
        dao: CharacterDao
    ): CharacterRepository = CharacterRepositoryImpl(dao)

    @Provides
    fun provideSkillRepository(
        dao:SkillDao
    ): SkillRepository = SkillRepositoryImpl(dao)

    @Provides
    fun provideEquipmentRepository(
        dao: EquipmentDao
    ): EquipmentRepository = EquipmentRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager =
        PreferenceManager(context)
}