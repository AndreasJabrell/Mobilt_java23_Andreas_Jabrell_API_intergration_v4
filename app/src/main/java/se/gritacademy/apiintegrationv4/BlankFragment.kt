package se.gritacademy.apiintegrationv4

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL


class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_blank, container, false)


        val image: ImageView = view.findViewById(R.id.imageView2)


        var rq: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = "https://random.dog/woof.json"


        var request: StringRequest = StringRequest(Request.Method.GET, url, { response ->
            Log.i("Andreas", "SUCCESS$response")
            try {
                // if response is an array
//                val jsonArray = JSONArray(response)
//                val firstObject = jsonArray.getJSONObject(0)
                val firstObject = JSONObject(response)
                val duck = firstObject.getString("url")

                // fetch picture async
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val url = URL(duck)
                        val bitmap =
                            BitmapFactory.decodeStream(url.openConnection().getInputStream())

                        // Uppdatera UI på huvudtråden
                        withContext(Dispatchers.Main) {
                            image.setImageBitmap(bitmap)
                        }
                    } catch (e: Exception) {
                        Log.e("Andreas", "Error loading image: ${e.message}")
                    }
                }


            } catch (e: Exception) {
                Log.e("Andreas", "JSON vill inte: ${e.message}")
            }
        }, {
            Log.i("Andreas", "FAIL")
        })
        rq.cache.clear()
        rq.add(request)

        return view
    }
}