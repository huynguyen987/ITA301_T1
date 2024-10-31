package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import controller.Requirement.*;


public class RequirementController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listRequirement";
        }

        try {
            switch (action) {
                case "searchRequirement":
                    new SearchRequirement().execute(request, response);
                    break;
                case "filterRequirement":
                    new FilterRequirement().execute(request, response);
                    break;
                case "detailRequirement":
                    new DetailRequirement().execute(request, response);
                    break;
                case "updateRequirement":
                    new UpdateRequirement().execute(request, response);
                    break;
                case "deleteRequirement":
                    new DeleteRequirement().execute(request, response);
                    break;
                case "insertRequirement":
                    new InsertRequirement().execute(request, response);
                    break;
                case "listRequirement":
                default:
                    new ListRequirement().execute(request, response);
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(RequirementController.class.getName()).log(Level.SEVERE, null, e);
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
