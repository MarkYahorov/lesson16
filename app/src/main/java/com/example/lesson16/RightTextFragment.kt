package com.example.lesson16

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import java.util.*
import java.util.regex.Pattern

class RightTextFragment : Fragment() {

    private lateinit var textView: TextView

    private val firstRegex = " +[^\n]"
    private val firstReplace = "-"
    private val secondCheckRegex = "[0-9] [0-9]{3} [0-9]{3}-[0-9]{2}-[0-9]{2}"
    private val secondRegex = "(\\d) (\\d)(\\d)(\\d) "
    private val secondReplace = "+375-\$3\$4-"
    private val fourthRegex = "(<)(\\w)+(>)((\\w)+)(<)(/)(\\w)+(>)"
    private val fourthReplace = "$4"
    private val fiveRegex = "[\\s](www\\.)(\\w+)(\\.)(\\w+)[\\s]"
    private val fiveReplace = " http://\$2\$3\$4\$5 "

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_right_text, container, false)
        textView = view.findViewById(R.id.current_text)
        return view
    }

    override fun onStart() {
        super.onStart()
        val bundle = arguments
        if (bundle != null) {
            checkTheCheckbox(bundle.getString("THIS"))
        }
    }

    private fun checkTheCheckbox(string: String?) {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        if (sp.getBoolean("#1_regex", false)) {
            setText(string, firstRegex, firstReplace)
        }
        if (sp.getBoolean("#2_regex", false)) {
            checkPattern(string, secondCheckRegex, secondRegex, secondReplace)
        }
        if (sp.getBoolean("#3_regex", false)) {
            thirdCheckBox(string)
        }
        if (sp.getBoolean("#4_regex", false)) {
            setText(string, fourthRegex, fourthReplace)
        }
        if (sp.getBoolean("#5_regex", false)) {
            setText(string, fiveRegex, fiveReplace)
        }
    }

    private fun checkPattern(
        string: String?,
        checkRegex: String,
        regex: String,
        replacement: String
    ) {
        val pattern = Pattern.compile(checkRegex)
        val matcher = pattern.matcher(string)
        if (matcher.find()) {
            setText(string, regex, replacement)
        } else {
            textView.text = "$string HAS NO YOUR REGEX"
        }
    }

    private fun thirdCheckBox(string: String?) {
        val pattern = Pattern.compile("[a-z]{4}+")
        val matcher = pattern.matcher(string)
        if (matcher.find()) {
            textView.text = string?.replace(
                Regex(
                    " [a-z]{4}$|^[a-z]{4}$|[\\s][a-z]{4}[\\s]|^[a-z]{4} "
                )
            ) {
                it.value.toUpperCase(Locale.ROOT)
            }
        } else {
            textView.text = "$string HAS NO YOUR REGEX"
        }
    }

    private fun setText(string: String?, regex: String, replacement: String) {
        textView.text = string?.replace(Regex(regex), replacement)
    }
}