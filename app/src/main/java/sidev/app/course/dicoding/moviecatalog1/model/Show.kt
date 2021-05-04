package sidev.app.course.dicoding.moviecatalog1.model

import com.google.gson.annotations.SerializedName
import sidev.app.course.dicoding.moviecatalog1.util.Const
import sidev.app.course.dicoding.moviecatalog1.util.Util
import java.io.Serializable

/**
 * For both TV Show and Movie because they have same structure.
 */
data class Show(
    val id: String,
    @SerializedName("title", alternate = ["name"])
    val title: String,
    @SerializedName("poster_path")
    val img: String,
    @SerializedName("release_date", alternate = ["first_air_date"])
    val release: String,
    @SerializedName("vote_average")
    val rating: Double,
): Serializable {
    fun imgUrl_300x450(): String = Const.getImgUrl_300x450(img)
    fun getFormattedDate(): String = Util.formatDate(release)
}
