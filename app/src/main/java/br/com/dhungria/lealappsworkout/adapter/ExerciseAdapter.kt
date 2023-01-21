package br.com.dhungria.lealappsworkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.lealappsworkout.databinding.CardviewRecyclerExercisesFragmentBinding
import br.com.dhungria.lealappsworkout.models.Exercise

class ExerciseAdapter: ListAdapter<Exercise, ExerciseAdapter.ViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<Exercise>()

    fun updateList(listExerciseList: List<Exercise>) {
        fullList = listExerciseList.toMutableList()
        submitList(fullList)
    }

    class ViewHolder (
        private val binding: CardviewRecyclerExercisesFragmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Exercise) {
            binding.textviewTrainCardRecyclerExercise.text = currentItem.name.toString()
            binding.textviewTrainDescriptionCardRecyclerExercise.text = currentItem.observation
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardviewRecyclerExercisesFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        return holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) =
            oldItem == newItem
    }


}