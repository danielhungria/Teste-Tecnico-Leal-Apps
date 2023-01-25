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
import br.com.dhungria.lealappsworkout.databinding.CardviewRecyclerExercisesFragmentBinding
import br.com.dhungria.lealappsworkout.extensions.tryLoadImage
import br.com.dhungria.lealappsworkout.models.Exercise

class ExerciseAdapter(
    val onLongPressEdit: (Exercise) -> Unit,
    val onLongPressDelete: (Exercise) -> Unit
) : ListAdapter<Exercise, ExerciseAdapter.ViewHolder>(DiffCallback()) {

    private var fullList = mutableListOf<Exercise>()

    fun updateList(listExerciseList: List<Exercise>) {
        fullList = listExerciseList.toMutableList()
        submitList(fullList)
    }

    private fun showMenu(
        context: Context,
        view: View,
        menuPopupMenu: Int,
        exercise: Exercise
    ) {
        val popup = PopupMenu(context, view)
        popup.menuInflater.inflate(menuPopupMenu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit_popup_menu -> {
                    onLongPressEdit(exercise)
                    true
                }
                R.id.delete_popup_menu -> {
                    onLongPressDelete(exercise)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    inner class ViewHolder(
        private val binding: CardviewRecyclerExercisesFragmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Exercise) {
            binding.apply {
                textviewTrainCardRecyclerExercise.text = currentItem.name.toString()
                textviewTrainDescriptionCardRecyclerExercise.text = currentItem.observation
                imageviewTrainCardRecyclerExercise.tryLoadImage(currentItem.image)

                root.setOnLongClickListener {
                    showMenu(it.context, it, R.menu.menu_popup_training_fragment, currentItem)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardviewRecyclerExercisesFragmentBinding.inflate(
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

    class DiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) =
            oldItem == newItem
    }


}