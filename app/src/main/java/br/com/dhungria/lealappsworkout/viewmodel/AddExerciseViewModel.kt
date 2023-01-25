package br.com.dhungria.lealappsworkout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.constants.Constants.COLLECTION_PATH_FIREBASE_EXERCISE
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.repositories.ExerciseRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddExerciseViewModel @Inject constructor(private val exerciseRepository: ExerciseRepository) :
    ViewModel() {

    private var exerciseID = UUID.randomUUID().toString()

    private var isEditMode = false

    fun setupEditMode(exerciseID: String) {
        this.exerciseID = exerciseID
        isEditMode = true
    }

    fun insertExercise(
        name: String,
        observation: String,
        image: String?,
        idTraining: String?,
        closeScreen: () -> Unit,
        onError: () -> Unit
    ) {
        if (name.isNotBlank() &&
            observation.isNotBlank()
        ) {
            viewModelScope.launch {
                val saveExercise = Exercise(
                    id = exerciseID,
                    name = name.toInt(),
                    observation = observation,
                    image = image ?: "",
                    idTraining = idTraining
                )
                Firebase.firestore
                    .collection(COLLECTION_PATH_FIREBASE_EXERCISE)
                    .document(exerciseID)
                    .set(saveExercise)

                if (isEditMode) {
                    exerciseRepository.update(saveExercise)
                } else {
                    exerciseRepository.insert(saveExercise)
                }
                closeScreen()
            }
        } else onError()
    }
}