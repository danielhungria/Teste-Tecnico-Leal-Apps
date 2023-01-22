package br.com.dhungria.lealappsworkout.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.dhungria.lealappsworkout.R
import br.com.dhungria.lealappsworkout.adapter.HomeAdapter
import br.com.dhungria.lealappsworkout.databinding.HomeFragmentBinding
import br.com.dhungria.lealappsworkout.viewmodel.TrainingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    private val viewModel: TrainingViewModel by viewModels()

    private val homeAdapter = HomeAdapter(onClick = {
        findNavController().navigate(R.id.action_homefragment_to_exercisefragment)
    })

    private fun setupRecycler(){
        binding.recyclerHomeFragment.apply {
            adapter = homeAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupButtonAddTraining(){
        binding.buttonAddTrainingHomeFragment.setOnClickListener {
            findNavController().navigate(R.id.action_homefragment_to_addtraining)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupButtonAddTraining()
        viewModel.trainingList.observe(viewLifecycleOwner){
            homeAdapter.updateList(it)
        }
        viewModel.fetchScreenList()
    }

}