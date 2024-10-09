package com.example.appensiklopediaandnews

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class ProfileActivityUser : Fragment(R.layout.fragment_profile_activity_user) {

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileName: TextView = view.findViewById(R.id.profileName)
        val titleName: TextView = view.findViewById(R.id.titleName)

        val profileEmail: TextView = view.findViewById(R.id.profileEmail)

        val profileUsername: TextView = view.findViewById(R.id.profileUsername)
        val titleUsername: TextView = view.findViewById(R.id.titleUsername)

        // Retrieve data from SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString("name", "Unknown")
        val email = sharedPreferences?.getString("email", "Unknown")
        val username = sharedPreferences?.getString("username", "Unknown")

        // Display data
        profileName.text = name
        titleName.text = name
        profileEmail.text = email
        profileUsername.text = username
        titleUsername.text = username
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_activity_user, container, false)
    }

}