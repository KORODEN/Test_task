package com.koroden.test_task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainFragment : Fragment(), RecyclerViewAdapter.ClickListener{

    private lateinit var adapter : RecyclerViewAdapter
    lateinit var listData: ArrayList<DataModel>
    private var lastPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        if (savedInstanceState != null) {
            lastPosition = savedInstanceState.getInt("lastPosition")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        buildDisplayData()
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View){
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerViewAdapter(listData, this)
        recyclerView.adapter = adapter

        recyclerView.scrollToPosition(lastPosition)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                lastPosition = layoutManager.findFirstVisibleItemPosition()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("lastPosition", lastPosition)
    }

    private fun buildDisplayData(){
        val jsonFileString = getJsonDataFromAsset("news.json")
        val listNewsType = object : TypeToken<List<DataModel>>() {}.type
        listData = Gson().fromJson(jsonFileString, listNewsType)
    }

    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = activity!!.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onItemClick(dataModel: DataModel) {
        val fragment : Fragment = DetailFragment.newInstance(dataModel.date, dataModel.header, dataModel.text)
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.hide(activity?.supportFragmentManager!!.findFragmentByTag("main_fragment")!!)
        transaction.add(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}