

public class Calisan {
    private String ad;
    private String soyad;
    private String statu;  
    private String proje_adi;
    private String task_adi;   

    
    public Calisan(String ad, String soyad, String statu) {
        this.ad = ad;
        this.soyad = soyad;
        this.statu = statu;
        this.proje_adi = ""; // Default value for projeAdi
        this.task_adi = "";  // Default value for taskAdi
    }    
    public Calisan(String ad, String soyad, String statu,
            String proje_adi, String task_adi) {
        this.ad = ad;
        this.soyad = soyad;
        this.statu = statu;
        this.proje_adi = proje_adi;
        this.task_adi = task_adi;
    }   

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getProje_adi() {
        return proje_adi;
    }

    public void setProje_adi(String projeAdi) {
        this.proje_adi = projeAdi;
    }

    public String getTask_adi() {
        return task_adi;
    }

    public void getTask_adi(String taskAdi) {
        this.task_adi = taskAdi;
    }
}



