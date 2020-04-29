package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;


public class DetailActivity extends AppCompatActivity {



    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView nIngredientsImageView;
    private TextView nOriginTextView;
    private TextView nAlsoKnowAsTextView;
    private TextView nIngredientsTextView;
    private TextView nDescriptionTextView;

    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nIngredientsImageView = findViewById(R.id.image_iv);
        nOriginTextView = findViewById(R.id.origin_tv);
        nAlsoKnowAsTextView = findViewById(R.id.also_known_tv);
        nIngredientsTextView = findViewById(R.id.ingredients_tv);
        nDescriptionTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(nIngredientsImageView);

        setTitle(mSandwich.getMainName());
        nOriginTextView.setText(mSandwich.getPlaceOfOrigin());
        nAlsoKnowAsTextView.setText(TextUtils.join(", ", mSandwich.getAlsoKnownAs()));
        nIngredientsTextView.setText(TextUtils.join(", ", mSandwich.getIngredients()));
        nDescriptionTextView.setText(mSandwich.getDescription());
    }
}