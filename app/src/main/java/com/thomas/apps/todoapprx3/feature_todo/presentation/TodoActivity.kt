package com.thomas.apps.todoapprx3.feature_todo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thomas.apps.todoapprx3.databinding.ActivityTodoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}