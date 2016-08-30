package creators.web;

import utils.GenerateUtils;
import utils.VelocityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jorge Rafael
 */
public class LayoutGenerator {

    private String templateFile;
    private Class<?> classe;

    public LayoutGenerator() {
        this.templateFile = "templates/web/layout.vm";
        this.classe = null;
    }

    public void generate(ArrayList<String> beanList) {
        try {
            classe = Class.forName(beanList.get(0));
            
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
            context.put("nomeDoSubmodulo", nomeDoSubmodulo);

            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

//            System.out.println(GenerateUtils.criaArquivo(result, "src/modules/"+nomeDoModulo+"/actions"+((nomeDoSubmodulo!=null)?"/"+nomeDoSubmodulo:"") ,nomeDoBean + "Actions.java"));
            System.out.println(GenerateUtils.criaArquivo(result, "output/web/modules/"+nomeDoModulo+((nomeDoSubmodulo!=null)?"/"+nomeDoSubmodulo:"")+"/template", "layout.css"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
