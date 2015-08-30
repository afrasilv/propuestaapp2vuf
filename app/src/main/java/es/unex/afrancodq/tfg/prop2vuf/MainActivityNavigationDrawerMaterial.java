package es.unex.afrancodq.tfg.prop2vuf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.unity3d.player.UnityPlayer;

public class MainActivityNavigationDrawerMaterial extends Activity {
    boolean sesionIniciada = false;
    FloatingActionButton settingsButton;
    public static Context mContext;

    protected UnityPlayer mUnityPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_material);

        settingsButton = (FloatingActionButton) findViewById(R.id.floating_button_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityNavigationDrawerMaterial.this, DialogActivity.class);
                i.putExtra("sesionIniciada", sesionIniciada);
                startActivity(i);
            }
        });

        mContext = this;
        getWindow().takeSurface(null);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        getWindow().setFormat(PixelFormat.RGBX_8888);

        mUnityPlayer = new UnityPlayer(this);
        if (mUnityPlayer.getSettings ().getBoolean ("hide_status_bar", true))
            getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int glesMode = mUnityPlayer.getSettings().getInt("gles_mode", 1);
        boolean trueColor8888 = false;
        mUnityPlayer.init(glesMode, trueColor8888);

        View playerView = mUnityPlayer.getView();

        FrameLayout layout = (FrameLayout) findViewById(R.id.fragment_container);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        layout.addView(playerView, 0, lp);
    }

    public void iniciarSesion(){
        sesionIniciada = true;
    }

    public void cerrarSesion(){
        sesionIniciada = false;
    }


    protected void onDestroy ()
    {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    // onPause()/onResume() must be sent to UnityPlayer to enable pause and resource recreation on resume.
    protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }
    protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();
    }
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.onKeyMultiple(event.getKeyCode(), event.getRepeatCount(), event);
        return super.dispatchKeyEvent(event);
    }

}
