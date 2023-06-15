import com.celvine.deb.esail.bby.data.model.Alergen

data class User(
    val id: String,
    val email: String,
    val role: String,
    val alergens: List<Alergen>,
    val name: String,
    val avatar: String,
    val createdAt: String,
    val updatedAt: String
)