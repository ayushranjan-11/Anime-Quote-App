package com.example.animequotes

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //for splash screen
        setTheme(R.style.Theme_AnimeQuotes)
        setContentView(R.layout.activity_main)

        jsonApiRequest()
    }

    private fun jsonApiRequest() {

        val animeNameTextView = findViewById<View>(R.id.animeName) as TextView
        val characterNameTextView = findViewById<View>(R.id.characterName) as TextView
        val characterQuoteTextView = findViewById<View>(R.id.characterQuote) as TextView

        val quoteCopyButton = findViewById<ImageButton>(R.id.imageButtonToCopyQuote)

        val url = "https://animechan.vercel.app/api/random"

        val queue = Volley.newRequestQueue(this)

       //getting request from api
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val animeName = response.getString("anime")
                val characterName = response.getString("character")
                val characterQuote = response.getString("quote")


                animeNameTextView.text = animeName
                characterNameTextView.text = characterName
                characterQuoteTextView.text = characterQuote

            },
            { error ->
                // TODO: Handle error
            }
        )

        //to copy the available text/quote
        quoteCopyButton.setOnClickListener {
            val clipBoard : ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val toCopyQuote = characterQuoteTextView.text.toString()
            val clip = ClipData.newPlainText("Text Copied",toCopyQuote)

           clipBoard.setPrimaryClip(clip)

            Toast.makeText(this,"Text Copied",Toast.LENGTH_SHORT).show()
        }

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest)
    }

    //button press for new request
    fun anotherRequest(view: View) {
        jsonApiRequest()
    }
}