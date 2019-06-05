package com.example.gitsqaure;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gitsqaure.adapter.UserAdapter;
import com.example.gitsqaure.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gitsqaure.API.RetrofitClient.getAPIService;

public class SquareContribs extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.btn_filter)
    Button btnFilter;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    UserAdapter mAdapter;
    ArrayList<User> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.square_contribs);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new UserAdapter(this, data);
        recyclerView.setAdapter(mAdapter);

        getData();
        swipeLayout.setOnRefreshListener(this);
    }

    private void getData() {
        if (GlobalElements.isConnectingToInternet(this)) {
            progressbar.setVisibility(View.VISIBLE);
            Call<ResponseBody> call = getAPIService().getData();
            final boolean[] isUpdateData = {false};

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json_response = response.body().string();
                        JSONArray json = new JSONArray(json_response);
                        if (json.length() > 0) {
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject object = json.getJSONObject(i);
                                User user = new User();
                                user.setId(object.getString("id"));
                                user.setLogin(object.getString("login"));
                                user.setAvatar_url(object.getString("avatar_url"));
                                user.setRepos_url(object.getString("repos_url"));
                                user.setContributions(Integer.parseInt(object.getString("contributions")));
                                data.add(user);
                            }
                            mAdapter.notifyDataSetChanged();
                            progressbar.setVisibility(View.GONE);

                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressbar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.print("error" + t.getMessage());
                    progressbar.setVisibility(View.GONE);
                }
            });
        } else {
            progressbar.setVisibility(View.GONE);
            Toast.makeText(this, "No Network Present", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {

        if (GlobalElements.isConnectingToInternet(this)) {
            data.clear();
            mAdapter.notifyDataSetChanged();
            getData();
            swipeLayout.setRefreshing(true);
        } else {
            swipeLayout.setRefreshing(false);
            Toast.makeText(this, "No Network Present!!", Toast.LENGTH_SHORT).show();
        }

        swipeLayout.setRefreshing(false);
    }
}
