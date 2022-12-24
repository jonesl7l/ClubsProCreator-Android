package com.jonesl7l.clubsProCreator.util

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jonesl7l.clubsProCreator.TemplateApplication

object TemplateExtensions

fun notImplemented() = toast("Not implemented")

fun toast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    if (message.isNullOrBlank()) {
        return
    }
    Toast.makeText(TemplateApplication.appContext, message, length).show()
}

fun DrawerLayout.toggleDrawer(drawerContent: View) {
    if (!isDrawerOpen(drawerContent)) {
        openDrawer(drawerContent)
    } else {
        closeDrawers()
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

inline fun View.showIf(default: Int = View.GONE, predicate: (View) -> Boolean) {
    if (predicate(this)) {
        show()
    } else {
        visibility = default
    }
}

inline fun View.hideIf(default: Int = View.VISIBLE, predicate: (View) -> Boolean) {
    if (predicate(this)) {
        hide()
    } else {
        visibility = default
    }
}

inline fun View.goneIf(default: Int = View.VISIBLE, predicate: (View) -> Boolean) {
    if (predicate(this)) {
        gone()
    } else {
        visibility = default
    }
}

/**
 * Will set the text to a view and make visible if passed in text is not blank
 */
fun TextView.showIfNotBlank(text: String? = null, default: Int = View.GONE) {
    if (!text.isNullOrBlank()) {
        show()
        this.text = text
    } else {
        visibility = default
    }
}

fun Activity.showKeyboard() {
    if (!isFinishing) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}

fun Activity.hideKeyboard() {
    if (!this.isFinishing) {
        this.findViewById<View>(android.R.id.content).hideKeyboard()
    }
}

fun Fragment.showKeyboard() {
    if (isAdded) {
        activity?.showKeyboard()
    }
}

fun Fragment.hideKeyboard() {
    if (isAdded) {
        this.activity?.findViewById<View>(android.R.id.content)?.hideKeyboard()
    }
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit): TextWatcher {
    val textChangedWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    }
    this.addTextChangedListener(textChangedWatcher)
    return textChangedWatcher
}


fun View.updatePadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun ViewGroup.layoutInflater(): LayoutInflater = LayoutInflater.from(context)

// endregion

// region Snackbar

fun Activity.snackbar(message: String?, f: Snackbar.() -> Unit = {}) {
    if (!this.isFinishing) {
        this.findViewById<View>(android.R.id.content).snackbar(message, f)
    }
}

fun Fragment.snackbar(message: String?, f: Snackbar.() -> Unit = {}) {
    if (this.isAdded) {
        this.activity?.snackbar(message, f)
    }
}

fun View.snackbar(message: String?, f: Snackbar.() -> Unit = {}) {
    message?.takeIf { it.isNotEmpty() }?.let {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).also {
            it.f()
            it.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.maxLines =
                3 // For the bulkier messages
            it.show()
        }
    }
}

fun Context.colour(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

infix fun <T> Boolean.then(param: T): T? = if (this) param else null

fun <T> List<T>?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()

fun <T> List<T>?.isNotNullOrEmpty(): Boolean = this != null && this.isNotEmpty()

fun Int?.orZero(): Int = this ?: 0

fun Double?.orZero(): Double = this ?: 0.0

fun Long?.orZero(): Long = this ?: 0L

fun Float?.orZero(): Float = this ?: 0f

fun Int?.orMinus(): Int = this ?: -1

fun Double?.orMinus(): Double = this ?: -1.0

fun Long?.orMinus(): Long = this ?: -1L

fun Float?.orMinus(): Float = this ?: -1f

fun Boolean?.orFalse(): Boolean = this ?: false

fun Boolean?.orTrue(): Boolean = this ?: true

fun Activity.dismissKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputMethodManager.isAcceptingText)
        inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, /*flags:*/ 0)
}