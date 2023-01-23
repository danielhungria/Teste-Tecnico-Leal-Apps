package br.com.dhungria.lealappsworkout.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.dhungria.lealappsworkout.databinding.FormImageDialogBinding
import br.com.dhungria.lealappsworkout.extensions.tryLoadImage


class FormularioImagemDialog(private val context: Context) {

    fun show(
        urlPadrao: String? = null,
        quandoImagemCarragada: (imagem: String) -> Unit
    )  {
        FormImageDialogBinding
            .inflate(LayoutInflater.from(context)).apply {

                urlPadrao?.let {
                    formularioImagemImageview.tryLoadImage(it)
                    formularioImagemUrl.setText(it)
                }

                formularioImagemBotaoCarregar.setOnClickListener {
                    val url = formularioImagemUrl.text.toString()
                    formularioImagemImageview.tryLoadImage(url)
                }

                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val url = formularioImagemUrl.text.toString()
                        quandoImagemCarragada(url)
                    }
                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .show()
            }




    }

}