package eyeseetea.org.dhis2.lightsdk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import eyeseetea.org.dhis2_light_sdk.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import org.eyeseetea.dhis2.lightsdk.D2Api
import org.eyeseetea.dhis2.lightsdk.D2Response
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    private var d2Api: D2Api = D2Api.Builder().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayAdapter =
            ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                getEndpoints().keys.toList()
            )

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        endpointSpinner.adapter = arrayAdapter

        requestButton.setOnClickListener {
            passwordEditText.hideKeyboard()

            d2Api = D2Api.Builder()
                .url(urlEditText.text.toString())
                .credentials(usernameEditText.text.toString(), passwordEditText.text.toString())
                .apiVersion(apiVersionEditText.text.toString())
                .build()

            executeRequest()
        }
    }

    private fun executeRequest() {
        val endpointsMap = getEndpoints()
        val name = endpointSpinner.selectedItem.toString()
        val response: D2Response<Any>? = endpointsMap[name]!!.execute()

        when (response) {
            is D2Response.Success -> helloTextView.text = response.value.toString()
            is D2Response.Error -> helloTextView.text = response.toString()
        }
    }

    private fun getEndpoints() =
        mapOf(
            "SystemInfo" to d2Api.systemInfo().get(),
            "OptionSets" to d2Api.optionSets().getAll())
}
