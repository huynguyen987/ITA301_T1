-- Create DATABASE and USE it
drop database if exists MPMS_1;
CREATE DATABASE IF NOT EXISTS MPMS_1;
USE MPMS_1;

-- Set up initial configurations
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Table structure for table `allocation`
DROP TABLE IF EXISTS `allocation`;
CREATE TABLE `allocation` (
  `allocation_id` int NOT NULL AUTO_INCREMENT,
  `member_id` int NOT NULL,
  `project_id` int NOT NULL,
  `project_role_id` int NOT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `effort_rate` decimal(5,2) DEFAULT NULL,
  `description` text,
  `status` bit(1) DEFAULT b'0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`allocation_id`),
  CONSTRAINT `fk_allocation_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_allocation_member` FOREIGN KEY (`member_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_allocation_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `fk_allocation_project_role` FOREIGN KEY (`project_role_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_allocation_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `issue`
DROP TABLE IF EXISTS `issue`;
CREATE TABLE `issue` (
  `issue_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `type_id` int DEFAULT NULL,
  `req_id` int DEFAULT NULL,
  `assigner_id` int DEFAULT NULL,
  `assignee_id` int DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `status_id` bit(3) DEFAULT b'001',
  `status_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`issue_id`),
  CONSTRAINT `fk_issue_assignee` FOREIGN KEY (`assignee_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_issue_assigner` FOREIGN KEY (`assigner_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_issue_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_issue_req` FOREIGN KEY (`req_id`) REFERENCES `requirement` (`req_id`),
  CONSTRAINT `fk_issue_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `project`
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `dept_id` int DEFAULT NULL,
  `status` bit(2) DEFAULT b'01',
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `code` (`code`),
  CONSTRAINT `fk_project_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_project_department` FOREIGN KEY (`dept_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_project_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `requirement`
DROP TABLE IF EXISTS `requirement`;
CREATE TABLE `requirement` (
  `req_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `owner_id` int DEFAULT NULL,
  `complexity_id` int DEFAULT NULL,
  `status_id` int DEFAULT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`req_id`),
  CONSTRAINT `fk_requirement_complexity` FOREIGN KEY (`complexity_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_requirement_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_requirement_owner` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_requirement_status` FOREIGN KEY (`status_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_requirement_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `setting`
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `setting_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `value` varchar(50) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `status` bit(1) DEFAULT b'1',
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`setting_id`),
  CONSTRAINT `fk_setting_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_setting_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `user`
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role_id` int DEFAULT NULL,
  `dept_id` int DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `note` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by_id` int DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_user_department` FOREIGN KEY (`dept_id`) REFERENCES `setting` (`setting_id`),
  CONSTRAINT `fk_user_created_by` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_user_updated_by` FOREIGN KEY (`updated_by_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insert initial data into `setting` table with categorized values in the `value` column

INSERT INTO `setting` (`name`, `value`, `type`, `priority`, `status`, `description`, `created_by_id`, `updated_by_id`)
VALUES
-- Project Roles
('Project Manager', 'Project Role', 1, 1, b'1', 'Role in projects', 1, 1),
('Developer', 'Project Role', 1, 2, b'1', 'Role in projects', 1, 1),
('Tester', 'Project Role', 1, 3, b'1', 'Role in projects', 1, 1),
('Designer', 'Project Role', 1, 4, b'1', 'Role in projects', 1, 1),

-- User Roles
('Admin', 'User Role', 2, 1, b'1', 'User role', 1, 1),
('Staff', 'User Role', 2, 2, b'1', 'User role', 1, 1),
('Team Leader', 'User Role', 2, 3, b'1', 'User role', 1, 1),
('Project Leader', 'User Role', 2, 4, b'1', 'User role', 1, 1),

-- Departments
('Development', 'Department', 3, 1, b'1', 'Department', 1, 1),
('Marketing', 'Department', 3, 2, b'1', 'Department', 1, 1),
('Finance', 'Department', 3, 3, b'1', 'Department', 1, 1),
('HR', 'Department', 3, 4, b'1', 'Department', 1, 1),

-- Complexity Levels
('High', 'Complexity Level', 4, 1, b'1', 'Complexity level', 1, 1),
('Medium', 'Complexity Level', 4, 2, b'1', 'Complexity level', 1, 1),
('Low', 'Complexity Level', 4, 3, b'1', 'Complexity level', 1, 1),

-- Requirement Status
('Pending', 'Requirement Status', 5, 1, b'1', 'Requirement status', 1, 1),
('In-progress', 'Requirement Status', 5, 2, b'1', 'Requirement status', 1, 1),
('Closed', 'Requirement Status', 5, 3, b'1', 'Requirement status', 1, 1),
('Cancelled', 'Requirement Status', 5, 4, b'1', 'Requirement status', 1, 1);

-- Thêm dữ liệu mẫu vào bảng `project`
INSERT INTO `project` (`name`, `code`, `start_date`, `end_date`, `dept_id`, `status`, `description`, `created_by_id`, `updated_by_id`)
VALUES
('Website Redesign', 'P001', '2022-01-01', '2022-03-01', 1, b'01', 'Redesign company website', 1, 1),
('Marketing Campaign', 'P002', '2022-01-15', '2022-04-15', 2, b'10', 'Launch marketing campaign', 2, 2),
('Mobile App Development', 'P003', '2022-02-01', '2022-06-01', 1, b'10', 'Develop mobile app', 3, 3),
('SEO Optimization', 'P004', '2022-02-20', '2022-05-20', 2, b'10', 'Optimize website for SEO', 4, 4),
('New Feature Implementation', 'P005', '2022-03-01', '2022-06-01', 1, b'01', 'Implement new features', 5, 5),
('Product Launch', 'P006', '2022-03-15', '2022-07-15', 2, b'01', 'Launch new product', 6, 6),
('Customer Support System', 'P007', '2022-04-01', '2022-08-01', 1, b'10', 'Develop support system', 7, 7),
('Cloud Migration', 'P008', '2022-05-01', '2022-09-01', 1, b'01', 'Migrate systems to the cloud', 8, 8),
('E-commerce Platform', 'P009', '2022-05-20', '2022-09-20', 2, b'01', 'Develop e-commerce platform', 9, 9),
('CRM System Update', 'P010', '2022-06-01', '2022-10-01', 3, b'01', 'Update CRM system for customer management', 10, 10);

-- Thêm dữ liệu mẫu vào bảng `user`
INSERT INTO `user` (`full_name`, `user_name`, `email`, `password`, `role_id`, `dept_id`, `start_date`, `status`, `note`, `created_by_id`, `updated_by_id`)
VALUES
('John Doe', 'jdoe', 'jdoe@example.com', 'password123', 1, 1, '2022-01-01', b'1', 'Admin user', 1, 1),
('Jane Smith', 'jsmith', 'jsmith@example.com', 'password123', 2, 2, '2022-01-15', b'1', 'Marketing specialist', 1, 1),
('Michael Johnson', 'mjohnson', 'mjohnson@example.com', 'password123', 3, 1, '2022-02-01', b'1', 'Developer', 1, 1),
('Emily Davis', 'edavis', 'edavis@example.com', 'password123', 4, 1, '2022-02-10', b'1', 'Team Leader', 1, 1),
('David Wilson', 'dwilson', 'dwilson@example.com', 'password123', 1, 1, '2022-02-20', b'1', 'Admin user', 1, 1),
('Sarah Brown', 'sbrown', 'sbrown@example.com', 'password123', 2, 2, '2022-03-01', b'1', 'Marketing manager', 1, 1),
('Chris Lee', 'clee', 'clee@example.com', 'password123', 3, 1, '2022-03-10', b'1', 'Developer', 1, 1),
('Olivia Martin', 'omartin', 'omartin@example.com', 'password123', 4, 2, '2022-03-15', b'1', 'Team Leader', 1, 1),
('James White', 'jwhite', 'jwhite@example.com', 'password123', 1, 3, '2022-04-01', b'1', 'Finance manager', 1, 1),
('Sophia Harris', 'sharris', 'sharris@example.com', 'password123', 2, 3, '2022-04-10', b'1', 'Finance specialist', 1, 1);

-- Thêm dữ liệu mẫu vào bảng `requirement`
INSERT INTO `requirement` (`title`, `owner_id`, `complexity_id`, `status_id`, `description`, `created_by_id`, `updated_by_id`)
VALUES
('Responsive Design', 1, 1, 1, 'Create responsive design for website', 1, 1),
('SEO Keyword Research', 2, 2, 2, 'Research SEO keywords for campaign', 2, 2),
('User Authentication', 3, 1, 3, 'Implement user authentication', 3, 3),
('Content Strategy', 4, 2, 4, 'Develop content strategy for marketing', 4, 4),
('Push Notifications', 5, 3, 1, 'Add push notifications to mobile app', 5, 5),
('Server Migration', 6, 3, 2, 'Migrate server to cloud', 6, 6),
('Marketing Material Creation', 7, 2, 3, 'Create marketing materials for campaign', 7, 7),
('Database Optimization', 8, 3, 4, 'Optimize database for performance', 8, 8),
('API Development', 9, 3, 1, 'Develop APIs for e-commerce platform', 9, 9),
('User Onboarding', 10, 2, 2, 'Implement user onboarding flow', 10, 10);

-- Thêm dữ liệu mẫu vào bảng `allocation`
INSERT INTO `allocation` (`member_id`, `project_id`, `project_role_id`, `from_date`, `to_date`, `effort_rate`, `description`, `status`, `created_by_id`, `updated_by_id`)
VALUES
(1, 1, 1, '2022-01-01', '2022-03-01', 100.00, 'Project manager for website redesign', b'1', 1, 1),
(2, 2, 2, '2022-01-15', '2022-04-15', 75.00, 'Frontend developer for marketing campaign', b'1', 2, 2),
(3, 3, 2, '2022-02-01', '2022-06-01', 100.00, 'Lead developer for mobile app', b'1', 3, 3),
(4, 4, 3, '2022-02-20', '2022-05-20', 50.00, 'Tester for SEO optimization', b'1', 4, 4),
(5, 5, 1, '2022-03-01', '2022-06-01', 50.00, 'Project manager for new feature implementation', b'1', 5, 5),
(6, 6, 2, '2022-03-15', '2022-07-15', 100.00, 'Developer for product launch', b'1', 6, 6),
(7, 7, 3, '2022-04-01', '2022-08-01', 50.00, 'Tester for customer support system', b'1', 7, 7),
(8, 8, 2, '2022-05-01', '2022-09-01', 100.00, 'Developer for cloud migration', b'1', 8, 8),
(9, 9, 1, '2022-05-20', '2022-09-20', 75.00, 'Project manager for e-commerce platform', b'1', 9, 9),
(10, 10, 2, '2022-06-01', '2022-10-01', 100.00, 'Developer for CRM system update', b'1', 10, 10);

-- Thêm dữ liệu mẫu vào bảng `issue`
INSERT INTO `issue` (`title`, `type_id`, `req_id`, `assigner_id`, `assignee_id`, `deadline`, `status_id`, `description`, `created_by_id`, `updated_by_id`)
VALUES
('Fix mobile layout', 1, 1, 1, 2, '2022-03-01', b'001', 'Fix mobile layout for website', 1, 1),
('Improve SEO ranking', 2, 2, 2, 3, '2022-03-15', b'010', 'Improve SEO ranking', 2, 2),
('Authentication bug', 1, 3, 3, 4, '2022-03-20', b'011', 'Fix authentication timeout issue', 3, 3),
('Content mismatch', 2, 4, 4, 5, '2022-04-01', b'001', 'Content mismatch with marketing strategy', 4, 4),
('Push notification delay', 1, 5, 5, 6, '2022-04-05', b'010', 'Push notifications are delayed', 5, 5),
('Server downtime', 1, 6, 6, 7, '2022-04-10', b'011', 'Server downtime during migration', 6, 6),
('Marketing material delay', 2, 7, 7, 8, '2022-04-15', b'100', 'Marketing materials delayed', 7, 7),
('Database query performance', 1, 8, 8, 9, '2022-04-20', b'001', 'Slow database queries', 8, 8),
('API authentication issue', 1, 9, 9, 10, '2022-04-25', b'010', 'Issue with API authentication', 9, 9),
('User onboarding flow bug', 1, 10, 10, 1, '2022-05-01', b'011', 'Bug in user onboarding flow', 10, 10);
