package com.kelompok.satwalaya.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kelompok.satwalaya.Grooming
import com.kelompok.satwalaya.Konsultasi
import com.kelompok.satwalaya.Penitipan
import com.kelompok.satwalaya.SatwalayaRepository
import com.kelompok.satwalaya.Vaksinasi
import com.kelompok.satwalaya.model.LayananResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LayananViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = SatwalayaRepository(application)

    private val _layananList = MutableLiveData<List<LayananResponse>>()
    val layananList: LiveData<List<LayananResponse>> = _layananList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init { loadSemuaBooking() }

    // Gabungkan semua booking dari semua tabel
    fun loadSemuaBooking() {
        viewModelScope.launch {
            _isLoading.value = true
            val semua = withContext(Dispatchers.IO) {
                val list = mutableListOf<LayananResponse>()

                repo.getAllPenitipan().forEach {
                    list.add(LayananResponse(it.id, "Penitipan", it.namaPemilik,
                        it.namaHewan, it.jenisHewan, it.tanggal, it.jam, it.catatan, it.status))
                }
                repo.getAllGrooming().forEach {
                    list.add(LayananResponse(it.id, "Grooming", it.namaPemilik,
                        it.namaHewan, it.jenisHewan, it.tanggal, it.jam, it.catatan, it.status))
                }
                repo.getAllKonsultasi().forEach {
                    list.add(LayananResponse(it.id, "Konsultasi", it.namaPemilik,
                        it.namaHewan, it.jenisHewan, it.tanggal, it.jam, it.catatan, it.status))
                }
                repo.getAllVaksinasi().forEach {
                    list.add(LayananResponse(it.id, "Vaksinasi", it.namaPemilik,
                        it.namaHewan, it.jenisHewan, it.tanggal, it.jam, it.catatan, it.status))
                }

                // Urutkan berdasarkan tanggal
                list.sortBy { it.tanggal }
                list
            }
            _layananList.value = semua
            _isLoading.value = false
        }
    }

    // Tambah booking baru sesuai jenis layanan
    fun tambahBooking(
        jenisLayanan: String,
        namaPemilik: String,
        namaHewan: String,
        jenisHewan: String,
        tanggal: String,
        jam: String,
        catatan: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (jenisLayanan) {
                "Grooming"   -> repo.addGrooming(Grooming(namaPemilik = namaPemilik,
                    namaHewan = namaHewan, jenisHewan = jenisHewan,
                    tanggal = tanggal, jam = jam, catatan = catatan))
                "Penitipan"  -> repo.addPenitipan(Penitipan(namaPemilik = namaPemilik,
                    namaHewan = namaHewan, jenisHewan = jenisHewan,
                    tanggal = tanggal, jam = jam, catatan = catatan))
                "Konsultasi" -> repo.addKonsultasi(Konsultasi(namaPemilik = namaPemilik,
                    namaHewan = namaHewan, jenisHewan = jenisHewan,
                    tanggal = tanggal, jam = jam, catatan = catatan))
                "Vaksinasi"  -> repo.addVaksinasi(Vaksinasi(namaPemilik = namaPemilik,
                    namaHewan = namaHewan, jenisHewan = jenisHewan,
                    tanggal = tanggal, jam = jam, catatan = catatan))
            }
            loadSemuaBooking()
        }
    }

    // Hapus booking sesuai jenis layanan
    fun hapusBooking(item: LayananResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            when (item.jenisLayanan) {
                "Grooming"   -> repo.deleteGrooming(item.id)
                "Penitipan"  -> repo.deletePenitipan(item.id)
                "Konsultasi" -> repo.deleteKonsultasi(item.id)
                "Vaksinasi"  -> repo.deleteVaksinasi(item.id)
            }
            loadSemuaBooking()
        }
    }
}