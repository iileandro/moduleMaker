package creators.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.futurepages.util.The;
import utils.GenerateUtils;
import utils.VelocityUtil;

/**
 *
 * @author Jorge Rafael
 */
public class ModuleManagerGenerator {

    private String templateFile;
    private Class<?> classe;

    public ModuleManagerGenerator() {
        this.templateFile = "templates/src/moduleManager.vm";
        this.classe = null;
    }

    public void generate(ArrayList<String> beanList) {
        try {

            
            classe = Class.forName(beanList.get(0));

            String[] nomes = GenerateUtils.caminhoClasse(classe.getCanonicalName());
            Map<String, Object> context = new HashMap();

            context.putAll(GenerateUtils.listMapModuleElements(nomes));
            context.putAll(GenerateUtils.listMapBeanElements(nomes, classe));

            context.put("beanMapList", GenerateUtils.listMapBeans(beanList));

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result, "output/src/modules/"+context.get("nomeDoModulo"), "ModuleManager.java"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}