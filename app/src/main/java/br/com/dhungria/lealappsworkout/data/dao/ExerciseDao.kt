package br.com.dhungria.lealappsworkout.data.dao

import androidx.room.*
import br.com.dhungria.lealappsworkout.models.Exercise
import kotlinx.coroutines.flow.Flow


@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise_table")
    fun getAll(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise_table WHERE idTraining = :idTraining")
    fun getAllExercisesOfTraining(idTraining: String): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Update
    suspend fun update(exercise: Exercise)

    @Query("DELETE FROM exercise_table")
    suspend fun deleteAll()

}