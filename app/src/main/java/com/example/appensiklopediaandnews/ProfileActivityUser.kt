package com.example.appensiklopediaandnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class ProfileActivityUser : Fragment(R.layout.fragment_profile_activity_user) {
    // TODO: Rename and change types of parameters

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileName: TextView = view.findViewById(R.id.profileName)
        val profileEmail: TextView = view.findViewById(R.id.profileEmail)
        val profileUsername: TextView = view.findViewById(R.id.profileUsername)
        val profilePassword: TextView = view.findViewById(R.id.profilePassword)

        // Retrieve data from Intent
        val name = activity?.intent?.getStringExtra("name")
        val email = activity?.intent?.getStringExtra("email")
        val username = activity?.intent?.getStringExtra("username")
        val password = activity?.intent?.getStringExtra("password")

        // Display data
        profileName.text = name
        profileEmail.text = email
        profileUsername.text = username
        profilePassword.text = password
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_activity_user, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileActivityUser().apply {
                arguments = Bundle().apply {

                }
            }
    }
}