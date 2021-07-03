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

    companion object{
        const val TAG = "MAIN_FRAGMENT"
    }

    private lateinit var editText: EditText
    private lateinit var goToNextBtn: Button

    interface ButtonClickListener {
        fun onClick(string: String)
    }

    var sendText: ButtonClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            sendText = context as ButtonClickListener
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
            sendText?.onClick(editText.text.toString())
        }
    }

    override fun onStop() {
        super.onStop()
        goToNextBtn.setOnClickListener(null)
    }

}