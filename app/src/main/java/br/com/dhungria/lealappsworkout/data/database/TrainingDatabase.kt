package br.com.dhungria.lealappsworkout.data.database

import androidx.room.Database
import br.com.dhungria.lealappsworkout.data.dao.TrainingDao
import br.com.dhungria.lealappsworkout.models.Training


@Database(
    entities = [Training::class],
    version = 1
)
interface TrainingDatabase {

    abstract fun getTrainingDao(): TrainingDao

}