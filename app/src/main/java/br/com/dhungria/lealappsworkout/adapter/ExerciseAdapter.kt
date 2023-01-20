package br.com.dhungria.lealappsworkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.lealappsworkout.databinding.CardviewRecyclerExercisesFragmentBinding

class ExerciseAdapter: ListAdapter<String, ExerciseAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder (
        private val binding: CardviewRecyclerExercisesFragmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: String) {
            binding.textviewTrainCardRecyclerExercise.text = currentItem
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

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }


}