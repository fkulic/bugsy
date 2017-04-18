package com.fkulic.bugsy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filip on 14.4.2017..
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    List<Article> mArticles;
    static OpenArticleInBrowser mOpenArticleInBrowser;

    public ArticleAdapter(OpenArticleInBrowser openArticleInBrowser) {
        this.mArticles = new ArrayList<>();
        mOpenArticleInBrowser = openArticleInBrowser;
    }

    public interface OpenArticleInBrowser{
        void openArticleInBrowser(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View articleView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
        ViewHolder articleViewholder = new ViewHolder(articleView);
        return articleViewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.tvArticleTitle.setText(article.getTitle().trim());
        Picasso.with(holder.ivArticleImg.getContext())
                .load(article.getImgUrl())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(holder.ivArticleImg);
        holder.tvArticleDescription.setText(article.getDescription().trim());
        holder.tvArticleCategory.setText(article.getCategory());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void loadArticles(List<Article> articles) {
        this.mArticles = articles;
        notifyDataSetChanged();
    }

    public void clear() {
        this.mArticles.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvArticleTitle;
        private ImageView ivArticleImg;
        private TextView tvArticleDescription;
        private TextView tvArticleCategory;
        private TextView tvOpenArticleInBrowser;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.tvArticleTitle = (TextView) itemView.findViewById(R.id.tvArticleTitle);
            this.ivArticleImg = (ImageView) itemView.findViewById(R.id.ivArticleImg);
            this.tvArticleDescription = (TextView) itemView.findViewById(R.id.tvArticleDescription);
            this.tvArticleCategory = (TextView) itemView.findViewById(R.id.tvArticleCategory);
            this.tvOpenArticleInBrowser = (TextView) itemView.findViewById(R.id.tvOpenArticleInBrowser);

            this.tvOpenArticleInBrowser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOpenArticleInBrowser.openArticleInBrowser(getLayoutPosition());
                }
            });
        }

    }
}
