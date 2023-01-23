package br.com.dhungria.lealappsworkout.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.dhungria.lealappsworkout.constants.Constants.EXERCISE_LIST_TO_EDIT
import br.com.dhungria.lealappsworkout.constants.Constants.TRAINING_LIST_ID
import br.com.dhungria.lealappsworkout.databinding.AddTrainingFragmentBinding
import br.com.dhungria.lealappsworkout.extensions.tryLoadImage
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.ui.dialog.FormularioImagemDialog
import br.com.dhungria.lealappsworkout.viewmodel.AddExerciseViewModel
import br.com.dhungria.lealappsworkout.viewmodel.AddTrainingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddExerciseFragment: Fragment() {

    private lateinit var binding: AddTrainingFragmentBinding

    private val viewModel: AddExerciseViewModel by viewModels()

    private val trainingListId by lazy { arguments?.getInt(TRAINING_LIST_ID) }

    private val exerciseList by lazy { arguments?.getParcelable<Exercise>(EXERCISE_LIST_TO_EDIT) }

    private var url: String? = null


    private fun setupItemBackMenuBar(){
        binding.toolbarExerciseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupListener() = with(binding){
        editTextInputLayoutName.hint = "Type number of exercise"
        editTextInputLayoutDescription.hint = "Type observation of exercise"

        imageViewAddTraining.setOnClickListener {
            FormularioImagemDialog(requireContext())
                .show(url) { imagemUrl ->
                    url = imagemUrl
                    imageViewAddTraining.tryLoadImage(url)
                }
        }

        buttonDoneAddTrainingFragment.setOnClickListener {
            viewModel.insertExercise(
                name = editTextName.text.toString(),
                observation = editTextDescription.text.toString(),
                idTraining = trainingListId,
                image = url
            )
            findNavController().popBackStack()
        }
    }

    private fun setupAccordingToEditMode(exercise: Exercise?) = with(binding){
        exercise?.run {
            viewModel.setupEditMode(id)
            editTextName.setText(name.toString())
            editTextDescription.setText(observation)
            imageViewAddTraining.tryLoadImage(image)
            //editTextDate
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
        setupListener()
        setupAccordingToEditMode(exerciseList)
    }

}