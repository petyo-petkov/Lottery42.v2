import com.example.pruebas.data.network.api.apiModels.extraInfo.extraInfoSorteos.Data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ExtraInfoSorteos(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("success")
    val success: Boolean,
    @SerialName("timestamp")
    val timestamp: String

)
