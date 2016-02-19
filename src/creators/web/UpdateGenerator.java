package creators.web;

import java.lang.reflect.Field;
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
public class UpdateGenerator {

    private String templateFile;
    private Class<?> classe;

    public UpdateGenerator() {
        this.templateFile = "templates/web/update.vm";
        this.classe = null;
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

            Map<String, Object> context = new HashMap();
//            context.put("list", getNames());
            context.put("nomeDoModulo", nomeDoModulo);
            context.put("nomeDoSubmodulo", nomeDoSubmodulo);
            context.put("nomeDoModuloCapitalized", The.capitalizedWord(nomeDoModulo));
            context.put("nomeDoBean", nomeDoBean);
            context.put("nomeDoBeanUnCapitalized", The.uncapitalizedWord(nomeDoBean));
            context.put("nomeDaVariavelDoBean", nomeDaVariavelDoBean);
            context.put("atributoChave", atributoChave);
            context.put("atributoChaveCapitalized", The.capitalizedWord(atributoChave));

            ArrayList atributoList = GenerateUtils.listMapAtributoTipo(classe);
            context.put("atributoList", atributoList);

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result, "web\\modules\\"+nomeDoModulo+((nomeDoSubmodulo!=null)?"\\"+nomeDoSubmodulo:"") ,nomeDoBean + "-update.jsp"));
//            System.out.println(GenerateUtils.criaArquivo(result, nomeDoBean, "-update.jsp"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
