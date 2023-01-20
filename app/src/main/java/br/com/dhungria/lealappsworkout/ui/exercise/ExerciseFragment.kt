package br.com.dhungria.lealappsworkout.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.dhungria.lealappsworkout.adapter.ExerciseAdapter
import br.com.dhungria.lealappsworkout.databinding.ExerciseFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment: Fragment() {

    private lateinit var binding: ExerciseFragmentBinding

    private val exerciseAdapter = ExerciseAdapter()

    private fun setupItemBackMenuBar(){
        binding.toolbarExerciseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecycler(){
        binding.recyclerExerciseFragment.apply {
            adapter = exerciseAdapter.apply {
                submitList(List(10){
                    "Push Up $it"
                })
            }
            layoutManager = GridLayoutManager(requireContext(), 2)
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
    }

}