package com.example.webviewtesting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class InputFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container,false)
        val navController = findNavController()
        val sharedPreferences:SharedPreferences = view.context.getSharedPreferences("DEFAULT_PREFERENCES",
            Context.MODE_PRIVATE)

        val nameInput = view.findViewById<TextView>(R.id.name_input)
        val ageInput = view.findViewById<TextView>(R.id.age_input)
        val button = view.findViewById<Button>(R.id.button)

        //Set texts
        nameInput.text = sharedPreferences.getString("name","-")
        ageInput.text = sharedPreferences.getInt("age",0).toString()

        button.setOnClickListener {
            navController.navigate(R.id.action_inputFragment_to_homeFragment)
        }

        return view
    }
}