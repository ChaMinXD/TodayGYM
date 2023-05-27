package com.example.TodayGYM.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="exercise_list")
data class ExerciseEntity(
    @PrimaryKey
    val ExName:String,
    val ExType:String,
    val ExExplain:String,
    val ExSrc:Int,
    val ExPlace:String
)

@Entity(tableName = "routine_list")
data class RoutineEntity(
    @PrimaryKey
    val Routine:List<String>,
    val RoutineName:String

    )
