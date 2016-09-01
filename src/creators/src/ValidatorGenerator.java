package creators.src;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.futurepages.util.The;
import utils.GenerateUtils;
import utils.VelocityUtil;

/**
 *
 * @author Jorge Rafael
 */
public class ValidatorGenerator {

    private String templateFile;
    private Class<?> classe;

    public ValidatorGenerator() {
        this.templateFile = "templates/src/validator.vm";
        this.classe = null;
    }

    public ArrayList listMapAtributoTipo(Class<?> c) {
        ArrayList atributoList = new ArrayList();
        for (Field f : c.getDeclaredFields()) {
            Map<String, String> map = new HashMap();
            map.put("nome", f.getName());
            map.put("nomeCapitalized", The.capitalizedWord(f.getName()));
            map.put("tipo", f.getType().getSimpleName());
            atributoList.add(map);
        }
        return atributoList;
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
                    , context.get("nomeDoBean") + "Validator.java"));
//            System.out.println(GenerateUtils.criaArquivo(result, nomeDoBean, "Validator.java"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
