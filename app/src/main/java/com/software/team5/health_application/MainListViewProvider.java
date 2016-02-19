package com.software.team5.health_application;

/**
 * MainListViewProvider
 * The data Provider for MainListView
 *
 * Created by chenzitai on 18/02/16.
 */
public class MainListViewProvider {
    private int health_icon_resource;
    private String health_name;
    private String health_figre;

    public MainListViewProvider(int health_icon_resource, String health_name, String health_figre){
        this.setHealth_icon_resource(health_icon_resource);
        this.setHealth_name(health_name);
        this.setHealth_figre(health_figre);
    }
    public int getHealth_icon_resource() {
        return health_icon_resource;
    }

    public void setHealth_icon_resource(int health_icon_resource) {
        this.health_icon_resource = health_icon_resource;
    }

    public String getHealth_name() {
        return health_name;
    }

    public void setHealth_name(String health_name) {
        this.health_name = health_name;
    }

    public String getHealth_figre() {
        return health_figre;
    }

    public void setHealth_figre(String health_figre) {
        this.health_figre = health_figre;
    }
}
