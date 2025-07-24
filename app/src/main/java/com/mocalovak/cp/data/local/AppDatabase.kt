package com.mocalovak.cp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mocalovak.cp.data.local.dao.CharacterDao
import com.mocalovak.cp.data.local.dao.SkillDao
import com.mocalovak.cp.data.local.entity.CharacterEntity
import com.mocalovak.cp.data.local.entity.SkillEntity

@Database(entities = [
    CharacterEntity::class,
    SkillEntity::class,
], version = 2, exportSchema = true)
abstract class AppDatabase: RoomDatabase(){
    abstract fun characterDao():CharacterDao
    abstract fun skillDao():SkillDao
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