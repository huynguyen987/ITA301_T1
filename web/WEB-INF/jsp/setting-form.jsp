<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Setting Form</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    </head>
    <body>
        <h2>${setting != null ? "Edit Setting" : "Add New Setting"}</h2>
        <form action="settings" method="post">
            <input type="hidden" name="action" value="${setting != null ? "update" : "insert"}">
            <input type="hidden" name="id" value="${setting != null ? setting.settingId : ""}">

            <label for="name">Name:</label>
            <input type="text" name="name" value="${setting != null ? setting.name : ""}" required><br>

            <label for="value">Value:</label>
            <select name="value" required>
                <option value="Project Roles" ${setting != null && "Project Roles".equals(setting.value) ? "selected" : ""}>Project Roles</option>
                <option value="User Roles" ${setting != null && "User Roles".equals(setting.value) ? "selected" : ""}>User Roles</option>
                <option value="Departments" ${setting != null && "Departments".equals(setting.value) ? "selected" : ""}>Departments</option>
                <option value="Complexity Levels" ${setting != null && "Complexity Levels".equals(setting.value) ? "selected" : ""}>Complexity Levels</option>
                <option value="Requirement Status" ${setting != null && "Requirement Status".equals(setting.value) ? "selected" : ""}>Requirement Status</option>
                <option value="Issue Types" ${setting != null && "Issue Types".equals(setting.value) ? "selected" : ""}>Issue Types</option>
            </select><br>

            <label for="status">Status:</label>
            <input type="checkbox" name="status" ${setting != null && setting.isStatus() ? "checked" : ""}><br>

            <label for="description">Description:</label>
            <textarea name="description">${setting != null ? setting.description : ""}</textarea><br>

            <button type="submit">Save</button>
            <a href="settings?action=list">Cancel</a>
        </form>
    </body>
</html>
