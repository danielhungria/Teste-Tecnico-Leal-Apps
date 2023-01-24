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

    @Query("SELECT EXISTS (SELECT * FROM exercise_table WHERE id = :exerciseID)")
    suspend fun getAllWithId(exerciseID: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Update
    suspend fun update(exercise: Exercise)



}