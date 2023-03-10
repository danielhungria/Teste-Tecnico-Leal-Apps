package br.com.dhungria.lealappsworkout.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.dhungria.lealappsworkout.R
import br.com.dhungria.lealappsworkout.constants.Constants.EXERCISE_LIST_TO_EDIT
import br.com.dhungria.lealappsworkout.constants.Constants.TRAINING_LIST_ID
import br.com.dhungria.lealappsworkout.databinding.AddTrainingFragmentBinding
import br.com.dhungria.lealappsworkout.extensions.setupFieldValidationListener
import br.com.dhungria.lealappsworkout.extensions.toast
import br.com.dhungria.lealappsworkout.extensions.tryLoadImage
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.ui.dialog.FormImageDialog
import br.com.dhungria.lealappsworkout.viewmodel.AddExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddExerciseFragment : Fragment() {

    private lateinit var binding: AddTrainingFragmentBinding

    private val viewModel: AddExerciseViewModel by viewModels()

    private val trainingListId by lazy { arguments?.getString(TRAINING_LIST_ID) }

    private val exerciseList by lazy { arguments?.getParcelable<Exercise>(EXERCISE_LIST_TO_EDIT) }

    private var url: String? = ""


    private fun setupItemBackMenuBar() {
        binding.toolbarExerciseFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateFields() = with(binding) {
        editTextInputLayoutName.setupFieldValidationListener(R.string.insert_number_of_exercise)
        editTextInputLayoutDescription.setupFieldValidationListener(R.string.insert_observation)
    }

    private fun setupListener() = with(binding) {
        editTextInputLayoutName.hint = getString(R.string.type_number_exercise)
        editTextInputLayoutDescription.hint = getString(R.string.type_observation_exercise)

        imageViewAddTraining.setOnClickListener {
            FormImageDialog(it.context)
                .show(url) { imageUrl ->
                    url = imageUrl
                    imageViewAddTraining.tryLoadImage(url)
                }
        }

        buttonDoneAddTrainingFragment.setOnClickListener {
            viewModel.insertExercise(
                name = editTextName.text.toString(),
                observation = editTextDescription.text.toString(),
                idTraining = trainingListId,
                image = url,
                closeScreen = { findNavController().popBackStack() },
                onError = { toast(R.string.fill_all_fields) }
            )
        }
    }


    private fun setupAccordingToEditMode(exercise: Exercise?) = with(binding) {
        exercise?.run {
            url = exercise.image
            viewModel.setupEditMode(id)
            editTextName.setText(name.toString())
            editTextDescription.setText(observation)
            imageViewAddTraining.tryLoadImage(url)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTrainingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupItemBackMenuBar()
        validateFields()
        setupListener()
        setupAccordingToEditMode(exerciseList)
    }

}