package com.batterysmart.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.batterysmart.Model.Country
import com.batterysmart.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.batterysmart.databinding.ActivityMainBinding
import com.batterysmart.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var appStoreHomeViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    var dataList: ArrayList<Country> = ArrayList()

    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    lateinit var edit: EditText
    lateinit var cancelButtom: ImageView
    lateinit var tv_country: TextView
    lateinit var tv_city: TextView
    var country_position: Int = 0
    lateinit var array: Array<String>
    var cityStatus: Boolean = false
    var countryStatus: Boolean = false

    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        appStoreHomeViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.mainModelHome = appStoreHomeViewModel
        callAPI()
        setListner()
    }

    private fun setListner() {
        binding.tvCountry.setOnClickListener { openDialog(array) }
        binding.tvCity.setOnClickListener {
            if (countryStatus) {
                val array2: Array<String> =
                    dataList.get(country_position).cities.map { it.toString() }.toTypedArray()
                println(array2.size)
                openCityDialog(array2)
            } else {
                Toast.makeText(this, "Select Country", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnSubmit.setOnClickListener {
            if (cityStatus) {
                displayBottomSheetDialog()
            } else {
                Toast.makeText(this, "Select Country and City", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callAPI() {
        showProgress()
        appStoreHomeViewModel.getProjectList()?.observe(this, Observer<ArrayList<Country>> {
            hideProgress()
            if (it != null) {
                dataList = it
                main()
            }

        })
    }

    private fun showProgress() {
        binding.linearSplash.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.linearSplash.visibility = View.GONE
    }

    fun openDialog(array: Array<String>) {
        alertDialog = AlertDialog.Builder(this)
        val rowList: View = layoutInflater.inflate(R.layout.countries_dialog, null)
        listView = rowList.findViewById(R.id.listView)
        edit = rowList.findViewById(R.id.et)
        cancelButtom = rowList.findViewById(R.id.closeBtn)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            binding.tvCountry.text = selectedItem
            country_position = position
            countryStatus = true
            dialog.cancel()
            Log.e("element", selectedItem.toString() + position)
        }
        cancelButtom.setOnClickListener { dialog.cancel() }
        edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                adapter.filter.filter(s.toString())
            }
        })
        dialog = alertDialog.create()
        dialog.show()
    }

    fun openCityDialog(array2: Array<String>) {
        alertDialog = AlertDialog.Builder(this)
        val rowList: View = layoutInflater.inflate(R.layout.countries_dialog, null)
        listView = rowList.findViewById(R.id.listView)
        edit = rowList.findViewById(R.id.et)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array2)
        cancelButtom = rowList.findViewById(R.id.closeBtn)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        cancelButtom.setOnClickListener { dialog.cancel() }
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            binding.tvCity.text = selectedItem
            cityStatus = true
            dialog.cancel()
            Log.e("element", selectedItem.toString() + position)
        }
        edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                adapter.filter.filter(s.toString())
            }
        })
        dialog = alertDialog.create()
        dialog.show()
    }

    fun main() {
        array = dataList.map { it.country.toString() }.toTypedArray()
        println(array)
    }

    private fun displayBottomSheetDialog() {
        if (::bottomSheetDialog.isInitialized) {
            bottomSheetDialog.dismiss()
        }
        val viewBottom: View = layoutInflater.inflate(R.layout.bottom_sheet_view, null, false)
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(viewBottom)
        val bottomSheetBehavior = BottomSheetBehavior.from(viewBottom.parent as View)
        bottomSheetBehavior.isHideable = false
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.show()
        var tabLayout: TabLayout = viewBottom.findViewById(R.id.tab_layout)
        setTabTitle(tabLayout)
    }

    private fun setTabTitle(tabLayout: TabLayout) {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_rs))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_rs))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_rs))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

}
