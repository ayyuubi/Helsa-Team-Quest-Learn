package com.example.data

import com.example.model.*

object QuestSampleData {

    fun getInitialMissions(): List<QuestMission> {
        val q1 = ChallengeQuestion(
            id = "q1",
            scenarioStory = "Sawah Desa Sukorejo diserang wereng coklat. Pak Karim menyemprot pestisida berkali-kali tanpa jeda.",
            questionText = "Apa dampak jangka panjang tindakan Pak Karim terhadap keseimbangan ekosistem sawah?",
            options = listOf(
                "Populasi padi langsung melonjak pesat dan panen stabil",
                "Wereng habis tuntas dan tidak akan pernah kembali lagi",
                "Predator alami ikut mati sehingga hama wereng berisiko meledak lagi",
                "Siklus rantai makanan sawah menjadi lebih kebal bahan kimia"
            ),
            correctIndex = 2,
            explanationText = "Pestisida berlebih membunuh predator alami wereng (seperti laba-laba serigala dan kepik). Tanpa pengendali alami, resistensi hama meningkat dan memicu ledakan populasi sekunder.",
            conceptKey = "Dinamika Populasi & Predator Alami"
        )

        val q2 = ChallengeQuestion(
            id = "q2",
            scenarioStory = "Petani di petak B menanam tanaman refugia (bunga matahari dan kenikir) di pematang sawah.",
            questionText = "Fungsi ekologis utama penanaman refugia dalam pengelolaan hama terpadu adalah...",
            options = listOf(
                "Menyediakan nektar dan habitat bagi musuh alami hama",
                "Mengusir seluruh jenis serangga pemakan daun",
                "Mempercepat proses fotosintesis batang padi",
                "Menyerap kelebihan pupuk urea dari tanah"
            ),
            correctIndex = 0,
            explanationText = "Tanaman refugia menyediakan mikrohabitat dan sumber nektar alternatif bagi parasitoid dan predator hama sawah, menjaga populasi musuh alami tetap lestari.",
            conceptKey = "Pengendalian Hama Terpadu"
        )

        val q3 = ChallengeQuestion(
            id = "q3",
            scenarioStory = "Ditemukan rantai makanan: Padi -> Belalang -> Katak -> Ular -> Elang di petak sawah Sukorejo.",
            questionText = "Jika populasi katak menurun drastis karena diburu manusia, dampak langsung yang terjadi adalah...",
            options = listOf(
                "Populasi belalang meningkat pesat dan merusak tanaman padi",
                "Populasi elang langsung melonjak tajam",
                "Tanaman padi tumbuh lebih rimbun dan subur",
                "Populasi ular menjadi meningkat dua kali lipat"
            ),
            correctIndex = 0,
            explanationText = "Katak berperan sebagai konsumen sekunder pemangsa belalang. Hilangnya katak menyebabkan populasi belalang melonjak tak terkendali dan merusak produsen (padi).",
            conceptKey = "Jaring-Jaring Makanan & Trofik"
        )

        return listOf(
            QuestMission(
                id = "m1",
                number = 1,
                title = "Sawah Sukorejo",
                chapterTitle = "Bab 1 · Ekosistem Sawah",
                scenarioIntro = "Amati kondisi ekosistem sawah Sukorejo dan identifikasi komponen biotik serta abiotik yang saling berinteraksi.",
                learningGoal = "Mengidentifikasi komponen ekosistem dan aliran energi alami.",
                estimatedMinutes = 10,
                difficulty = QuestDifficulty.PEMULA,
                isTeamMission = false,
                status = QuestStatus.COMPLETED,
                bestScore = 100,
                stars = 3,
                questions = listOf(q1, q2)
            ),
            QuestMission(
                id = "m2",
                number = 2,
                title = "Rantai Makanan",
                chapterTitle = "Bab 1 · Ekosistem Sawah",
                scenarioIntro = "Telusuri jalur rantai makanan di sawah, perhatikan peran produsen, konsumen, hingga dekomposer tanah.",
                learningGoal = "Menganalisis tingkatan trofik rantai makanan.",
                estimatedMinutes = 12,
                difficulty = QuestDifficulty.PEMULA,
                isTeamMission = false,
                status = QuestStatus.COMPLETED,
                bestScore = 90,
                stars = 2,
                questions = listOf(q3, q1)
            ),
            QuestMission(
                id = "m3",
                number = 3,
                title = "Wereng Menyerang",
                chapterTitle = "Bab 1 · Ekosistem Sawah",
                scenarioIntro = "Hama wereng mulai menyebar ke petak utama. Putuskan strategi ekologis yang tepat sebelum terlambat!",
                learningGoal = "Mengevaluasi interaksi trofik dan dampak intervensi manusia terhadap dinamika hama.",
                estimatedMinutes = 15,
                difficulty = QuestDifficulty.MENENGAH,
                isTeamMission = false,
                status = QuestStatus.AVAILABLE,
                bestScore = 0,
                stars = 0,
                questions = listOf(q1, q2, q3)
            ),
            QuestMission(
                id = "m4",
                number = 4,
                title = "Misi Tim: Pulihkan Sawah",
                chapterTitle = "Bab 1 · Ekosistem Sawah",
                scenarioIntro = "Ayo selamatkan sawah Sukorejo bersama tim! Kolaborasikan peran Pemimpin, Penjawab, Pemeriksa, dan Pencatat.",
                learningGoal = "Mendesain rencana perbaikan ekosistem terpadu secara kolaboratif.",
                estimatedMinutes = 20,
                difficulty = QuestDifficulty.MENENGAH,
                isTeamMission = true,
                status = QuestStatus.IN_PROGRESS,
                bestScore = 80,
                stars = 2,
                questions = listOf(q2, q3, q1)
            ),
            QuestMission(
                id = "m5",
                number = 5,
                title = "Jaring Kehidupan",
                chapterTitle = "Bab 1 · Ekosistem Sawah",
                scenarioIntro = "Simulasi jaring-jaring kehidupan rumit saat anomali iklim dan perubahan tata guna lahan terjadi.",
                learningGoal = "Menganalisis daya lenting ekosistem kompleks.",
                estimatedMinutes = 20,
                difficulty = QuestDifficulty.LANJUT,
                isTeamMission = false,
                status = QuestStatus.LOCKED,
                bestScore = 0,
                stars = 0,
                questions = listOf(q3, q2)
            )
        )
    }

