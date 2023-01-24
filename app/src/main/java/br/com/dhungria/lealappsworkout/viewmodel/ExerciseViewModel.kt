package br.com.dhungria.lealappsworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.ExerciseRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private var idTraining: String = "0"

    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>>
        get() = _exerciseList

    private var isEditMode = false


    fun setup(idTraining: String) {
        this.idTraining = idTraining
    }

    fun fetchScreenList() {
        viewModelScope.launch {
            exerciseRepository.getAllExercisesOfTraining(idTraining).collect {
                _exerciseList.postValue(it)
            }
        }
    }

    fun insertExercise(
        id: String,
        name: String,
        observation: String,
        image: String? = "",
        idTraining: String?
    ){
        viewModelScope.launch {
            val saveExercise = Exercise(
                id = id,
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

    fun onItemSwiped(exercise: Exercise) = viewModelScope.launch {
        exerciseRepository.delete(exercise)
        Firebase.firestore
            .collection("Exercise")
            .document(exercise.name.toString())
            .delete()
    }

    fun delete(exercise: Exercise) = viewModelScope.launch {
        exerciseRepository.delete(exercise)
        Firebase.firestore
            .collection("Exercise")
            .document(exercise.name.toString())
            .delete()
    }

    suspend fun firebaseVerification(exerciseID: String): Boolean {
        return (exerciseRepository.getAllWithId(exerciseID))
    }

}