package com.example.numad22sp_huixinhuang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LinkCollector extends AppCompatActivity {
    private RecyclerView url_RecyclerView;
    private List<Url> urls;
    private FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        url_RecyclerView = (RecyclerView) findViewById(R.id.user_recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        url_RecyclerView.setLayoutManager(lm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(url_RecyclerView.getContext(),
               lm.getOrientation());
        url_RecyclerView.addItemDecoration(dividerItemDecoration);
        //url_RecyclerView.setAdapter();
        urls = new ArrayList<>();
        UserAdapter adapter = new UserAdapter(urls);
        url_RecyclerView.setAdapter(adapter);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                urls.remove(position);
                adapter.notifyItemRemoved(position);
                Snackbar snackbar = Snackbar
                        .make(viewHolder.itemView, "Remove item successfully", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar.show();
            }
        });
        itemTouchHelper.attachToRecyclerView(url_RecyclerView);


        addBtn = findViewById(R.id.floatingActionButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               TextInputEditText textInput = findViewById(R.id.item1);
               String text = textInput.getText().toString();
               if (text.startsWith("http") || text.startsWith("https")) {
                   textInput.getText().clear();
                   urls.add(new Url(text));
                   adapter.notifyItemInserted(urls.size() - 1);
                   Snackbar snackbar = Snackbar
                           .make(view, "Add URL successfully!", Snackbar.LENGTH_LONG)
                           .setAction("CLOSE", new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                               }
                           });
                   snackbar.show();
               } else {
                   Snackbar snackbar = Snackbar
                           .make(view, "URL invalid! It should start with http or https", Snackbar.LENGTH_LONG)
                           .setAction("CLOSE", new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                               }
                           });
                   snackbar.show();
               }
            }
        });
    }

    class UserAdapter extends RecyclerView.Adapter<UrlViewHolder> {
        private List<Url> urls;
        public UserAdapter(List<Url> urls) {
            super();
            this.urls = urls;
        }

        @Override
        public int getItemCount() {
            return this.urls.size();
        }

        @Override
       public UrlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
            return new UrlViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UrlViewHolder holder, int position) {
            Url url = urls.get(position);
            holder.bind(url);
        }

    }

    class UrlViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public UrlViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textView5);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = text.getText().toString();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    // TODO: Uri.parse should check valid URL.
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }

        public void bind(Url url) {
            text.setText(url.getUrl());
        }
    }

}



class Url {
    private String url;

    public Url(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}