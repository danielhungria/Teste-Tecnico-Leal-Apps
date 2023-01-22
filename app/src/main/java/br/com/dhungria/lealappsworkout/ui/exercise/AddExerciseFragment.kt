package br.com.dhungria.lealappsworkout.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.dhungria.lealappsworkout.databinding.AddTrainingFragmentBinding
import br.com.dhungria.lealappsworkout.viewmodel.AddExerciseViewModel
import br.com.dhungria.lealappsworkout.viewmodel.AddTrainingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddExerciseFragment: Fragment() {

    private lateinit var binding: AddTrainingFragmentBinding

    private val viewModel: AddExerciseViewModel by viewModels()


    private fun setupItemBackMenuBar(){
        binding.toolbarExerciseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupListener() = with(binding){
        editTextInputLayoutName.hint = "Type number of exercise"
        editTextInputLayoutDescription.hint = "Type observation of exercise"
        buttonDoneAddTrainingFragment.setOnClickListener {
            viewModel.insertExercise(
                name = editTextName.text.toString(),
                observation = editTextDescription.text.toString()
            )
            findNavController().popBackStack()
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
    }

}