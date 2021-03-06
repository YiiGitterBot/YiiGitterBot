package org.YiiCommunity.GitterBot.containers;

import com.amatkivskiy.gitter.sdk.model.response.UserResponse;
import com.amatkivskiy.gitter.sdk.model.response.message.MessageResponse;
import com.amatkivskiy.gitter.sdk.model.response.room.RoomResponse;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterApiClient;
import com.amatkivskiy.gitter.sdk.rx.client.RxGitterStreamingApiClient;
import com.amatkivskiy.gitter.sdk.sync.client.SyncGitterApiClient;
import com.squareup.okhttp.OkHttpClient;
import lombok.Getter;
import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.utils.L;
import retrofit.client.OkClient;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Gitter {
    @Getter
    private static Gitter instance = new Gitter();
    @Getter
    private static RxGitterStreamingApiClient streamingClient;
    @Getter
    private static RxGitterApiClient apiClient;
    @Getter
    private static SyncGitterApiClient syncApiClient;
    private static List<RoomResponse> rooms = null;

    public Gitter() {
        initApiClient();
        initStreamingClient();
        initSyncApiClient();
    }

    private void initStreamingClient() {
        streamingClient = new RxGitterStreamingApiClient.Builder()
                .withAccountToken(GitterBot.getInstance().getConfiguration().getGitterToken())
                .withClient(new OkClient(getOkHttpClient()))
                .build();
    }

    private void initApiClient() {
        apiClient = new RxGitterApiClient.Builder()
                .withAccountToken(GitterBot.getInstance().getConfiguration().getGitterToken())
                .withClient(new OkClient(getOkHttpClient()))
                .build();
    }

    private void initSyncApiClient() {
        syncApiClient = new SyncGitterApiClient.Builder()
                .withAccountToken(GitterBot.getInstance().getConfiguration().getGitterToken())
                .withClient(new OkClient(getOkHttpClient()))
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient okclient = new OkHttpClient();
        okclient.setConnectTimeout(15 * 1000, TimeUnit.MILLISECONDS);
        okclient.setReadTimeout(2147483647L, TimeUnit.MILLISECONDS);
        return okclient;
    }

    public static List<RoomResponse> getRooms() {
        if (rooms == null) {
            rooms = new ArrayList<>();
            rooms.addAll(Gitter.getSyncApiClient().getCurrentUserRooms());
        }

        return rooms;
    }

    public static UserResponse getUser(String userId) {
        L.$D("Requesting info about user # " + userId);
        return Gitter.getSyncApiClient().searchUsers(userId).get(0);
    }

    public static List<UserResponse> getUsersInRoom(String id) {
        return Gitter.getSyncApiClient().getRoomUsers(id);
    }

    public static void sendMessage(RoomResponse room, String text) {
        getApiClient().sendMessage(room.id, text).subscribe(new Observer<MessageResponse>() {
            @Override
            public void onCompleted() {
                L.$D("GitterBot sending message ... " + L.ANSI_GREEN + "[SUCCESS]" + L.ANSI_RESET);
            }

            @Override
            public void onError(Throwable e) {
                L.$D("GitterBot sending message ... " + L.ANSI_RED + "[ERROR] " + L.ANSI_RESET + "Response: " + e.getMessage());
            }

            @Override
            public void onNext(MessageResponse messageResponse) {

            }
        });
    }

    public static void broadcastMessage(String text) {
        for (RoomResponse room : Gitter.getRooms()) {
            sendMessage(room, text);
        }
    }
}
