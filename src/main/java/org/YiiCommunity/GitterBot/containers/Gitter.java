package org.YiiCommunity.GitterBot.containers;

import com.amatkivskiy.gitter.rx.sdk.api.RxGitterApiClient;
import com.amatkivskiy.gitter.rx.sdk.api.RxGitterStreamingApiClient;
import com.amatkivskiy.gitter.rx.sdk.model.response.message.MessageResponse;
import com.squareup.okhttp.OkHttpClient;
import lombok.Getter;
import org.YiiCommunity.GitterBot.GitterBot;
import org.YiiCommunity.GitterBot.utils.L;
import retrofit.client.OkClient;
import rx.Observer;

import java.util.concurrent.TimeUnit;

public class Gitter {
    private static Gitter instance = new Gitter();
    @Getter
    private static RxGitterStreamingApiClient streamingClient;
    @Getter
    private static RxGitterApiClient apiClient;

    public Gitter() {
        initApiClient();
        initStreamingClient();
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

    private OkHttpClient getOkHttpClient() {
        OkHttpClient okclient = new OkHttpClient();
        okclient.setConnectTimeout(15 * 1000, TimeUnit.MILLISECONDS);
        okclient.setReadTimeout(2147483647L, TimeUnit.MILLISECONDS);
        return okclient;
    }

    public static void sendMessage(String text) {
        getApiClient().sendMessage(GitterBot.getInstance().getConfiguration().getGitterRoomId(), text).subscribe(new Observer<MessageResponse>() {
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
}
