package com.kelompok.satwalaya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.kelompok.satwalaya.model.LayananResponse
import com.kelompok.satwalaya.viewmodel.LayananViewModel

class ListFragment : Fragment() {

    private val viewModel: LayananViewModel by viewModels()
    private lateinit var adapter: LayananAdapter<LayananResponse>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewList)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabTambah)

        adapter = LayananAdapter(
            items = emptyList(),
            binder = { v, item ->
                v.findViewById<TextView>(R.id.tvJenisLayanan).text =
                    "${item.getIconLayanan()} ${item.jenisLayanan}"
                v.findViewById<TextView>(R.id.tvNamaPemilik).text = "👤 ${item.namaPemilik}"
                v.findViewById<TextView>(R.id.tvNamaHewan).text =
                    "🐾 ${item.namaHewan} (${item.jenisHewan})"
                v.findViewById<TextView>(R.id.tvTanggalJam).text =
                    "📅 ${item.tanggal} • ⏰ ${item.jam}"
                v.findViewById<TextView>(R.id.tvStatus).text = item.getStatusText()
                v.findViewById<TextView>(R.id.tvCatatan).text =
                    if (item.catatan.isNotBlank() && item.catatan != "-")
                        "📝 ${item.catatan}" else ""
            },
            onHapus = { item -> showDialogHapus(item) }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        fab.setOnClickListener { showDialogTambah() }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            fab.isEnabled = !loading
        }

        viewModel.layananList.observe(viewLifecycleOwner) { list ->
            adapter.updateData(list)
        }
    }

    private fun showDialogTambah() {
        val dv = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_tambah, null)

        // Setup Spinner jenis layanan
        val spinner = dv.findViewById<Spinner>(R.id.spinnerJenisLayanan)
        val jenisLayanan = listOf("Grooming", "Penitipan", "Konsultasi", "Vaksinasi")
        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            jenisLayanan
        )

        AlertDialog.Builder(requireContext())
            .setTitle("➕ Tambah Booking")
            .setView(dv)
            .setPositiveButton("Simpan") { _, _ ->
                val jenis    = spinner.selectedItem.toString()
                val nama     = dv.findViewById<TextInputEditText>(R.id.etNamaPemilik).text.toString().trim()
                val hewan    = dv.findViewById<TextInputEditText>(R.id.etNamaHewan).text.toString().trim()
                val jenisHew = dv.findViewById<TextInputEditText>(R.id.etJenisHewan).text.toString().trim()
                val tanggal  = dv.findViewById<TextInputEditText>(R.id.etTanggal).text.toString().trim()
                val jam      = dv.findViewById<TextInputEditText>(R.id.etJam).text.toString().trim()
                val catatan  = dv.findViewById<TextInputEditText>(R.id.etCatatan).text.toString().trim()

                if (nama.isBlank() || hewan.isBlank() || tanggal.isBlank()) {
                    Toast.makeText(requireContext(),
                        "Nama pemilik, nama hewan & tanggal wajib diisi!",
                        Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                viewModel.tambahBooking(jenis, nama, hewan,
                    jenisHew.ifBlank { "-" }, tanggal, jam.ifBlank { "-" },
                    catatan.ifBlank { "-" })
                Toast.makeText(requireContext(),
                    "Booking $jenis berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showDialogHapus(item: LayananResponse) {
        AlertDialog.Builder(requireContext())
            .setTitle("🗑️ Hapus Booking")
            .setMessage("Hapus booking ${item.jenisLayanan} untuk ${item.namaHewan}?")
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.hapusBooking(item)
                Toast.makeText(requireContext(), "Booking dihapus!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}