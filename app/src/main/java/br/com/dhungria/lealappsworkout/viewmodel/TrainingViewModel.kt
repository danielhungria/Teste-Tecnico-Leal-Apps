package br.com.dhungria.lealappsworkout.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.repositories.TrainingRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository
) : ViewModel() {

    private val _trainingList = MutableLiveData<List<Training>>()
    val trainingList: LiveData<List<Training>>
        get() = _trainingList


    private var isEditMode = false

    fun fetchScreenList() {
        viewModelScope.launch {
            trainingRepository.getAll().collect {
                _trainingList.postValue(it)
            }
        }
    }

    fun onItemSwiped(training: Training) = viewModelScope.launch {
        trainingRepository.delete(training)
        Firebase.firestore
            .collection("Training")
            .document(training.id)
            .delete()
    }

    fun delete(training: Training) = viewModelScope.launch {
        trainingRepository.delete(training)
        Firebase.firestore
            .collection("Training")
            .document(training.id)
            .delete()
    }

    private fun insertTraining(
        id: String,
        name: String,
        description: String,
        date: Long,
        image: String? = ""
    ) {
        viewModelScope.launch {
            val saveTraining = Training(
                id = id,
                name = name.toInt(),
                description = description,
                date = date,
                image = image
            )

            if (isEditMode) {
                trainingRepository.update(saveTraining)
            } else {
                trainingRepository.insert(saveTraining)
            }

        }
    }

    fun getFirestoreData() {
        FirebaseFirestore.getInstance()
            .collection("Training")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { items ->
                viewModelScope.launch {
                    trainingRepository.deleteAll()
                    items.forEach { item ->
                        item.run {
                            insertTraining(
                                id = data["id"].toString(),
                                name = data["name"].toString(),
                                description = data["description"].toString(),
                                date = data["date"].toString().toLong(),
                                image = data["image"].toString()
                            )
                        }
                    }
                }
            }
    }

}