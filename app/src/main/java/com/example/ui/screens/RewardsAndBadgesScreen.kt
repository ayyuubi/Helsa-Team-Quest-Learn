package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BadgeItem
import com.example.model.QuestMission
import com.example.ui.theme.*
import com.example.util.QuestExportHelper

@Composable
fun RewardsAndBadgesScreen(
    currentXp: Int,
    nextLevelXp: Int,
    levelTitle: String,
    level: Int,
    cognitive: Int,
    emotional: Int,
    behavioral: Int,
    badges: List<BadgeItem>,
    missions: List<QuestMission>,
    studentName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedBadgeForDetail by remember { mutableStateOf<BadgeItem?>(null) }
    var showAiRecommendationDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Big Level Badge Avatar (Mockup Gambar L3)
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(QuestPrimaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(QuestGold),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = QuestBaseDark
                )
            }

            // Level pill footer
            Surface(
                color = QuestGold,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 6.dp)
            ) {
                Text(
                    text = "$level",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = QuestBaseDark,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = levelTitle,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "${nextLevelXp - currentXp} poin lagi menuju Level ${level + 1}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Three Engagement Dimensions (LKTI OISEMA - Mockup L3)
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Analisis Dimensi Keterlibatan (LKTI 2026)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))

                DimensionBarItem(label = "Kognitif", value = cognitive, color = QuestPrimary)
                Spacer(modifier = Modifier.height(10.dp))
                DimensionBarItem(label = "Emosional", value = emotional, color = Color(0xFFF472B6))
                Spacer(modifier = Modifier.height(10.dp))
                DimensionBarItem(label = "Perilaku", value = behavioral, color = QuestSuccess)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // AI Performance Analysis Card & Button
        Card(
            colors = CardDefaults.cardColors(containerColor = QuestPrimaryContainer),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = "AI",
                    tint = QuestPrimary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Analisis Performa Berbasis AI",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = QuestBlueTint
                    )
                    Text(
                        text = "Rekomendasi materi & tindak lanjut belajar personal",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                FilledTonalButton(
                    onClick = { showAiRecommendationDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = QuestPrimary,
                        contentColor = QuestBaseDark
                    ),
                    modifier = Modifier.testTag("buka_analisis_ai_button")
                ) {
                    Text("Buka", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Export PDF and Social Share Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    val pdf = QuestExportHelper.exportProgressReportPdf(
                        context = context,
                        studentName = studentName,
                        studentLevel = level,
                        xp = currentXp,
                        missions = missions,
                        cognitiveScore = cognitive,
                        emotionalScore = emotional,
                        behavioralScore = behavioral
                    )
                    if (pdf != null) {
                        Toast.makeText(context, "PDF Berhasil dibuat di cache: ${pdf.name}", Toast.LENGTH_SHORT).show()
                        QuestExportHelper.shareProgressAchievement(
                            context = context,
                            text = "Laporan Kemajuan Belajar Siswa di Platform Quest-Learn MAN 2 Lamongan.",
                            pdfFile = pdf
                        )
                    } else {
                        Toast.makeText(context, "Gagal membuat berkas PDF", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = QuestTeal,
                    contentColor = QuestBaseDark
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("ekspor_pdf_button")
            ) {
                Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Ekspor PDF", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    val shareMsg = "Aku berhasil mencapai Level $level ($levelTitle) dengan $currentXp XP di Quest-Learn MAN 2 Lamongan! Belajar jadi misi petualangan seru! 🚀🌾"
                    QuestExportHelper.shareProgressAchievement(context, shareMsg)
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = QuestGold,
                    contentColor = QuestBaseDark
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("bagikan_pencapaian_button")
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Bagikan", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Badge Grid (Mockup Gambar L3)
        Text(
            text = "Koleksi Lencana Penghargaan",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Grid 3 columns of Badges
        val rows = badges.chunked(3)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                rowItems.forEach { badge ->
                    BadgeCircleItem(
                        badge = badge,
                        onClick = { selectedBadgeForDetail = badge }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(96.dp))
    }

    // Modal Sheet Detail Lencana
    selectedBadgeForDetail?.let { badge ->
        AlertDialog(
            onDismissRequest = { selectedBadgeForDetail = null },
            confirmButton = {
                TextButton(onClick = { selectedBadgeForDetail = null }) {
                    Text("Tutup", color = QuestPrimary)
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(badge.iconEmoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(badge.name, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text("Kategori: ${badge.category}", color = QuestGold, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(badge.description, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(6.dp))
                    if (badge.isUnlocked) {
                        Text("Diraih pada: ${badge.unlockedDate ?: "Terkini"}", color = QuestSuccess, fontSize = 12.sp)
                    } else {
                        Text("Status: Belum Terbuka (Selesaikan misi untuk meraih lencana ini)", color = QuestOrange, fontSize = 12.sp)
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    // Dialog AI Recommendation
    if (showAiRecommendationDialog) {
        AlertDialog(
            onDismissRequest = { showAiRecommendationDialog = false },
            confirmButton = {
                Button(
                    onClick = { showAiRecommendationDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = QuestPrimary, contentColor = QuestBaseDark)
                ) {
                    Text("Pahami Rekomendasi", fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = QuestGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Analisis AI Quest-Learn", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text(
                        text = "Evaluasi Pola Kemajuan Belajar:",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• Kelebihan: Kamu sangat cepat dan tepat dalam materi pengenalan tingkatan rantai makanan (Akurasi 95%).\n\n" +
                                "• Perlu Diperkuat: Konsep Pengendalian Hama Terpadu (PHT) dan perlindungan predator alami masih memerlukan 1 kali pengulangan misi.\n\n" +
                                "• Rekomendasi Misi Berikutnya: Kerjakan Misi 3 'Wereng Menyerang' atau gabung di Misi Tim 4 untuk memperkuat pemahaman holistik.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun DimensionBarItem(
    label: String,
    value: Int,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(80.dp)
        )

        LinearProgressIndicator(
            progress = { value / 100f },
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp)),
            color = color,
            trackColor = QuestOutlineVariant
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "$value",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(30.dp)
        )
    }
}

@Composable
fun BadgeCircleItem(
    badge: BadgeItem,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(96.dp)
            .clickable { onClick() }
            .testTag("badge_item_${badge.id}")
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(if (badge.isUnlocked) QuestSurfaceSunken else QuestBaseDark)
                .border(
                    width = 2.dp,
                    color = if (badge.isUnlocked) QuestGold else QuestOutlineVariant,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (badge.isUnlocked) {
                Text(text = badge.iconEmoji, fontSize = 28.sp)
            } else {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Terkunci",
                    tint = QuestOutlineVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = badge.name,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (badge.isUnlocked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
