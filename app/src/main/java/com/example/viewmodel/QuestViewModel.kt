package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.QuestSampleData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class QuestUiState(
    // Auth & Role
    val isLoggedIn: Boolean = true,
    val currentRole: UserRole = UserRole.SISWA,
    val studentName: String = "Anya Firna",
    val nickname: String = "Anya",
    val schoolName: String = "MAN 2 Lamongan",
    val userClassCode: String = "XI-IPA-2",

    // Gamification Stats
    val currentLevel: Int = 4,
    val levelTitle: String = "Penjelajah Ekosistem",
    val currentXp: Int = 1250,
    val nextLevelXp: Int = 1500,
    val streakDays: Int = 3,
    val safeStreakAvailable: Boolean = true,
    val isDataSaverActive: Boolean = false,
    val isOfflineMode: Boolean = false,
    val isDarkMode: Boolean = true,
    val hideMyRanking: Boolean = false,

    // Three Engagement Dimensions (LKTI OISEMA)
    val cognitiveEngagement: Int = 74,
    val emotionalEngagement: Int = 68,
    val behavioralEngagement: Int = 81,

    // Active screen navigation
    val selectedTab: Int = 0, // 0: Peta/Beranda, 1: Lencana/Hadiah, 2: Komunitas, 3: Peringkat, 4: Profil/Analisis
    val activeMissionToPlay: QuestMission? = null,
    val currentQuestionIndex: Int = 0,
    val currentQuestionChecked: Boolean = false,
    val selectedOptionIndex: Int? = null,
    val isAnswerCorrect: Boolean = false,
    val attemptsOnCurrentQuestion: Int = 0,
    val missionFinishedScore: Int? = null,
    val missionEarnedXp: Int = 0,
    val showMissionResultModal: Boolean = false,

    // Missions and badges list
    val missions: List<QuestMission> = emptyList(),
    val badges: List<BadgeItem> = emptyList(),
    val teamMembers: List<TeamMember> = emptyList(),
    val teamLeaderboard: List<TeamLeaderboardEntry> = emptyList(),
    val individualLeaderboard: List<LeaderboardEntry> = emptyList(),
    val communityPosts: List<CommunityPost> = emptyList(),
    val teacherStudentsNeedingSupport: List<StudentSupportStatus> = emptyList(),

    // Teacher controls
    val teacherSelectedDifficulty: String = "Sedang",
    val teacherIndividualLeaderboardEnabled: Boolean = true,
    val teacherClassTargetPercent: Int = 75,
    val teacherShowNotificationToast: String? = null
)

class QuestViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuestUiState())
    val uiState: StateFlow<QuestUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        _uiState.value = _uiState.value.copy(
            missions = QuestSampleData.getInitialMissions(),
            badges = QuestSampleData.getInitialBadges(),
            teamMembers = QuestSampleData.getTeamMembers(),
            teamLeaderboard = QuestSampleData.getTeamLeaderboard(),
            individualLeaderboard = QuestSampleData.getIndividualLeaderboard(),
            communityPosts = QuestSampleData.getCommunityPosts(),
            teacherStudentsNeedingSupport = QuestSampleData.getTeacherSupportStudents()
        )
    }

    fun selectTab(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = index)
    }

    fun toggleRole() {
        val newRole = if (_uiState.value.currentRole == UserRole.SISWA) UserRole.GURU else UserRole.SISWA
        _uiState.value = _uiState.value.copy(
            currentRole = newRole,
            selectedTab = 0
        )
    }

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(isDarkMode = !_uiState.value.isDarkMode)
    }

    fun toggleOfflineMode() {
        _uiState.value = _uiState.value.copy(isOfflineMode = !_uiState.value.isOfflineMode)
    }

    fun toggleHideMyRanking() {
        _uiState.value = _uiState.value.copy(hideMyRanking = !_uiState.value.hideMyRanking)
    }

    fun startMission(mission: QuestMission) {
        _uiState.value = _uiState.value.copy(
            activeMissionToPlay = mission,
            currentQuestionIndex = 0,
            currentQuestionChecked = false,
            selectedOptionIndex = null,
            isAnswerCorrect = false,
            attemptsOnCurrentQuestion = 0,
            missionFinishedScore = null,
            missionEarnedXp = 0,
            showMissionResultModal = false
        )
    }

    fun selectOption(index: Int) {
        if (!_uiState.value.currentQuestionChecked) {
            _uiState.value = _uiState.value.copy(selectedOptionIndex = index)
        }
    }

    fun checkAnswer() {
        val mission = _uiState.value.activeMissionToPlay ?: return
        val currentQ = mission.questions.getOrNull(_uiState.value.currentQuestionIndex) ?: return
        val selected = _uiState.value.selectedOptionIndex ?: return

        val isCorrect = selected == currentQ.correctIndex
        val attempts = _uiState.value.attemptsOnCurrentQuestion + 1

        val earnedDelta = if (isCorrect) 10 else 5

        _uiState.value = _uiState.value.copy(
            currentQuestionChecked = true,
            isAnswerCorrect = isCorrect,
            attemptsOnCurrentQuestion = attempts,
            missionEarnedXp = _uiState.value.missionEarnedXp + earnedDelta
        )
    }

    fun retryQuestion() {
        _uiState.value = _uiState.value.copy(
            currentQuestionChecked = false,
            selectedOptionIndex = null
        )
    }

    fun nextQuestionOrFinish() {
        val mission = _uiState.value.activeMissionToPlay ?: return
        val nextIdx = _uiState.value.currentQuestionIndex + 1

        if (nextIdx < mission.questions.size) {
            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = nextIdx,
                currentQuestionChecked = false,
                selectedOptionIndex = null,
                isAnswerCorrect = false,
                attemptsOnCurrentQuestion = 0
            )
        } else {
            // Finish mission
            val finalScore = 95
            val gainedXp = _uiState.value.missionEarnedXp + 50 // bonus ketuntasan
            val newTotalXp = _uiState.value.currentXp + gainedXp

            // Update missions state
            val updatedMissions = _uiState.value.missions.map {
                if (it.id == mission.id) {
                    it.copy(
                        status = QuestStatus.COMPLETED,
                        bestScore = finalScore,
                        stars = 3,
                        attemptsCount = it.attemptsCount + 1
                    )
                } else it
            }

            _uiState.value = _uiState.value.copy(
                missions = updatedMissions,
                currentXp = newTotalXp,
                missionFinishedScore = finalScore,
                showMissionResultModal = true,
                cognitiveEngagement = minOf(95, _uiState.value.cognitiveEngagement + 2),
                emotionalEngagement = minOf(95, _uiState.value.emotionalEngagement + 3),
                behavioralEngagement = minOf(95, _uiState.value.behavioralEngagement + 1)
            )
        }
    }

    fun exitMission() {
        _uiState.value = _uiState.value.copy(
            activeMissionToPlay = null,
            showMissionResultModal = false
        )
    }

    fun addCommunityQuestion(title: String, content: String, tags: List<String>) {
        val newPost = CommunityPost(
            id = "p_${System.currentTimeMillis()}",
            authorName = _uiState.value.studentName,
            roleTag = "Siswa XI",
            timeAgo = "Baru saja",
            title = title,
            content = content,
            tags = tags,
            upvotes = 1,
            replyCount = 0,
            isAnswered = false
        )
        _uiState.value = _uiState.value.copy(
            communityPosts = listOf(newPost) + _uiState.value.communityPosts
        )
    }

    fun upvoteCommunityPost(postId: String) {
        val updated = _uiState.value.communityPosts.map {
            if (it.id == postId) it.copy(upvotes = it.upvotes + 1) else it
        }
        _uiState.value = _uiState.value.copy(communityPosts = updated)
    }

    fun updateTeacherDifficulty(difficulty: String) {
        _uiState.value = _uiState.value.copy(teacherSelectedDifficulty = difficulty)
    }

    fun updateTeacherLeaderboardEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(teacherIndividualLeaderboardEnabled = enabled)
    }

    fun sendTeacherCheerMessage(studentName: String) {
        _uiState.value = _uiState.value.copy(
            teacherShowNotificationToast = "Pesan semangat otomatis terkirim kepada $studentName: 'Ayo semangat, kamu pasti bisa tuntas!'"
        )
    }

    fun clearNotificationToast() {
        _uiState.value = _uiState.value.copy(teacherShowNotificationToast = null)
    }
}
