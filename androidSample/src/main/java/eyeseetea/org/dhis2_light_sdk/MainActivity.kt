package eyeseetea.org.dhis2.lightsdk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.eyeseetea.dhis2.lightsdk.D2Api
import org.eyeseetea.dhis2.lightsdk.D2Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestButton.setOnClickListener {
            val d2Api = D2Api.Builder()
                .url(urlEditText.text.toString())
                .credentials(usernameEditText.text.toString(), passwordEditText.text.toString())
                .build();
            val response = d2Api.optionSets().getAll().execute()

            when (response) {
                is D2Response.Success -> helloTextView.text = response.value.toString()
                is D2Response.Error -> helloTextView.text = response.toString()
            }
        }
    }
}
