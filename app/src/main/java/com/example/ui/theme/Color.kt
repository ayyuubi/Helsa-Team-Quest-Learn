package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Palet Dasar: Permukaan, Merek, Teks (Dokumen Desain Brief Bagian 8.1 - 8.5)
val QuestBaseDark = Color(0xFF0B1426)           // Latar belakang layar (paling dominan)
val QuestSurfaceDark = Color(0xFF14233F)        // Kartu, bottom nav, sheet, panel statistik
val QuestSurfaceSunken = Color(0xFF0F1B33)      // Bidang isian & area dalam kartu
val QuestOutlineVariant = Color(0xFF2A3D5E)     // Garis tepi dekoratif, pembagi, track progres
val QuestOutline = Color(0xFF6A7FA6)            // Border bidang isian & interaktif (>= 3:1)

val QuestPrimary = Color(0xFF38BDF8)            // Biru Quest - Merek, CTA, aktif, simpul Tersedia
val QuestPrimaryDeep = Color(0xFF0C5A8A)        // Status pressed, border kartu penjelasan
val QuestPrimaryContainer = Color(0xFF0E3A5C)   // Chip biru gelap, kartu penjelasan
val QuestBlueTint = Color(0xFFBAE6FD)           // Teks aksen di permukaan gelap

val QuestTextPrimaryDark = Color(0xFFEEF3FA)    // Teks utama tema gelap
val QuestTextSecondaryDark = Color(0xFFA9B8D3)  // Subjudul, keterangan, label sumbu grafik
val QuestTextTertiaryDark = Color(0xFF7D8FB0)   // Placeholder, teks pendukung

// Palet Penghargaan & Status (Bagian 8.2)
val QuestGold = Color(0xFFFFC83D)               // XP, lencana, streak, bintang, status Berjalan
val QuestGoldContainer = Color(0xFF4A3A0A)      // Wadah chip XP & lencana
val QuestGoldText = Color(0xFFFDE68A)

val QuestTeal = Color(0xFF2DD4BF)               // Segala hal tim, misi tim, ruang tim, kompak
val QuestTealContainer = Color(0xFF0F3D3A)      // Wadah kartu tim
val QuestTealText = Color(0xFF99F6E4)

val QuestSuccess = Color(0xFF34D399)            // Tuntas, skor >= ambang, jawaban benar
val QuestSuccessContainer = Color(0xFF0F3D2B)   // Panel Tuntas
val QuestSuccessText = Color(0xFFBBF7D0)

val QuestOrange = Color(0xFFFB923C)             // Perlu diulang, belum tepat (suportif, bukan merah)
val QuestOrangeContainer = Color(0xFF4A2410)    // Panel Perlu Diulang
val QuestOrangeText = Color(0xFFFED7AA)

val QuestError = Color(0xFFF87171)              // Hanya error sistem/formulir
val QuestErrorContainer = Color(0xFF4C1D1D)
val QuestErrorText = Color(0xFFFECACA)

val QuestDanger = Color(0xFFDC2626)             // Tombol destruktif
