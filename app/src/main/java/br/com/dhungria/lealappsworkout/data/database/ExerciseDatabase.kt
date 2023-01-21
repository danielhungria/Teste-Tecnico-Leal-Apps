package br.com.dhungria.lealappsworkout.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.dhungria.lealappsworkout.data.dao.ExerciseDao
import br.com.dhungria.lealappsworkout.models.Exercise

@Database(
    entities = [Exercise::class],
    version = 1
)
abstract class ExerciseDatabase: RoomDatabase() {

    abstract fun getExerciseDao(): ExerciseDao

}