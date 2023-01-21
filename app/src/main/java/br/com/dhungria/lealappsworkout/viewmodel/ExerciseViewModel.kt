package br.com.dhungria.lealappsworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.repositories.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository
): ViewModel() {

    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>>
        get() = _exerciseList

    fun fetchScreenList() {
        viewModelScope.launch {
            exerciseRepository.getAll().collect {
                _exerciseList.postValue(it)
            }
        }
    }

    fun insertTraining(){
        viewModelScope.launch {
            val saveExercise = Exercise(
                name = 1,
                observation = "Push Up",
                idTraining = 0
            )
            exerciseRepository.insert(saveExercise)
        }
    }

}