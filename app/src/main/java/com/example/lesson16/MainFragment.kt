package com.example.lesson16

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainFragment : Fragment() {

    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initAll(view)
        return view
    }

    private fun initAll(view: View){
        toolbar = view.findViewById(R.id.toolbar)
    }

    private fun setToolbar(){
        (activity as MainActivity).setSupportActionBar(toolbar)

    }
}