package models.auth

data class TokenComponent(val token: String = "")

data class PhoneComponent(val phoneNumber: String = "")

data class TokenResponse(val token: TokenComponent? = null, val error: TokenError? = null)

data class TokenError(val code: Int = 0, val error: String = "")