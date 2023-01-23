package com.example.livetv;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.media.MediaPlayer;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

public class TVData
{
    public static SimpleListProperty<Channel> tdt = new SimpleListProperty<>(FXCollections.observableArrayList());

    public static Map<String, MediaPlayer> playerMap = new HashMap<>();

    public static void addChannels(Channel ... channels)
    {
        tdt.get().addAll(channels);
    }
}
