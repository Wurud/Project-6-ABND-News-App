package com.example.wurudaldibasi.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    Context context;
    List<Article> articlesList;

    public ListAdapter(Context context, List<Article> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
    }

    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section, parent, false);
        return new ListAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ListViewHolder holder, int position) {
        holder.bind(articlesList.get(position));
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Article article;
        TextView title;
        TextView section;
        TextView date;
        TextView authorName;
        TextView url;

        public ListViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.article_title);
            section = itemView.findViewById(R.id.article_section);
            date = itemView.findViewById(R.id.article_date);
            authorName = itemView.findViewById(R.id.article_authorName);
            ;
            itemView.setOnClickListener(this);
        }

        public void bind(Article article) {
            title.setText(article.getTitle());
            section.setText(article.getSection());
            date.setText(article.getDate());
            authorName.setText(article.getAuthorName());
            this.article = article;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
            context.startActivity(intent);
        }
    }
}
