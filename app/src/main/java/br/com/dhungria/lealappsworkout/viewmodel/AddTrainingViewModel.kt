package br.com.dhungria.lealappsworkout.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.TrainingRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTrainingViewModel @Inject constructor(private val trainingRepository: TrainingRepository) :
    ViewModel() {

    private var trainingId = UUID.randomUUID().toString()

    private var isEditMode = false

    fun setupEditMode(trainingId: String) {
        this.trainingId = trainingId
        isEditMode = true
    }

    fun insertTraining(
        name: String,
        description: String,
        data: Long
    ) {
        viewModelScope.launch {
            val saveTraining = Training(
                id = trainingId,
                name = name.toInt(),
                description = description,
                date = data
            )
            Firebase.firestore
                .collection("Training")
                .document(trainingId)
                .set(saveTraining)

            if (isEditMode) {
                trainingRepository.update(saveTraining)
            } else {
                trainingRepository.insert(saveTraining)
            }

        }
    }


}