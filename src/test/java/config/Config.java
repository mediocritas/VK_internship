package config;
import lombok.Getter;

@Getter
public class Config {
    public static final String BASE_URL = System.getProperty("BASE_URL", "https://api.ok.ru/fb.do");
    public static final String APPLICATION_KEY = System.getProperty("APPLICATION_KEY","CBOCDILGDIHBABABA");
    public static final String ACCESS_TOKEN = System.getProperty("ACCESS_TOKEN",
            "-n-Vpte1ky5EgIEso1Qz8JWFafvx4AFttIoMpv46Y4ZWO3OevvPFuELxavDo3G6QhkBMfHYehvBGSNSVzLos0");
}