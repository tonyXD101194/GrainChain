package com.example.grainchaintest.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.grainchaintest.R
import com.example.grainchaintest.adapters.RouteAdapter
import com.example.grainchaintest.interfaces.ActionsRouteInterface
import com.example.grainchaintest.models.DetailModel
import com.example.grainchaintest.models.RouteModel
import com.example.grainchaintest.viewModels.DetailViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_detail.view.*

class DetailFragment : Fragment(), ActionsRouteInterface {

    private lateinit var rootView : View
    private lateinit var detailViewModel : DetailViewModel
    private lateinit var adapter : RouteAdapter

    companion object {

        @JvmStatic
        fun newInstance() = DetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_detail, container, false)

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        detailViewModel.parseDetailModel()

        this.initObservers()

        this.initListeners()

        return rootView
    }

    private fun initAdapter(list : List<DetailModel>) {

        adapter = RouteAdapter(
            list,
            activity as Context,
            this)

        rootView.fragmentDetailListRecyclerView.adapter = adapter
    }

    private fun initObservers() {

        detailViewModel.details.observe(this, Observer {

            if (it.isNotEmpty()){

                initAdapter(it)
            }
        })

        detailViewModel.itemDeleted.observe(this, Observer {

            adapter.removeItem(it)

            Toast.makeText(activity,"Item deleted", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initListeners() {

        rootView.fragmentDetailBackImageView.setOnClickListener {

            activity!!.finish()
        }
    }

    override fun onDelete(pos : Int, model: Any) {

        detailViewModel.deleteRoute(model = model as DetailModel)
    }

    override fun onShare(model: Any) {

        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, Gson().toJson(model))
            type = "text/plain"
        }

        activity!!.startActivity(Intent.createChooser(shareIntent, null))
    }
}