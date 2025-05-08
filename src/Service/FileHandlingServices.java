package Service;

import java.io.*;

public class FileHandlingServices {
    public void WriteLog(String logData){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.data",true))){
            bw.write(logData);
            bw.newLine();
        }catch (IOException e){
            System.out.println("Message: "+e.getMessage());
        }
    }

    public void ReadLog(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("log.data"));
            String line;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
            br.close();
        }catch (IOException e){
            System.out.println("Message: " + e.getMessage());
        }
    }
}