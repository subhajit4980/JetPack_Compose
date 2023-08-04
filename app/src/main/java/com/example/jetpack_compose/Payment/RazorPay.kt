package com.example.jetpack_compose.Payment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Checkbox
import com.example.jetpack_compose.Component.TextInput
import com.example.jetpack_compose.Component.toast
import com.example.jetpack_compose.R
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class RazorPay : ComponentActivity(), PaymentResultListener {
    lateinit var context:Context
    var ActivityRequestCode = 2
    var name="";
    var email="";
    var Phone="";
    var txnAmount="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpack_composeTheme(dynamicColor = true) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    PaymentUI()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PaymentUI() {
        context = LocalContext.current.applicationContext
        var checked by remember {
            mutableStateOf(true)
        }
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
             name = TextInput(
                "Enter your Name",
                Icons.Outlined.PermIdentity,
                KeyboardType.Text
            )
             email = TextInput(
                "Enter your Email",
                Icons.Outlined.Email,
                KeyboardType.Email
            )
            Phone = TextInput(
                "Enter your Phone Number",
                Icons.Outlined.Phone,
                KeyboardType.Number
            )
            txnAmount = TextInput(
                "Enter your Tax Amount",
                Icons.Outlined.CurrencyRupee,
                KeyboardType.Number
            )
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(all=10.dp)) {
                Checkbox(

                    checked = checked,
                    onCheckedChange = { checked_ ->
                        checked = checked_
                    }
                )
                Text(text = "All environment are ready .", modifier =Modifier.padding(start = 10.dp))
            }
            Button(
                onClick = { startPayment()}, colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Blue
                )
            ) {
                Text(text = "Pay")
            }
        }

    }

//RazorPay Payment gateWay
    private fun startPayment() {
        val activity: Activity = this
        val co = Checkout()
        try {
            val options = JSONObject()
            options.put("name", "Subscription")
            options.put("description", "Service Charge")
            options.put("image", R.drawable.reading)
            options.put("theme.color", "#FFD24A");
            options.put("currency", "INR");
            try {
                val payment: String = txnAmount
                val total = (payment.toDouble() * 100).toInt()
                options.put("amount", total)
            }catch (e:Exception)
            {
                toast("${e}",activity)
            }
            val preFill = JSONObject()
            preFill.put("email", email.toString())
            preFill.put("contact", Phone.toString())

            options.put("prefill", preFill)
            co.open(activity, options)
        }catch (e: Exception){
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successfully:${p0}", Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Error in payment: ", Toast.LENGTH_LONG).show()
    }
}
