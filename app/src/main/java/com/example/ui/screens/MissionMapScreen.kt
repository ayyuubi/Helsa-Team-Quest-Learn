package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.QuestDifficulty
import com.example.model.QuestMission
import com.example.model.QuestStatus
import com.example.ui.theme.*
import com.example.util.QuestExportHelper

@Composable
fun MissionMapScreen(
    missions: List<QuestMission>,
    onSelectMission: (QuestMission) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentMissionToPlay = missions.find { it.status == QuestStatus.AVAILABLE || it.status == QuestStatus.IN_PROGRESS } ?: missions.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
    ) {
        // 1. Chapter Title Card (Bab 1 · Ekosistem Sawah)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = QuestPrimaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Explore,
                                    contentDescription = null,
                                    tint = QuestPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Bab 1 · Ekosistem Sawah",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "MAN 2 Lamongan · Berbasis Gamifikasi",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Badge counter
                    Surface(
                        color = QuestGold,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "2/5 Tuntas",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            color = QuestBaseDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // 2. Scenario Banner (Sawah Sukorejo)
        item {
            Spacer(modifier = Modifier.height(14.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                    // Try to load generated landscape banner
                    Image(
                        painter = painterResource(id = R.drawable.bg_sawah_sukorejo_1791424329737),
                        contentDescription = "Sawah Sukorejo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, QuestBaseDark.copy(alpha = 0.85f))
                                )
                            )
                    )

                    // Text overlay on banner
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp)
                    ) {
                        Surface(
                            color = QuestGoldContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "⭐ Skenario Berkelanjutan",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = QuestGoldText,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ayo selamatkan sawah Sukorejo bersama tim!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 3. Sync Google Calendar Card
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = QuestPrimaryContainer),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        QuestExportHelper.syncMissionToGoogleCalendar(
                            context = context,
                            missionTitle = currentMissionToPlay.title,
                            description = "Selesaikan misi berjenjang #${currentMissionToPlay.number} ${currentMissionToPlay.title} untuk mempertahankan streak belajar!"
                        )
                    }
                    .testTag("sync_calendar_card")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = "Google Calendar",
                        tint = QuestPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Sinkronkan Jadwal Belajar ke Google Calendar",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = QuestBlueTint
                        )
                        Text(
                            text = "Pengingat otomatis untuk capai target akademik tepat waktu",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint = QuestPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // 4. Mission Path (Winding Roadmap nodes per Mockup page 1)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Peta Petualangan dan Jalur Misi",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(missions.size) { index ->
            val mission = missions[index]
            val isEven = index % 2 == 0
            val horizontalOffset = if (isEven) 0.dp else 40.dp

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = if (isEven) Arrangement.Start else Arrangement.End
            ) {
                MissionNodeCard(
                    mission = mission,
                    onClick = { onSelectMission(mission) }
                )
            }
        }

        // 5. Bottom Big CTA "MAIN MISI" (Mockup Gambar L1)
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { onSelectMission(currentMissionToPlay) },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = QuestGold,
                    contentColor = QuestBaseDark
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .testTag("main_misi_button")
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "MAIN MISI ${currentMissionToPlay.number}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "${currentMissionToPlay.questions.size} soal · Hadiah hingga +50 poin XP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun MissionNodeCard(
    mission: QuestMission,
    onClick: () -> Unit
) {
    val nodeColor = when (mission.status) {
        QuestStatus.COMPLETED -> QuestSuccess
        QuestStatus.AVAILABLE -> QuestPrimary
        QuestStatus.IN_PROGRESS -> QuestGold
        QuestStatus.NEEDS_RETRY -> QuestOrange
        QuestStatus.LOCKED -> MaterialTheme.colorScheme.outlineVariant
    }

    val iconVector = when (mission.status) {
        QuestStatus.COMPLETED -> Icons.Default.CheckCircle
        QuestStatus.AVAILABLE -> Icons.Default.PlayArrow
        QuestStatus.IN_PROGRESS -> Icons.Default.HourglassTop
        QuestStatus.NEEDS_RETRY -> Icons.Default.Refresh
        QuestStatus.LOCKED -> Icons.Default.Lock
    }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, nodeColor),
        modifier = Modifier
            .width(280.dp)
            .clickable(enabled = mission.status != QuestStatus.LOCKED) { onClick() }
            .testTag("mission_node_${mission.number}")
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Node circle avatar
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(nodeColor),
                contentAlignment = Alignment.Center
            ) {
                if (mission.status == QuestStatus.COMPLETED) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Tuntas",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                } else if (mission.status == QuestStatus.LOCKED) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Terkunci",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "${mission.number}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = QuestBaseDark
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (mission.isTeamMission) {
                        Surface(
                            color = QuestTealContainer,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Text(
                                text = "Misi Tim",
                                color = QuestTealText,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = mission.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(mission.difficulty.torches) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Obor",
                            tint = QuestGold,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = mission.difficulty.label,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (mission.stars > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "★".repeat(mission.stars),
                            fontSize = 11.sp,
                            color = QuestGold
                        )
                    }
                }
            }
        }
    }
}
