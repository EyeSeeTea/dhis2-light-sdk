package eyeseetea.org.dhis2_light_sdk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.eyeseetea.dhis2.lightsdk.D2Api
import org.eyeseetea.dhis2.lightsdk.D2Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val d2Api = D2Api.Builder()
            .url("")
            .credentials("","")
            .build();
        val response = d2Api.optionSets().getAll().execute()

        when (response){
            is D2Response.Success -> helloTextView.text = response.value.toString()
            is D2Response.Error -> helloTextView.text = response.toString()
        }
    }
}
