package com.example.model

enum class UserRole {
    SISWA,
    GURU
}

enum class QuestStatus {
    LOCKED,      // Belum dibuka (abu, ikon gembok)
    AVAILABLE,   // Tersedia (biru bernyala)
    IN_PROGRESS, // Berjalan (emas, cincin progres)
    COMPLETED,   // Selesai (hijau, centang, bintang)
    NEEDS_RETRY  // Perlu diulang (jingga, coba lagi)
}

enum class QuestDifficulty(val label: String, val torches: Int, val weight: Float) {
    PEMULA("Pemula", 1, 1.0f),
    MENENGAH("Menengah", 2, 1.5f),
    LANJUT("Lanjut", 3, 2.0f)
}

data class ChallengeQuestion(
    val id: String,
    val questionText: String,
    val scenarioStory: String = "",
    val options: List<String>,
    val correctIndex: Int,
    val explanationText: String,
    val conceptKey: String
)

data class QuestMission(
    val id: String,
    val number: Int,
    val title: String,
    val chapterTitle: String,
    val scenarioIntro: String,
    val learningGoal: String,
    val estimatedMinutes: Int = 12,
    val difficulty: QuestDifficulty = QuestDifficulty.PEMULA,
    val isTeamMission: Boolean = false,
    val isOptional: Boolean = false,
    val questions: List<ChallengeQuestion> = emptyList(),
    val isDownloadedForOffline: Boolean = true,
    var status: QuestStatus = QuestStatus.AVAILABLE,
    var bestScore: Int = 0,
    var stars: Int = 0,
    var attemptsCount: Int = 0
)

data class BadgeItem(
    val id: String,
    val name: String,
    val category: String, // Tuntas, Ketekunan, Kolaborasi, Eksplor
    val description: String,
    val isUnlocked: Boolean,
    val unlockedDate: String? = null,
    val iconEmoji: String = "⭐"
)

data class TeamMember(
    val id: String,
    val name: String,
    val roleTitle: String, // Pemimpin Misi, Penjawab, Pemeriksa, Pencatat
    val contributionPercent: Int,
    val avatarInitial: String
)

data class LeaderboardEntry(
    val rank: Int,
    val name: String,
    val scoreXp: Int,
    val isCurrentUser: Boolean = false,
    val changeText: String = "+0 XP"
)

data class TeamLeaderboardEntry(
    val rank: Int,
    val teamName: String,
    val scoreXp: Int,
    val isCurrentTeam: Boolean = false,
    val emblem: String = "🦅"
)

data class CommunityPost(
    val id: String,
    val authorName: String,
    val roleTag: String,
    val timeAgo: String,
    val title: String,
    val content: String,
    val tags: List<String>,
    val upvotes: Int,
    val replyCount: Int,
    val isAnswered: Boolean = false
)

data class StudentSupportStatus(
    val studentName: String,
    val studentCode: String,
    val reasonText: String,
    val nGainCategory: String, // Tinggi, Sedang, Rendah
    val averageScore: Int,
    val lastActive: String
)
