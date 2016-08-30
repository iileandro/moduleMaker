package creators.web;

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
public class SubmenuGenerator {

    private String templateFile;
    private Class<?> classe;

    public SubmenuGenerator() {
        this.templateFile = "templates/web/submenu.vm";
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

            ArrayList beanMapList = GenerateUtils.listMapBeans(beanList);
            context.put("beanMapList", beanMapList);
            
            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

//            System.out.println(GenerateUtils.criaArquivo(result, "src/modules/"+nomeDoModulo+"/actions"+((nomeDoSubmodulo!=null)?"/"+nomeDoSubmodulo:"") ,nomeDoBean + "Actions.java"));
            System.out.println(GenerateUtils.criaArquivo(result, "output/web/modules/"+nomeDoModulo+((nomeDoSubmodulo!=null)?"/"+nomeDoSubmodulo:"")+"/template", "submenu.jsp"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
