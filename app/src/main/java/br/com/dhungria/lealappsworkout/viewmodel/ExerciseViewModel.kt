package br.com.dhungria.lealappsworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.constants.Constants.COLLECTION_PATH_FIREBASE_EXERCISE
import br.com.dhungria.lealappsworkout.constants.Constants.ID
import br.com.dhungria.lealappsworkout.constants.Constants.ID_TRAINING
import br.com.dhungria.lealappsworkout.constants.Constants.IMAGE
import br.com.dhungria.lealappsworkout.constants.Constants.NAME
import br.com.dhungria.lealappsworkout.constants.Constants.OBSERVATION
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.repositories.ExerciseRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
    ) {
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
            .collection(COLLECTION_PATH_FIREBASE_EXERCISE)
            .document(exercise.id)
            .delete()
    }

    fun delete(exercise: Exercise) = viewModelScope.launch {
        exerciseRepository.delete(exercise)
        Firebase.firestore
            .collection(COLLECTION_PATH_FIREBASE_EXERCISE)
            .document(exercise.id)
            .delete()
    }

    fun getRepositoryData() {
        FirebaseFirestore.getInstance()
            .collection(COLLECTION_PATH_FIREBASE_EXERCISE)
            .orderBy(NAME, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { items ->
                viewModelScope.launch {
                    exerciseRepository.deleteAll()
                    items.forEach { item ->
                        item.run {
                            insertExercise(
                                id = data[ID].toString(),
                                name = data[NAME].toString(),
                                observation = data[OBSERVATION].toString(),
                                image = data[IMAGE].toString(),
                                idTraining = data[ID_TRAINING].toString()
                            )
                        }
                    }
                }
            }
    }

}