package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.UserRole
import com.example.ui.components.QuestTopBar
import com.example.ui.screens.*
import com.example.ui.theme.QuestLearnTheme
import com.example.viewmodel.QuestViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: QuestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsState()

            QuestLearnTheme(darkTheme = uiState.isDarkMode) {
                // If a mission is active, show the Challenge Play Screen (Gambar L2)
                if (uiState.activeMissionToPlay != null) {
                    ChallengePlayScreen(
                        mission = uiState.activeMissionToPlay!!,
                        currentQuestionIndex = uiState.currentQuestionIndex,
                        selectedOptionIndex = uiState.selectedOptionIndex,
                        isChecked = uiState.currentQuestionChecked,
                        isCorrect = uiState.isAnswerCorrect,
                        earnedXp = uiState.missionEarnedXp,
                        attempts = uiState.attemptsOnCurrentQuestion,
                        onSelectOption = { viewModel.selectOption(it) },
                        onCheckAnswer = { viewModel.checkAnswer() },
                        onRetryQuestion = { viewModel.retryQuestion() },
                        onNextQuestion = { viewModel.nextQuestionOrFinish() },
                        onExitMission = { viewModel.exitMission() }
                    )
                } else {
                    // Main scaffold with TopBar and Bottom Navigation
                    Scaffold(
                        topBar = {
                            QuestTopBar(
                                level = uiState.currentLevel,
                                levelTitle = uiState.levelTitle,
                                currentXp = uiState.currentXp,
                                nextLevelXp = uiState.nextLevelXp,
                                streakDays = uiState.streakDays,
                                isOffline = uiState.isOfflineMode,
                                onRoleToggle = { viewModel.toggleRole() },
                                currentRoleName = if (uiState.currentRole == UserRole.SISWA) "Siswa" else "Guru"
                            )
                        },
                        bottomBar = {
                            QuestBottomNavigationBar(
                                currentRole = uiState.currentRole,
                                selectedTab = uiState.selectedTab,
                                onTabSelected = { viewModel.selectTab(it) }
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.background
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            if (uiState.currentRole == UserRole.GURU) {
                                // Guru Mode: Dasbor Pantau Kelas & Atur Misi (Gambar L5)
                                TeacherDashboardScreen(
                                    cognitivePercent = uiState.cognitiveEngagement,
                                    emotionalPercent = uiState.emotionalEngagement,
                                    behavioralPercent = uiState.behavioralEngagement,
                                    studentsNeedingSupport = uiState.teacherStudentsNeedingSupport,
                                    selectedDifficulty = uiState.teacherSelectedDifficulty,
                                    individualLeaderboardEnabled = uiState.teacherIndividualLeaderboardEnabled,
                                    onDifficultyChanged = { viewModel.updateTeacherDifficulty(it) },
                                    onToggleLeaderboard = { viewModel.updateTeacherLeaderboardEnabled(it) },
                                    onSendCheer = { viewModel.sendTeacherCheerMessage(it) }
                                )
                            } else {
                                // Siswa Mode: 5 Tabs per Design Brief
                                when (uiState.selectedTab) {
                                    0 -> MissionMapScreen(
                                        missions = uiState.missions,
                                        onSelectMission = { viewModel.startMission(it) }
                                    )
                                    1 -> RewardsAndBadgesScreen(
                                        currentXp = uiState.currentXp,
                                        nextLevelXp = uiState.nextLevelXp,
                                        levelTitle = uiState.levelTitle,
                                        level = uiState.currentLevel,
                                        cognitive = uiState.cognitiveEngagement,
                                        emotional = uiState.emotionalEngagement,
                                        behavioral = uiState.behavioralEngagement,
                                        badges = uiState.badges,
                                        missions = uiState.missions,
                                        studentName = uiState.studentName
                                    )
                                    2 -> CommunityForumScreen(
                                        posts = uiState.communityPosts,
                                        onUpvote = { viewModel.upvoteCommunityPost(it) },
                                        onAddQuestion = { title, content, tags ->
                                            viewModel.addCommunityQuestion(title, content, tags)
                                        }
                                    )
                                    3 -> LeaderboardScreen(
                                        teamLeaderboard = uiState.teamLeaderboard,
                                        individualLeaderboard = uiState.individualLeaderboard,
                                        hideMyRanking = uiState.hideMyRanking,
                                        onToggleHideRanking = { viewModel.toggleHideMyRanking() },
                                        onStartTeamMission = {
                                            val teamMission = uiState.missions.find { it.isTeamMission } ?: uiState.missions.first()
                                            viewModel.startMission(teamMission)
                                        }
                                    )
                                    4 -> ProfileAndSettingsScreen(
                                        studentName = uiState.studentName,
                                        nickname = uiState.nickname,
                                        schoolName = uiState.schoolName,
                                        level = uiState.currentLevel,
                                        levelTitle = uiState.levelTitle,
                                        currentXp = uiState.currentXp,
                                        isDarkMode = uiState.isDarkMode,
                                        isOffline = uiState.isOfflineMode,
                                        onToggleDark = { viewModel.toggleDarkMode() },
                                        onToggleOffline = { viewModel.toggleOfflineMode() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestBottomNavigationBar(
    currentRole: UserRole,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        if (currentRole == UserRole.GURU) {
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                icon = { Icon(Icons.Default.Dashboard, contentDescription = "Pantau") },
                label = { Text("Pantau") },
                modifier = Modifier.testTag("nav_guru_pantau")
            )
            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                icon = { Icon(Icons.Default.Tune, contentDescription = "Atur Misi") },
                label = { Text("Atur Misi") },
                modifier = Modifier.testTag("nav_guru_atur")
            )
        } else {
            // Siswa Tabs (Bagian 3 Arsitektur Navigasi: Peta Misi, Lencana, Komunitas, Peringkat, Profil)
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                icon = { Icon(Icons.Default.Map, contentDescription = "Misi") },
                label = { Text("Misi") },
                modifier = Modifier.testTag("nav_tab_misi")
            )
            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                icon = { Icon(Icons.Default.MilitaryTech, contentDescription = "Lencana") },
                label = { Text("Lencana") },
                modifier = Modifier.testTag("nav_tab_lencana")
            )
            NavigationBarItem(
                selected = selectedTab == 2,
                onClick = { onTabSelected(2) },
                icon = { Icon(Icons.Default.Forum, contentDescription = "Komunitas") },
                label = { Text("Forum") },
                modifier = Modifier.testTag("nav_tab_forum")
            )
            NavigationBarItem(
                selected = selectedTab == 3,
                onClick = { onTabSelected(3) },
                icon = { Icon(Icons.Default.Leaderboard, contentDescription = "Peringkat") },
                label = { Text("Peringkat") },
                modifier = Modifier.testTag("nav_tab_peringkat")
            )
            NavigationBarItem(
                selected = selectedTab == 4,
                onClick = { onTabSelected(4) },
                icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
                label = { Text("Profil") },
                modifier = Modifier.testTag("nav_tab_profil")
            )
        }
    }
}
