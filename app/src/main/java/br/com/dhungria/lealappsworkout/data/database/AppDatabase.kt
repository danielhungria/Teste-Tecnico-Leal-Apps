package br.com.dhungria.lealappsworkout.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.dhungria.lealappsworkout.data.dao.ExerciseDao
import br.com.dhungria.lealappsworkout.data.dao.TrainingDao
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.models.Training

@Database(
    entities = [Exercise::class, Training::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getExerciseDao(): ExerciseDao

    abstract fun getTrainingDao(): TrainingDao

}