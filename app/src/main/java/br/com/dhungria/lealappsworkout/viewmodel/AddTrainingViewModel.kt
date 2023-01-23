package br.com.dhungria.lealappsworkout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTrainingViewModel @Inject constructor(private val trainingRepository: TrainingRepository) : ViewModel() {

    private var trainingId = 0

    private var isEditMode = false

    fun setupEditMode(trainingId: Int){
        this.trainingId = trainingId
        isEditMode = true
    }

    fun insertTraining(
        name: String,
        description: String
    ){
        viewModelScope.launch {
            val saveTraining = Training(
                id = trainingId,
                name = name.toInt(),
                description = description
            )
            if (isEditMode){
                trainingRepository.update(saveTraining)
            }else{
                trainingRepository.insert(saveTraining)
            }

        }
    }

}