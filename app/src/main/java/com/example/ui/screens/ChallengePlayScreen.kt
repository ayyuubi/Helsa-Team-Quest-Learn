package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.QuestMission
import com.example.ui.theme.*

@Composable
fun ChallengePlayScreen(
    mission: QuestMission,
    currentQuestionIndex: Int,
    selectedOptionIndex: Int?,
    isChecked: Boolean,
    isCorrect: Boolean,
    earnedXp: Int,
    attempts: Int,
    onSelectOption: (Int) -> Unit,
    onCheckAnswer: () -> Unit,
    onRetryQuestion: () -> Unit,
    onNextQuestion: () -> Unit,
    onExitMission: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentQuestion = mission.questions.getOrNull(currentQuestionIndex) ?: return
    val totalQuestions = mission.questions.size

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onExitMission,
                        modifier = Modifier.testTag("exit_mission_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Keluar Misi",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Beruntun pill (Mockup L2: "Beruntun 3")
                    Surface(
                        color = QuestGoldContainer,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "★ Beruntun 3",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = QuestGoldText
                            )
                        }
                    }

                    // Lives / Hearts or Safe Torch
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(3) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Nyawa",
                                tint = Color(0xFFF472B6),
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Stepped checkpoints progress indicator (Gambar L2)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(totalQuestions) { index ->
                        val isFinished = index < currentQuestionIndex || (index == currentQuestionIndex && isChecked && isCorrect)
                        val isCurrent = index == currentQuestionIndex
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    when {
                                        isFinished -> QuestSuccess
                                        isCurrent -> QuestPrimary
                                        else -> QuestOutlineVariant
                                    }
                                )
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Skenario Card with Image (Gambar L2)
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, QuestOutlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    // Scenario Illustration (img_hama_wereng)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .background(QuestSurfaceSunken)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hama_wereng_1791424347965),
                            contentDescription = "Hama Wereng",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Surface(
                            color = QuestPrimaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Skenario Kasus",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = QuestBlueTint,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentQuestion.scenarioStory,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Question Text
            Text(
                text = currentQuestion.questionText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Options A-D with Full Touch Target (Gambar L2)
            currentQuestion.options.forEachIndexed { optIndex, optionText ->
                val optLabel = when (optIndex) {
                    0 -> "A"
                    1 -> "B"
                    2 -> "C"
                    else -> "D"
                }

                val isSelected = selectedOptionIndex == optIndex
                val isAnswerTarget = optIndex == currentQuestion.correctIndex

                // Feedback coloring per design spec
                val (borderColor, containerColor) = when {
                    isChecked && isAnswerTarget -> Pair(QuestSuccess, QuestSuccessContainer)
                    isChecked && isSelected && !isCorrect -> Pair(QuestOrange, QuestOrangeContainer)
                    isSelected -> Pair(QuestPrimary, QuestPrimaryContainer)
                    else -> Pair(QuestOutlineVariant, MaterialTheme.colorScheme.surface)
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = containerColor),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable(enabled = !isChecked) {
                            onSelectOption(optIndex)
                        }
                        .testTag("option_card_$optIndex")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isSelected || (isChecked && isAnswerTarget)) borderColor else QuestSurfaceSunken
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = optLabel,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected || (isChecked && isAnswerTarget)) QuestBaseDark else MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action / Check Button before feedback
            if (!isChecked) {
                Button(
                    onClick = onCheckAnswer,
                    enabled = selectedOptionIndex != null,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = QuestPrimary,
                        contentColor = QuestBaseDark
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("periksa_jawaban_button")
                ) {
                    Text(
                        text = "PERIKSA JAWABAN",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }

            // Real-Time Feedback Card (Gambar L2: "Hampir tepat! +10 poin usaha" / "Tepat! +10 XP")
            AnimatedVisibility(visible = isChecked) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCorrect) QuestSuccessContainer else QuestOrangeContainer
                        ),
                        shape = RoundedCornerShape(18.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isCorrect) QuestSuccess else QuestOrange
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = if (isCorrect) QuestSuccessText else QuestOrangeText,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isCorrect) "Tepat Sekali!" else "Hampir tepat!",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 16.sp,
                                        color = if (isCorrect) QuestSuccessText else QuestOrangeText
                                    )
                                }

                                Surface(
                                    color = QuestGoldContainer,
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = if (isCorrect) "+10 XP" else "+5 poin usaha",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = QuestGoldText,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = currentQuestion.explanationText,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Buttons: COBA LAGI (if wrong) and LANJUT
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (!isCorrect) {
                            OutlinedButton(
                                onClick = onRetryQuestion,
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .testTag("coba_lagi_button")
                            ) {
                                Text(
                                    text = "COBA LAGI",
                                    fontWeight = FontWeight.Bold,
                                    color = QuestOrange
                                )
                            }
                        }

                        Button(
                            onClick = onNextQuestion,
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCorrect) QuestSuccess else QuestPrimary,
                                contentColor = QuestBaseDark
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("lanjut_pertanyaan_button")
                        ) {
                            Text(
                                text = if (currentQuestionIndex + 1 < totalQuestions) "LANJUT" else "SELESAIKAN",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
