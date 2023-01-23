package com.example.livetv;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Channel
{
    public SimpleIntegerProperty idProperty;
    public SimpleStringProperty nameProperty;
    public SimpleStringProperty urlProperty;
    public SimpleStringProperty logoProperty;

    public Channel(int id, String name, String url, String logo)
    {
        idProperty = new SimpleIntegerProperty(id);
        nameProperty = new SimpleStringProperty(name);
        urlProperty = new SimpleStringProperty(url);
        logoProperty = new SimpleStringProperty(logo);
    }
}
