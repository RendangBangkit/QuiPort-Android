package id.practice.mynews.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListMessageResponse(

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("result")
    val result: List<MessageResponse>,

)