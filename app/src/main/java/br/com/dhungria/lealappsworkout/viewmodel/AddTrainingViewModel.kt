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

    fun insertTraining(
        name: String,
        description: String
    ){
        viewModelScope.launch {
            val saveTraining = Training(
                name = name.toInt(),
                description = description
            )
            trainingRepository.insert(saveTraining)
        }
    }

}