
import java.time.LocalDate;
public class Task {   
    private String projectName;
    private String taskName;
    private String assignedEmployee;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    public Task(String projectName, String taskName, String assignedEmployee, 
            String status, LocalDate startDate, LocalDate endDate) {
        this.projectName = projectName;
        this.taskName = taskName;
        this.assignedEmployee = assignedEmployee;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(String assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

