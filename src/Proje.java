
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
    public class Proje {
        private String projeAdi;
        private LocalDate baslangicTarihi;
        private LocalDate bitisTarihi;
        private ObservableList<String> tasks;
        private StringProperty durum; 
         private int adamGunDegeri;

        public Proje(String projeAdi, LocalDate baslangicTarihi, 
                LocalDate bitisTarihi, String durum) {
            this.projeAdi = projeAdi;
            this.baslangicTarihi = baslangicTarihi;
            this.bitisTarihi = bitisTarihi;
            this.tasks = FXCollections.observableArrayList();
            this.durum = new SimpleStringProperty(durum);
            this.adamGunDegeri= adamGunDegeri;
        }   

        public ObservableList<String> getTasks() {
        return tasks;
    }
        
    public void addTask(String task) {
        tasks.add(task);
    }
        

        public String getProjeAdi() {
            return projeAdi;
        }

        public LocalDate getBaslangicTarihi() {
            return baslangicTarihi;
        }

        public LocalDate getBitisTarihi() {
            return bitisTarihi;
        }
        public String getDurum() {
        return durum.get();
    }

    public void setDurum(String durum) {
        this.durum.set(durum);
    }

    public StringProperty durumProperty() {
        return durum;
    }

    public int getAdamGunDegeri() {
        return adamGunDegeri;
    }

    public void setAdamGunDegeri(int adamGunDegeri) {
        this.adamGunDegeri = adamGunDegeri;
    }
    
    
    
    }
