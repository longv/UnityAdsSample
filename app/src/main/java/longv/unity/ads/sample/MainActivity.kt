package longv.unity.ads.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.UnityAds
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize
import longv.unity.ads.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), IUnityAdsInitializationListener, BannerView.IListener {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupVideoAd()
        setupBannerAd()

        UnityAds.initialize(applicationContext, "14851", true, this)
    }

    private fun setupVideoAd() {
        viewBinding.watchAdBtn.text = "Initializing Ads..."
        viewBinding.watchAdBtn.isEnabled = false
        viewBinding.watchAdBtn.setOnClickListener {
            if (UnityAds.isInitialized()) {
                UnityAds.show(this)
            }
        }
    }

    private fun setupBannerAd() {
       BannerView(this, "bannerads", UnityBannerSize(320, 50)).apply {
           viewBinding.bannerAdContainer.addView(this)
           listener = this@MainActivity
           load()
       }
    }

    override fun onInitializationComplete() {
        viewBinding.watchAdBtn.text = "Watch Ads!"
        viewBinding.watchAdBtn.isEnabled = true
    }

    override fun onInitializationFailed(error: UnityAds.UnityAdsInitializationError, message: String) {
        Snackbar.make(viewBinding.root, "Fail to initialize Unity Ads: $message", Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onBannerLoaded(view: BannerView) {
    }

    override fun onBannerClick(view: BannerView) {
    }

    override fun onBannerFailedToLoad(view: BannerView, info: BannerErrorInfo) {
        Snackbar.make(viewBinding.root, "Fail to load banner ad: $info", Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onBannerLeftApplication(view: BannerView?) {
        Snackbar.make(viewBinding.root, "Banner has left the app", Snackbar.LENGTH_SHORT)
            .show()
    }
}