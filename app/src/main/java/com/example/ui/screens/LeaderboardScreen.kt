package com.example.ui.screens

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LeaderboardEntry
import com.example.model.TeamLeaderboardEntry
import com.example.ui.theme.*

@Composable
fun LeaderboardScreen(
    teamLeaderboard: List<TeamLeaderboardEntry>,
    individualLeaderboard: List<LeaderboardEntry>,
    hideMyRanking: Boolean,
    onToggleHideRanking: () -> Unit,
    onStartTeamMission: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeMode by remember { mutableStateOf(0) } // 0: Tim, 1: Individu

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Toggle Pill: Tim vs Individu (Mockup Gambar L4)
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(QuestSurfaceDark)
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { activeMode = 0 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeMode == 0) QuestTeal else Color.Transparent,
                    contentColor = if (activeMode == 0) QuestBaseDark else MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                modifier = Modifier.testTag("tab_peringkat_tim")
            ) {
                Text("Tim", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { activeMode = 1 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeMode == 1) QuestPrimary else Color.Transparent,
                    contentColor = if (activeMode == 1) QuestBaseDark else MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                modifier = Modifier.testTag("tab_peringkat_individu")
            ) {
                Text("Individu", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Podium Top 3 (Mockup Gambar L4)
        if (activeMode == 0) {
            TeamPodiumView(teamLeaderboard.take(3))
        } else {
            IndividualPodiumView(individualLeaderboard.take(3))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // List 4th, 5th, etc.
        if (activeMode == 0) {
            teamLeaderboard.drop(3).forEach { team ->
                TeamLeaderboardRow(team = team)
                Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            if (hideMyRanking) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = QuestSurfaceSunken),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = null, tint = QuestTextSecondaryDark)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Peringkat individumu disembunyikan untuk menjaga kenyamanan belajarmu. Kemajuan pribadimu tetap tercatat rapi.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                individualLeaderboard.drop(3).forEach { user ->
                    IndividualLeaderboardRow(user = user)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Misi Kelas Kolaboratif Card (Mockup Gambar L4: "Misi Kelas: Pulihkan Sawah 68%")
        Card(
            colors = CardDefaults.cardColors(containerColor = QuestSurfaceDark),
            shape = RoundedCornerShape(18.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Groups, contentDescription = null, tint = QuestGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Misi Kelas: Pulihkan Sawah",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "68%",
                        fontWeight = FontWeight.ExtraBold,
                        color = QuestPrimary,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { 0.68f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFFF472B6),
                    trackColor = QuestOutlineVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Target 75%. Poin semua tim ikut menambah dan memulihkan ekosistem sawah Sukorejo bersama!",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Privacy switch: Sembunyikan peringkat individu (Design Brief Bagian 4.8 & Mockup L4)
        Card(
            colors = CardDefaults.cardColors(containerColor = QuestSurfaceSunken),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Sembunyikan peringkat individu",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Hanya kamu yang melihat posisimu",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = hideMyRanking,
                    onCheckedChange = { onToggleHideRanking() },
                    modifier = Modifier.testTag("switch_sembunyikan_peringkat")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button: KERJAKAN MISI TIM +150 (Gambar L4)
        Button(
            onClick = onStartTeamMission,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = QuestSuccess,
                contentColor = QuestBaseDark
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("kerjakan_misi_tim_button")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Groups, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "KERJAKAN MISI TIM +150",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(96.dp))
    }
}

@Composable
fun TeamPodiumView(top3: List<TeamLeaderboardEntry>) {
    if (top3.size < 3) return
    val first = top3[0]
    val second = top3[1]
    val third = top3[2]

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        // 2nd Place
        PodiumPillar(
            rank = 2,
            title = second.teamName,
            score = second.scoreXp,
            heightDp = 100.dp,
            badgeColor = Color(0xFFE2E8F0),
            textColor = QuestBaseDark,
            emblemLetter = second.teamName.first().toString()
        )

        // 1st Place
        PodiumPillar(
            rank = 1,
            title = first.teamName,
            score = first.scoreXp,
            heightDp = 130.dp,
            badgeColor = QuestGold,
            textColor = QuestBaseDark,
            isWinner = true,
            emblemLetter = first.teamName.first().toString()
        )

        // 3rd Place
        PodiumPillar(
            rank = 3,
            title = third.teamName,
            score = third.scoreXp,
            heightDp = 80.dp,
            badgeColor = Color(0xFFFDBA74),
            textColor = QuestBaseDark,
            emblemLetter = third.teamName.first().toString()
        )
    }
}

@Composable
fun IndividualPodiumView(top3: List<LeaderboardEntry>) {
    if (top3.size < 3) return
    val first = top3[0]
    val second = top3[1]
    val third = top3[2]

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        PodiumPillar(
            rank = 2,
            title = second.name,
            score = second.scoreXp,
            heightDp = 100.dp,
            badgeColor = Color(0xFFE2E8F0),
            textColor = QuestBaseDark,
            emblemLetter = second.name.first().toString()
        )

        PodiumPillar(
            rank = 1,
            title = first.name,
            score = first.scoreXp,
            heightDp = 130.dp,
            badgeColor = QuestGold,
            textColor = QuestBaseDark,
            isWinner = true,
            emblemLetter = first.name.first().toString()
        )

        PodiumPillar(
            rank = 3,
            title = third.name,
            score = third.scoreXp,
            heightDp = 80.dp,
            badgeColor = Color(0xFFFDBA74),
            textColor = QuestBaseDark,
            emblemLetter = third.name.first().toString()
        )
    }
}

@Composable
fun PodiumPillar(
    rank: Int,
    title: String,
    score: Int,
    heightDp: androidx.compose.ui.unit.Dp,
    badgeColor: Color,
    textColor: Color,
    isWinner: Boolean = false,
    emblemLetter: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        if (isWinner) {
            Text("👑", fontSize = 22.sp)
        }

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(badgeColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emblemLetter,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = textColor
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "$score",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Pillar block
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(heightDp)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(badgeColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$rank",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textColor
            )
        }
    }
}

@Composable
fun TeamLeaderboardRow(team: TeamLeaderboardEntry) {
    Surface(
        color = if (team.isCurrentTeam) QuestPrimaryContainer else QuestSurfaceDark,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (team.isCurrentTeam) QuestPrimary else QuestOutlineVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (team.isCurrentTeam) QuestGold else QuestSurfaceSunken),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${team.rank}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (team.isCurrentTeam) QuestBaseDark else MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = team.teamName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (team.isCurrentTeam) FontWeight.Bold else FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${team.scoreXp}",
                fontWeight = FontWeight.Bold,
                color = QuestGold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun IndividualLeaderboardRow(user: LeaderboardEntry) {
    Surface(
        color = if (user.isCurrentUser) QuestPrimaryContainer else QuestSurfaceDark,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (user.isCurrentUser) QuestPrimary else QuestOutlineVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (user.isCurrentUser) QuestGold else QuestSurfaceSunken),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${user.rank}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (user.isCurrentUser) QuestBaseDark else MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (user.isCurrentUser) FontWeight.Bold else FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = user.changeText,
                    fontSize = 11.sp,
                    color = QuestSuccess
                )
            }

            Text(
                text = "${user.scoreXp} XP",
                fontWeight = FontWeight.Bold,
                color = QuestGold,
                fontSize = 14.sp
            )
        }
    }
}
