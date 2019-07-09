package com.example.android.myhealth.ui.sendbird.main;


import com.example.android.myhealth.ui.sendbird.utils.PreferenceUtils;
import com.sendbird.android.SendBird;

public class ConnectionManager {
    public static void login(String userId, final SendBird.ConnectHandler handler) {
        SendBird.connect(userId, (user, e) -> {
            if (handler != null) {
                handler.onConnected(user, e);
            }
        });
    }

    public static void logout(final SendBird.DisconnectHandler handler) {
        SendBird.disconnect(() -> {
            if (handler != null) {
                handler.onDisconnected();
            }
        });
    }

    public static void addConnectionManagementHandler(String handlerId, final ConnectionManagementHandler handler) {
        SendBird.addConnectionHandler(handlerId, new SendBird.ConnectionHandler() {
            @Override
            public void onReconnectStarted() {
            }

            @Override
            public void onReconnectSucceeded() {
                if (handler != null) {
                    handler.onConnected(true);
                }
            }

            @Override
            public void onReconnectFailed() {
            }
        });

        if (SendBird.getConnectionState() == SendBird.ConnectionState.OPEN) {
            if (handler != null) {
                handler.onConnected(false);
            }
        } else if (SendBird.getConnectionState() == SendBird.ConnectionState.CLOSED) { // push notification or system kill
            String userId = PreferenceUtils.getUserId();
            SendBird.connect(userId, (user, e) -> {
                if (e != null) {
                    return;
                }

                if (handler != null) {
                    handler.onConnected(false);
                }
            });
        }
    }

    public static void removeConnectionManagementHandler(String handlerId) {
        SendBird.removeConnectionHandler(handlerId);
    }

    public interface ConnectionManagementHandler {
        /**
         * A callback for when connected or reconnected to refresh.
         *
         * @param reconnect Set false if connected, true if reconnected.
         */
        void onConnected(boolean reconnect);
    }
}

