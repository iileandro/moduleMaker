package creators.web;

import utils.GenerateUtils;
import utils.VelocityUtil;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jorge Rafael
 */
public class FunctionsGenerator {

    private String templateFile;
    private Class<?> classe;

    public FunctionsGenerator() {
        this.templateFile = "templates/web/functions.vm";
        this.classe = null;
    }

    public void generate(ArrayList<String> beanList) {
        try {
            classe = Class.forName(beanList.get(0));
            String[] nomes = GenerateUtils.caminhoClasse(classe.getCanonicalName());
            Map<String, Object> context = new HashMap();

            context.putAll(GenerateUtils.listMapModuleElements(nomes));
            context.putAll(GenerateUtils.listMapBeanElements(nomes, classe));

            context.put("atributoList", GenerateUtils.listMapAtributoTipo(classe));

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result
                    , "output/web/modules/"+context.get("nomeDoModulo")+((context.get("nomeDoSubmodulo")!=null)?"/"+context.get("nomeDoSubmodulo"):"")+"/template"
                    , "functions.js"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}