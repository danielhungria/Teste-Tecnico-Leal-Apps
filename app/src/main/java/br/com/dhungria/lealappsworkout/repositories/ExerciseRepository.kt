package br.com.dhungria.lealappsworkout.repositories

import br.com.dhungria.lealappsworkout.data.dao.ExerciseDao
import br.com.dhungria.lealappsworkout.models.Exercise
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val exerciseDao: ExerciseDao) {

    fun getAllExercisesOfTraining(idTraining: String) =
        exerciseDao.getAllExercisesOfTraining(idTraining)

    suspend fun insert(exercise: Exercise) = exerciseDao.insert(exercise)

    suspend fun delete(exercise: Exercise) = exerciseDao.delete(exercise)

    suspend fun update(exercise: Exercise) = exerciseDao.update(exercise)

    suspend fun deleteAll() = exerciseDao.deleteAll()

}