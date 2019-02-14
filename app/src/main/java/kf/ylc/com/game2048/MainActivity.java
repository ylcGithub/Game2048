package kf.ylc.com.game2048;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = findViewById(R.id.gameView);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);//隐藏返回键
        actionBar.setTitle("游戏");
        gameView.setListener(new GameView.ScoreListener() {
            @Override
            public void score(int score) {
                showScore(score);
            }
        });
    }

    public void showScore(int score) {
        actionBar.setTitle(score + "");
    }

}
