package br.com.dhungria.lealappsworkout.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.ExerciseRepository
import br.com.dhungria.lealappsworkout.repositories.TrainingRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddExerciseViewModel @Inject constructor(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    private var exerciseID = UUID.randomUUID().toString()

    private var isEditMode = false

    fun setupEditMode(exerciseID: String){
        this.exerciseID = exerciseID
        isEditMode = true
    }

    fun insertExercise(
        name: String,
        observation: String,
        image: String? = "",
        idTraining: String?
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

    fun saveOnFirebase(
        name: String,
        description: String,
        image: String?,
        idTraining: String?
    ){

        val exerciseMap = hashMapOf(
            "id" to exerciseID,
            "name" to name.toInt(),
            "observation" to description,
            "image" to image,
            "idTraining" to idTraining
        )

        Firebase.firestore
            .collection("Exercise")
            .document(name)
            .set(exerciseMap)
            .addOnSuccessListener {
                Log.i("firebase", "insertExercise: $it")
            }
            .addOnFailureListener {
                Log.i("firebase", "insertExercise: $it")
            }
    }

}