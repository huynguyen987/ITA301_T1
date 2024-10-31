package controller;

import model.Setting;
import service.SettingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/settings")
public class SettingController extends HttpServlet {

    private SettingService settingService;

    @Override
    public void init() {
        settingService = new SettingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Assign a default value if action is null
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteSetting(request, response);
                    break;
                case "list":
                default:
                    listSettings(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listSettings(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Setting> settings = settingService.getAllSettings();
        request.setAttribute("listSettings", settings);
        request.getRequestDispatcher("/WEB-INF/jsp/setting-list.jsp").forward(request, response);
    }
    
     private void searchSettings(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Setting> settings = settingService.searchSettings(keyword);
        request.setAttribute("listSettings", settings);
        request.getRequestDispatcher("/WEB-INF/jsp/setting-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/setting-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Setting existingSetting = settingService.getSettingById(id);
        request.setAttribute("setting", existingSetting);
        request.getRequestDispatcher("/WEB-INF/jsp/setting-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("insert".equals(action)) {
                insertSetting(request, response);
            } else if ("update".equals(action)) {
                updateSetting(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void insertSetting(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        boolean status = request.getParameter("status") != null; // Set status to true if checkbox is checked
        String description = request.getParameter("description");

        // Get the highest priority and increment it for the new setting
        int maxPriority = settingService.getMaxPriority() + 1;

        Setting newSetting = new Setting();
        newSetting.setName(name);
        newSetting.setValue(value);
        newSetting.setPriority(maxPriority); // Automatically assign new priority
        newSetting.setStatus(status);
        newSetting.setDescription(description);

        settingService.addSetting(newSetting);
        response.sendRedirect("settings?action=list");
    }

    private void updateSetting(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        boolean status = request.getParameter("status") != null; // Set status to true if checkbox is checked
        String description = request.getParameter("description");

        Setting setting = settingService.getSettingById(id);
        setting.setName(name);
        setting.setValue(value);
        setting.setStatus(status);
        setting.setDescription(description);

        settingService.updateSetting(setting);
        response.sendRedirect("settings?action=list");
    }

    private void deleteSetting(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        settingService.deleteSetting(id);
        response.sendRedirect("settings?action=list");
    }
}
