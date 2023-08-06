package me.xxgradzix.channels.entities;

public class ServerPlayerCountInfo {

    private String server;
    private int count;
    boolean isOnline;

    public ServerPlayerCountInfo(String server, int count, boolean isOnline) {
        this.server = server;
        this.count = count;
        this.isOnline = true;
    }

    public String getServer() {
        return server;
    }

    public int getCount() {
        return count;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
