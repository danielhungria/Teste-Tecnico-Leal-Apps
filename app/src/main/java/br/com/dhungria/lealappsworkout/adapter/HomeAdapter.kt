package br.com.dhungria.lealappsworkout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dhungria.lealappsworkout.R
import br.com.dhungria.lealappsworkout.databinding.CardviewRecyclerHomeFragmentBinding
import br.com.dhungria.lealappsworkout.models.Training

class HomeAdapter(
    val onClick: (Training) -> Unit,
    val onLongPressEdit: (Training) -> Unit,
    val onLongPressDelete: (Training) -> Unit
) : ListAdapter<Training, HomeAdapter.ViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<Training>()

    private fun showMenu(
        context: Context,
        view: View,
        menuPopupMenu: Int,
        training: Training
    ) {
        val popup = PopupMenu(context, view)
        popup.menuInflater.inflate(menuPopupMenu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit_popup_menu -> {
                    onLongPressEdit(training)
                    true
                }
                R.id.delete_popup_menu -> {
                    onLongPressDelete(training)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    fun updateList(listTraining: List<Training>) {
        fullList = listTraining.toMutableList()
        submitList(fullList)
    }

    inner class ViewHolder(
        private val binding: CardviewRecyclerHomeFragmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Training) {
            binding.apply {
                textviewTrainCardRecycler.text = currentItem.name.toString()
                textviewTrainDescriptionCardRecycler.text = currentItem.description

                root.setOnClickListener {
                    onClick(currentItem)
                }

                root.setOnLongClickListener {
                    showMenu(it.context, it ,R.menu.menu_popup_training_fragment, currentItem)
                    true
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardviewRecyclerHomeFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        return holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<Training>() {
        override fun areItemsTheSame(oldItem: Training, newItem: Training) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Training, newItem: Training) =
            oldItem == newItem
    }


}