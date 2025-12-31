package com.practicum.work_test_project.data.db

import android.media.MediaParser
import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.work_test_project.data.db.dao.CourseDao

@Database(
    version = 1,
    entities = [
        CourseEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun courseDao() : CourseDao

}
