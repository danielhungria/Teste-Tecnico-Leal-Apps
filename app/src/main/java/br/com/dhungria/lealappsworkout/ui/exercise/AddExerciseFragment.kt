package br.com.dhungria.lealappsworkout.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.dhungria.lealappsworkout.constants.Constants.EXERCISE_LIST_TO_EDIT
import br.com.dhungria.lealappsworkout.constants.Constants.TRAINING_LIST_ID
import br.com.dhungria.lealappsworkout.databinding.AddTrainingFragmentBinding
import br.com.dhungria.lealappsworkout.extensions.tryLoadImage
import br.com.dhungria.lealappsworkout.models.Exercise
import br.com.dhungria.lealappsworkout.ui.dialog.FormularioImagemDialog
import br.com.dhungria.lealappsworkout.viewmodel.AddExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddExerciseFragment : Fragment() {

    private lateinit var binding: AddTrainingFragmentBinding

    private val viewModel: AddExerciseViewModel by viewModels()

    private val trainingListId by lazy { arguments?.getString(TRAINING_LIST_ID) }

    private val exerciseList by lazy { arguments?.getParcelable<Exercise>(EXERCISE_LIST_TO_EDIT) }

    private var url: String? = null


    private fun setupItemBackMenuBar() {
        binding.toolbarExerciseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateFields() = with(binding) {
        editTextName.setOnFocusChangeListener { _, focused ->
            if (!focused && editTextName.text.toString().isBlank()) {
                editTextInputLayoutName.apply {
                    helperText = "Insira o número do exercício"
                    error = "Insira o número do exercício"
                }
            } else {
                editTextInputLayoutName.apply {
                    helperText = null
                    error = null
                }
            }
        }
        editTextDescription.setOnFocusChangeListener { _, focused ->
            if (!focused && editTextDescription.text.toString().isBlank()) {
                editTextInputLayoutDescription.apply {
                    helperText = "Insira uma observação"
                    error = "Insira uma observação"
                }
            } else {
                editTextInputLayoutDescription.apply {
                    helperText = null
                    error = null
                }
            }
        }
    }

    private fun setupListener() = with(binding) {
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
            if (!editTextName.text.isNullOrBlank() &&
                !editTextDescription.text.isNullOrBlank()
            ) {
                viewModel.insertExercise(
                    name = editTextName.text.toString(),
                    observation = editTextDescription.text.toString(),
                    idTraining = trainingListId,
                    image = url
                )
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    private fun setupAccordingToEditMode(exercise: Exercise?) = with(binding) {
        exercise?.run {
            url = exercise.image
            viewModel.setupEditMode(id)
            editTextName.setText(name.toString())
            editTextDescription.setText(observation)
            imageViewAddTraining.tryLoadImage(image)
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