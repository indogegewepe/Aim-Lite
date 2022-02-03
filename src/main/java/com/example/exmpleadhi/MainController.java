package com.example.exmpleadhi;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.example.exmpleadhi.MainApplication.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    // tempat backgroun awal menu
    @FXML
    private ImageView bg;
    // tempat menggambar game utama

    @FXML
    private Canvas GameCanvas;

    @FXML
    private Text txtScore;
    @FXML
    private Text txtTime;
    
    //Rank
    @FXML
    private Region bg_rank;
    @FXML
    private GridPane gridrank;
    @FXML
    private Button leader;

    @FXML
    private Button Startbtn;
    @FXML
    private Button exitBtn;

    // TBLTBL
    @FXML
    private TableView<Player> tablerank;
    @FXML
    private TableColumn<Player, String> tbl_col_nama;
    @FXML
    private TableColumn<Player, Integer> tbl_col_score;

    //input nama
    @FXML
    private TextField inputnama;
    @FXML
    private Region bg_input;
    @FXML
    private Label labelnama;
    @FXML
    private Button btnMulai;

    // tampilan utama 
    @FXML
    private Text txtKesulitan;
    @FXML
    private Text nickP;

    // pemilihan diff
    @FXML
    private ComboBox<String> pilihan;
    private String[] opsi = {"ez","medium","god"};

    // Next Level

    @FXML
    private Button NextLevelCancel;

    @FXML
    private Pane NextLevelUI;

    @FXML
    private Button NextLevelYes;


    //Game Over
    @FXML
    private Region GO_bg;
    @FXML
    private Button GO_close;
    @FXML
    private GridPane GO_grid;
    @FXML
    private Text GO_kesulitan;
    @FXML
    private Text GO_nama;
    @FXML
    private Text GO_score;
    @FXML
    private Text GO_txt;
    @FXML
    private Label LevelLabel;

    // local atribut
    private int score;
    private int level;
    private int count;
    private int timer;
    private int index;
    private double opacity;
    private int[] flag;

    private Sprite aimgethit;
    private ArrayList<Sprite> aimtargets;
    AnimationTimer animTimer;

    private GraphicsContext gc;
    private Long lastNanoTime;


    // pembuatan counting timer
    @FXML
    private Text countTime;
    public Integer STARTTIME = 10;
    private Timeline timeline;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

    @Override
    public void initialize(URL location, ResourceBundle resources){
        pilihan.getItems().addAll(opsi);
        pilihan.setValue("ez");
        score = 0;
        level = 1;
        count = 0;
        timer = 0;
        index = -1;
        opacity = 1.0;
        timeSeconds = new SimpleIntegerProperty(timer);
        countTime.textProperty().bind(timeSeconds.asString());
        txtScore.setText("SCORE : " + score);
        aimtargets = new ArrayList<>();
        animTimer = new AnimTimer();
    }

    private class AnimTimer extends AnimationTimer {
        @Override
        public void handle(long currentNanoTime) {
            double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
            lastNanoTime = currentNanoTime;

            // render
            gc.clearRect(0,0, width, height);
            for (int i = 0; i < level; i++) {
                if (flag[i] == 1) {
                    aimtargets.get(i).setVelocity(0,0);
                    if (pilihan.getValue() == "medium") {
                        double speed = 150;
                        int randDirection = (int) (Math.random() * 4);
                        if (randDirection == 0)
                            aimtargets.get(i).addVelocity(-speed, 0);
                        else if (randDirection == 1)
                            aimtargets.get(i).addVelocity(speed, 0);
                        else if (randDirection == 2)
                            aimtargets.get(i).addVelocity(0, -speed);
                        else
                            aimtargets.get(i).addVelocity(0, speed);
                    } else if (pilihan.getValue() == "god") {
                        double speed = 300;
                        int randDirection = (int) (Math.random() * 4);
                        if (randDirection == 0)
                            aimtargets.get(i).addVelocity(-speed, 0);
                        else if (randDirection == 1)
                            aimtargets.get(i).addVelocity(speed, 0);
                        else if (randDirection == 2)
                            aimtargets.get(i).addVelocity(0, -speed);
                        else
                            aimtargets.get(i).addVelocity(0, speed);
                    }
                    aimtargets.get(i).update(GameCanvas.getWidth(), GameCanvas.getHeight(), elapsedTime);
                    gc.drawImage(aimtargets.get(i).getImage(), aimtargets.get(i).getPositionX(), aimtargets.get(i).getPositionY());
                }
            }
        }
    }

    private void startGame() {
        LevelLabel.setVisible(true);
        index = 1;
        opacity = 1.0;

        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(timer);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(timer+1),
                        new KeyValue(timeSeconds, 0))
        );
        timeline.playFromStart();
        timeline.setOnFinished((actionEvent) -> {
            LevelLabel.setVisible(false);
            GO_bg.setVisible(true);
            GO_close.setVisible(true);
            GO_grid.setVisible(true);

            GO_kesulitan.setText("Kesulitan : " +  pilihan.getValue());
            GO_kesulitan.setVisible(true);

            GO_nama.setText("Name : " + inputnama.getText());
            GO_nama.setVisible(true);

            GO_score.setText("Score : " + score);
            GO_score.setVisible(true);

            GO_txt.setVisible(true);

            nickP.setVisible(false);
            txtScore.setText("Score : 000" );

            GameCanvas.setVisible(false);
            inputnama.setVisible(false);
            bg_input.setVisible(false);
            labelnama.setVisible(false);
            btnMulai.setVisible(false);
            Startbtn.setVisible(false);
            txtKesulitan.setVisible(false);
            pilihan.setVisible(false);
            txtTime.setVisible(false);
            txtScore.setVisible(false);
            countTime.setVisible(false);
            exitBtn.setVisible(false);

            String query = "INSERT INTO `tbl_leaderboard` ( `Nama`, `Score`, `Difficulty`) VALUES ('"+ inputnama.getText() +"', '" + score + "', '" + pilihan.getValue() + "');";
            DBConnector.executeQuery(query);
            animTimer.stop();
        });

        double wCanvas = GameCanvas.getWidth();
        double hCanvas = GameCanvas.getHeight();
        gc = GameCanvas.getGraphicsContext2D();
        lastNanoTime = System.nanoTime();

        gc.clearRect(0,0,width, height);

        aimtargets.clear();
        flag = new int[level];
        LevelLabel.setText("LEVEL: " + level);
        for (int i = 0; i < level; i++) {
            Sprite aimtarget = new Sprite();
            aimtarget.setImage(getClass().getResource("aimtarget.png").toString());
            double px = (wCanvas-50) * Math.random();
            double py = (hCanvas-50) * Math.random();
            aimtarget.setPosition(px, py);
            gc.drawImage(aimtarget.getImage(), aimtarget.getPositionX(), aimtarget.getPositionY());
            aimtargets.add(aimtarget);
            flag[i] = 1;
        }
        aimgethit = new Sprite();
        aimgethit.setImage(getClass().getResource("dead-aimtarget.png").toString());

        // jika diklik
        GameCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent> () {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        for (int i = 0; i < level; i++) {
                            if (flag[i] == 1) {
                                Rectangle2D rect = aimtargets.get(i).getBoundary();
                                if (mouseEvent.getX() > rect.getMinX()
                                        && mouseEvent.getX() < rect.getMaxX()
                                        && mouseEvent.getY() > rect.getMinY()
                                        && mouseEvent.getY() < rect.getMaxY()) {
                                    // jika posisi mouse ada didalam area gambar
                                    flag[i] = 0; // kena hit
                                    score++; // tambah score
                                    count++;
                                    index = i;
                                    opacity = 1.0;
                                    txtScore.setText("Score : " + score);

                                    // jika level ini sudah selesai
                                    if (count == level) {
                                        count = 0;
                                        NextLevelUI.setVisible(true);
                                        // kasih yang mau dihilangin.

                                        if (timeline != null) {
                                            timeline.stop();
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
        animTimer.start();
    }

    @FXML
    void onNextLevelClick(ActionEvent event) {
        NextLevelUI.setVisible(false);
        level += 1;
        timer = 10 * (int) Math.ceil((double) level / 5);
        startGame();
    }

    //1. input nama & start the game
    public void inputnama() {
        nickP.setVisible(false);
        inputnama.setVisible(true);
        bg_input.setVisible(true);
        labelnama.setVisible(true);
        btnMulai.setVisible(true);
        score = 0;
        level = 1;
        count = 0;
        timer = 10 * (int)Math.ceil((double) level / 5);
        LevelLabel.setText("LEVEL : " + level);
        txtScore.setText("SCORE : " + score);
        timeSeconds = new SimpleIntegerProperty(timer);
        countTime.textProperty().bind(timeSeconds.asString());
    }

    // 2. game berjalan
    public void Start() {
        exitBtn.setVisible(true);
        // atur waktu
        countTime.textProperty().bind(timeSeconds.asString());
        countTime.setVisible(true);
        txtTime.setVisible(true);
        txtScore.setVisible(true);
        pilihan.setDisable(true);
        txtKesulitan.setVisible(true);
        txtKesulitan.setText("Kesulitan : " + pilihan.getValue());

        nickP.setVisible(true);
        // OUTPUTKAN NAMA
        nickP.setText("Player name : " + inputnama.getText());
        bg.setVisible(false);
        GameCanvas.setVisible(true);
        leader.setVisible(false);
        inputnama.setVisible(false);
        bg_input.setVisible(false);
        labelnama.setVisible(false);
        btnMulai.setVisible(false);
        Startbtn.setDisable(true);

        // txtScore.setText("Score : " + points.value);
        txtScore.setText("Score : 0");
        startGame();
    }


    // 3. exit game 
    public void Exit(ActionEvent e) {
        timeline.setRate(1000.0);
        NextLevelUI.setVisible(false);
        LevelLabel.setVisible(false);
        GO_bg.setVisible(true);
        GO_close.setVisible(true);
        GO_grid.setVisible(true);

        GO_kesulitan.setText("Kesulitan : " +  pilihan.getValue());
        GO_kesulitan.setVisible(true);

        GO_nama.setText("Name : " + inputnama.getText());
        GO_nama.setVisible(true);

        GO_score.setText("Score : " + score);
        GO_score.setVisible(true);

        GO_txt.setVisible(true);

        nickP.setVisible(false);
        txtScore.setText("Score : 000" );

        GameCanvas.setVisible(false);
        inputnama.setVisible(false);
        bg_input.setVisible(false);
        labelnama.setVisible(false);
        btnMulai.setVisible(false);
        Startbtn.setVisible(false);
        txtKesulitan.setVisible(false);
        pilihan.setVisible(false);
        txtTime.setVisible(false);
        txtScore.setVisible(false);
        countTime.setVisible(false);
        exitBtn.setVisible(false);

        String query = "INSERT INTO `tbl_leaderboard` ( `Nama`, `Score`, `Difficulty`) VALUES ('"+ inputnama.getText() +"', '" + score + "', '" + pilihan.getValue() + "');";
        DBConnector.executeQuery(query);
        animTimer.stop();
    }

    // 4. outputin leaderboard
    public void leaderBoard(ActionEvent e){
        pilihan.setDisable(false);
        nickP.setVisible(false);
        bg_rank.setVisible(true);
        gridrank.setVisible(true);
        inputnama.setVisible(false);
        bg_input.setVisible(false);
        labelnama.setVisible(false);
        btnMulai.setVisible(false);
        pilihan.setVisible(false);

        // show leaderboard
        tablerank.getItems().clear();
        showPlayers();
    }

    public void showPlayers(){
        ObservableList<Player> list = DBConnector.getPlayerList(pilihan.getValue());

        tbl_col_nama.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tbl_col_score.setCellValueFactory(new PropertyValueFactory<>("Score"));

        tablerank.setItems(list);
    }
    
    // 5. exit leaderboard
    public void closeLeaderboard(ActionEvent e){
        nickP.setVisible(false);
        bg_rank.setVisible(false);
        gridrank.setVisible(false);
        inputnama.setVisible(false);
        bg_input.setVisible(false);
        labelnama.setVisible(false);
        btnMulai.setVisible(false);
        pilihan.setVisible(true);
    }
    
    // keluar game
    public void GO_Close(){
        exitBtn.setVisible(false);
        leader.setVisible(true);
        GO_grid.setVisible(false);
        GO_bg.setVisible(false);
        bg.setVisible(true);
        
        Startbtn.setDisable(false);
        Startbtn.setVisible(true);
        pilihan.setDisable(false);
        pilihan.setVisible(true);
    }
    
}
