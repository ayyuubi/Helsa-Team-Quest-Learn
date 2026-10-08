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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ProfileAndSettingsScreen(
    studentName: String,
    nickname: String,
    schoolName: String,
    level: Int,
    levelTitle: String,
    currentXp: Int,
    isDarkMode: Boolean,
    isOffline: Boolean,
    onToggleDark: () -> Unit,
    onToggleOffline: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Profile Avatar Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(QuestPrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = nickname.first().toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = QuestPrimary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = studentName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "$schoolName · Kelas XI-IPA-2",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    color = QuestGoldContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Level $level: $levelTitle ($currentXp XP)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = QuestGoldText,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // UU PDP 27/2022 Mandate Card (Design Brief Bagian 2.4)
        Card(
            colors = CardDefaults.cardColors(containerColor = QuestPrimaryContainer),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Privasi",
                    tint = QuestPrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Data Terlindungi (UU No. 27 Tahun 2022)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = QuestBlueTint
                    )
                    Text(
                        text = "Pemrosesan data mengacu UU PDP RI. Hanya digunakan untuk pembelajaran dan diakses sesuai peran yang sah.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Settings Toggles
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(18.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Preferensi & Kenyamanan Belajar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Mode Gelap (Dark Mode)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.DarkMode, contentDescription = null, tint = QuestGold)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Mode Gelap (Malam Hari)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text("Kenyamanan mata saat belajar di malam hari", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { onToggleDark() },
                        modifier = Modifier.testTag("switch_mode_gelap")
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = QuestOutlineVariant)

                // Mode Offline (Akses tanpa internet)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.CloudOff, contentDescription = null, tint = QuestOrange)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Simulasi Mode Luring (Offline)", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text("Akses materi dan simpan jawaban tanpa koneksi", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Switch(
                        checked = isOffline,
                        onCheckedChange = { onToggleOffline() },
                        modifier = Modifier.testTag("switch_mode_offline")
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = QuestOutlineVariant)

                // Notifikasi Pengingat Otomatis
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.NotificationsActive, contentDescription = null, tint = QuestPrimary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Pengingat Misi Otomatis", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text("Maksimal 1 notifikasi lembut per hari", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Switch(
                        checked = true,
                        onCheckedChange = {
                            Toast.makeText(context, "Pengingat misi aktif jam 16.30 WIB", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.testTag("switch_pengingat_notif")
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Role Locking statement
        Text(
            text = "Role-Locking Aktif: Peran dikunci pada sesi ini untuk integritas penilaian akademik.",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(96.dp))
    }
}
