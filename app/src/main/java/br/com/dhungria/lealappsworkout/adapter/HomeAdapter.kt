package br.com.dhungria.lealappsworkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.lealappsworkout.databinding.CardviewRecyclerHomeFragmentBinding
import br.com.dhungria.lealappsworkout.models.Training

class HomeAdapter(
    val onClick:(Boolean) -> Unit
): ListAdapter<Training, HomeAdapter.ViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<Training>()

    fun updateList(listTraining: List<Training>) {
        fullList = listTraining.toMutableList()
        submitList(fullList)
    }

    inner class ViewHolder (
        private val binding: CardviewRecyclerHomeFragmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Training) {
            binding.apply {
                textviewTrainCardRecycler.text = currentItem.name.toString()
                textviewTrainDescriptionCardRecycler.text = currentItem.description

                root.setOnClickListener {
                    onClick(true)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardviewRecyclerHomeFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        return holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<Training>() {
        override fun areItemsTheSame(oldItem: Training, newItem: Training) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Training, newItem: Training) =
            oldItem == newItem
    }


}