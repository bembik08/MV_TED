package com.movieapp.mv_ted.data.datasource.localstore.commentdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CommentEntity::class], version = 1, exportSchema = false)
abstract class CommentDataBase : RoomDatabase() {
    abstract fun commentDao() : CommentDAO

    companion object {
       private const val DB_NAME = "add_database.db"
        fun create(context: Context) = Room.databaseBuilder(
            context,
            CommentDataBase :: class.java,
            DB_NAME
        ).build()
    }
}