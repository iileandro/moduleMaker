package creators.src;

import org.futurepages.util.The;
import utils.GenerateUtils;
import utils.VelocityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jorge Rafael
 */
public class RolesEnumGenerator {

    private String templateFile;
    private Class<?> classe;

    public RolesEnumGenerator() {
        this.templateFile = "templates/src/rolesEnum.vm";
        this.classe = null;
    }

    public void generate(ArrayList<String> beanList) {
        try {


            classe = Class.forName(beanList.get(0));

            String beanCanonicalName = classe.getCanonicalName();
            System.out.println(beanCanonicalName);
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

            String nomeDoModuloCapitalized = The.capitalizedWord(nomeDoModulo);

            String atributoChave = GenerateUtils.atributoChave(classe);
            String atributoChaveCapitalized = The.capitalizedWord(atributoChave);

            Map<String, Object> context = new HashMap();
//            context.put("list", getNames());
            context.put("nomeDoModulo", nomeDoModulo);
            context.put("nomeDoModuloCapitalized", nomeDoModuloCapitalized);
            context.put("nomeDoSubmodulo", nomeDoSubmodulo);
            context.put("nomeDoBean", nomeDoBean);
            context.put("nomeDaVariavelDoBean", nomeDaVariavelDoBean);
            context.put("atributoChave", atributoChave);
            context.put("atributoChaveCapitalized", atributoChaveCapitalized);
            context.put("atributoChaveTipo", classe.getDeclaredField(atributoChave).getType().getCanonicalName());
            context.put("atributoChaveTipoCapitalized", The.capitalizedWord(classe.getDeclaredField(atributoChave).getType().getCanonicalName()));

            context.put("atributoList", GenerateUtils.listMapAtributoTipo(classe));

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result, "output/src/modules/"+nomeDoModulo+"/enums"+((nomeDoSubmodulo!=null)?"/"+nomeDoSubmodulo:"") ,nomeDoModuloCapitalized + "RolesEnum.java"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
