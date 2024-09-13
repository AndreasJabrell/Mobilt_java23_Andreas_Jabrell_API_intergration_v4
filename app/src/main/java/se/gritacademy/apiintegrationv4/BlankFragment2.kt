package se.gritacademy.apiintegrationv4

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

class BlankFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_blank2, container, false)

        val tv1: TextView = view.findViewById(R.id.textView3)
        val tv2: TextView = view.findViewById(R.id.textView4)
        val image: ImageView = view.findViewById(R.id.imageView)


        var rq: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = "https://potterapi-fedeperin.vercel.app/en/books/random"


        var request: StringRequest = StringRequest(Request.Method.GET, url, { response ->
            Log.i("Andreas", "SUCCESS$response")
            try {
                // If response is an array
//                val jsonArray = JSONArray(response)
//                val firstObject = jsonArray.getJSONObject(0)
                val firstObject = JSONObject(response)
                val title = firstObject.getString("originalTitle")
                val description = firstObject.getString("description")
                val cover = firstObject.getString("cover")

                Log.i(
                    "Andreas",
                    "Titel ${title} och sen ${description} och till slut bildens länk ${cover}"
                )

                tv1.text = title
                tv2.text = description
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val url = URL(cover)
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
                //tv.text = response

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