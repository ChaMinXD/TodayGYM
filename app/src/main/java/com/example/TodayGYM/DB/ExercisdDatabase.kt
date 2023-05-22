package com.example.TodayGYM.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.TodayGYM.ListConverters

@TypeConverters(ListConverters::class)
@Database(entities = [ExerciseEntity::class,RoutineEntity::class], version = 1)
abstract class ExerciseDatabase:RoomDatabase() {
    abstract  fun exerciseDao():ExerciseDAO
    abstract  fun routineDao():RoutineDAO
    companion object {
        private var instance: ExerciseDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ExerciseDatabase? {
            if (instance == null)
                synchronized(ExerciseDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExerciseDatabase::class.java,
                        "exercise.db"
                    )
                        .build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}