package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
     var currentImageUrl:String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadme()
    }
   private fun loadme(){

       progressBar.visibility=View.VISIBLE
       val queue = Volley.newRequestQueue(this)
       currentImageUrl = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
       val jsonObjectRequest = JsonObjectRequest(
           Request.Method.GET,  currentImageUrl, null,
           Response.Listener { response ->
               currentImageUrl =response.getString("url")

               Glide.with(this).load( currentImageUrl).listener(object : RequestListener<Drawable> {

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressBar.visibility= View.GONE
                       return false
                   }

                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                      progressBar.visibility= View.VISIBLE
                       return false
                   }


               }).into(imageView)

//               textView.text = "Response: %s".format(response.toString())
           },
           Response.ErrorListener { error ->
               // TODO: Handle error
           }
       )

// Add the request to the RequestQueue.
       queue.add(jsonObjectRequest)

   }

    fun Nextme(view: View) {
        loadme()

    }
    fun Shareme(view: View) {
        val intent =Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
//        intent.type="image/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey Chechout This Cool Meme I Got From Redit.." + " \n " +currentImageUrl)
        val chooser= Intent.createChooser(intent,"Share This Meme Using.....")
        startActivity(chooser)


    }
;

}