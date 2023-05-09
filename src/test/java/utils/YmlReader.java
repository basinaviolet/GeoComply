package utils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YmlReader {

    protected static Yaml instance;

    public static Yaml getYaml() {
        if (instance == null) {
            instance = new Yaml();
        }
        return instance;
    }
    public Map<String, Object> readYaml(String path) {
        Yaml yaml = getYaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return yaml.load(inputStream);
    }
}
