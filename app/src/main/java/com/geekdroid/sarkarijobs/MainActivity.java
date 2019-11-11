package com.geekdroid.sarkarijobs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.geekdroid.sarkarijobs.model.MySingleton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private TextView latest, admitcard, result, answerKey, quiz, syllabus;
    private RequestQueue requestQueue;
    TextView[] buttons;
    ProgressBar progressBar;
    ScrollView sv;
    private InterstitialAd mInterstitialAd;
    Document doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        latest = (TextView) findViewById(R.id.latest);
        admitcard = (TextView) findViewById(R.id.admit);
        result = (TextView) findViewById(R.id.result);
        answerKey = (TextView) findViewById(R.id.answerKey);
        quiz = (TextView) findViewById(R.id.quiz);
        syllabus = (TextView) findViewById(R.id.syllabus);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        sv = (ScrollView) findViewById(R.id.scrollView);

        new loadData().execute();
        mInterstitialAd=new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8692315641498587/5927607816");
        AdRequest adRequest=new  AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latestjob();
            }
        });

        admitcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admitCrad();
            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result();
            }
        });
        answerKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerKey();
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiz();
            }
        });
        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syllabus();
            }
        });


    }


    public class loadData extends AsyncTask<Void,Void,Void> {

        int btnArray[]={R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b8};

        TextView []btn=new TextView[btnArray.length];
        @Override
        protected Void doInBackground(Void... voids) {

            requestQueue = MySingleton.getInstance(MainActivity.this).getRequestQueue();

            final String url = getResources().getString(R.string.links);
            StringRequest mRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    sv.setVisibility(View.VISIBLE);
                    doc= Jsoup.parse(response);
                    Element tab = doc.select("table").get(0);
                    Elements row = tab.select("a");
                    for (int i = 0; i < 8; i++) {
                        btn[i] = (TextView) findViewById(btnArray[i]);
                        Element pName = row.select("a").get(i);
                        final String abs = pName.attr("abs:href");
                        btn[i].setText(pName.text());
                        btn[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               Intent intent=new Intent(MainActivity.this,Display.class);
                                intent.putExtra("links",abs);
                                startActivity(intent);
                            }
                        });

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Unable to Connect With Internet", Toast.LENGTH_SHORT).show();
                }
            });

            MySingleton.getInstance(MainActivity.this).addToRequestQueue(mRequest);

            return null;
        }
    }


    private void latestjob() {
        Intent intent=new Intent(this,Latest.class);
        startActivity(intent);
    }

    private void result() {
        Intent intent=new Intent(this,Result.class);
        startActivity(intent);
    }

    private void admitCrad() {
        Intent intent=new Intent(this,AdmitCard.class);
        intent.putExtra("name","Support");
        startActivity(intent);
    }
    private void answerKey() {
        Intent intent=new Intent(this,AnswerKey.class);
        intent.putExtra("name","Admin");
        startActivity(intent);
    }

    private void syllabus() {
        Intent intent=new Intent(this,Syllabus.class);
        startActivity(intent);
    }
    private void quiz() {
        Intent intent=new Intent(this,Quiz.class);
        startActivity(intent);
    }




    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_result) {
            startActivity(new Intent(MainActivity.this,Result.class));

        } else if (id == R.id.nav_quiz) {
            Intent intent=new Intent(MainActivity.this,Quiz.class);
            String st=" https://www.indiabix.com/";
            intent.putExtra("links",st);
            startActivity(intent);

        }else if (id == R.id.nav_gk) {
           Intent intent=new Intent(MainActivity.this,Quiz.class);
           String st="https://www.indiabix.com/current-affairs/questions-and-answers/";
           intent.putExtra("links",st);
           startActivity(intent);


        } else if (id == R.id.nav_reasoning) {
            Intent intent=new Intent(MainActivity.this,Quiz.class);
            String st="https://www.indiabix.com/verbal-ability/questions-and-answers/";
            intent.putExtra("links",st);
            startActivity(intent);


        }else if (id == R.id.nav_aptitude) {

            Intent intent=new Intent(MainActivity.this,Quiz.class);
            String st="https://www.indiabix.com/aptitude/questions-and-answers/";
            intent.putExtra("links",st);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            String link="https://play.google.com/store/apps/details?id=com.geekdroid.sarkarijobs";
            Intent likeIng = new Intent(Intent.ACTION_SEND);
            likeIng.putExtra(Intent.EXTRA_TEXT,link);
            likeIng.setType("text/plain");
            startActivity(likeIng);

        } else if (id == R.id.nav_more) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=TakshakSH&hl=en");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(likeIng);
            Toast.makeText(this, "More Apps", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this,About.class));
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setTitle("Are you sure to EXIT ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();

    }

}
