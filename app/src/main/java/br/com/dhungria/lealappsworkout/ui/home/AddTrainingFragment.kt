package br.com.dhungria.lealappsworkout.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.dhungria.lealappsworkout.databinding.AddTrainingFragmentBinding
import br.com.dhungria.lealappsworkout.viewmodel.AddTrainingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTrainingFragment: Fragment() {

    private lateinit var binding: AddTrainingFragmentBinding

    private val viewModel: AddTrainingViewModel by viewModels()


    private fun setupItemBackMenuBar(){
        binding.toolbarExerciseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupListener() = with(binding){
        editTextInputLayoutDate.visibility = View.VISIBLE
        cardViewAddTraining.visibility = View.GONE

        buttonDoneAddTrainingFragment.setOnClickListener {
            viewModel.insertTraining(
                name = editTextName.text.toString(),
                description = editTextDescription.text.toString()
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