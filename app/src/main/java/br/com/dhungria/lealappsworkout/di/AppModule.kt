package br.com.dhungria.lealappsworkout.di

import android.content.Context
import androidx.room.Room
import br.com.dhungria.lealappsworkout.data.database.AppDatabase
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
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "app.database")
        .build()


    @Singleton
    @Provides
    fun providesTrainingDao(
        db: AppDatabase
    ) = db.getTrainingDao()

    @Singleton
    @Provides
    fun providesExerciseDao(
        db: AppDatabase
    ) = db.getExerciseDao()


}