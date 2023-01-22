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
) : ViewModel() {

    private var idTraining: Int = 0

    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>>
        get() = _exerciseList

    fun setup(idTraining: Int) {
        this.idTraining = idTraining
    }

    fun fetchScreenList() {
        viewModelScope.launch {
            exerciseRepository.getAllExercisesOfTraining(idTraining).collect {
                _exerciseList.postValue(it)
            }
        }
    }

    fun onItemSwiped(exercise: Exercise) = viewModelScope.launch {
        exerciseRepository.delete(exercise)
    }

}