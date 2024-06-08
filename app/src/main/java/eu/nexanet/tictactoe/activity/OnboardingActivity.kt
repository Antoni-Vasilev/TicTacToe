package eu.nexanet.tictactoe.activity

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import eu.nexanet.tictactoe.R
import eu.nexanet.tictactoe.extansions.text_animation.TextAnimator
import eu.nexanet.tictactoe.extansions.text_animation.TextAnimatorCallback

class OnboardingActivity : AppCompatActivity() {

    lateinit var image: ImageView
    lateinit var textHeader: TextView
    lateinit var textSubtitle: TextView
    lateinit var btnBack: TextView
    lateinit var slider_1: View
    lateinit var slider_2: View
    lateinit var slider_3: View
    lateinit var btnNext: TextView

    private var currentSlide = 0
    private lateinit var slideHeaders: List<String>
    private lateinit var slideSubtitles: List<String>
    private lateinit var slideImages: List<Drawable?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun init() {
        slideHeaders = listOf(
            getString(R.string.welcome),
            getString(R.string.compete),
            getString(R.string.scoreboard)
        )
        slideSubtitles = listOf(
            getString(R.string.onboarding_subtitle_1),
            getString(R.string.onboarding_subtitle_2),
            getString(R.string.onboarding_subtitle_3)
        )
        slideImages = listOf(
            getDrawable(R.drawable.onboarding_image_1),
            getDrawable(R.drawable.onboarding_image_2),
            getDrawable(R.drawable.onboarding_image_3)
        )

        image = findViewById(R.id.image)
        textHeader = findViewById(R.id.textHeader)
        textSubtitle = findViewById(R.id.textSubtitle)
        btnBack = findViewById(R.id.btnBack)
        slider_1 = findViewById(R.id.slider_1)
        slider_2 = findViewById(R.id.slider_2)
        slider_3 = findViewById(R.id.slider_3)
        btnNext = findViewById(R.id.btnNext)

        btnBack.setOnClickListener { prevSlide() }
        btnNext.setOnClickListener { nextSlide() }
    }

    private fun nextSlide() {
        currentSlide++
        btnBack.setTextColor(getColor(R.color.text_secondary))
        if (currentSlide >= 3) {
            currentSlide = 2
            showSignIn()
            return
        }

        TextAnimator.animateText(
            this,
            slideHeaders[currentSlide - 1],
            slideHeaders[currentSlide],
            600,
            object : TextAnimatorCallback {
                override fun onTextChange(text: String) {
                    textHeader.text = text
                }
            })

        TextAnimator.animateText(
            this,
            slideSubtitles[currentSlide - 1],
            slideSubtitles[currentSlide],
            600,
            object : TextAnimatorCallback {
                override fun onTextChange(text: String) {
                    textSubtitle.text = text
                }
            })

        val anim: ValueAnimator = ValueAnimator.ofFloat(image.rotation, 360F * currentSlide)
        anim.duration = 600
        anim.addUpdateListener { animation -> image.rotation = animation.animatedValue as Float }
        anim.start()
        Thread {
            Thread.sleep(300)
            runOnUiThread { image.setImageDrawable(slideImages[currentSlide]) }
        }.start()

        updateSlider()
    }

    private fun prevSlide() {
        currentSlide--
        if (currentSlide <= 0) btnBack.setTextColor(getColor(android.R.color.transparent))
        if (currentSlide < 0) {
            currentSlide = 0
            return
        }

        TextAnimator.animateText(
            this,
            slideHeaders[currentSlide + 1],
            slideHeaders[currentSlide],
            600,
            object : TextAnimatorCallback {
                override fun onTextChange(text: String) {
                    textHeader.text = text
                }
            })

        TextAnimator.animateText(
            this,
            slideSubtitles[currentSlide + 1],
            slideSubtitles[currentSlide],
            600,
            object : TextAnimatorCallback {
                override fun onTextChange(text: String) {
                    textSubtitle.text = text
                }
            })

        val anim: ValueAnimator = ValueAnimator.ofFloat(image.rotation, 360F * currentSlide)
        anim.duration = 600
        anim.addUpdateListener { animation -> image.rotation = animation.animatedValue as Float }
        anim.start()
        Thread {
            Thread.sleep(300)
            runOnUiThread { image.setImageDrawable(slideImages[currentSlide]) }
        }.start()

        updateSlider()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateSlider() {
        val sliders = listOf(slider_1, slider_2, slider_3)
        for (i in sliders.indices) {
            if (i == currentSlide) sliders[i].background =
                getDrawable(R.drawable.onboarding_slider_select)
            else sliders[i].background = getDrawable(R.drawable.onboarding_slider_unselect)
        }
    }

    private fun showSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}