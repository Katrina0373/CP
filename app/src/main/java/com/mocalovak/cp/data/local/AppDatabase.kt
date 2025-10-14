package com.mocalovak.cp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mocalovak.cp.data.local.dao.CharacterDao
import com.mocalovak.cp.data.local.dao.EquipmentCharacterRefDao
import com.mocalovak.cp.data.local.dao.EquipmentDao
import com.mocalovak.cp.data.local.dao.SkillCharacterRefDao
import com.mocalovak.cp.data.local.dao.SkillDao
import com.mocalovak.cp.data.local.entity.CharacterEntity
import com.mocalovak.cp.data.local.entity.CharacterEquipmentCrossRef
import com.mocalovak.cp.data.local.entity.EquipmentEntity
import com.mocalovak.cp.data.local.entity.SkillCharacterCrossRef
import com.mocalovak.cp.data.local.entity.SkillEntity
import com.mocalovak.cp.domain.model.Equipment

@Database(entities = [
    CharacterEntity::class,
    SkillEntity::class,
    EquipmentEntity::class,
    CharacterEquipmentCrossRef::class,
    SkillCharacterCrossRef::class
], version = 8, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun characterDao():CharacterDao
    abstract fun skillDao():SkillDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun skillCharacterRefDao(): SkillCharacterRefDao
    abstract fun equipmentCharacterRefDao(): EquipmentCharacterRefDao
    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {

            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "cp_database"
                    )
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}