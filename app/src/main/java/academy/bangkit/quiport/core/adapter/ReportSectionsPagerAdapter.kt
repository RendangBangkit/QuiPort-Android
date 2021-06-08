package academy.bangkit.quiport.core.adapter

import academy.bangkit.quiport.presentation.main.components.reportList.ReportListFragment
import academy.bangkit.quiport.presentation.main.components.reportMain.ReportMainFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ReportSectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> ReportMainFragment()
            1 -> ReportListFragment()
            else -> Fragment()
        }
}