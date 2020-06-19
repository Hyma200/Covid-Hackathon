/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webApplication.pack.State;
import webApplication.pack.WebDriver;

/**
 *
 * @author hyma2
 */
public class WebAppServerlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            File file = new File("C:\\Users\\hyma2\\Documents\\MyPersonalProjects\\coronaData.txt");
            Scanner s = new Scanner(file);
            ArrayList<State> states = new ArrayList<State>();
            String line;
            while(s.hasNextLine()){
                line = s.nextLine();
                String[] tokens = line.split(",");
                states.add(new State(tokens[0], Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6])));
            }
            s.close();
            
            out.println("<!DOCTYPE HTML>\n" +
"<html>\n" +
"<head>\n");
            out.println("<style>"
                    + "footer {\n" +
"  text-align: center;\n" +
"  padding: 3px;\n" +
"  background-color: DarkSalmon;\n" +
"  color: white;\n" +
"}"
                    + "</style>");
            //STARTING FORMATTING
        out.println("<meta charset=\"UTF-8\">\n" +
"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n" +
"<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Oswald\">\n" +
"<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Open Sans\">\n" +
"<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
"<style>\n" +
"h1,h2,h3,h4,h5,h6 {font-family: \"Oswald\"}\n" +
"body {font-family: \"Open Sans\"}\n" +
"</style>");  
                
                //ENDING FORMATTING
out.println("<script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script>\n" +
"<script type=\"text/javascript\">\n" +
"\n" +
"window.onload = function () {\n" +
"	var chart = new CanvasJS.Chart(\"chartContainer\", {\n" +
"		title:{\n" +
"			text: \"Recommended Covid-19 Funding for Each State\"              \n" +
"		},\n" +
"		data: [              \n" +
"		{\n" +
"			// Change type to \"doughnut\", \"line\", \"splineArea\", etc.\n" +
"			type: \"column\",\n" +
"			dataPoints: [\n");
                        for (int i = 0; i<states.size();i++){
                            out.println("{ label: \"" + states.get(i).name +"\",  y: " + states.get(i).getTotalCost() + "  },\n");
                        }
			
			out.println("]\n" +
"		}\n" +
"		]\n" +
"	});\n");
      out.println("var chart2 = new CanvasJS.Chart(\"chartContainer2\", {\n" +
"		title:{\n" +
"			text: \"Confirmed Cases in Each State\"              \n" +
"		},\n" +
"		data: [              \n" +
"		{\n" +
"			// Change type to \"doughnut\", \"line\", \"splineArea\", etc.\n" +
"			type: \"column\",\n" +
"			dataPoints: [\n");
                        for (int i = 0; i<states.size();i++){
                            out.println("{ label: \"" + states.get(i).name +"\",  y: " + states.get(i).confirmed + "  },\n");
                        }
			
			out.println("]\n" +
"		}\n" +
"		]\n" +
"	});\n");
        out.println("	var chart3 = new CanvasJS.Chart(\"chartContainer3\", {\n" +
"		title:{\n" +
"			text: \"Number of Covid-19 Deaths in Each State\"              \n" +
"		},\n" +
"		data: [              \n" +
"		{\n" +
"			// Change type to \"doughnut\", \"line\", \"splineArea\", etc.\n" +
"			type: \"column\",\n" +
"			dataPoints: [\n");
                        for (int i = 0; i<states.size();i++){
                            out.println("{ label: \"" + states.get(i).name +"\",  y: " + states.get(i).deaths + "  },\n");
                        }
			
			out.println("]\n" +
"		}\n" +
"		]\n" +
"	});\n");
out.println("	chart.render();\n" +
"	chart2.render();\n" +
"	chart3.render();\n" +
"}\n" +
"</script>\n"); 
out.println("</head>\n" +
"<body class=\"w3-light-grey\">\n");
             out.println("<div class=\"w3-content\" style=\"max-width:1600px\">\n" +
"\n" +
"  <header class=\"w3-container w3-center w3-padding-48 w3-white\">\n" +
"    <h1 class=\"w3-xxxlarge\"><b>Covid-19 Statistics for each state</b></h1>\n" +
"    <div id=\"chartContainer\" style=\"height: 300px; width: 100%;\"></div>\n" + 
"     <div class = \"w3 Container w3-light-gray\"> <p>The above graph displays the recommended amount of funding for coronavirus in each state."
                     + " It was calculated based on a seris of approximations on patient and staff data, including the number of recoveries,"
                     + "deaths, confirmed cases, and number of beds available in hospitals. Please place your carson on one of the"
                     + " above bars to get more information about specific funding. </p></div>" +                     
"    <div id=\"chartContainer2\" style=\"height: 300px; width: 100%;\"></div>\n" + 
"     <div class = \"w3 Container w3-light-gray\"> <p>The above graph displays the confirmed cases of covid-19 for each state."
                     + " It was a part of the data used to calculate the amount of funding needed for a particular state."
                     + " Please place your cursor on of the above bars to get more information about the specific number of confirmed cases. </p></div>" +   
"    <div id=\"chartContainer3\" style=\"height: 300px; width: 100%;\"></div>\n" +
"     <div class = \"w3 Container w3-light-gray\"> <p>The above graph displays the number of deaths due to covid-19 in each state."
                     + " Please place your carson on of the  above bars to get more information about the specific number of deaths. </p></div>" +                      
"  </header>");
     out.println("<footer>NOTE: The information displayed on these graphs is only based on approximations and can only be used for comparison"
             + "purposes. It is updated 3:00 PM EDT with the most recent data displayed by Google tracking of Covid-19. </footer>");
out.println("</body>\n" +
"</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
