package com.example.twofa.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.twofa.appContext

@Database(entities = [Token::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "token_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}