package net.yanzm.droidkaigi2019

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.yanzm.droidkaigi2019.detail.DetailActivity
import net.yanzm.droidkaigi2019.domain.SessionId

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(DetailActivity.createIntent(this, SessionId("70866")))
    }
}
