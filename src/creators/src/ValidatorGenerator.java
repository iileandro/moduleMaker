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
            String nomeDoBean = nomes[nomes.length - 1];
            String nomeDaVariavelDoBean = The.uncapitalizedWord(nomeDoBean);
            String nomeDoModulo = null;
            String nomeDoSubmodulo = null;
            if (nomes.length == 5) {
                nomeDoSubmodulo = nomes[nomes.length - 2];
                nomeDoModulo = nomes[nomes.length - 4];
            } else {
                nomeDoModulo = nomes[nomes.length - 3];
            }

            String atributoChave = GenerateUtils.atributoChave(classe);
            String atributoChaveCapitalized = The.capitalizedWord(atributoChave);

            Map<String, Object> context = new HashMap();
//            context.put("list", getNames());
            context.put("nomeDoModulo", nomeDoModulo);
            context.put("nomeDoSubmodulo", nomeDoSubmodulo);
            context.put("nomeDoBean", nomeDoBean);
            context.put("nomeDaVariavelDoBean", nomeDaVariavelDoBean);
            context.put("atributoChave", atributoChave);
            context.put("atributoChaveCapitalized", atributoChaveCapitalized);

            ArrayList atributoList = GenerateUtils.listMapAtributoTipo(classe);
            context.put("atributoList", atributoList);

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result, "output/src/modules/"+nomeDoModulo+"/validators"+((nomeDoSubmodulo!=null)?"/"+nomeDoSubmodulo:"") ,nomeDoBean + "Validator.java"));
//            System.out.println(GenerateUtils.criaArquivo(result, nomeDoBean, "Validator.java"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
