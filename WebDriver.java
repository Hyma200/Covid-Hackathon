package webApplication.pack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author hyma2
 */
public class WebDriver {
    @SuppressWarnings("empty-statement")
    public static ArrayList<State> states = new ArrayList<>();
    public void runApp() throws Exception{
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        WebClient webClient = new WebClient();  
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        
        /**
         * Uses google statistics to estimate number of deaths and recoveries associated with covid-19 in each state.
         */
        final HtmlPage googleStatistics = webClient.getPage("https://news.google.com/covid19/map?hl=en-US&mid=%2Fm%2F09c7w0&gl=US&ceid=US%3Aen");
        
        final HtmlTable cases = (HtmlTable) googleStatistics.getByXPath("//table").get(0);
        List<HtmlTableRow> casesRows = cases.getRows();
        for (int i = 0; i<casesRows.size(); i++){
            HtmlTableRow row = casesRows.get(i);
            List<HtmlTableCell> cells = row.getCells();
            try{
                int confirmed = Integer.parseInt(cells.get(1).asText().replace(",", ""));
                String name = cells.get(0).asText().replace(",", "");
                double confirmedRatio = Integer.parseInt(cells.get(2).asText().replace(",", ""))/10000.0;
                int deaths = Integer.parseInt(cells.get(4).asText().replace(",", ""));
                State s = new State(name);
                states.add(s);
                s.confirmed = confirmed;
                s.confirmedRatio = confirmedRatio;
                s.deaths = deaths;
                int recovered = Integer.parseInt(cells.get(3).asText().replace(",", ""));
                s.recovered = recovered;    
            }
            catch(Exception e){}
        }
        
        /**
         * Queries number of hospitals in each state.
         */
        
        final HtmlPage hospitalStaff = webClient.getPage("https://www.ahd.com/state_statistics.html");
        final HtmlTable table = (HtmlTable) hospitalStaff.getByXPath("//table").get(1);
        List<HtmlTableRow> rows = table.getRows();
        for (int i = 0; i<rows.size(); i++){
            String text = rows.get(i).asText();
            try{
		int firstOccurence = text.indexOf("	");
		int secondOccurence = text.indexOf("	", firstOccurence+1);
		int thirdOccurence = text.indexOf("	", secondOccurence+1);
                String numBeds = text.substring(secondOccurence+1, thirdOccurence);
                int beds = Integer.parseInt(numBeds.replace(",", ""));
                String stateName = text.substring((text.indexOf("-") + 2), text.indexOf("	"));
                for (int j = 0; j<states.size(); j++){
                    if (states.get(j).name.equals(stateName))
                        states.get(j).beds = beds;
                }
            }
            catch(Exception e){}
        }
        
        /**
         * ArrayList of states contains the following info.
         */
         for (int j = 0; j<states.size(); j++){
            //System.out.print(states.get(j).name + " " + states.get(j).beds + " ");
            //System.out.print(states.get(j).confirmed + " " + states.get(j).confirmedRatio + " " + states.get(j).recovered);
            //System.out.print(" " + states.get(j).deaths + " ");
            if(states.get(j).beds <99)
                states.get(j).staff = 410;
            else if (states.get(j).beds < 199)
                states.get(j).staff = 885;
            else if (states.get(j).beds < 299)
                states.get(j).staff = 1579;
            else if (states.get(j).beds < 399)
                states.get(j).staff = 2288;
            else if (states.get(j).beds<499)
                states.get(j).staff = 3241;
            else if (states.get(j).beds <599)
                states.get(j).staff = 6106;
            else if (states.get(j).beds < 1000)
                states.get(j).staff = 12212;
            else if (states.get(j).beds < 2000)
                states.get(j).staff = 24424;
            else if (states.get(j).beds < 4000)
                states.get(j).staff = 50000;
            else if (states.get(j).beds < 8000)
                states.get(j).staff = 10000;
            else if (states.get(j).beds < 16000)
                states.get(j).staff = 20000;
            else
                states.get(j).staff = 40000;
        }   
    }
    
    public String getStates() throws Exception{
        this.runApp();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<states.size(); i++){
            sb.append(states.get(i).name + "\n");
        }
        return sb.toString();
    }
    
    public static void main (String [] args) throws Exception{
        WebDriver wb = new WebDriver();
        wb.runApp();
        File file = new File ("C:\\Users\\hyma2\\Documents\\MyPersonalProjects\\coronaData.txt");
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            String header = "Name, Confirmed, Confirmed Ratio, Recovered, Deaths, Beds, Staff\n";
            writer.write(header);
            for (int i = 0; i<wb.states.size(); i++){
                writer.write(wb.states.get(i).name + "," + wb.states.get(i).confirmed + ",");
                writer.write(wb.states.get(i).confirmedRatio + "," + wb.states.get(i).recovered + ",");
                writer.write(wb.states.get(i).deaths + "," + wb.states.get(i).beds + ",");
                writer.write(wb.states.get(i).staff  + "\n");
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
        /*
        for (int i = 0; i<wb.states.size(); i++){
            System.out.print("NAME: " + wb.states.get(i).name + " CONFIRMED CASES: " +wb.states.get(i).confirmed);
            System.out.print(" Confirmed Ratio: "  + wb.states.get(i).confirmedRatio + "Recovered: " + wb.states.get(i).recovered);
            System.out.print("Deaths: " + wb.states.get(i).deaths + " Beds: " + wb.states.get(i).beds + " Staff: " + wb.states.get(i).staff);
            System.out.println();
        }*/
    }
    
}
