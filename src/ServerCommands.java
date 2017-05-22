


import java.io.*;


public final class ServerCommands {


    private static int firstLevel=1;

    private static int lastLevel=2;

    private static int clientNumber =0;

    private static  String[] levelList;

    private static boolean acceptingClients= true;


    static{
        try {
            BufferedReader br = new BufferedReader(new FileReader("Config\\levels.txt"));
            firstLevel=Integer.parseInt(br.readLine());
            lastLevel=Integer.parseInt(br.readLine());
            System.out.println(firstLevel);
            System.out.println(lastLevel);
        }
        catch (IOException e){
            System.out.println("Plik nie mógł zostać otwarty");
            System.out.println(e);
        }
        levelList=new String[lastLevel];
        for(int i=0;i<lastLevel;i++) {
            levelList[i] = "level" + (i + 1);
            System.out.println(levelList[i]);
        }

    }



    public static String serverAction(String command){
        String serverCommand = command;
        String originalCommand= command;
        System.out.println(command);
        if(command.contains("GET_LEVEL:")){
            originalCommand=command;
            serverCommand=("GET_LEVEL");
        }
        if(command.contains("GAME_SCORE:")){
            originalCommand=command;
            serverCommand="GAME_SCORE";
        }


        String serverMessage;
        switch (serverCommand){
            case "CONNECT":
                serverMessage= connect();
                break;
            case "GET_SETTINGS":
                serverMessage=getSettings();
                break;

            case "GET_LEVEL":
                String str[] = originalCommand.split(":");
                serverMessage=getLevel(Integer.parseInt(str[1]));
                break;



            default:
                serverMessage="INVALID_COMMAND";
        }
        return serverMessage;
    }



    private static String connect(){
        String serverMessage;
        if(acceptingClients) {
            serverMessage="CONNECTED "+clientNumber+"\n";
            clientNumber++;
        }
        else{
            serverMessage="CONNECTION_REJECTED";
        }
        return serverMessage;
    }


    private static String getSettings(){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("Config\\config.xml"))){
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
            }
        }
        catch (Exception e){
            System.out.println("get settings error: "+e);
        }
        System.out.println(sb.toString());

        return sb.toString();
    }






    private static String getLevel(int levelNumber){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("Config\\"+levelList[levelNumber-1]+".xml"))){
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
            }
        }
        catch (Exception e){

        }
        return sb.toString();
    }





}
