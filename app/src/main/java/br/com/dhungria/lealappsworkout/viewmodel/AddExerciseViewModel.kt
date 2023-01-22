package br.com.dhungria.lealappsworkout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.ExerciseRepository
import br.com.dhungria.lealappsworkout.repositories.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExerciseViewModel @Inject constructor(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    fun insertExercise(
        name: String,
        observation: String,
        image: String = "",
        idTraining: Int = 0
    ){
        viewModelScope.launch {
            val saveExercise = Exercise(
                name = name.toInt(),
                observation = observation,
                image = image,
                idTraining = idTraining
            )
            exerciseRepository.insert(saveExercise)
        }
    }

}