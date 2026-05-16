package com.kelompok.satwalaya.model

data class LayananResponse(
    val id: Int,
    val jenisLayanan: String,
    val namaPemilik: String,
    val namaHewan: String,
    val jenisHewan: String,
    val tanggal: String,
    val jam: String,
    val catatan: String,
    val status: Int = 0
) {
    fun getStatusText() = if (status == 1) "✅ Selesai" else "⏳ Terjadwal"
    fun getIconLayanan() = when (jenisLayanan) {
        "Grooming"   -> "🛁"
        "Penitipan"  -> "🏨"
        "Konsultasi" -> "🩺"
        "Vaksinasi"  -> "💉"
        else         -> "🐾"
    }
}