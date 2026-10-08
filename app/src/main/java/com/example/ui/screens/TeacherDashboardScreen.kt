package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import com.example.model.StudentSupportStatus
import com.example.ui.theme.*

@Composable
fun TeacherDashboardScreen(
    cognitivePercent: Int,
    emotionalPercent: Int,
    behavioralPercent: Int,
    studentsNeedingSupport: List<StudentSupportStatus>,
    selectedDifficulty: String,
    individualLeaderboardEnabled: Boolean,
    onDifficultyChanged: (String) -> Unit,
    onToggleLeaderboard: (Boolean) -> Unit,
    onSendCheer: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showClassSuccessDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Header Title (Mockup Gambar L5: "Ruang Guru · Kelas XI · Ekosistem / Pantau dan atur misi")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Ruang Guru · Kelas XI-IPA-2 · Ekosistem",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Pantau dan Atur Misi",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Surface(
                color = QuestGoldContainer,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Data Real-Time",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = QuestGoldText,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Three Circular Gauge Cards (Mockup Gambar L5: 74% Kognitif, 68% Emosional, 81% Perilaku)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TeacherStatCard(
                percent = cognitivePercent,
                label = "Keterlibatan\nkognitif",
                indicatorColor = QuestPrimary,
                modifier = Modifier.weight(1f)
            )

            TeacherStatCard(
                percent = emotionalPercent,
                label = "Keterlibatan\nemosional",
                indicatorColor = Color(0xFFF472B6),
                modifier = Modifier.weight(1f)
            )

            TeacherStatCard(
                percent = behavioralPercent,
                label = "Keterlibatan\nperilaku",
                indicatorColor = QuestSuccess,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Card Siswa yang Perlu Didampingi (Daftar "Perlu Dukungan" - Gambar L5)
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Siswa yang Perlu Didampingi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        color = QuestOrangeContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${studentsNeedingSupport.size} Siswa",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = QuestOrangeText,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                studentsNeedingSupport.forEach { student ->
                    Surface(
                        color = QuestSurfaceSunken,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(QuestOrangeContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = student.studentName.first().toString(),
                                    fontWeight = FontWeight.Bold,
                                    color = QuestOrangeText
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = student.studentName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = student.reasonText,
                                    fontSize = 11.sp,
                                    color = QuestOrangeText
                                )
                            }

                            FilledTonalIconButton(
                                onClick = {
                                    onSendCheer(student.studentName)
                                    Toast.makeText(context, "Pesan semangat dikirim ke ${student.studentName}!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(32.dp).testTag("btn_cheer_${student.studentCode}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Kirim Semangat",
                                    tint = QuestPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Atur Misi 4 & Pengaturan Gamifikasi (Mockup Gambar L5)
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Atur Misi 4: Pulihkan Sawah",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Tingkat Kesulitan:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Triple Segment Button (Mudah, Sedang, Sulit - Gambar L5)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Mudah", "Sedang", "Sulit").forEach { level ->
                        val isSelected = selectedDifficulty == level
                        Button(
                            onClick = { onDifficultyChanged(level) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) QuestGold else QuestSurfaceSunken,
                                contentColor = if (isSelected) QuestBaseDark else MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("btn_diff_$level")
                        ) {
                            Text(text = level, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Switch Tampilkan peringkat individu (Gambar L5)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tampilkan peringkat individu",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Switch(
                        checked = individualLeaderboardEnabled,
                        onCheckedChange = { onToggleLeaderboard(it) },
                        modifier = Modifier.testTag("switch_teacher_leaderboard")
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tombol SIMPAN (Gambar L5)
                Button(
                    onClick = {
                        showClassSuccessDialog = true
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = QuestGold,
                        contentColor = QuestBaseDark
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_simpan_pengaturan_guru")
                ) {
                    Text("SIMPAN PENGATURAN KELAS", fontWeight = FontWeight.ExtraBold)
                }
            }
        }

        Spacer(modifier = Modifier.height(96.dp))
    }

    if (showClassSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showClassSuccessDialog = false },
            confirmButton = {
                TextButton(onClick = { showClassSuccessDialog = false }) {
                    Text("OK", color = QuestPrimary)
                }
            },
            title = { Text("Tersimpan!", fontWeight = FontWeight.Bold) },
            text = { Text("Pengaturan gamifikasi kelas dan adaptasi kesulitan misi berhasil disinkronkan ke seluruh siswa.") },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun TeacherStatCard(
    percent: Int,
    label: String,
    indicatorColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(54.dp)
            ) {
                CircularProgressIndicator(
                    progress = { percent / 100f },
                    modifier = Modifier.fillMaxSize(),
                    color = indicatorColor,
                    strokeWidth = 6.dp,
                    trackColor = QuestOutlineVariant
                )
                Text(
                    text = "$percent%",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = label,
                fontSize = 10.sp,
                lineHeight = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
