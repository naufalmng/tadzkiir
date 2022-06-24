package org.destinyardiente.tadzkiir.core.data.source.remote.request


import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("password_confirmation")
    var passwordConfirmation: String?
)