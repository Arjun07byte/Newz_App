package com.example.newzapp.database

import android.content.Context
import androidx.room.*
import com.example.newzapp.models.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    Converters::class
)

public abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDAO(): ArticleDAO
    companion object {
        /*
            we will be creating a singleton instance of our database
            so that it will only be updated by a single thread which uses
            it so that the concurrency and transaction status of the database
            is maintained correctly
        */
        @Volatile
        private var databaseInstance: ArticleDatabase? = null
        private val LOCK = Any()

        /*
            If the database instance is currently null we will be creating
            a new singleton database instance of our database
         */
        operator fun invoke(currContext: Context) = databaseInstance ?: synchronized((LOCK)) {
            databaseInstance ?: createDatabase(currContext).also {
                databaseInstance = it
            }
        }

        private fun createDatabase(currContext: Context) =
            Room.databaseBuilder(
                currContext.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}