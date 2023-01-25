package br.com.dhungria.lealappsworkout.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.dhungria.lealappsworkout.R
import br.com.dhungria.lealappsworkout.databinding.FormImageDialogBinding
import br.com.dhungria.lealappsworkout.extensions.tryLoadImage


class FormImageDialog(private val context: Context) {

    fun show(
        url: String? = null,
        whenLoadImage: (image: String) -> Unit
    ) {
        FormImageDialogBinding
            .inflate(LayoutInflater.from(context)).apply {
                url?.let {
                    imageViewFormImage.tryLoadImage(it)
                    editTextUrlFormImage.setText(it)
                }
                loadButtonFormImage.setOnClickListener {
                    imageViewFormImage.tryLoadImage(editTextUrlFormImage.text.toString())
                }
                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton(context.getString(R.string.confirm)) { _, _ ->
                        whenLoadImage(editTextUrlFormImage.text.toString())
                    }
                    .setNegativeButton(context.getString(R.string.cancel)) { _, _ ->

                    }
                    .show()
            }
    }

}