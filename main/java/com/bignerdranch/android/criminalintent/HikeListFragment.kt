package com.bignerdranch.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "HikeListFragment"
private const val SAVED_SUBTITLE_VISIBLE = "subtitle"

class HikeListFragment : Fragment() {

    private lateinit var hikeRecyclerView: RecyclerView
    private var adapter: HikeAdapter? = HikeAdapter(emptyList())
    private val hikeListViewModel: HikeListViewModel by lazy {
        ViewModelProviders.of(this).get(HikeListViewModel::class.java)
    }
    private var callbacks
            : Callbacks? = null

    interface Callbacks {
        fun onHikeSelected(crimeId: UUID)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callbacks = context as? Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hike_list, container, false)

        hikeRecyclerView =
                view.findViewById(R.id.crime_recycler_view) as RecyclerView
        hikeRecyclerView.layoutManager = LinearLayoutManager(context)
        hikeRecyclerView.adapter = adapter

        return view
    }

    override fun onStart() {
        super.onStart()
        hikeListViewModel.hikeListLiveData.observe(
            viewLifecycleOwner,
            Observer { hikes ->
                hikes?.let {
                    Log.i(TAG, "Got hikeLiveData ${hikes.size}")
                    updateUI(hikes)
                }
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_hike_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_hike -> {
                val hike = Hike()
                hikeListViewModel.addCrime(hike)
                callbacks?.onHikeSelected(hike.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(hikes: List<Hike>) {
        adapter?.let {
            it.hikes = hikes
        } ?: run {
            adapter = HikeAdapter(hikes)
        }
        hikeRecyclerView.adapter = adapter
    }

    private inner class HikeHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var hike: Hike

        private val titleTextView: TextView = itemView.findViewById(R.id.adventure_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.hike_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.image_save)
        //private val ratingView: TextView = itemView.findViewById(R.id.rating)


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(hike: Hike) {
            this.hike = hike
            titleTextView.text = this.hike.title
            dateTextView.text = this.hike.date.toString()
            solvedImageView.visibility = if (hike.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(v: View) {
            callbacks?.onHikeSelected(hike.id)
        }
    }

    private inner class HikeAdapter(var hikes: List<Hike>)
        : RecyclerView.Adapter<HikeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : HikeHolder {
            val layoutInflater = LayoutInflater.from(context)
            val view = layoutInflater.inflate(R.layout.list_item_hike, parent, false)
            return HikeHolder(view)
        }

        override fun onBindViewHolder(holder: HikeHolder, position: Int) {
            val hike = hikes[position]
            holder.bind(hike)
        }

        override fun getItemCount() = hikes.size
    }

    companion object {
        fun newInstance(): HikeListFragment {
            return HikeListFragment()
        }
    }
}