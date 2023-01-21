package br.com.dhungria.lealappsworkout.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.dhungria.lealappsworkout.data.dao.TrainingDao
import br.com.dhungria.lealappsworkout.models.Training


@Database(
    entities = [Training::class],
    version = 1
)
abstract class TrainingDatabase: RoomDatabase() {

    abstract fun getTrainingDao(): TrainingDao

}