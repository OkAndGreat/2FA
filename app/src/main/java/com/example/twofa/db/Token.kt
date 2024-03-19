package com.example.twofa.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.crypto.SecretKey

@Entity(tableName = "token_table")
data class Token(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "platform_name") val platformName: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "secret") val secretKey: String
)

val emptyToken = Token(0, "github", "wangzhongtai", "")