package com.practicum.work_test_project.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.work_test_project.data.db.CourseEntity

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    @Delete(entity = CourseEntity::class)
    suspend fun deleteCourse(course: CourseEntity)

    @Query(value = "SELECT * FROM course_table")
    suspend fun getFavorites() : List<CourseEntity>

    @Query(value = "SELECT * FROM course_table WHERE id IN (:courseId)")
    suspend fun isCourseInFavorites(courseId: Long) : CourseEntity?
}