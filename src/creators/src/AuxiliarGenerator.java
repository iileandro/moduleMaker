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
            Map<String, Object> context = new HashMap();

            context.putAll(GenerateUtils.listMapModuleElements(nomes));
//            context.putAll(GenerateUtils.listMapBeanElements(nomes, classe));

            context.put("atributoList", GenerateUtils.listMapAtributoTipo(classe));

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result, "output/src/modules/"+context.get("nomeDoModulo") , "DadosAuxiliares.txt"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
