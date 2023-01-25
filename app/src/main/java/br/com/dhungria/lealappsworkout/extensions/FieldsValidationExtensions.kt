package br.com.dhungria.lealappsworkout.extensions

import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setupFieldValidationListener(@StringRes onErrorMessage: Int) {
    editText?.setOnFocusChangeListener { _, hasFocus ->
        error = if (!hasFocus && editText?.text.toString().isBlank()) {
            context.getString(onErrorMessage)
        } else {
            null
        }
    }
}