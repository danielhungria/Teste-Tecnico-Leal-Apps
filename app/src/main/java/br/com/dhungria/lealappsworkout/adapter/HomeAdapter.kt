package br.com.dhungria.lealappsworkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.lealappsworkout.databinding.CardviewRecyclerHomeFragmentBinding

class HomeAdapter(
    val onClick:(Boolean) -> Unit
): ListAdapter<String, HomeAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder (
        private val binding: CardviewRecyclerHomeFragmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: String) {
            binding.apply {
                textviewTrainCardRecycler.text = currentItem

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

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }


}