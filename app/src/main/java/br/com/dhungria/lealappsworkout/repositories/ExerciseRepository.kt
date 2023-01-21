package br.com.dhungria.lealappsworkout.repositories

import br.com.dhungria.lealappsworkout.data.dao.ExerciseDao
import br.com.dhungria.lealappsworkout.models.Exercise
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val exerciseDao: ExerciseDao) {

    fun getAll() = exerciseDao.getAll()

    suspend fun insert(exercise: Exercise) = exerciseDao.insert(exercise)

}