package br.com.dhungria.lealappsworkout.data.dao

import androidx.room.*
import br.com.dhungria.lealappsworkout.models.Exercise
import kotlinx.coroutines.flow.Flow


@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise_table")
    fun getAll(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise_table WHERE idTraining = :idTraining")
    fun getAllExercisesOfTraining(idTraining: Int): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

}