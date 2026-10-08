package com.example.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.provider.CalendarContract
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.model.QuestMission
import java.io.File
import java.io.FileOutputStream

object QuestExportHelper {

    /**
     * Mengekspor laporan kemajuan belajar siswa ke berkas PDF nyata.
     */
    fun exportProgressReportPdf(
        context: Context,
        studentName: String,
        studentLevel: Int,
        xp: Int,
        missions: List<QuestMission>,
        cognitiveScore: Int,
        emotionalScore: Int,
        behavioralScore: Int
    ): File? {
        try {
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Standar A4
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas

            val paint = Paint().apply {
                isAntiAlias = true
            }

            // Background
            paint.color = Color.parseColor("#0B1426")
            canvas.drawRect(0f, 0f, 595f, 842f, paint)

            // Header Banner
            paint.color = Color.parseColor("#14233F")
            canvas.drawRect(20f, 20f, 575f, 130f, paint)

            // Title
            paint.color = Color.parseColor("#38BDF8")
            paint.textSize = 22f
            paint.isFakeBoldText = true
            canvas.drawText("QUEST-LEARN · LAPORAN KEMAJUAN BELAJAR", 40f, 60f, paint)

            // Subtitle & School
            paint.color = Color.parseColor("#EEF3FA")
            paint.textSize = 14f
            paint.isFakeBoldText = false
            canvas.drawText("Madrasah Aliyah Negeri 2 Lamongan · Berbasis Gamifikasi Interaktif", 40f, 85f, paint)
            paint.color = Color.parseColor("#A9B8D3")
            paint.textSize = 12f
            canvas.drawText("Siswa: $studentName  |  Level $studentLevel (Penjelajah)  |  Total XP: $xp", 40f, 110f, paint)

            // Dimensi Keterlibatan Box
            paint.color = Color.parseColor("#14233F")
            canvas.drawRect(20f, 150f, 575f, 260f, paint)

            paint.color = Color.parseColor("#FFC83D")
            paint.textSize = 16f
            paint.isFakeBoldText = true
            canvas.drawText("Dimensi Keterlibatan Siswa (Model ADDIE - LKTI OISEMA 2026)", 40f, 180f, paint)

            paint.color = Color.parseColor("#EEF3FA")
            paint.textSize = 13f
            paint.isFakeBoldText = false
            canvas.drawText("• Keterlibatan Kognitif (Pemahaman Konsep): $cognitiveScore%", 40f, 210f, paint)
            canvas.drawText("• Keterlibatan Emosional (Motivasi & Ketekunan): $emotionalScore%", 40f, 230f, paint)
            canvas.drawText("• Keterlibatan Perilaku (Partisipasi & Kerja Tim): $behavioralScore%", 40f, 250f, paint)

            // Rincian Misi Bab 1
            paint.color = Color.parseColor("#14233F")
            canvas.drawRect(20f, 280f, 575f, 520f, paint)

            paint.color = Color.parseColor("#2DD4BF")
            paint.textSize = 16f
            paint.isFakeBoldText = true
            canvas.drawText("Daftar Progres Misi Bertahap (Bab 1 Ekosistem)", 40f, 310f, paint)

            var y = 340f
            missions.forEach { mission ->
                val statusText = when (mission.status) {
                    com.example.model.QuestStatus.COMPLETED -> "[TUNTAS] Skor ${mission.bestScore}/100 (${mission.stars} Bintang)"
                    com.example.model.QuestStatus.IN_PROGRESS -> "[BERJALAN] Sedang dikerjakan tim"
                    com.example.model.QuestStatus.AVAILABLE -> "[TERSEDIA] Siap dikerjakan"
                    com.example.model.QuestStatus.NEEDS_RETRY -> "[PERLU DIULANG] Peluang lencana ketekunan"
                    com.example.model.QuestStatus.LOCKED -> "[TERKUNCI] Prasyarat belum tuntas"
                }
                paint.color = Color.parseColor("#EEF3FA")
                paint.textSize = 12f
                paint.isFakeBoldText = true
                canvas.drawText("Misi ${mission.number}: ${mission.title}", 40f, y, paint)

                paint.color = Color.parseColor("#A9B8D3")
                paint.isFakeBoldText = false
                canvas.drawText(statusText, 320f, y, paint)
                y += 26f
            }

            // Rekomendasi AI
            paint.color = Color.parseColor("#0E3A5C")
            canvas.drawRect(20f, 540f, 575f, 680f, paint)

            paint.color = Color.parseColor("#38BDF8")
            paint.textSize = 15f
            paint.isFakeBoldText = true
            canvas.drawText("Rekomendasi Analitik Performa Berbasis AI:", 40f, 570f, paint)

            paint.color = Color.parseColor("#EEF3FA")
            paint.textSize = 12f
            paint.isFakeBoldText = false
            canvas.drawText("1. Siswa menunjukkan penguasaan kuat pada materi rantai makanan dasar.", 40f, 600f, paint)
            canvas.drawText("2. Perlu peningkatan pada konsep Pengendalian Hama Terpadu & Interaksi Predator Alami.", 40f, 625f, paint)
            canvas.drawText("3. Disarankan mengikuti Misi Tim nomor 4 untuk memperkuat dimensi kolaborasi.", 40f, 650f, paint)

            // Footer
            paint.color = Color.parseColor("#7D8FB0")
            paint.textSize = 10f
            canvas.drawText("Dokumen ini dibuat otomatis oleh Platform Pembelajaran Quest-Learn · MAN 2 Lamongan", 40f, 800f, paint)

            pdfDocument.finishPage(page)

            val dir = File(context.cacheDir, "reports")
            if (!dir.exists()) dir.mkdirs()
            val file = File(dir, "Laporan_Kemajuan_QuestLearn_${studentName.replace(" ", "_")}.pdf")
            val outputStream = FileOutputStream(file)
            pdfDocument.writeTo(outputStream)
            outputStream.flush()
            outputStream.close()
            pdfDocument.close()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Membagikan file PDF atau pencapaian belajar ke aplikasi lain / media sosial
     */
    fun shareProgressAchievement(context: Context, text: String, pdfFile: File? = null) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = if (pdfFile != null) "application/pdf" else "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Pencapaian Quest-Learn!")
            putExtra(Intent.EXTRA_TEXT, text)
            if (pdfFile != null) {
                try {
                    val uri: Uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        pdfFile
                    )
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } catch (e: Exception) {
                    // Fallback normal share text
                }
            }
        }
        val chooser = Intent.createChooser(intent, "Bagikan Pencapaian Quest-Learn")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    /**
     * Menambahkan jadwal belajar misi Quest-Learn ke Google Calendar pengguna
     */
    fun syncMissionToGoogleCalendar(
        context: Context,
        missionTitle: String,
        description: String,
        minutesFromNow: Long = 60
    ) {
        try {
            val startTime = System.currentTimeMillis() + (minutesFromNow * 60 * 1000)
            val endTime = startTime + (30 * 60 * 1000) // Durasi 30 menit

            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, "Quest-Learn: $missionTitle")
                putExtra(CalendarContract.Events.DESCRIPTION, "Jadwal Belajar Gamifikasi Quest-Learn: $description")
                putExtra(CalendarContract.Events.EVENT_LOCATION, "MAN 2 Lamongan (Online/Offline)")
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Aplikasi Kalender tidak ditemukan pada perangkat", Toast.LENGTH_SHORT).show()
        }
    }
}
