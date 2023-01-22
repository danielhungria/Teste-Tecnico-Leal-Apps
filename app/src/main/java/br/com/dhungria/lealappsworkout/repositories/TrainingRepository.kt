package br.com.dhungria.lealappsworkout.repositories

import br.com.dhungria.lealappsworkout.data.dao.TrainingDao
import br.com.dhungria.lealappsworkout.models.Training
import javax.inject.Inject

class TrainingRepository @Inject constructor(private val trainingDao: TrainingDao) {

    fun getAll() = trainingDao.getAll()

    suspend fun insert(training: Training) = trainingDao.insert(training)

    suspend fun delete(training: Training) = trainingDao.delete(training)

}