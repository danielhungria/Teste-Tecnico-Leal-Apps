package br.com.dhungria.lealappsworkout.viewmodel

import android.util.Log
import androidx.lifecycle.*
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.ExerciseRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private var idTraining: String = "0"

    private val _exerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>>
        get() = _exerciseList


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

    private fun insertExercise(
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
            exerciseRepository.insert(saveExercise)
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

    fun getRepositoryData() {
        FirebaseFirestore.getInstance()
            .collection("Exercise")
            .get()
            .addOnSuccessListener { items ->
                viewModelScope.launch {
                    exerciseRepository.deleteAll()
                    items.forEach { item ->
                        item.run {
                            insertExercise(
                                id = data["id"].toString(),
                                name = data["name"].toString(),
                                observation = data["observation"].toString(),
                                image = data["image"].toString(),
                                idTraining = data["idTraining"].toString()
                            )
                        }
                    }
                }
            }
    }

}