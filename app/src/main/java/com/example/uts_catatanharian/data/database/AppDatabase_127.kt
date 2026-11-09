package com.example.uts_catatanharian.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uts_catatanharian.data.dao.NoteDao_127
import com.example.uts_catatanharian.data.dao.UserDao_127
import com.example.uts_catatanharian.data.entity.NoteEntity_127
import com.example.uts_catatanharian.data.entity.UserEntity_127

@Database(entities = [UserEntity_127::class, NoteEntity_127::class], version = 1, exportSchema = false)
abstract class AppDatabase_127 : RoomDatabase() {
    abstract fun userDao_127(): UserDao_127
    abstract fun noteDao_127(): NoteDao_127

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase_127? = null

        fun getDatabase_127(context: Context): AppDatabase_127 {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase_127::class.java,
                    "catatan_harian_db_127"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
