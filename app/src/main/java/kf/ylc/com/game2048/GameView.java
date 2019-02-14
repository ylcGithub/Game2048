package kf.ylc.com.game2048;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView() {
        setOnTouchListener(new OnTouchListener() {
            //记录起始位置和偏移坐标
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: //监听手指按下的初始位置坐标
                        startX = event.getX();
                        startY = event.getY();
                        break;

                    case MotionEvent.ACTION_UP: //监听手指离开时的位置坐标
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                               swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                               swipeDown();
                            }
                        }
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
        setColumnCount(4);
        setBackgroundColor(0xffbbada0); // 设置整体背景
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h) - 10) / 4;
        addCards(cardWidth, cardWidth);
        startGame();
    }

    private void addCards(int cardWith, int cardHeight){
        Card c;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c=new Card(getContext());
                c.setNum(2);
                addView(c, cardWith, cardHeight);
                cardsMap[x][y] = c;
            }
        }
    }

    private void startGame() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {
        emptyPoints.clear();// 清空
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0) {
                    emptyPoints.add(new Point(x, y));
                }

            }
        }
        if(emptyPoints != null && emptyPoints.size() > 0){
            Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
            cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
        }
    }
    private boolean addRandomNum = false;
    private void swipeLeft() { // 往左滑动手指
        addRandomNum = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            addRandomNum = true;
                            x--;
                            break;
                        } else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            addRandomNum = true;
                            break;
                        }
                    }
                }
            }
        }
        if(addRandomNum)addRandomNum();
        getScore();
    }

    private void swipeRight() {
        addRandomNum = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >=0; x--) {

                for (int x1 = x - 1; x1 >=0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            addRandomNum = true;
                            x++;
                            break;
                        } else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            addRandomNum = true;
                            break;
                        }
                    }
                }
            }
        }
        if(addRandomNum) addRandomNum();
        getScore();
    }

    private void swipeUp() {
        addRandomNum = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            addRandomNum = true;
                            y--;
                            break;
                        } else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            addRandomNum = true;
                            break;
                        }
                    }
                }
            }
        }
        if(addRandomNum) addRandomNum();
        getScore();
    }

    private void swipeDown() {
        addRandomNum = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >=0; y--) {

                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {

                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            addRandomNum = true;
                            y++;
                            break;
                        } else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            addRandomNum = true;
                            break;
                        }
                    }
                }
            }
        }
        if(addRandomNum)addRandomNum();
        getScore();
    }

    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();

    public void getScore(){
        int score = 0;
        if(cardsMap != null){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int num = cardsMap[i][j].getNum();
                    if(num > score) score = num;
                }

            }
        }
        if(listener != null) listener.score(score);
    }

    public interface ScoreListener{
        void score(int score);
    }
    private ScoreListener listener;
    public void setListener(ScoreListener listener){
        this.listener = listener;
    }
}
