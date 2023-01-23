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

    private var exerciseID = 0

    private var isEditMode = false

    fun setupEditMode(exerciseID: Int){
        this.exerciseID = exerciseID
        isEditMode = true
    }

    fun insertExercise(
        name: String,
        observation: String,
        image: String = "",
        idTraining: Int?
    ){
        viewModelScope.launch {
            val saveExercise = Exercise(
                id = exerciseID,
                name = name.toInt(),
                observation = observation,
                image = image,
                idTraining = idTraining
            )
            if (isEditMode){
                exerciseRepository.update(saveExercise)
            }else{
                exerciseRepository.insert(saveExercise)
            }

        }
    }

}