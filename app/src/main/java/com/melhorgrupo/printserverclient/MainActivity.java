package com.melhorgrupo.printserverclient;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "MainActivity";

    Fragment fragmentAtual = new TarefasFragment();
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FormArquivoEnviadoActivity.class);
                startActivityForResult(intent, ArquivosEnviadosFragment.REQUEST_CODE_FORMULARIO);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        configurarMensagensFirebase();

        if (savedInstanceState == null) {
            // Fragment que vai abrir por padrão
            abrirFragment(new TarefasFragment(), TarefasFragment.TAG, false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Se o resultado da activity for referente ao formulário de arquivos
        if (requestCode == ArquivosEnviadosFragment.REQUEST_CODE_FORMULARIO) {
            Fragment arquivosEnviadosFragment = getSupportFragmentManager().findFragmentByTag(ArquivosEnviadosFragment.TAG);


            if (arquivosEnviadosFragment != null) {
                arquivosEnviadosFragment.onActivityResult(requestCode, resultCode, data);
            } else if (resultCode == FormArquivoEnviadoActivity.RESULT_SUCESSO) {
                // Se o resultado do form for SUCESSO, criar o fragment e chamar o onActivityResult

                // Não funciona: java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
//                arquivosEnviadosFragment = new ArquivosEnviadosFragment();
//                abrirFragment(arquivosEnviadosFragment, ArquivosEnviadosFragment.TAG);
//                arquivosEnviadosFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;
        String TAG;

        if (id == R.id.nav_tarefa) {
            fragment = new TarefasFragment();
            TAG = TarefasFragment.TAG;
        } else if (id == R.id.nav_arq_env) {
            fragment = new ArquivosEnviadosFragment();
            TAG = ArquivosEnviadosFragment.TAG;
        } else if (id == R.id.nav_sobre) {
            fragment = new SobreFragment();
            TAG = SobreFragment.TAG;
        } else {
            return false;
        }

        abrirFragment(fragment, TAG);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void abrirFragment(Fragment fragment, String TAG, boolean backStack) {
        fragment.setArguments(getIntent().getExtras());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, fragment, TAG);
        if (backStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    private void abrirFragment(Fragment fragment, String TAG) {
        abrirFragment(fragment, TAG, true);
    }

    private void configurarMensagensFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}
