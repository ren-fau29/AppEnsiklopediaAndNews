package com.example.appensiklopediaandnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ReportFragment : Fragment() {

    // Inisialisasi Firestore
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextMessage = view.findViewById<EditText>(R.id.etBugReport)
        val buttonSend = view.findViewById<Button>(R.id.btnSendReport)

        // Event saat tombol 'Send Report' ditekan
        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()

            // Validasi agar pesan tidak kosong atau kurang dari 10 karakter
            if (message.length < 10) {
                Toast.makeText(
                    requireContext(),
                    "Message must be at least 10 characters",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Membuat map untuk data yang akan disimpan
            val reportData = hashMapOf(
                "message" to message,
                "timestamp" to System.currentTimeMillis()
            )

            // Menyimpan ke Firestore pada tabel 'report_bug'
            db.collection("report_bug")
                .add(reportData)
                .addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "Report sent successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Reset EditText setelah sukses
                    editTextMessage.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        requireContext(),
                        "Failed to send report: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReportFragment().apply {

            }
    }
}