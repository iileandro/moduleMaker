package creators.src;

import java.util.HashMap;
import java.util.Map;
import org.futurepages.util.The;
import utils.GenerateUtils;
import utils.VelocityUtil;

/**
 *
 * @author Jorge Rafael
 */
public class DaoGenerator {

    private String templateFile;
    private Class<?> classe;

    public DaoGenerator() {
        this.templateFile = "templates/src/dao.vm";
        this.classe = null;
    }

    public void generate(String classPath) {
        try {
            classe = Class.forName(classPath);
            String[] nomes = GenerateUtils.caminhoClasse(classe.getCanonicalName());
            Map<String, Object> context = new HashMap();

            context.putAll(GenerateUtils.listMapModuleElements(nomes));
            context.putAll(GenerateUtils.listMapBeanElements(nomes, classe));

            context.put("atributoList", GenerateUtils.listMapAtributoTipo(classe));

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result
                    , "output/src/modules/"+context.get("nomeDoModulo")+((context.get("nomeDoSubmodulo")!=null)?"/"+context.get("nomeDoSubmodulo"):"")
                    , context.get("nomeDoBean") +  "Dao.java"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
