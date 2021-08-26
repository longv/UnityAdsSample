package longv.unity.ads.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.UnityAds
import longv.unity.ads.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IUnityAdsInitializationListener {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.watchAdBtn.text = "Initializing Ads..."
        viewBinding.watchAdBtn.isEnabled = false
        viewBinding.watchAdBtn.setOnClickListener {
            if (UnityAds.isInitialized()) {
                UnityAds.show(this)
            }
        }

        UnityAds.initialize(applicationContext, "14851", this)
    }

    override fun onInitializationComplete() {
        viewBinding.watchAdBtn.text = "Watch Ads!"
        viewBinding.watchAdBtn.isEnabled = true
    }

    override fun onInitializationFailed(error: UnityAds.UnityAdsInitializationError?, message: String?) {
        Snackbar.make(viewBinding.root, "Fail to initialize Unity Ads: $message", Snackbar.LENGTH_SHORT)
            .show()
    }
}