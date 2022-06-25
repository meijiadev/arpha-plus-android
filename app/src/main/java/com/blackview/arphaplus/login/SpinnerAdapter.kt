package com.blackview.arphaplus.login

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.blackview.base.request.Region

class SpinnerAdapter(
    context: Context, textViewResourceId: Int,
    private val array: Array<Region>
    ) : ArrayAdapter<Region?>(context, textViewResourceId, array) {

        override fun getCount(): Int {
            return array.size
        }

        override fun getItem(position: Int): Region {
            return array[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun isEnabled(position: Int): Boolean {
            return position != 0
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val label: TextView = super.getView(position, convertView, parent) as TextView
            label.setTextColor(Color.BLACK)
            label.text = array[position].name
            return label
        }

        override fun getDropDownView(
            position: Int, convertView: View?,
            parent: ViewGroup?
        ): View {
            val view = super.getDropDownView(position, convertView, parent)
            val tv = view as TextView
            if (position == 0) {
                tv.setTextColor(Color.GRAY)
            } else {
                tv.setTextColor(Color.BLACK)
            }
            tv.text= array[position].name
            return view
        }

    }