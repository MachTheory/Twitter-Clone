/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,  View.OnKeyListener{





  public void ShowUserList(){
    Intent intent = new Intent(getApplicationContext(),User_Page.class);
    startActivity(intent);
  }

  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {
    if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
      signUp(view);
    }
    return false;
  }

  EditText username;
  EditText password;
  TextView signUpTextView;
  Button signUpButton;
  Boolean SignUpButtonVisible = true;
  ImageView iconImageView;
  RelativeLayout backgroundLayout;

  public void onClick (View view){

    if(view.getId()== R.id.logTextView){
    if(SignUpButtonVisible) {
      SignUpButtonVisible = false;
      signUpTextView.setText("or, Sign Up");
      signUpButton.setText("Login");
    }else{
      SignUpButtonVisible = true;
      signUpTextView.setText("or, Log In");
      signUpButton.setText("Sign Up");
    }
  }else if (view.getId() == R.id.iconImageView || view.getId() == R.id.backgroundLayout){
      InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
  }



  public void signUp (View view){

    if( username.length() == 0 || password.length() == 0) {
      Toast.makeText(this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
    }
    if( SignUpButtonVisible){
      ParseUser user = new ParseUser();
      user.setUsername(username.getText().toString());
      user.setPassword(password.getText().toString());

      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null){
            Log.i("Success", "Signup Complete!");
            ShowUserList();
          }else {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    } else{
      ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if (user != null) {
            Log.i("Success", "you have logged in");
            ShowUserList();
          } else {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    }

  }





  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("Instagram Demo");

    username = (EditText) findViewById(R.id.userEditText);
    password = (EditText) findViewById(R.id.passwordEditText);
    signUpTextView = (TextView) findViewById(R.id.logTextView);
    signUpButton = (Button) findViewById(R.id.button1);
    iconImageView = (ImageView)findViewById(R.id.iconImageView);
    backgroundLayout = (RelativeLayout)findViewById(R.id.backgroundLayout);
    iconImageView.setOnClickListener(this);
    backgroundLayout.setOnClickListener(this);
    password.setOnKeyListener(this);

    if (ParseUser.getCurrentUser() != null){
      ShowUserList();
    }


    ParseAnalytics.trackAppOpenedInBackground(getIntent());




  }
}
    //Parse User Creation
/*
    ParseUser user = new ParseUser();
    user.setUsername("Di");
    user.setPassword("password123");

    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null){
          Log.i("Success", "Signup Complete!");
        }else {
          e.printStackTrace();
        }
      }
    });



    ParseUser.logInInBackground("Di", "password123", new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(user != null){
          Log.i("Success", "you have logged in");
        }else{
          e.printStackTrace();
        }
      }
    });

    ParseUser.logOut();

    if (ParseUser.getCurrentUser() != null){
      Log.i("Logged in", ParseUser.getCurrentUser().getUsername());
    } else{
      Log.i("Sorry", "No user logged in");
    }


  }
}

 changing or advanced queries
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

    query.whereGreaterThanOrEqualTo("score", 70);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if (e == null){
          if(objects.size() > 0){
            for(ParseObject object: objects){
              object.put("score", object.getInt("score") + 20);
              object.saveInBackground();
              Log.i("username",object.getString("username"));
              Log.i("score", Integer.toString(object.getInt("score")));
            }
          }
        }
      }
    });


*/



/* This code was practice for creating and querying Parse data
    ParseObject score = new ParseObject("Score");
    score.put("username", "Di");
    score.put("score", 98);
    score.put("username", "Naomi");
    score.put("score", 97);

    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e==null){
          Log.i("Success", "The score has been saved");
        } else{
          e.printStackTrace();
        }
      }
    });

 */
    /*To grab stuff from parse server

    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("rQpY86mv8u", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if (e == null && object != null){
          Log.i("username", object.getString("username"));
          Log.i("score", Integer.toString(object.getInt("score")));
        }else{
          e.printStackTrace();
        }
      }
    });



   /*Practice parse object creation

    ParseObject tweet = new ParseObject("Tweet");
    tweet.put("username", "Mach");
    tweet.put("tweet", "Hey, This is Mach!");

    tweet.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null){
          Log.i("Success", "Tweet Saved!");
        }else{
          e.printStackTrace();
        }
      }
    });



    ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
    query.getInBackground("AZZMjjblHm", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if( e == null && object != null){

          object.put("tweet", "This is my second tweet!");
          object.saveInBackground();
          Log.i("username", object.getString("username"));
          Log.i("tweet", object.getString("tweet"));
        }else{
          e.printStackTrace();
        }
      }
    });

    */

