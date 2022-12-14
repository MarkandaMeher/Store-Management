package com.example.mystore

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class InputValidation() {

    fun isInputEditTextFilled(textInputEditText: EditText): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            return false
        }
        return true
    }
    fun isInputEditTextEmail(textInputEditText: EditText): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            return false
        }
        return true
    }
    fun isInputEditTextMatches(textInputEditText1: EditText, textInputEditText2: EditText): Boolean {
        val value1 = textInputEditText1.text.toString().trim()
        val value2 = textInputEditText2.text.toString().trim()
        if (!value1.contentEquals(value2)) {
            return false
        }
        return true
    }
    fun isInputEditTextNumber(textInputEditText1: EditText):Boolean{
        val value = textInputEditText1.text.toString().trim()
        if(value.isDigitsOnly())
            return true
        return false
    }

}