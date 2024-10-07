package com.example.appensiklopediaandnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.appensiklopediaandnews.model.GameDataSpec
import com.example.appensiklopediaandnews.model.GpuDataSpec
import com.example.appensiklopediaandnews.model.CpuDataSpec
import com.example.appensiklopediaandnews.model.RamDataSpec
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
                val gamesList = mutableListOf<GameDataSpec>()  // List to store full game data
                val gameTitles = mutableListOf<String>()  // List for displaying in spinner

                for (document in result) {
                    val game = GameDataSpec(
                        title = document.getString("title") ?: "",
                        cpuMi = document.getString("cpuMi") ?: "",
                        cpuMiCek = document.getLong("cpuMi_cek")?.toString() ?: "0",  // Use camelCase naming
                        ramMi = document.getString("ramMi") ?: "",
                        ramMiCek = document.getLong("ramMi_cek")?.toString() ?: "0",  // Use camelCase naming
                        vgaMi = document.getString("vgaMi") ?: "",
                        vgaMiCek = document.getLong("vgaMi_cek")?.toString() ?: "0"   // Use camelCase naming
                    )

                    gamesList.add(game)  // Add full game data to list
                    game.title.let { gameTitles.add(it) }  // Add only title for spinner
                }

                // Set up adapter with game titles for spinner
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gameTitles)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                gamesSpinner.adapter = adapter

                gamesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        // Get the selected game object
                        val selectedGame = gamesList[position]

                        // You can now access the selected game data
                        val cpuMi = selectedGame.cpuMi
                        val cpuMiCek = selectedGame.cpuMiCek
                        val ramMi = selectedGame.ramMi
                        val ramMiCek = selectedGame.ramMiCek
                        val vgaMi = selectedGame.vgaMi
                        val vgaMiCek = selectedGame.vgaMiCek

                        // Use this data for calculations later
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Do nothing
                    }
                }
            }
            .addOnFailureListener {
                // Handle the error
            }
    }


    private fun loadPC_Cpu() {
        firestore.collection("pcs_cpu")
            .get()
            .addOnSuccessListener { result ->
                val processorDataList = mutableListOf<CpuDataSpec>()  // List to store full processor data
                val processorNames = mutableListOf<String>()  // List for displaying in spinner

                for (document in result) {
                    val processor = CpuDataSpec(
                        processor = document.getString("processor") ?: "",
                        cek = document.getLong("cek")?.toInt() ?: 0  // Assuming 'processor_poin' is stored as a number
                    )

                    processorDataList.add(processor)  // Add full processor data to list
                    processor.processor.let { processorNames.add(it) }  // Add only processor name for spinner
                }

                // Set up adapter with processor names for spinner
                val processorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, processorNames)
                processorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                processorSpinner.adapter = processorAdapter

                // Spinner item selection listener
                processorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        // Get the selected processor object
                        val selectedProcessor = processorDataList[position]

                        // You can now access the selected processor data for background calculation
                        val processorName = selectedProcessor.processor
                        val processorPoin = selectedProcessor.cek

                        // You can use this data for calculations later
                        // Example: pass to a function for SAW method calculation
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Do nothing
                    }
                }
            }
            .addOnFailureListener {
                // Handle the error
            }
    }


    private fun loadPC_Gpu() {
        firestore.collection("pcs_vga")
            .get()
            .addOnSuccessListener { result ->
                val gpuDataList = mutableListOf<GpuDataSpec>()  // List to store full GPU data
                val gpuNames = mutableListOf<String>()  // List for displaying in spinner

                for (document in result) {
                    val gpu = GpuDataSpec(
                        gpu = document.getString("gpu") ?: "",
                        cek = document.getLong("cek")?.toInt() ?: 0  // Assuming 'gpu_poin' is stored as a number
                    )

                    gpuDataList.add(gpu)  // Add full GPU data to list
                    gpu.gpu.let { gpuNames.add(it) }  // Add only GPU name for spinner
                }

                // Set up adapter with GPU names for spinner
                val gpuAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gpuNames)
                gpuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                gpuSpinner.adapter = gpuAdapter

                // Spinner item selection listener
                gpuSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        // Get the selected GPU object
                        val selectedGpu = gpuDataList[position]

                        // You can now access the selected GPU data for background calculation
                        val gpuName = selectedGpu.gpu
                        val gpuPoin = selectedGpu.cek

                        // Use this data for calculations later
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Do nothing
                    }
                }
            }
            .addOnFailureListener {
                // Handle the error
            }
    }


    private fun loadPC_Ram() {
        firestore.collection("pcs_ram")
            .get()
            .addOnSuccessListener { result ->
                val ramDataList = mutableListOf<RamDataSpec>()  // List to store full RAM data
                val ramNames = mutableListOf<String>()  // List for displaying in spinner

                for (document in result) {
                    val ram = RamDataSpec(
                        ram = document.getString("ram") ?: "",
                        cek = document.getLong("cek")?.toInt() ?: 0  // Assuming 'ram_poin' is stored as a number
                    )

                    ramDataList.add(ram)  // Add full RAM data to list
                }

                // Sort RAM data list by RAM value (assuming it is stored as a string)
                ramDataList.sortBy { it.ram.toIntOrNull() ?: 0 }  // Convert to Int for sorting

                // Prepare the list of RAM names for spinner after sorting
                ramDataList.forEach { ramData ->
                    ramNames.add(ramData.ram)  // Add only RAM name for spinner
                }

                // Set up adapter with RAM names for spinner
                val ramAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ramNames)
                ramAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                ramSpinner.adapter = ramAdapter

                // Spinner item selection listener
                ramSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        // Dapatkan RAM object
                        val selectedRam = ramDataList[position]

                        // You can now access the selected RAM data for background calculation
                        val ramName = selectedRam.ram
                        val ramPoin = selectedRam.cek

                        // Use this data for calculations later
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Do nothing
                    }
                }
            }
            .addOnFailureListener {
                // Handle the error
            }
    }


}