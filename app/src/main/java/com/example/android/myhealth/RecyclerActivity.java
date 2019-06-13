package com.example.android.myhealth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private List<ListItem> listItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recycler);

		recyclerView = findViewById(R.id.Recyclerview);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		listItems = new ArrayList<>();

		for (int i = 0; i <= 5; i++) {
			ListItem listItem = new ListItem(
					"heading " + (i + 1),
					"Lorem Ipsum"
			);

			listItems.add(listItem);
			adapter = new RecyclerAdapter(listItems, this);
			recyclerView.setAdapter(adapter);
		}
	}

}