    fun getInitialBadges(): List<BadgeItem> {
        return listOf(
            BadgeItem("b1", "Langkah Pertama", "Kompetensi", "Menyelesaikan Misi Latihan perdana", true, "3 Okt 2026", "🌱"),
            BadgeItem("b2", "Tuntas Misi 1", "Kompetensi", "Menuntaskan misi pertama dengan skor sempurna", true, "4 Okt 2026", "⭐"),
            BadgeItem("b3", "Pantang Menyerah", "Ketekunan", "Mengulang misi hingga skor naik (Penghargaan ketekunan)", true, "5 Okt 2026", "💖"),
            BadgeItem("b4", "Tim Kompak", "Kolaborasi", "Semua anggota tim aktif berkontribusi (+20 bonus)", true, "6 Okt 2026", "🤝"),
            BadgeItem("b5", "Mentor Teman", "Kolaborasi", "Membantu menjelaskan konsep sulit di ruang tim", false, null, "🛡️"),
            BadgeItem("b6", "Misi Sempurna", "Kompetensi", "Meraih 3 bintang pada 3 misi berturut-turut", false, null, "👑"),
            BadgeItem("b7", "Penjelajah Jalur", "Otonomi", "Mencoba jalur misi pilihan di luar alur utama", false, null, "🧭"),
            BadgeItem("b8", "Jujur Berefleksi", "Kognitif", "Mengisi angket refleksi belajar tepat waktu", true, "7 Okt 2026", "📝")
        )
    }

    fun getTeamMembers(): List<TeamMember> {
        return listOf(
            TeamMember("tm1", "Anya (Kamu)", "Pemimpin Misi", 35, "A"),
            TeamMember("tm2", "Dwi Surya", "Penjawab", 25, "D"),
            TeamMember("tm3", "Callysta", "Pemeriksa", 25, "C"),
            TeamMember("tm4", "Rafi", "Pencatat", 15, "R")
        )
    }

