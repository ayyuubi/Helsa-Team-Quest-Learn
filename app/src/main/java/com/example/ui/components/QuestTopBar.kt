package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun QuestTopBar(
    level: Int,
    levelTitle: String,
    currentXp: Int,
    nextLevelXp: Int,
    streakDays: Int,
    isOffline: Boolean,
    onRoleToggle: () -> Unit,
    currentRoleName: String
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Avatar + Level pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(QuestPrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "A",
                            fontWeight = FontWeight.Bold,
                            color = QuestPrimary,
                            fontSize = 18.sp
                        )
                        // Mini level badge
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(QuestGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$level",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = QuestBaseDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = levelTitle,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (isOffline) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = QuestOrangeContainer,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "LURING",
                                        color = QuestOrangeText,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        // XP Progress mini bar
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val progress = (currentXp.toFloat() / nextLevelXp.toFloat()).coerceIn(0f, 1f)
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .width(90.dp)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = QuestGold,
                                trackColor = QuestOutlineVariant
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "$currentXp / $nextLevelXp",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Streak & Stars Counter + Role switcher chip
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = QuestGoldContainer,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Streak",
                                tint = QuestGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "$streakDays Hari",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = QuestGoldText
                            )
                        }
                    }

                    // Role switch button for testing per Design Brief (Siswa / Guru)
                    FilterChip(
                        selected = currentRoleName == "Guru",
                        onClick = onRoleToggle,
                        label = {
                            Text(
                                text = currentRoleName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = QuestTealContainer,
                            selectedLabelColor = QuestTealText
                        ),
                        modifier = Modifier.testTag("role_switcher_chip")
                    )
                }
            }
        }
    }
}
