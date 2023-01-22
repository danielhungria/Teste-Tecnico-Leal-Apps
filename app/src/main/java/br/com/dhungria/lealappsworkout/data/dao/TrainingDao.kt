package br.com.dhungria.lealappsworkout.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dhungria.lealappsworkout.models.Training
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {

    @Query("SELECT * FROM training_table")
    fun getAll(): Flow<List<Training>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(training: Training)

}