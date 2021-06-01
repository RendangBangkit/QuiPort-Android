package academy.bangkit.quiport.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "messageId")
    var messageId: String,

    @ColumnInfo(name = "welcomeMessage")
    val welcomeMessage: String
)