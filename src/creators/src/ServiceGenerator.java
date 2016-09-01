package creators.src;

import org.futurepages.util.The;
import utils.GenerateUtils;
import utils.VelocityUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jorge Rafael
 */
public class ServiceGenerator {
	private String templateFile;
	private Class<?> classe;

	public ServiceGenerator() {
		this.templateFile = "templates/src/service.vm";
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

			CharSequence result = VelocityUtil.getInstance().render(templateFile, context);

			System.out.println(GenerateUtils.criaArquivo(result, "output/src/modules/"+nomeDoModulo+"/services"+((nomeDoSubmodulo!=null)?"/"+nomeDoSubmodulo:"") ,nomeDoBean + "Services.java"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}