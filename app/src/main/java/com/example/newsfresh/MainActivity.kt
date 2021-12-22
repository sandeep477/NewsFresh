package com.example.newsfresh

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdpater: News_Adpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val recyclerview=findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager=LinearLayoutManager(this)
        cal()
        mAdpater=News_Adpater(this)
        recyclerview.adapter=mAdpater

    }
    private fun cal()
    {
      val url="https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { it ->
                        val newsJsonArray= it.getJSONArray("articles")
                        val newsArray = ArrayList<News>()
                        for(i in 0 until newsJsonArray.length())
                        {
                            val newsJsonObject = newsJsonArray.getJSONObject(i)
                            val news = News(
                                    newsJsonObject.getString("title"),
                                    newsJsonObject.getString("author"),
                                    newsJsonObject.getString("url"),
                                    newsJsonObject.getString("urlToImage"))
                            newsArray.add(news)

                        }
                    mAdpater.updatedNews(newsArray)
                },
                { error ->

                    Toast.makeText(this,"sorry $error.toString()",Toast.LENGTH_LONG
                    ).show()
                }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder= CustomTabsIntent.Builder();
        val  customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}