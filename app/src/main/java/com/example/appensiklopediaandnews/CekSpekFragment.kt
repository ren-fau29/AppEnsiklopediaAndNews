package com.example.appensiklopediaandnews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore


class CekSpekFragment : Fragment(R.layout.fragment_cek_spek) {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var gamesSpinner: Spinner
    private lateinit var processorSpinner: Spinner
    private lateinit var gpuSpinner: Spinner
    private lateinit var ramSpinner: Spinner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize Spinners
        gamesSpinner = view.findViewById(R.id.spinner_games)
        processorSpinner = view.findViewById(R.id.et_processor)
        gpuSpinner = view.findViewById(R.id.et_gpu)
        ramSpinner = view.findViewById(R.id.et_ram)

        // Fetch data for games and PCs from Firestore
        loadGames()
        loadPC_Cpu()
        loadPC_Gpu()
        loadPC_Ram()
    }

    private fun loadGames() {
        firestore.collection("games")
            .get()
            .addOnSuccessListener { result ->
                val gamesList = mutableListOf<String>()
                for (document in result) {
                    val gameName = document.getString("title")

                    gameName?.let { gamesList.add(it) }
                }
                // Set up processors spinner
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gamesList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                gamesSpinner.adapter = adapter
            }
            .addOnFailureListener {
                // Handle the error
            }
    }

    private fun loadPC_Cpu() {
        firestore.collection("pcs_cpu")
            .get()
            .addOnSuccessListener { result ->
                val processorList = mutableListOf<String>()

                for (document in result) {
                    val processor = document.getString("processor")

                    processor?.let { processorList.add(it) }
                }
                // Set up processors spinner
                val processorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, processorList)
                processorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                processorSpinner.adapter = processorAdapter
            }
            .addOnFailureListener {
                // Handle the error
            }
    }

    private fun loadPC_Gpu() {
        firestore.collection("pcs_vga")
            .get()
            .addOnSuccessListener { result ->
                val gpuList = mutableListOf<String>()
                for (document in result) {
                    val gpu = document.getString("gpu")

                    gpu?.let { gpuList.add(it) }
                }
                // Set up GPU spinner
                val gpuAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gpuList)
                gpuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                gpuSpinner.adapter = gpuAdapter
            }
            .addOnFailureListener {
                // Handle the error
            }
    }

    private fun loadPC_Ram() {
        firestore.collection("pcs_ram")
            .get()
            .addOnSuccessListener { result ->
                val ramList = mutableListOf<String>()
                for (document in result) {
                    val ram = document.getString("ram")

                    ram?.let { ramList.add(it) }
                }
                // Set up RAM spinner
                val ramAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ramList)
                ramAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                ramSpinner.adapter = ramAdapter
            }
            .addOnFailureListener {
                // Handle the error
            }
    }
}