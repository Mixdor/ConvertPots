package com.mixdor.covertpots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mixdor.covertpots.databinding.ActAboutBinding

class ActAbout : AppCompatActivity() {

    private lateinit var binding: ActAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)

        binding = ActAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnFace.setOnClickListener{
            Toast.makeText(this,"Facebook", Toast.LENGTH_SHORT).show()
        }

        binding.btnTwitter.setOnClickListener{
            Toast.makeText(this,"Twitter", Toast.LENGTH_SHORT).show()
        }

        binding.btnInstagram.setOnClickListener{
            Toast.makeText(this,"Instagram", Toast.LENGTH_SHORT).show()
        }

        binding.btnYoutube.setOnClickListener{
            Toast.makeText(this,"YouTube", Toast.LENGTH_SHORT).show()
        }

        binding.btnFigma.setOnClickListener{
            Toast.makeText(this,"Figma", Toast.LENGTH_SHORT).show()
        }

        binding.btnGithub.setOnClickListener{
            Toast.makeText(this,"GitHub", Toast.LENGTH_SHORT).show()
        }

    }
}