package br.com.dhungria.lealappsworkout.di

import android.content.Context
import androidx.room.Room
import br.com.dhungria.lealappsworkout.data.database.ExerciseDatabase
import br.com.dhungria.lealappsworkout.data.database.TrainingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabaseTraining(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, TrainingDatabase::class.java, "training.database")
            .build()

    @Singleton
    @Provides
    fun provideDatabaseExercise(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ExerciseDatabase::class.java, "exercise.database")
        .build()


    @Provides
    fun providesTrainingDao(
        db: TrainingDatabase
    ) = db.getTrainingDao()

    @Provides
    fun providesExerciseDao(
        db: ExerciseDatabase
    ) = db.getExerciseDao()


}