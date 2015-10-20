package yiigitterbot;

public class YiiGitterBot {
    
    /**
     * Settings Gitter REST & Streaming API
     */
     static final String gitterToken = "873b11943ff0bfc4e4c5d6989f96da616121d246"; //https://developer.gitter.im/apps
    static final String gitterRoomId = "56266df816b6c7089cb7a9a7";
    static final String idGitterBot = "562567a916b6c7089cb79037"; // curl -i -H "Accept: application/json" -H "Authorization: Bearer 33caf7e2c9953a0dcbd7b5745bd4569ff17cf566" "https://api.gitter.im/v1/user"
    
    static final String gitterRestUrl = "https://api.gitter.im/v1/";
    static final String gitterStreamingUrl = "https://stream.gitter.im/v1/rooms/";

    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Yii Gitter Bot ... [START]");
        
        GitterRest gitter = new GitterRest();
        
        gitter.streaming();
    }
    
}
