package com.babumusai.fakenews

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var textInput: EditText
    private lateinit var subjectInput: EditText
    private lateinit var checkButton: Button
    private lateinit var resultIcon: ImageView
    private lateinit var progressBar: ProgressBar

    // Initializing the GenerativeModel with 'responseMimeType' and 'systemInstruction'
    private val model = GenerativeModel(
//            modelName = "gemini-exp-1114",
//            modelName = "gemini-1.5-pro",
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey,
        safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE),
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE)
        ), // Set all safety settings to BlockThreshold.NONE
        generationConfig = generationConfig {
            temperature = 1f
            topK = 40
            topP = 0.95f
            maxOutputTokens = 8192
            responseMimeType = "text/plain"
        },
        systemInstruction = content {
            text(
                "Fake News Detection System: Search everywhere on internet and deep research.\n" +
                        "Give result in text as FAKE or REAL only\n"
            )

        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titleInput = findViewById(R.id.inputTitle)
        textInput = findViewById(R.id.inputText)
        subjectInput = findViewById(R.id.inputSubject)
        checkButton = findViewById(R.id.checkButton)
        resultIcon = findViewById(R.id.resultIcon)
        progressBar = findViewById(R.id.progressBar)


        setLoadingState(false)

        checkButton.setOnClickListener {
            val title = titleInput.text.toString()
            val text = textInput.text.toString()
            val subject = subjectInput.text.toString()

            if (title.isNotEmpty() && text.isNotEmpty() && subject.isNotEmpty()) {
                val prompt = createPrompt(title, text, subject)
                lifecycleScope.launch {
                    checkNewsWithGeminiApi(prompt)
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createPrompt(title: String, text: String, subject: String): String {
        return """
            A title, some text, subject(political, weather, general, breaking, sports, celebrity, science, fashion, etc), and location will be given of a certain news. And, result will show that the particular news is fake or true in only one word./n/n
            Title: $title\n
            Text: $text\n
            Subject: $subject\n
        """.trimIndent()
    }

    private suspend fun checkNewsWithGeminiApi(prompt: String) {
        setLoadingState(true)
        try {
            val response = withContext(Dispatchers.IO) {
                model.generateContent(prompt)
            }

            val isFake = response.text?.let { processResponse(it) } ?: false
            setLoadingState(false)

            if (isFake) {
                resultIcon.setImageResource(R.drawable.ic_fake_news)
            } else {
                resultIcon.setImageResource(R.drawable.ic_true_news)
            }

        } catch (e: Exception) {
            setLoadingState(false)
            Toast.makeText(
                this@MainActivity,
                "Failed to check news: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun processResponse(responseText: String): Boolean {
        return responseText.contains("fake", ignoreCase = true)
    }

    private fun setLoadingState(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = ProgressBar.VISIBLE  // Show loading indicator
            resultIcon.visibility = ImageView.GONE  // Hide result icon
            checkButton.isEnabled = false  // Disable the button
        } else {
            progressBar.visibility = ProgressBar.GONE  // Hide loading indicator
            resultIcon.visibility = ImageView.VISIBLE  // Show result icon when done
            checkButton.isEnabled = true  // Enable the button
        }
    }


}
