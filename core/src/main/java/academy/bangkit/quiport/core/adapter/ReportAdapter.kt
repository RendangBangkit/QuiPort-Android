package academy.bangkit.quiport.core.adapter

import academy.bangkit.quiport.core.databinding.ItemReportRowBinding
import academy.bangkit.quiport.core.domain.model.report.Report
import academy.bangkit.quiport.core.utils.Tools
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.util.*


class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ListViewHolder>() {

    private var listData = ArrayList<Report>()
    var onItemClick: ((Report) -> Unit)? = null

    fun setData(newListData: List<Report>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemReportRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListViewHolder(binding.root)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemReportRowBinding.bind(itemView)
        fun bind(data: Report) {

            with(binding) {
                title.text = Tools.generateTitle(data.aiResults.categories, data.addressComponents.address)
                time.text = Tools.timestampToHumanize(data.createdAt)
                location.text = data.addressComponents.province

                val requestOptions = RequestOptions()
                requestOptions.placeholder(Tools.randomDrawableColor)
                requestOptions.error(Tools.randomDrawableColor)
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                requestOptions.centerCrop()

                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .apply(requestOptions)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressLoadPhoto.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressLoadPhoto.visibility = View.GONE
                            return false
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(img)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}