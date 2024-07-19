package me.sshcrack.tinkerers_fletching.server;

import me.sshcrack.tinkerers_fletching.networking.TinkerersS2CNetworking;

public class TinkerersModDedicatedServer {
    public static void init() {
        TinkerersS2CNetworking.registerDedicated();
    }
}
