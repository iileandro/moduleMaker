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
public class AuxiliarGenerator {

    private String templateFile;
    private Class<?> classe;

    public AuxiliarGenerator() {
        this.templateFile = "templates/src/auxiliar.vm";
        this.classe = null;
    }

    public void generate(String classPath) {
        try {
            classe = Class.forName(classPath);

            String[] nomes = GenerateUtils.caminhoClasse(classe.getCanonicalName());
            String nomeDoModulo = null;
            String nomeDoSubmodulo = null;
            if (nomes.length == 5) {
                nomeDoSubmodulo = nomes[nomes.length - 2];
                nomeDoModulo = nomes[nomes.length - 4];
            } else {
                nomeDoModulo = nomes[nomes.length - 3];
            }

            Map<String, Object> context = new HashMap();
//            context.put("list", getNames());
            context.put("nomeDoModulo", nomeDoModulo);
            context.put("nomeDoModuloCapitalized", The.capitalizedWord(nomeDoModulo));
            context.put("nomeDoSubmodulo", nomeDoSubmodulo);

            ArrayList atributoList = GenerateUtils.listMapAtributoTipo(classe);
            context.put("atributoList", atributoList);

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result, "output/src/modules/"+nomeDoModulo , "DadosAuxiliares.txt"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
