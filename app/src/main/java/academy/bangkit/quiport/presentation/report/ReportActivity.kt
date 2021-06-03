package academy.bangkit.quiport.presentation.report

import academy.bangkit.quiport.R
import academy.bangkit.quiport.core.adapter.ReportAdapter
import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.utils.Tools
import academy.bangkit.quiport.databinding.ActivityReportBinding
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private val viewModel: ReportViewModel by viewModel()
    private var doubleBackToExitPressedOnce = false

    companion object {
        private const val DOUBLE_TAP_DELAY = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reportAdapter = ReportAdapter()
        reportAdapter.onItemClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Tools.generateMapsLink(
                it.addressComponents.latitude,
                it.addressComponents.longitude
            )))

            startActivity(browserIntent)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
        }

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = reportAdapter
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.report.observe(this, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = true
                        binding.componentError.root.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        reportAdapter.setData(it.data)
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.topHeadlines.visibility = View.VISIBLE

                        if (it.data == null) {
                            showErrorMessage(
                                R.drawable.no_result, getString(R.string.template_empty_title),
                                String.format(getString(R.string.template_empty_message), it.message)
                            )
                        }
                    }
                    is Resource.Error -> {
                        binding.topHeadlines.visibility = View.INVISIBLE
                        binding.swipeRefreshLayout.isRefreshing = false
                        showErrorMessage(
                            R.drawable.oops, getString(R.string.template_oops_title),
                            String.format(getString(R.string.template_oops_message), it.message)
                        )
                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Snackbar.make(binding.root, resources.getString(R.string.double_press_notification), Snackbar.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed( {
            doubleBackToExitPressedOnce = false
        }, DOUBLE_TAP_DELAY)
    }

    private fun showErrorMessage(imageView: Int, title: String, message: String) {
        if (binding.componentError.root.visibility == View.GONE) {
            binding.componentError.root.visibility = View.VISIBLE
        }

        binding.componentError.errorImage.setImageResource(imageView)
        binding.componentError.errorTitle.text = title
        binding.componentError.errorMessage.text = message
        binding.componentError.btnRetry.setOnClickListener { }
    }
}