    fun getTeamLeaderboard(): List<TeamLeaderboardEntry> {
        return listOf(
            TeamLeaderboardEntry(1, "Tim Elang", 3420, false, "🦅"),
            TeamLeaderboardEntry(2, "Tim Garuda", 3310, false, "🦉"),
            TeamLeaderboardEntry(3, "Tim Rajawali", 3080, false, "🦜"),
            TeamLeaderboardEntry(4, "Tim Bengawan (Kamu)", 2960, true, "🌊"),
            TeamLeaderboardEntry(5, "Tim Kutilang", 2740, false, "🐦")
        )
    }

    fun getIndividualLeaderboard(): List<LeaderboardEntry> {
        return listOf(
            LeaderboardEntry(1, "Rafi Cendekia", 1450, false, "+45 XP"),
            LeaderboardEntry(2, "Anya (Kamu)", 1250, true, "+75 XP"),
            LeaderboardEntry(3, "Dwi Surya", 1180, false, "+20 XP"),
            LeaderboardEntry(4, "Callysta", 1120, false, "+30 XP"),
            LeaderboardEntry(5, "Fikri H.", 980, false, "+15 XP"),
            LeaderboardEntry(6, "Sinta M.", 940, false, "+10 XP")
        )
    }

    fun getCommunityPosts(): List<CommunityPost> {
        return listOf(
            CommunityPost(
                id = "p1",
                authorName = "Anya Firna",
                roleTag = "Siswa XI",
                timeAgo = "2 jam lalu",
                title = "Mengapa tanaman refugia bisa membasmi wereng secara biologis?",
                content = "Apakah semua bunga kuning efektif atau hanya kenikir dan bunga matahari saja yang disukai predator musuh alami wereng?",
                tags = listOf("Ekosistem", "Refugia", "Wereng"),
                upvotes = 12,
                replyCount = 5,
                isAnswered = true
            ),
            CommunityPost(
                id = "p2",
                authorName = "Bu Laila",
                roleTag = "Guru Fasilitator",
                timeAgo = "Kemarin",
                title = "Tips Menyelesaikan Misi 4: Kerja Tim Terpadu",
                content = "Ingat anak-anak, pembagian peran di Misi Tim sangat penting. Pemimpin menentukan urutan penyelidikan, Pemeriksa memastikan argumentasi logis sebelum klik submit!",
                tags = listOf("Tips Belajar", "Kolaborasi", "Misi Tim"),
                upvotes = 28,
                replyCount = 8,
                isAnswered = true
            ),
            CommunityPost(
                id = "p3",
                authorName = "Rafi Firmansyah",
                roleTag = "Siswa XI",
                timeAgo = "1 hari lalu",
                title = "Korelasi piramida biomassa dengan jaring-jaring makanan sawah",
                content = "Kalau katak mati massal, apakah biomassa produsen padi akan drop signifikan dalam 2 minggu?",
                tags = listOf("Trofik", "Biomassa"),
                upvotes = 8,
                replyCount = 3,
                isAnswered = false
            )
        )
    }

    fun getTeacherSupportStudents(): List<StudentSupportStatus> {
        return listOf(
            StudentSupportStatus("Rina Agustina", "S04", "Mengulang Misi 3 sebanyak 4 kali tanpa naik ambang", "Rendah", 58, "1 hari lalu"),
            StudentSupportStatus("Dimas Pratama", "S11", "Belum memulai Misi 2 sejak 5 hari terakhir", "Rendah", 52, "5 hari lalu"),
            StudentSupportStatus("Salsa Nabila", "S19", "Aktivitas menurun drastis 2 minggu terakhir", "Sedang", 64, "3 hari lalu"),
            StudentSupportStatus("Fikri Hidayat", "S22", "Selalu di peringkat bawah tim dan belum mengambil peran", "Rendah", 60, "Kemarin")
        )
    }
}
