package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

/* At beginning of this lesson(Chapter3_Lsn2), SleepNightAdapter extends RecyclerView.Adapter<SleepNightAdapter.ViewHolder>()
 as described in commented line below. But as lesson progresses, we require to provide an efficient way to notify Recyclerview
 with the differences occur in any of sleep night object, so it can only draw/redraw/update that object on screen and mke smoother experience for UX.
*/
//class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>(){

class SleepNightAdapter(val nightClickListener: SleepNightClickListener): ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    // We don't need these following 2 members, data and getItemCount() as ListAdapter will take care it for us.
    /*var data = listOf<SleepNight>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size*/


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = data[position]
        val item = getItem(position)
        holder.bind(item, nightClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SleepNight, clickListener:SleepNightClickListener) {
        /*
        This below commented lines are simply using view binding technique, which
        we've been using since beginning of the course in Android Nano degree program.
        But to use data binding we've defined BindingUtils.kt and some binding adapters,
        which can be directly called from XML and data can be bound to views. Here data means a SleepNight object.
        */
        /*val res = itemView.context.resources
            binding.sleepLengthText.text =
                convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            binding.sleepQualityText.text = convertNumericQualityToString(item.sleepQuality, res)
            binding.qualityImage.setImageResource(
                when (item.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )*/

            binding.sleep = item
            binding.nightItemClickListener = clickListener
            binding.executePendingBindings()
        }


        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                //val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
                //return ViewHolder(view)

                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class SleepNightDiffCallback: DiffUtil.ItemCallback<SleepNight>() {
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem.nightId == newItem.nightId
        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
            return oldItem == newItem
        }

    }
}

class SleepNightClickListener(val clickListener: (sleepId: Long) -> Unit){
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}