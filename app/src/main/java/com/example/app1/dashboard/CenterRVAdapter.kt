package com.example.app1.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.R

// on below line we are creating our adapter class
// in this class we are passing our array list
// and our View Holder class which we have created.
class CenterRVAdapter(private val centerList: List<CenterRvModal>) :
    RecyclerView.Adapter<CenterRVAdapter.CenterRVViewHolder>() {

    // on below line we are creating our view holder class which will
    // be used to initialize each view of our layout file.
    class CenterRVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are initializing all our text views along with  its ids.
        val centerNameTV: TextView = itemView.findViewById(R.id.idTVCenterName)
        val DistrictTV: TextView = itemView.findViewById(R.id.idTVDistrictName)
        val StateTV: TextView = itemView.findViewById(R.id.idTVStateName)
        val LocationTV: TextView = itemView.findViewById(R.id.idTVLocation)
        val Pincode : TextView = itemView.findViewById(R.id.idTVPincode)

    }

    // below method is for on Create View Holder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterRVViewHolder {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.center_rv_item,
            parent, false
        )
        // at last we are returning our view holder
        // class with our item View File.
        return CenterRVViewHolder(itemView)
    }

    // this method is to count the size of our array list.
    override fun getItemCount(): Int {

        // on below line we are returning
        // the size of our array list.
        return centerList.size
    }

    // below method is to set the data to each view of our recycler view item.
    override fun onBindViewHolder(holder: CenterRVViewHolder, position: Int) {

        // on below line we are getting item
        // from our list along with its position.
        val currentItem = centerList[position]

        // after getting current item we are setting
        // data from our list to our text views.
        holder.centerNameTV.text = currentItem.centerName
       holder.DistrictTV.text=currentItem.District_name
        holder.StateTV.text=currentItem.State_name
        holder.LocationTV.text=currentItem.Location
        holder.Pincode.text=currentItem.Pincode
    }
}