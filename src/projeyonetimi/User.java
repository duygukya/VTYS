
package projeyonetimi;
public class User {
    private String kullaniciAdi;
    private String sifre;
    private String ad;
    private String soyad;
    private String pozisyon;
    public User(String kullaniciAdi, String sifre, String ad,
            String soyad, String pozisyon) {
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.ad = ad;
        this.soyad = soyad;
        this.pozisyon = pozisyon;
    } 
    public String getKullaniciAdi() {
        return kullaniciAdi;
    }
    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }
    public String getSifre() {
        return sifre;
    }
    public void setSifre(String sifre) {
        this.sifre = sifre;
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
    public String getPozisyon() {
        return pozisyon;
    }
    public void setPozisyon(String pozisyon) {
        this.pozisyon = pozisyon;
    }
}

