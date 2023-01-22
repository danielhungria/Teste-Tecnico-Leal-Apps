package br.com.dhungria.lealappsworkout.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.dhungria.lealappsworkout.R
import br.com.dhungria.lealappsworkout.adapter.ExerciseAdapter
import br.com.dhungria.lealappsworkout.databinding.ExerciseFragmentBinding
import br.com.dhungria.lealappsworkout.viewmodel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    private lateinit var binding: ExerciseFragmentBinding

    private val exerciseAdapter = ExerciseAdapter()

    private val viewModel: ExerciseViewModel by viewModels()


    private fun setupItemBackMenuBar() {
        binding.toolbarExerciseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecycler() {
        binding.recyclerExerciseFragment.apply {
            adapter = exerciseAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupButtonAddExercise() {
        binding.buttonAddExerciseFragment.setOnClickListener {
            findNavController().navigate(R.id.action_exercisefragment_to_addtraining)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupItemBackMenuBar()
        setupButtonAddExercise()
        viewModel.exerciseList.observe(viewLifecycleOwner) {
            exerciseAdapter.updateList(it)
        }
        viewModel.fetchScreenList()
    }

}