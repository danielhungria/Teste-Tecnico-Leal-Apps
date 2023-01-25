package br.com.dhungria.lealappsworkout.ui.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.lealappsworkout.R
import br.com.dhungria.lealappsworkout.adapter.ExerciseAdapter
import br.com.dhungria.lealappsworkout.constants.Constants.EXERCISE_LIST_TO_EDIT
import br.com.dhungria.lealappsworkout.constants.Constants.TRAINING_LIST_ID
import br.com.dhungria.lealappsworkout.constants.Constants.TRAINING_LIST_TO_EDIT
import br.com.dhungria.lealappsworkout.databinding.ExerciseFragmentBinding
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.viewmodel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    private lateinit var binding: ExerciseFragmentBinding

    private val viewModel: ExerciseViewModel by viewModels()

    private val trainingList by lazy { arguments?.getParcelable<Training>(TRAINING_LIST_TO_EDIT) }

    private val exerciseAdapter = ExerciseAdapter(onLongPressEdit = {
        findNavController().navigate(
            R.id.action_exercisefragment_to_addtraining,
            bundleOf(
                EXERCISE_LIST_TO_EDIT to it,
                TRAINING_LIST_ID to trainingList?.id
            )
        )
    },
        onLongPressDelete = {
            viewModel.delete(it)
        })


    private fun setupItemBackMenuBar() {
        binding.toolbarExerciseFragment.title = "Treino ${trainingList?.name.toString()}"
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
            findNavController().navigate(
                R.id.action_exercisefragment_to_addtraining, bundleOf(
                    TRAINING_LIST_ID to trainingList?.id
                )
            )
        }
    }

    private fun setupItemTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = exerciseAdapter.currentList[viewHolder.adapterPosition]
                viewModel.onItemSwiped(item)
            }

        }).attachToRecyclerView(binding.recyclerExerciseFragment)
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
        setupItemTouchHelper()
        viewModel.exerciseList.observe(viewLifecycleOwner) {
            exerciseAdapter.updateList(it)
        }
        trainingList?.id?.let {
            viewModel.setup(it)
        }
        viewModel.fetchScreenList()
        viewModel.getRepositoryData()
    }

}