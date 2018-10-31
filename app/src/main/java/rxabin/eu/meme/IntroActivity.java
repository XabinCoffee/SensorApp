package rxabin.eu.meme;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    int a;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=0;

        MediaPlayer mp = MediaPlayer.create(this, R.raw.clap);
        mp.start();

        addSlide(AppIntroFragment.newInstance("fortnite 2", "click the screen to win big prizes", R.drawable.spook,
                getResources().getColor(R.color.degenerate)));

        addSlide(AppIntroFragment.newInstance("Sample Text", "yote", R.drawable.drake,
                getResources().getColor(R.color.degenerate)));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Toast.makeText(this, "no skipping allowed fuckface", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        if (a!=0) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.drake);
            mp.start();
        } else a++;

    }


}
