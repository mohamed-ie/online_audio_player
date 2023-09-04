package com.example.onlineaudioplayer.feature.data.local

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class AppDatabaseCallback : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val contentValues = ContentValues().apply {
            put("name", "إذاعة القرآن الكريم من القاهرة")
            put(
                "description",
                "إذاعة القرآن الكريم من القاهرة هي إذاعة إسلامية من مصر، تبث على الهواء من القاهرة. تهدف الإذاعة إلى تحفيز الشعب المصري للاستماع إلى القرآن الكريم والتعرف على أحكامه وأحاديثه. يبث المحطة بشكل مستمر على الهواء، ويتضمن البرامج الإذاعية المختلفة التي تتحدث عن الإسلام والشخصيات الإسلامية المشهورة."
            )
            put("url", "http://n12.radiojar.com/8s5u5tpdtwzuv")
            put("is_favorite", true)
        }
        db.insert("channel", SQLiteDatabase.CONFLICT_REPLACE, contentValues)
    }
}