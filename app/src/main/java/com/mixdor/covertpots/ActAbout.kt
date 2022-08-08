package com.mixdor.covertpots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class ActAbout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CovertPots)
        setContentView(R.layout.act_about)

        val btnFacebook = findViewById<ImageButton>(R.id.btnFace)
        val btnTwitter = findViewById<ImageButton>(R.id.btnTwitter)
        val btnInsta = findViewById<ImageButton>(R.id.btnInstagram)
        val btnYoutube = findViewById<ImageButton>(R.id.btnYoutube)
        val btnFigma = findViewById<ImageButton>(R.id.btnFigma)
        val bntGithub = findViewById<ImageButton>(R.id.btnGithub)

        btnFacebook.setOnClickListener{
            Toast.makeText(this,"Facebook", Toast.LENGTH_SHORT).show()
        }

        btnTwitter.setOnClickListener{
            Toast.makeText(this,"Twitter", Toast.LENGTH_SHORT).show()
        }

        btnInsta.setOnClickListener{
            Toast.makeText(this,"Instagram", Toast.LENGTH_SHORT).show()
        }

        btnYoutube.setOnClickListener{
            Toast.makeText(this,"YouTube", Toast.LENGTH_SHORT).show()
        }

        btnFigma.setOnClickListener{
            Toast.makeText(this,"Figma", Toast.LENGTH_SHORT).show()
        }

        bntGithub.setOnClickListener{
            Toast.makeText(this,"GitHub", Toast.LENGTH_SHORT).show()
        }

    }
}