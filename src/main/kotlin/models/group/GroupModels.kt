package models.group

import com.google.gson.annotations.SerializedName

data class OrganizationComponent(
    var _id: String? = null,
    var cloudinaryId: String = "",
    @SerializedName("tenantId")
    var tenantCode: String ="",
    var name: String = "",
    var url: String ="",
    var users: List<Users> = listOf(),
    var description: String =""
)

data class Users(
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val phone: String = "",
    val picture:String ="",
    val group: String = "",
    val type: String = "",
    var tenantCode: String = "",
    val isAdmin: Boolean = false
)