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

import static com.fkulic.bugsy.MainActivity.CATEGORY_ALL;

/**
 * Created by Filip on 14.4.2017..
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    List<Article> mArticles;
    List<Article> allArticles;
    private String selectedCategory;

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    static OpenArticleInBrowser mOpenArticleInBrowser;

    public ArticleAdapter(OpenArticleInBrowser openArticleInBrowser) {
        this.allArticles = new ArrayList<>();
        this.mArticles = new ArrayList<>();
        mOpenArticleInBrowser = openArticleInBrowser;
        this.selectedCategory = CATEGORY_ALL;
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
        this.allArticles = articles;
        filterList();
        notifyDataSetChanged();
    }

    public void filterList() {
        if (selectedCategory.equals(CATEGORY_ALL)) {
            this.mArticles = this.allArticles;
        } else {
            this.mArticles = new ArrayList<>();
            for (Article article : this.allArticles) {
                if (article.getCategory().equals(selectedCategory)) {
                    this.mArticles.add(article);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void clear() {
        this.allArticles.clear();
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
