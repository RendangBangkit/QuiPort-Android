package academy.bangkit.quiport.core.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class Tools {
    companion object {
        private var vibrantLightColorList = arrayOf(
            ColorDrawable(Color.parseColor("#ffeead")),
            ColorDrawable(Color.parseColor("#93cfb3")),
            ColorDrawable(Color.parseColor("#fd7a7a")),
            ColorDrawable(Color.parseColor("#faca5f")),
            ColorDrawable(Color.parseColor("#1ba798")),
            ColorDrawable(Color.parseColor("#6aa9ae")),
            ColorDrawable(Color.parseColor("#ffbf27")),
            ColorDrawable(Color.parseColor("#d93947"))
        )

        val randomDrawableColor: ColorDrawable
            get() {
                val idx = Random().nextInt(vibrantLightColorList.size)
                return vibrantLightColorList[idx]
            }

        fun timestampToHumanize(timestamp: Long): String? {
            return PrettyTime().format(Date(timestamp + 1000*60*10))
        }

        fun generateTitle(categories: List<String>, address: String): String {
            return "${categories.joinToString(", ")} | $address"
        }

        fun generateMapsLink(latitude: Double, longitude: Double): String {
            return "https://www.google.com/maps/place/$latitude,$longitude"
        }
    }
}