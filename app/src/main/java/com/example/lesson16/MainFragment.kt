package com.example.lesson16

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainFragment : Fragment() {

    private lateinit var editText: EditText
    private lateinit var goToNextBtn: Button

    interface sendTextForEdits {
        fun onSave(string: String)
    }

    var sendText: sendTextForEdits? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            sendText = context as sendTextForEdits
        } catch (e: Exception) {
            Toast.makeText(context, "IMPL INTERFACE", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initAll(view)
        return view
    }

    private fun initAll(view: View) {
        editText = view.findViewById(R.id.text_for_redactor)
        goToNextBtn = view.findViewById(R.id.btn_to_go_on_next_screen)
    }

    override fun onStart() {
        super.onStart()
        sendThisText()
    }

    private fun sendThisText() {
        goToNextBtn.setOnClickListener {
            sendText?.onSave(editText.text.toString())
        }
    }

    override fun onStop() {
        super.onStop()
        goToNextBtn.setOnClickListener(null)
    }

}