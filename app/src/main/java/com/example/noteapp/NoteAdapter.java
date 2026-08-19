package com.example.noteapp;
import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.*;
import java.util.List;
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.VH> {
    interface OnClick { void onClick(Note n); }
    private final List<Note> notes;
    private final OnClick listener;
    NoteAdapter(List<Note> notes, OnClick listener) { this.notes = notes; this.listener = listener; }
    @Override public VH onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new VH(v);
    }
    @Override public void onBindViewHolder(VH h, int pos) {
        Note n = notes.get(pos);
        h.title.setText(n.title.isEmpty() ? "^(no title^)" : n.title);
        h.content.setText(n.content);
        h.itemView.setOnClickListener(v -> listener.onClick(n));
    }
    @Override public int getItemCount() { return notes.size(); }
    static class VH extends RecyclerView.ViewHolder {
        TextView title, content;
        VH(View v) { super(v); title = v.findViewById(R.id.tvTitle); content = v.findViewById(R.id.tvContent); }
    }
}
