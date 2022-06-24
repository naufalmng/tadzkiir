package org.destinyardiente.tadzkiir.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("name")
    var name: String?,
    @SerializedName("token")
    var token: String?
)