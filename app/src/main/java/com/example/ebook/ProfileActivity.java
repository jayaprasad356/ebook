package com.example.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ebook.helper.Constant;
import com.example.ebook.helper.Session;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {


    Activity activity;
    TextView tvName,tvEmail;
    Session session;
    FirebaseAuth mAuth;
    RelativeLayout rlSignOut,rlHelpandContact,rlNeedbook;
    ImageView profile_image;
    private GoogleSignInClient mGoogleSignInClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        activity = ProfileActivity.this;
        session = new Session(activity);

        mAuth = FirebaseAuth.getInstance();


        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        rlSignOut = findViewById(R.id.rlSignOut);
        rlHelpandContact = findViewById(R.id.rlHelpAndContact);
        rlNeedbook = findViewById(R.id.rlNeedBook);
        profile_image = findViewById(R.id.profile_image);


        Glide.with(activity).load(session.getData(Constant.PROFILE)).into(profile_image);


        tvEmail.setText(session.getData(Constant.EMAIL));
        tvName.setText(session.getData(Constant.NAME));



        rlHelpandContact.setOnClickListener(v -> {

// intent to telegram changelink
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=AuBooksTechnicalpublication"));
            intent.setPackage("org.telegram.messenger");
            startActivity(intent);


        });


        rlNeedbook.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=AuBooksTechnicalpublication"));
            intent.setPackage("org.telegram.messenger");
            startActivity(intent);

        });


        rlSignOut.setOnClickListener(v -> {

            session.clearData();

            // Sign out from Firebase
            FirebaseAuth.getInstance().signOut();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            // Sign out from Google
            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Redirect to Login page
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


        });




    }
}