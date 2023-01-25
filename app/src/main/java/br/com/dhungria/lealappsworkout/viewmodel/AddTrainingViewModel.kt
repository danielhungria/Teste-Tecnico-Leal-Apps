package br.com.dhungria.lealappsworkout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.constants.Constants
import br.com.dhungria.lealappsworkout.constants.Constants.COLLECTION_PATH_FIREBASE_TRAINING
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.TrainingRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTrainingViewModel @Inject constructor(private val trainingRepository: TrainingRepository) :
    ViewModel() {

    private var trainingId = UUID.randomUUID().toString()

    private var isEditMode = false

    var date: String = ""
        private set

    fun setupDate(date: String){
        this.date = date
    }

    fun setupEditMode(trainingId: String) {
        this.trainingId = trainingId
        isEditMode = true
    }

    fun insertTraining(
        name: String,
        description: String,
        date: String,
        image: String?,
        closeScreen: () -> Unit,
        onError: () -> Unit
    ) {
        if (name.isNotBlank() &&
            description.isNotBlank() &&
            date.isNotBlank()
        ) {
            val dateFormatted = SimpleDateFormat(Constants.DATE_DEFAULT, Locale.getDefault()).parse(date)?.time ?: 0

            viewModelScope.launch {
                val saveTraining = Training(
                    id = trainingId,
                    name = name.toInt(),
                    description = description,
                    date = dateFormatted,
                    image = image ?: ""
                )
                Firebase.firestore
                    .collection(COLLECTION_PATH_FIREBASE_TRAINING)
                    .document(trainingId)
                    .set(saveTraining)

                if (isEditMode) {
                    trainingRepository.update(saveTraining)
                } else {
                    trainingRepository.insert(saveTraining)
                }
                closeScreen()
            }
        } else onError()

    }


}