package academy.bangkit.quiport.presentation.main.components.reportList

import academy.bangkit.quiport.R
import academy.bangkit.quiport.core.adapter.ReportAdapter
import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.utils.Tools
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.quiport.databinding.FragmentReportListBinding
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel

class ReportListFragment : Fragment() {
    private var _binding : FragmentReportListBinding? = null
    private val binding get() = _binding as FragmentReportListBinding
    private val reportListViewModel: ReportListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reportAdapter = ReportAdapter()
        reportAdapter.onItemClick = {
            val browserIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    Tools.generateMapsLink(
                it.addressComponents.latitude,
                it.addressComponents.longitude
            )))

            startActivity(browserIntent)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            reportListViewModel.refresh()
        }

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = reportAdapter
            itemAnimator = DefaultItemAnimator()
        }

        reportListViewModel.report.observe(requireActivity(), {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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