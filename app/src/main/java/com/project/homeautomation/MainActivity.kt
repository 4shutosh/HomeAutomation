package com.project.homeautomation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.homeautomation.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
//        initButtons()
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        mainBinding.currentIpEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val count = s?.length
                Log.d(TAG, "onTextChanged: " + count)
                if (count == 2) {
                    try {
                        val imm: InputMethodManager =
                            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    } catch (e: Exception) {
                        // TODO: handle exception
                    }
                    initButtons(s.toString())
                    mainBinding.currentIpEt.text?.clear()
                    mainBinding.currentIpEt.clearFocus()
                    mainBinding.currentIpInputLayout.clearFocus()
                }
            }

        })
    }

    private fun initButtons(string: String) {
        val base = getString(R.string.baseIp)
        // input string concatenation
        val url = base + string

        // textView show
        val temp = "Current IP: $url"
        mainBinding.currentIpTextView.text = temp
        mainBinding.currentIpTextView.visibility = View.VISIBLE


        // set init colors to RED
        mainBinding.cardFan.setCardBackgroundColor(ContextCompat.getColor(this, R.color.OFF))
        mainBinding.LEDCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.OFF))
        mainBinding.bedCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.OFF))
        mainBinding.kitchenCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.OFF))


        mainBinding.cardFan.setOnClickListener {
            mainBinding.fanCheck.isChecked = !mainBinding.fanCheck.isChecked
            sendGET("Fan", url, mainBinding.fanCheck.isChecked)
            val bool = mainBinding.fanCheck.isChecked
            mainBinding.cardFan.setCardBackgroundColor(
                if (bool) ContextCompat.getColor(
                    this,
                    R.color.ON
                ) else ContextCompat.getColor(this, R.color.OFF)
            )
        }
        mainBinding.LEDCard.setOnClickListener {
            mainBinding.ledCheck.isChecked = !mainBinding.ledCheck.isChecked
            sendGET("Disco", url, mainBinding.ledCheck.isChecked)
            val bool = mainBinding.ledCheck.isChecked
            mainBinding.LEDCard.setCardBackgroundColor(
                if (bool) ContextCompat.getColor(
                    this,
                    R.color.ON
                ) else ContextCompat.getColor(this, R.color.OFF)
            )

        }
        mainBinding.bedCard.setOnClickListener {
            mainBinding.bedCheck.isChecked = !mainBinding.bedCheck.isChecked
            sendGET("LigFar", url, mainBinding.bedCheck.isChecked)
            val bool = mainBinding.bedCheck.isChecked
            mainBinding.bedCard.setCardBackgroundColor(
                if (bool) ContextCompat.getColor(
                    this,
                    R.color.ON
                ) else ContextCompat.getColor(this, R.color.OFF)
            )

        }
        mainBinding.kitchenCard.setOnClickListener {
            mainBinding.kitchenCheck.isChecked = !mainBinding.kitchenCheck.isChecked
            sendGET("LigNer", url, mainBinding.kitchenCheck.isChecked)
            val bool = mainBinding.kitchenCheck.isChecked
            mainBinding.kitchenCard.setCardBackgroundColor(
                if (bool) ContextCompat.getColor(
                    this,
                    R.color.ON
                ) else ContextCompat.getColor(this, R.color.OFF)
            )

        }
    }

    private fun sendGET(buttonName: String, url: String, bool: Boolean) {
        val queue = Volley.newRequestQueue(this)

        // url : ip + port
        // finalUrl : url+ TurnON / TurnOFF + buttonName

        // LigFar, LigNer, Fan, Disco
        val finalUrl: String = if (bool) {
            "$url/80/TurnON$buttonName"
        } else {
            "$url/80/TurnOFF$buttonName"
        }

        Log.d(TAG, "sendGET: url: $finalUrl")

        // url/80
        val stringRequest = StringRequest(Request.Method.GET, finalUrl,
            { response ->
                // response
                Log.d(TAG, "initVolley: $response")
            },
            { error ->
                if (error != null) {
                    Log.d(TAG, "initVolley:$error")
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })

        queue.add(stringRequest)
    }


    companion object {
        private const val TAG = "MainActivity"
    }


}