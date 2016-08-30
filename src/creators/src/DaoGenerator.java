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
            context.put("atributoList", GenerateUtils.listMapAtributoTipo(classe));

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result, "output/src/modules/"+nomeDoModulo+"/dao"+((nomeDoSubmodulo!=null)?"/"+nomeDoSubmodulo:"") ,nomeDoBean + "Dao.java"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
