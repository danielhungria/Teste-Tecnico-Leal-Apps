package br.com.dhungria.lealappsworkout.extensions

import android.widget.ImageView
import br.com.dhungria.lealappsworkout.R
import coil.load

fun ImageView.tryLoadImage(
    url: String? = null,
    fallback: Int = R.drawable.imagem_padrao
){
    load(url) {
        fallback(fallback)
        error(R.drawable.imagem_padrao)
        placeholder(R.drawable.placeholder)
    }
}