package creators.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.futurepages.util.Is;
import org.futurepages.util.The;
import utils.GenerateUtils;
import utils.VelocityUtil;

/**
 *
 * @author Jorge Rafael
 */
public class ExploreGenerator {

    private String templateFile;
    private Class<?> classe;

    public ExploreGenerator() {
        this.templateFile = "templates/web/explore.vm";
        this.classe = null;
    }

    public void generate(String classPath) {
        try {
            classe = Class.forName(classPath);
            String[] nomes = GenerateUtils.caminhoClasse(classe.getCanonicalName());
            Map<String, Object> context = new HashMap();

	        context.putAll(GenerateUtils.listMapModuleElements(nomes));
	        context.putAll(GenerateUtils.listMapBeanElements(nomes, classe));

            context.put("atributoList", GenerateUtils.listMapAtributoTipo(classe));
            
            CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

            System.out.println(GenerateUtils.criaArquivo(result,
		            "output/web/modules/"+context.get("nomeDoModulo")+((context.get("nomeDoSubmodulo")!=null)?"/"+context.get("nomeDoSubmodulo"):"") ,
		            context.get("nomeDoBean") + "-explore.jsp"));
//            System.out.println(GenerateUtils.criaArquivo(result, nomeDoBean, "-explore.jsp"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
