


import java.io.*;


public final class ServerCommands {


    /**
     * zmienna oznaczajaca pierwszy level
     */
    private static int firstLevel=1;

    /**
     * zmienna oznaczajaca ostatni level
     */
    private static int lastLevel=2;

    /**
     * zmienna przyporzadkowujaca lcizbe kolejnym laczacymi sie z nami klientami
     */
    private static int clientNumber =0;

    /**
     * lista leveli: level1, level2 itd.
     */
    private static  String[] levelList;

    /**
     * czy serwer akceptuje w danym momencie połączenia od klientów
     */
    private static boolean acceptingClients= true;


    /**
     * statyczny blok inicjujacy podstawowe zmienne
     */
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


    /**
     * Metoda która na podstawie komendy wysłanej do serwera tworzy odpowiednie dane do odesłania
     * @param command
     * @return
     */
    public static String serverAction(String command){
        String serverCommand = command;
        String originalCommand= command;
        System.out.println(command);
        if(command.contains("GET_LEVEL:")){
            originalCommand=command;
            serverCommand=("GET_LEVEL");
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


    /**
     * komenda tworząca dane połączenia klienta z serwerem (informacja o tym, że udało się połączyć)
     * @return
     */
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


    /**
     * tworzy ustawienia głównego menu
     * @return
     */
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


    /**
     * tworzy dane na podstawie których tworzony jest poziom
     * @param levelNumber
     * @return
     */
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
