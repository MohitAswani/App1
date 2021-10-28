package com.example.app1.ui.dashboard


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.app1.databinding.FragmentDashboardBinding
import com.google.android.gms.location.*
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var searchButton: Button

    // creating variable for our edit text.

    // creating a variable for our recycler view.
    lateinit var centersRV: RecyclerView

    // creating a variable for adapter class.
    lateinit var centerRVAdapter: CenterRVAdapter

    // creating a variable for our list
    lateinit var centerList: List<CenterRvModal>

    // creating a variable for progress bar.
    lateinit var loadingPB: ProgressBar

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private  var lati:String=""
    private  var long:String=""
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val callback= object: LocationCallback(){
        override fun onLocationAvailability(p0: LocationAvailability?) {
            super.onLocationAvailability(p0)
        }

        override fun onLocationResult(result: LocationResult?) {
            val lastLocation= result?.lastLocation
            Log.d("TAG", "onLocationResult: ${lastLocation?.longitude.toString()}")
            Log.d("TAG", "onLocationResult: ${lastLocation?.latitude.toString()}")
             lati=lastLocation?.latitude.toString()
             long=lastLocation?.longitude.toString()
            super.onLocationResult(result)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(activity)

        onGPS()

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        searchButton = binding.idBtnSearch
        centersRV = binding.centersRV
        loadingPB = binding.idPBLoading
        centerList = ArrayList<CenterRvModal>()

        // on below line we are adding on
        // click listener to our button.
        searchButton.setOnClickListener {
            fetchLocation()
            // inside on click listener we are getting data from
            // edit text and creating a val for ite on below line.


            // on below line we are validating
            // our pin code as 6 digit or not.

            getAppointments()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // below is the method for getting data from API.
    private fun getAppointments() {
        val url =
            "https://cdn-api.co-vin.in/api/v2/appointment/centers/public/findByLatLong?lat="+lati+"&long="+long
        val queue = Volley.newRequestQueue(activity)

        // on below line we are creating a request
        // variable for making our json object request.
        val request =
            // as we are getting json object response and we are making a get request.
            JsonObjectRequest(Request.Method.GET, url, null, { response ->
                // this method is called when we get successful response from API.
                Log.e("TAG", "SUCCESS RESPONSE IS $response")
                // we are setting the visibility of progress bar as gone.
                loadingPB.setVisibility(View.GONE)
                // on below line we are adding a try catch block.
                try {
                    // in try block we are creating a variable for center
                    // array and getting our array from our object.
                    val centerArray = response.getJSONArray("centers")

                    // on below line we are checking if the length of the array is 0.
                    // the zero length indicates that there is no data for the given pincode.
                    if (centerArray.length().equals(0)) {
                        Toast.makeText(activity, "No Center Found", Toast.LENGTH_SHORT).show()
                    }
                    for (i in 0 until centerArray.length()) {

                        // on below line we are creating a variable for our center object.
                        val centerObj = centerArray.getJSONObject(i)

                        // on below line we are getting data from our session
                        // object and we are storing that in a different variable.
                        val centerName: String = centerObj.getString("name")
                        val District_name: String = centerObj.getString("district_name")
                        val State_name: String = centerObj.getString("state_name")
                        val Location: String = centerObj.getString("location")
                        val Pincode:String = centerObj.getString("pincode")

                        // on below line we are creating a variable for our session object
                        val sessionObj = centerObj.getJSONArray("sessions").getJSONObject(0)
                        val ageLimit: Int = sessionObj.getInt("min_age_limit")
                        val vaccineName: String = sessionObj.getString("vaccine")
                        val avaliableCapacity: Int = sessionObj.getInt("available_capacity")

                        // after extracting all the data we are passing this
                        // data to our modal class we have created
                        // a variable for it as center.
                        val center = CenterRvModal(
                            centerName,
                            District_name,
                            State_name,
                            Location,
                            Pincode
                        )
                        // after that we are passing this modal to our list on the below line.
                        centerList = centerList + center
                    }

                    // on the below line we are passing this list to our adapter class.
                    centerRVAdapter = CenterRVAdapter(centerList)

                    // on the below line we are setting layout manager to our recycler view.
                    centersRV.layoutManager = LinearLayoutManager(activity)

                    // on the below line we are setting an adapter to our recycler view.
                    centersRV.adapter = centerRVAdapter

                    // on the below line we are notifying our adapter as the data is updated.


                } catch (e: JSONException) {
                    // below line is for handling json exception.
                    e.printStackTrace();
                }
            },
                { error ->
                    // this method is called when we get any
                    // error while fetching data from our API
                    Log.e("TAG", "RESPONSE IS $error")
                    // in this case we are simply displaying a toast message.
                    Toast.makeText(activity, "Fail to get response", Toast.LENGTH_SHORT)
                        .show()
                })
        // at last we are adding
        // our request to our queue.
        queue.add(request)
    }
    fun onGPS() {

        Log.d("TAG", "onGPS: ${isLocationEnabled()}")

        if (!isLocationEnabled()) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        } else {
            fetchLocation()
        }


    }

    private fun fetchLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (ActivityCompat.checkSelfPermission(
                    this.requireActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                   this.requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    200
                )
                return
            }else{
                requestLocation()
            }


        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        Log.d("TAG", "requestLocation: ")
        val requestLocation= LocationRequest()
        requestLocation.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        requestLocation.interval = 0
        requestLocation.fastestInterval = 0
        requestLocation.numUpdates = 1
        fusedLocationProviderClient.requestLocationUpdates(
            requestLocation,callback, Looper.myLooper()
        )

    }


    fun isLocationEnabled(): Boolean {
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }



}
