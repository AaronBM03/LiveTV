package com.example.livetv;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class HelloController
{
    @FXML
    private ListView videosListView;

    @FXML
    void initialize()
    {
        videosListView.setCellFactory(listView -> new ListCell<Channel>()
        {
            MediaView mediaView;
            Text txt;
            Text txtTitulo;
            VBox vbox;
            HBox hbox;
            ImageView imgLogo;

            @Override
            protected void updateItem(Channel channel, boolean empty)
            {
                super.updateItem(channel, empty);

                if (channel == null || empty)
                {
                    setGraphic(null);
                } else
                {
                    MediaPlayer mediaPlayer;

                    if (!TVData.playerMap.containsKey(channel.urlProperty.get()))
                    {
                        mediaPlayer = new MediaPlayer(new Media(channel.urlProperty.get()));
                        TVData.playerMap.put(channel.urlProperty.get(), mediaPlayer);
                    } else
                    {
                        mediaPlayer = TVData.playerMap.get(channel.urlProperty.get());
                    }

                    setAlignment(Pos.CENTER);

                    mediaPlayer.setAutoPlay(true);
                    mediaPlayer.setMute(!isSelected());

                    mediaView = new MediaView(mediaPlayer);
                    mediaView.autosize();
                    mediaView.setFitHeight(300);
                    mediaView.setPreserveRatio(true);

                    txt = new Text();
                    txt.setText(channel.nameProperty.get());
                    txt.setFont(Font.font("Open Sans", FontWeight.BOLD, 20));

                    imgLogo = new ImageView(new Image(channel.logoProperty.get()));
                    imgLogo.setPreserveRatio(true);
                    imgLogo.setFitHeight(50);

                    AnchorPane anchorPane = new AnchorPane(txt, imgLogo);
                    AnchorPane.setLeftAnchor(txt, 10.0);
                    AnchorPane.setRightAnchor(imgLogo, 10.0);
                    // En vez de usar el HBox, podriamos usar esto y asi se quedaría uno a cada lado

                    hbox = new HBox(txt, imgLogo);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, null, null)));

                    vbox = new VBox(txtTitulo, mediaView, hbox);
                    vbox.setAlignment(Pos.CENTER);

                    boundsInParentProperty().addListener((observableValue, oldBounds, newBounds) ->
                    {
                        if (newBounds.getHeight() != 0)
                        {
                            double listMaxY = getListView().getHeight();

                            if ((oldBounds.getMinY() < listMaxY && newBounds.getMinY() > listMaxY) || (newBounds.getMaxY() > 0 && oldBounds.getMaxY() <= 0))
                            {
                                // La celda desaparece por abajo
                                mediaPlayer.pause();
                            } else if ((oldBounds.getMinY() > listMaxY && newBounds.getMinY() < listMaxY) || (newBounds.getMaxY() <= 0 && oldBounds.getMaxY() > 0))
                            {
                                // La celda aparece por abajoç
                                mediaPlayer.play();
                            }
                        }

                    });

                    setGraphic(vbox);
                }
            }
        });
        videosListView.itemsProperty().bind(TVData.tdt);
    }

}