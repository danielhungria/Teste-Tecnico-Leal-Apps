package br.com.dhungria.lealappsworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository
) : ViewModel() {

    private val _trainingList = MutableLiveData<List<Training>>()
    val trainingList: LiveData<List<Training>>
        get() = _trainingList

    private var trainingId = "0"

    private var isEditMode = false

    fun fetchScreenList() {
        viewModelScope.launch {
            trainingRepository.getAll().collect {
                _trainingList.postValue(it)
            }
        }
    }

    fun onItemSwiped(training: Training) = viewModelScope.launch {
        trainingRepository.delete(training)
    }

    fun delete(training: Training) = viewModelScope.launch {
        trainingRepository.delete(training)
    }

    fun setupEditMode(trainingId: Int){
//        this.trainingId = trainingId
        isEditMode = true
    }

    fun insertTraining(
        id: String,
        name: String,
        description: String
    ){
        viewModelScope.launch {
            val saveTraining = Training(
                id = id,
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

    suspend fun firebaseVerification(trainingID: String): Boolean {
        return (trainingRepository.getAllWithId(trainingID))
    }

}