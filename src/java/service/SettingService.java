package service;

import dao.SettingDAO;
import model.Setting;

import java.sql.SQLException;
import java.util.List;

public class SettingService {

    private SettingDAO settingDAO;

    public SettingService() {
        this.settingDAO = new SettingDAO();
    }

    public Setting getSettingById(int id) throws SQLException {
        return settingDAO.selectSetting(id);
    }

    public List<Setting> getAllSettings() throws SQLException {
        return settingDAO.selectAllSettings();
    }

    public boolean addSetting(Setting setting) throws SQLException {
        return settingDAO.insertSetting(setting);
    }

    public boolean updateSetting(Setting setting) throws SQLException {
        return settingDAO.updateSetting(setting);
    }

    public boolean deleteSetting(int id) throws SQLException {
        return settingDAO.deleteSetting(id);
    }

    public List<Setting> getAllRoles() throws SQLException {
        return settingDAO.selectAllRoles();
    }

    public List<Setting> getAllDepartments() throws SQLException {
        return settingDAO.selectAllDepartments();
    }

    public int getMaxPriority() throws SQLException {
        return settingDAO.getMaxPriority();
    }

    public List<Setting> searchSettings(String keyword) throws SQLException {
        return settingDAO.searchSettings(keyword);
    }

}
