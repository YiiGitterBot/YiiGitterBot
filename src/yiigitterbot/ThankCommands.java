package yiigitterbot;

import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThankCommands {
    
    static final String thankPatternWords = "(?:Thank|Thanks|Спасибо|thank|thanks|спасибо)";
    static final String thankPatternUsername = "@([0-9a-zA-Z_]+)\\s";
    static final String thankPatternUsernameWords = "@([0-9a-zA-Z-_]+)\\s+" + thankPatternWords + "\\b";
    
    /**
     * Run Thank Commands
     * @param data
     * @throws ParseException
     * @throws Exception 
     */
    public void run(String data) throws ParseException, Exception {
        GitterRest gitter = new GitterRest();        
        JSONParser parser = new JSONParser();
        
        Object objData = parser.parse(data);        
        JSONObject jsonObj = (JSONObject) objData;
        JSONObject jsonUser = (JSONObject) jsonObj.get("fromUser");
        
        if (!jsonUser.get("id").toString().equals(YiiGitterBot.idGitterBot)){
            
            Pattern p = Pattern.compile(thankPatternUsernameWords);
            Matcher m = p.matcher(jsonObj.get("text").toString());
            
            while(m.find()){
                if (!jsonUser.get("username").toString().equals(m.group(1))) {
                    gitter.sendMessages("*Спасибо (+1) для @" + m.group(1) + " принято! Текущая карма **+0**.*");
                } else {
                    gitter.sendMessages("*@" + m.group(1) + " СЛАВА ПУТИНУ!!!*");
                }
            }
            
        }
    }
}