package academy.bangkit.quiport.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MessageResponse (
    @field:SerializedName("message_id")
    val messageId: String,

    @field:SerializedName("welcome_message")
    val welcomeMessage: String
)