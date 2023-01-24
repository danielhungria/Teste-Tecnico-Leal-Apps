package br.com.dhungria.lealappsworkout.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.TrainingRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTrainingViewModel @Inject constructor(private val trainingRepository: TrainingRepository) : ViewModel() {

    private var trainingId = UUID.randomUUID().toString()

    private var isEditMode = false

    fun setupEditMode(trainingId: String){
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

    fun saveOnFirebase(name:String, description: String){

        val trainingMap = hashMapOf(
            "id" to trainingId,
            "name" to name.toInt(),
            "description" to description
        )

        Firebase.firestore
            .collection("Training")
            .document(name)
            .set(trainingMap)
            .addOnSuccessListener {
                Log.i("firebase", "insertTraining: $it")
            }
            .addOnFailureListener {
                Log.i("firebase", "insertTraining: $it")
            }
    }

}