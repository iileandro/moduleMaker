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
public class MenuGenerator {

    private String templateFile;
    private Class<?> classe;

    public MenuGenerator() {
        this.templateFile = "templates/web/menu.vm";
        this.classe = null;
    }

    public void generate(ArrayList<String> beanList) {
        try {
            Map<String, Object> context = new HashMap();

            classe = Class.forName(beanList.get(0));

            String[] nomes = GenerateUtils.caminhoClasse(classe.getCanonicalName());

            context.putAll(GenerateUtils.listMapModuleElements(nomes));

            ArrayList beanMapList = GenerateUtils.listMapBeans(beanList);
            context.put("beanMapList", beanMapList);
            
            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result
                    , "output/web/modules/"+context.get("nomeDoModulo")+((context.get("nomeDoSubmodulo")!=null)?"/"+context.get("nomeDoSubmodulo"):"")+"/template"
                    , "menu.jsp"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
