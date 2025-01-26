package models.group

import com.google.gson.annotations.SerializedName
import ui.NavKeys.EMPTY

data class AuthResponseComponent(
    val tenantLang: String,
    val org: OrganizationComponent,
    val tenantId: String,
    val regex: String,
    val format: String,
    val key: String,
    val domain: String,
    val levels: List<String>
)

data class OrganizationComponent(
    var _id: String? = null,
    var cloudinaryId: String = EMPTY,
    @SerializedName("tenantId")
    var tenantCode: String = EMPTY,
    var name: String = EMPTY,
    var url: String = EMPTY,
    var users: List<Users> = listOf(),
    var description: String = EMPTY
)

data class Users(
    val firstName: String = EMPTY,
    val lastName: String = EMPTY,
    val middleName: String = EMPTY,
    val phone: String = EMPTY,
    val picture:String = EMPTY,
    val group: String = EMPTY,
    val type: String = EMPTY,
    var tenantCode: String = EMPTY,
    val isAdmin: Boolean = false
)