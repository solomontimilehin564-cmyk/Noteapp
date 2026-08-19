package com.example.noteapp;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import org.json.JSONArray;
import org.json.JSONObject;
public class EditNoteActivity extends AppCompatActivity {
    private long noteId = -1;
    private EditText etTitle, etContent;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        if (getIntent().hasExtra("id")) {
            noteId = getIntent().getLongExtra("id", -1);
            etTitle.setText(getIntent().getStringExtra("title"));
            etContent.setText(getIntent().getStringExtra("content"));
        }
        toolbar.inflateMenu(R.menu.menu_edit);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_save) { save(); return true; }
            if (item.getItemId() == R.id.action_delete) { delete(); return true; }
            return false;
        });
    }
    void save() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);
        try {
            JSONArray arr = new JSONArray(prefs.getString(MainActivity.KEY, "[]"));
            JSONObject obj = new JSONObject();
            obj.put("id", noteId == -1 ? System.currentTimeMillis() : noteId);
            obj.put("title", etTitle.getText().toString());
            obj.put("content", etContent.getText().toString());
            if (noteId == -1) { arr.put(obj); }
            else {
                for (int i = 0; i < arr.length(); i++) {
                    if (arr.getJSONObject(i).getLong("id") == noteId) { arr.put(i, obj); break; }
                }
            }
            prefs.edit().putString(MainActivity.KEY, arr.toString()).apply();
        } catch (Exception ignored) {}
        finish();
    }
    void delete() {
        if (noteId == -1) { finish(); return; }
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);
        try {
            JSONArray arr = new JSONArray(prefs.getString(MainActivity.KEY, "[]"));
            JSONArray newArr = new JSONArray();
            for (int i = 0; i < arr.length(); i++) {
                if (arr.getJSONObject(i).getLong("id") != noteId) newArr.put(arr.getJSONObject(i));
            }
            prefs.edit().putString(MainActivity.KEY, newArr.toString()).apply();
        } catch (Exception ignored) {}
        finish();
    }
}
