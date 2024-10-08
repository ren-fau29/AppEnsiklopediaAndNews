package com.example.appensiklopediaandnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
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
    private lateinit var btnMatch: Button
    private lateinit var resultTextView: TextView

    private lateinit var selectedCpu: CpuDataSpec
    private lateinit var selectedGpu: GpuDataSpec
    private lateinit var selectedRam: RamDataSpec

    private val bobotCpu = 0.4
    private val bobotGpu = 0.35
    private val bobotRam = 0.25

    private lateinit var gamesList: MutableList<GameDataSpec>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize Spinners
        gamesSpinner = view.findViewById(R.id.spinner_games)
        processorSpinner = view.findViewById(R.id.et_processor)
        gpuSpinner = view.findViewById(R.id.et_gpu)
        ramSpinner = view.findViewById(R.id.et_ram)
        btnMatch = view.findViewById(R.id.btn_match)
        resultTextView = view.findViewById(R.id.resultTextView)

        // Fetch data for games and PCs from Firestore
        loadGames()
        loadPC_Cpu()
        loadPC_Gpu()
        loadPC_Ram()

        // Set up listeners for calculating SAW after all selections are made
        setupSpinnerListeners(view)

        btnMatch.setOnClickListener {
            if (::selectedCpu.isInitialized && ::selectedGpu.isInitialized && ::selectedRam.isInitialized) {
                val selectedGame = gamesList[gamesSpinner.selectedItemPosition]

                // Get minimum requirements for selected game
                val cpuMi = selectedGame.cpuMiCek.toInt()
                val ramMi = selectedGame.ramMiCek.toInt()
                val vgaMi = selectedGame.vgaMiCek.toInt()

                // Calculate the score using SAW method
                val finalScore = calculateSAW(
                    userCpu = selectedCpu.cek,
                    userGpu = selectedGpu.cek,
                    userRam = selectedRam.cek,
                    gameCpu = cpuMi,
                    gameGpu = vgaMi,
                    gameRam = ramMi
                )

                // Display the result based on the final score
                val hasil = if (finalScore >= 1) {
                    "PC kamu memenuhi spesifikasi untuk game ini."
                } else {
                    "PC kamu tidak memenuhi spesifikasi untuk game ini."
                }

                // Show the result in the TextView
                resultTextView.text = "Score: %.2f\n$hasil".format(finalScore)
            } else {
                resultTextView.text = "Harap pilih semua komponen terlebih dahulu."
            }
        }
    }

    private fun loadGames() {
        firestore.collection("games")
            .get()
            .addOnSuccessListener { result ->
                gamesList = mutableListOf()
                val gameTitles = mutableListOf<String>()

                for (document in result) {
                    val game = GameDataSpec(
                        title = document.getString("title") ?: "",
                        cpuMi = document.getString("cpuMi") ?: "",
                        cpuMiCek = document.getLong("cpuMi_cek")?.toString() ?: "0",
                        ramMi = document.getString("ramMi") ?: "",
                        ramMiCek = document.getLong("ramMi_cek")?.toString() ?: "0",
                        vgaMi = document.getString("vgaMi") ?: "",
                        vgaMiCek = document.getLong("vgaMi_cek")?.toString() ?: "0"
                    )

                    gamesList.add(game)
                    gameTitles.add(game.title)
                }

                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gameTitles)
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
                val processorDataList = mutableListOf<CpuDataSpec>()
                val processorNames = mutableListOf<String>()

                for (document in result) {
                    val processor = CpuDataSpec(
                        processor = document.getString("processor") ?: "",
                        cek = document.getLong("cek_cpu")?.toInt() ?: 0
                    )

                    processorDataList.add(processor)
                    processorNames.add(processor.processor)
                }

                val processorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, processorNames)
                processorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                processorSpinner.adapter = processorAdapter

                processorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        selectedCpu = processorDataList[position]
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
                val gpuDataList = mutableListOf<GpuDataSpec>()
                val gpuNames = mutableListOf<String>()

                for (document in result) {
                    val gpu = GpuDataSpec(
                        gpu = document.getString("gpu") ?: "",
                        cek = document.getLong("gpu_cek")?.toInt() ?: 0
                    )

                    gpuDataList.add(gpu)
                    gpuNames.add(gpu.gpu)
                }

                val gpuAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gpuNames)
                gpuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                gpuSpinner.adapter = gpuAdapter

                gpuSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        selectedGpu = gpuDataList[position]
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
                val ramDataList = mutableListOf<RamDataSpec>()
                val ramNames = mutableListOf<String>()

                for (document in result) {
                    val ram = RamDataSpec(
                        ram = document.getString("ram") ?: "",
                        cek = document.getLong("ram_cek")?.toInt() ?: 0
                    )

                    ramDataList.add(ram)
                    ramNames.add(ram.ram)
                }

                ramDataList.sortBy { it.ram.toIntOrNull() ?: 0 }

                val ramAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ramNames)
                ramAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                ramSpinner.adapter = ramAdapter

                ramSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        selectedRam = ramDataList[position]
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

    private fun setupSpinnerListeners(view: View) {
        gamesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedGame = gamesList[position]
                val cpuMi = selectedGame.cpuMiCek.toInt()
                val ramMi = selectedGame.ramMiCek.toInt()
                val vgaMi = selectedGame.vgaMiCek.toInt()

                // Pastikan user sudah memilih CPU, GPU, dan RAM sebelum menghitung
                if (::selectedCpu.isInitialized && ::selectedGpu.isInitialized && ::selectedRam.isInitialized) {
                    val finalScore = calculateSAW(
                        userCpu = selectedCpu.cek,
                        userGpu = selectedGpu.cek,
                        userRam = selectedRam.cek,
                        gameCpu = cpuMi,
                        gameGpu = vgaMi,
                        gameRam = ramMi
                    )

                    // Tentukan hasil apakah PC memenuhi kebutuhan game
                    val hasil = if (finalScore >= 1) {
                        "PC kamu memenuhi spesifikasi untuk game ini."
                    } else {
                        "PC kamu tidak memenuhi spesifikasi untuk game ini."
                    }

                    view?.findViewById<TextView>(R.id.resultTextView)?.text = hasil
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun calculateSAW(
        userCpu: Int, userGpu: Int, userRam: Int,
        gameCpu: Int, gameGpu: Int, gameRam: Int
    ): Double {
        val normalizedCpu = userCpu.toDouble() / gameCpu
        val normalizedGpu = userGpu.toDouble() / gameGpu
        val normalizedRam = userRam.toDouble() / gameRam

        val finalScore = (normalizedCpu * bobotCpu) + (normalizedGpu * bobotGpu) + (normalizedRam * bobotRam)
        return finalScore
    }


}
