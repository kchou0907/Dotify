package kchou97.dotify.model

data class User (
    val username: String,
    val firstName: String,
    val lastName: String,
    val hasNose: Boolean,
    val platform: Float,
    val profilePicURL: String,
)