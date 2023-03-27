package com.project.agroworld.chatbot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.agroworld.R;
import com.project.agroworld.databinding.ActivityChatbotBinding;

import java.util.ArrayList;

public class ChatBotActivity extends AppCompatActivity {

    ActivityChatbotBinding binding;
    private ArrayList<ChatBotModel> chatBotModels;
    private PerformRequest request;
    private ChatBotAdapter chatBotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chatbot);
        request = new PerformRequest(this);

        // Set RecyclerView layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        // Set an animation
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        chatBotModels = new ArrayList<>();
        chatBotAdapter = new ChatBotAdapter(chatBotModels);
        binding.recyclerView.setAdapter(chatBotAdapter);

        binding.msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.msgInput.getText().toString();
                if (message.length() != 0) {
                    chatBotModels.add(new ChatBotModel(true, message));
                    int newPosition = chatBotModels.size() - 1;
                    chatBotAdapter.notifyItemInserted(newPosition);
                    binding.recyclerView.scrollToPosition(newPosition);
                    binding.msgInput.setText("");
                    getReply(message);
                }
            }
        });

    }

    private void getReply(String message) {
        request.getResponse(message, new ChatBotListener() {
            @Override
            public void onError(String message) {
                Log.d("REQUEST ERROR", message);
            }

            @Override
            public void onResponse(String reply) {
                chatBotModels.add(new ChatBotModel(false, reply));
                int newPosition = chatBotModels.size() - 1;
                chatBotAdapter.notifyItemInserted(newPosition);
                binding.recyclerView.scrollToPosition(newPosition);
            }
        });

    }
}