package com.argel.weathertraker.data.dto

import kotlinx.serialization.SerialName


/**
 * Created by Argel Chan on 28/04/2025.
 * didier.chan@ingenierosmafur.com
 */

data class LoginRequest(
   @SerialName("email") val email: String,
   @SerialName("password") val password: String
)

data class UserResponse(
    val id: String,
    val name: String,
    val nickName: String,
    val familyName: String,
    val email: String,
    val picture: String) {
    companion object {
        fun empty() = UserResponse("", "", "", "", "", "")

    }
}