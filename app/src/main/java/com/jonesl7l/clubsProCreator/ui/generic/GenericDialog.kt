package com.jonesl7l.clubsProCreator.ui.generic

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import com.jonesl7l.clubsProCreator.R
import com.jonesl7l.clubsProCreator.databinding.DialogGenericBinding
import com.jonesl7l.clubsProCreator.util.show
import com.jonesl7l.clubsProCreator.util.showIf

/**
 * GenericDialog(this).setDialogTitle("TitleText")
 * .setTextBody("BodyText")
 *
 * for multi button:
 * .setPositiveButton("ButtonText") {
 *      clickListener
 * }
 * .setNegativeButton("ButtonText") {
 *      clickListener
 * }
 *
 * for single button:
 * .setSingleButton("ButtonText") {
 *      clickListener
 * }
 *
 * can/can't tap out of dialog:
 * .setDismissible(true/false)
 * .show()
 */
class GenericDialog(context: Context) : AlertDialog(context) {

    private var dialogTitle: String = ""
    private var dialogText: String = ""
    private var checkboxText: String? = null
    private var checkCallBack: ((isChecked: Boolean) -> Unit)? = null
    private var dialogHyperlinkText: SpannableString? = null
    private var dialogButtonText: String = ""
    private var dialogPositiveButtonText: String = ""
    private var dialogNegativeButtonText: String = ""

    private var titleMaxLines: Int = 1

    private var buttonOnClick: () -> Unit = {}
    private var positiveButtonOnClick: () -> Unit = {}
    private var negativeButtonOnClick: () -> Unit = {}

    private var dismissListener: DialogInterface.OnDismissListener? = null
    private var dialogButtonClickable: Boolean = true
    private var dialogPositiveButtonClickable: Boolean = true

    private var dialogNegativeButtonClickable: Boolean = true

    private var isDismissible: Boolean = true

    private lateinit var binding: DialogGenericBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogGenericBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTextViews()
        setButtons()
        setCheckBox()
        setCancelable(isDismissible)
        setOnDismissListener(dismissListener)
        binding.dialogGenericTitle.maxLines = titleMaxLines
        window?.decorView?.setBackgroundResource(android.R.color.transparent) //transparents any surrounding white background
    }

    //region Builder methods

    fun setDialogTitle(title: String): GenericDialog {
        dialogTitle = title
        return this
    }

    fun setTextBody(textBody: String): GenericDialog {
        dialogText = textBody
        return this
    }

    fun setCheckBox(checkText: String, checkCallBack: (isChecked: Boolean) -> Unit): GenericDialog {
        checkboxText = checkText
        this.checkCallBack = checkCallBack
        return this
    }

    fun setHyperlinkBody(textBody: String, hyperlinkStrings: List<Pair<String, (() -> Unit)>>): GenericDialog {
        val spannableString = SpannableString(textBody)
        hyperlinkStrings.forEach {
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    it.second()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }
            val startIndex = textBody.lastIndexOf(it.first)
            spannableString.setSpan(clickableSpan, startIndex, startIndex + it.first.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        dialogHyperlinkText = spannableString
        return this
    }

    fun setSingleButton(buttonText: String, clickable: Boolean = true, onClick: () -> Unit = {}): GenericDialog {
        dialogButtonText = buttonText
        dialogButtonClickable = clickable
        buttonOnClick = onClick
        return this
    }

    fun setPositiveButton(buttonText: String, clickable: Boolean = true, onClick: () -> Unit = {}): GenericDialog {
        dialogPositiveButtonText = buttonText
        dialogPositiveButtonClickable = clickable
        positiveButtonOnClick = onClick
        return this
    }

    fun setNegativeButton(buttonText: String, clickable: Boolean = true, onClick: () -> Unit = {}): GenericDialog {
        dialogNegativeButtonText = buttonText
        dialogNegativeButtonClickable = clickable
        negativeButtonOnClick = onClick
        return this
    }

    fun setDismissable(canDismiss: Boolean): GenericDialog {
        isDismissible = canDismiss
        return this
    }

    fun setDismissListener(onDismiss: () -> Unit = {}): GenericDialog {
        dismissListener = DialogInterface.OnDismissListener { onDismiss() }
        return this
    }

    fun setTitleMaxLines(maxLines: Int): GenericDialog {
        titleMaxLines = maxLines
        return this
    }

    //endregion

    //region Dialog setters

    private fun setTextViews() {
        binding.dialogGenericTitle.text = dialogTitle
        binding.dialogGenericText.apply {
            showIf { dialogText.isNotBlank() || !dialogHyperlinkText.isNullOrBlank() }
            text = dialogHyperlinkText ?: dialogText
            if (dialogHyperlinkText != null) {
                isClickable = true
                highlightColor = Color.TRANSPARENT
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    private fun setCheckBox() {
        checkboxText?.let { text ->
            binding.dialogGenericCheckbox.show()
            binding.dialogGenericCheckbox.text = text
            binding.dialogGenericCheckbox.setOnCheckedChangeListener { _, isChecked ->
                checkCallBack?.invoke(isChecked)
            }
        }
    }

    private fun setButtons() {
        if (dialogButtonText.isNotBlank()) {
            handleMultiButtons(false)
            binding.dialogGenericButton.apply {
                text = dialogButtonText
                isEnabled = dialogButtonClickable
                setOnClickListener {
                    buttonOnClick()
                    dismiss()
                }
            }
            handleMultiButtonConstraints(false)
            return
        }
        handleMultiButtons(true)
        binding.dialogGenericPositiveButton.apply {
            text = dialogPositiveButtonText
            isEnabled = dialogPositiveButtonClickable
            setOnClickListener {
                positiveButtonOnClick()
                dismiss()
            }
        }
        binding.dialogGenericNegativeButton.apply {
            text = dialogNegativeButtonText
            isEnabled = dialogNegativeButtonClickable
            setOnClickListener {
                negativeButtonOnClick()
                dismiss()
            }
        }
        handleMultiButtonConstraints(true)
    }


    private fun handleMultiButtonConstraints(isMultiButtonDialog: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.dialogGenericConstraintLayout)
        if (!isMultiButtonDialog) {
            constraintSet.connect(
                R.id.dialog_generic_text,
                ConstraintSet.BOTTOM,
                R.id.dialog_generic_button,
                ConstraintSet.TOP, 0
            )
        } else {
            constraintSet.connect(
                R.id.dialog_generic_text,
                ConstraintSet.BOTTOM,
                R.id.dialog_generic_negative_button,
                ConstraintSet.TOP, 0
            )
        }
        constraintSet.applyTo(binding.dialogGenericConstraintLayout)
    }

    private fun handleMultiButtons(shouldShowMultiple: Boolean) {
        binding.dialogGenericButton.showIf { !shouldShowMultiple && dialogButtonText.isNotBlank() }
        binding.dialogGenericNegativeButton.showIf { shouldShowMultiple && dialogNegativeButtonText.isNotBlank() }
        binding.dialogGenericPositiveButton.showIf { shouldShowMultiple && dialogPositiveButtonText.isNotBlank() }
    }

    //endregion
}