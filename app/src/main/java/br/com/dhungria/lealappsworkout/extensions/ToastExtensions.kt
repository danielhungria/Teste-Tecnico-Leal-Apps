package br.com.dhungria.lealappsworkout.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(@StringRes message: Int, duration: Int = Toast.LENGTH_LONG) {
    context?.toast(message, duration)
}

private fun Context.toast(@StringRes message: Int, duration: Int) {
    Toast.makeText(this, message, duration).show()
}