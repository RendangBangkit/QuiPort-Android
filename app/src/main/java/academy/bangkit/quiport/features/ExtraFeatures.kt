package academy.bangkit.quiport.features

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class ExtraFeatures(private val context: Context) {
    companion object {
        const val MODULE_CLASS_NAME = "academy.bangkit.quiport.extra.ExtraActivity"
    }

    fun installModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(context)
        val moduleExtra = "extra"

        if (splitInstallManager.installedModules.contains(moduleExtra)) {
            Toast.makeText(context, "Module has been installed.", Toast.LENGTH_SHORT).show()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleExtra)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(context, "Success installing module.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error installing module.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun getIntent() : Intent = Intent(context, Class.forName(MODULE_CLASS_NAME))
}