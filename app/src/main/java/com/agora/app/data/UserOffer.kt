package com.agora.app.data

data class UserOffer(
    val id: Int,
    val offers: List<String>,
    val needs: List<String>
)