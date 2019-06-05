package com.example.gitsqaure.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gitsqaure.R;
import com.example.gitsqaure.model.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<User> users = new ArrayList<>();
    private Context mContext;
    String ori_type;
    onItemClick listner;

    public UserAdapter(Context context, List<User> categoryList) {
        if (categoryList != null)
            this.users = categoryList;
        mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView tvLogin;
        TextView tvLink;
        TextView tvContributors;


        public MyViewHolder(View view) {
            super(view);

            image = view.findViewById(R.id.image);
            tvLogin = view.findViewById(R.id.tv_login);
            tvLink = view.findViewById(R.id.tv_link);
            tvContributors = view.findViewById(R.id.tv_contributors);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final User user = users.get(position);
        holder.tvLogin.setText(user.getLogin());
        holder.tvContributors.setText("Contributors : " + user.getContributions());

        final SpannableString content = new SpannableString(user.getRepos_url());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.tvLink.setText(content);

        holder.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(user.getRepos_url()));
                mContext.startActivity(viewIntent);
            }
        });

        // Set the image
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(users.get(position).getAvatar_url())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public interface onItemClick {
        void onItemClicked(User note);
    }

    public void setOnItemClick(onItemClick listner) {
        this.listner = listner;
    }
}