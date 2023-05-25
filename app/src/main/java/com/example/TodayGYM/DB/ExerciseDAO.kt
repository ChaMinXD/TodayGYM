package com.example.TodayGYM.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface ExerciseDAO {
    @Query ("SELECT * FROM exercise_list")
    fun getAll():List<ExerciseEntity>

    @Query ("SELECT * FROM exercise_list WHERE ExName=:exName")
    fun getExercise(exName:String):ExerciseEntity

    @Query ("SELECT * FROM exercise_list WHERE ExType=:exType AND ExPlace=:exPlace ")
    fun getList(exType:String,exPlace:String):List<ExerciseEntity>

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insertData(exerciseEntity: ExerciseEntity)

    @Delete
    fun deleteData(exerciseEntity: ExerciseEntity)

    @Query("DELETE FROM exercise_list")
    fun deleteAll()
}
@Dao
interface RoutineDAO{
    @Query ("SELECT * FROM routine_list")
    fun getAll():List<RoutineEntity>
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insertData(routineEntity: RoutineEntity)
    @Delete
    fun deleteData(routineEntity: RoutineEntity)

}