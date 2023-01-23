package com.example.livetv;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application
{
    public TVData tvData;

    @Override
    public void start(Stage stage) throws IOException
    {
        tvData.addChannels(
                new Channel(1, "La 1", "https://ztnr.rtve.es/ztnr/1688877.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/19/Logo_TVE-1.svg/269px-Logo_TVE-1.svg.png"),
                new Channel(2, "La 2", "https://ztnr.rtve.es/ztnr/1688885.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/75/Logo_TVE-2.svg/257px-Logo_TVE-2.svg.png"),
                new Channel(24, "24h", "https://ztnr.rtve.es/ztnr/1694255.m3u8", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Logo_TVE-24h.svg/281px-Logo_TVE-24h.svg.png")
        );

        Pagination TVView = new Pagination(TVData.tdt.getSize());
//        TVView.setPageCount(TVData.tdt.getSize());
        TVView.setCurrentPageIndex(0);
        TVView.setMaxPageIndicatorCount(TVData.tdt.getSize());

        TVView.setPageFactory(pageIndex ->
        {
            MediaPlayer mediaPlayer;

            Channel channel = TVData.tdt.get().get(pageIndex);

            if (TVData.playerMap.containsKey(channel.urlProperty.get()))
            {
                mediaPlayer = TVData.playerMap.get(channel.urlProperty.get());
            } else
            {
                mediaPlayer = new MediaPlayer(new Media(channel.urlProperty.get()));
                TVData.playerMap.put(channel.urlProperty.get(), mediaPlayer);
            }

            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setMute(true);

            MediaView mediaView = new MediaView(mediaPlayer);

            mediaView.autosize();
            mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(1800);

            return  mediaView;
        });

        VBox vBox = new VBox(TVView);
        Scene tvScene = new Scene(vBox, 960, 600);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 900);

        stage.maximizedProperty().addListener(((observableValue, aBoolean, t1) ->
        {
            if (stage.isMaximized())
            {
                stage.setScene(tvScene);
            }
        }));

        stage.setTitle("LiveTV");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}