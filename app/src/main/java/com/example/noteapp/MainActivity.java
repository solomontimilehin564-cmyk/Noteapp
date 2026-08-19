package com.example.noteapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    static final String PREFS = "notes_prefs";
    static final String KEY = "notes_json";
    private List<Note> notes = new ArrayList<>();
    private NoteAdapter adapter;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(notes, note -> {
            Intent i = new Intent(this, EditNoteActivity.class);
            i.putExtra("id", note.id);
            i.putExtra("title", note.title);
            i.putExtra("content", note.content);
            startActivity(i);
        });
        rv.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(this, EditNoteActivity.class)));
    }
    @Override protected void onResume() {
        super.onResume();
        loadNotes();
    }
    void loadNotes() {
        notes.clear();
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String json = prefs.getString(KEY, "[]");
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                notes.add(new Note(o.getLong("id"), o.getString("title"), o.getString("content")));
            }
        } catch (Exception ignored) {}
        adapter.notifyDataSetChanged();
    }
}
