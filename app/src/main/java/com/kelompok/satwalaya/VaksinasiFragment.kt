package com.kelompok.satwalaya

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class VaksinasiFragment : Fragment() {
    private lateinit var repo: SatwalayaRepository
    private lateinit var adapter: LayananAdapter<Vaksinasi>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_vaksinasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repo = SatwalayaRepository(requireContext())
        adapter = LayananAdapter(
            items = repo.getAllVaksinasi(),
            binder = { v, item ->
                v.findViewById<TextView>(R.id.tvNamaPemilik).text = "👤 ${item.namaPemilik}"
                v.findViewById<TextView>(R.id.tvNamaHewan).text = "🐾 ${item.namaHewan} (${item.jenisHewan})"
                v.findViewById<TextView>(R.id.tvTanggalJam).text = "📅 ${item.tanggal} • ⏰ ${item.jam}"
                v.findViewById<TextView>(R.id.tvStatus).text = item.getStatusText()
                v.findViewById<TextView>(R.id.tvCatatan).text = "📝 ${item.catatan}"
            },
            onHapus = { item ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Data")
                    .setMessage("Hapus vaksinasi ${item.namaHewan}?")
                    .setPositiveButton("Hapus") { _, _ ->
                        repo.deleteVaksinasi(item.id)
                        adapter.updateData(repo.getAllVaksinasi())
                    }
                    .setNegativeButton("Batal", null).show()
            }
        )
        view.findViewById<RecyclerView>(R.id.rvVaksinasi).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@VaksinasiFragment.adapter
        }
        view.findViewById<FloatingActionButton>(R.id.fabTambahVaksinasi).setOnClickListener {
            showTambahDialog()
        }
    }

    private fun showTambahDialog() {
        val dv = layoutInflater.inflate(R.layout.dialog_tambah, null)
        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Vaksinasi")
            .setView(dv)
            .setPositiveButton("Simpan") { _, _ ->
                val nama = dv.findViewById<TextInputEditText>(R.id.etNamaPemilik).text.toString()
                val hewan = dv.findViewById<TextInputEditText>(R.id.etNamaHewan).text.toString()
                val jenis = dv.findViewById<TextInputEditText>(R.id.etJenisHewan).text.toString()
                val tanggal = dv.findViewById<TextInputEditText>(R.id.etTanggal).text.toString()
                val jam = dv.findViewById<TextInputEditText>(R.id.etJam).text.toString()
                val catatan = dv.findViewById<TextInputEditText>(R.id.etCatatan).text.toString()
                if (nama.isBlank() || hewan.isBlank()) {
                    Toast.makeText(requireContext(), "Nama pemilik & hewan wajib diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                repo.addVaksinasi(Vaksinasi(namaPemilik = nama, namaHewan = hewan, jenisHewan = jenis, tanggal = tanggal, jam = jam, catatan = catatan.ifBlank { "-" }))
                adapter.updateData(repo.getAllVaksinasi())
                Toast.makeText(requireContext(), "Berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null).show()
    }
}
