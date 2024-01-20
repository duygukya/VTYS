
package projeyonetimi;
public enum TaskStatus {
    TAMAMLANDI("Tamamlandı"),
    DEVAM_EDİYOR("Devam Ediyor"),
    TAMAMLANACAK("Tamamlanacak"),
    DEFAULT("Default");   
    
 private String value;
    TaskStatus(String value) {
        this.value = value;
    }    

    public String getValue() {
        return value;
    }
     public static TaskStatus fromValue(String value) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return DEFAULT;
    }
}
