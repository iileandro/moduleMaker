package utils;

import java.util.Properties;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Singleton that initializes velocity (see Apache Velocity project)
 *
 * @author huseyin
 *
 */
public class VelocityInitializer {

    private boolean isInitialized = false;

    // SINGLETON
    public static VelocityInitializer getInstance() {
        return SingletonHolder.instance;
    }

    // ---------------------------------------- INNER CLASS
    private static class SingletonHolder {
        private static final VelocityInitializer instance = new VelocityInitializer();
    }

    // ---------------------------------------- CONSTRUCTOR
    private VelocityInitializer() {
    }

    // INITIALIZATION METHOD
    /**
     * Initialize Velocity (template engine library)
     */
    public void initializeVelocity() {
        if (!isInitialized) {
            // Nós configuramos Velocity para carregar a load template
            //files from the classpath
            Properties p = new Properties();
            p.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//            p.setProperty("directive.foreach.counter.initial.value", "0");
            
            try {
                Velocity.init(p);
            } catch (Exception e) {
                throw new RuntimeException("Erro interno : impossível inicializar o velocity.", e);
            }
            isInitialized = true;
        }
    }
}
