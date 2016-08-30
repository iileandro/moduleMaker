package gerar;

import creators.src.ActionGenerator;
import creators.src.AuxiliarGenerator;
import creators.src.DaoGenerator;
import creators.src.ModuleGenerator;
import creators.src.RolesEnumGenerator;
import creators.src.ValidatorGenerator;
import creators.web.CreateGenerator;
import creators.web.ExploreGenerator;
import creators.web.FormGenerator;
import creators.web.FunctionsGenerator;
import creators.web.MenuGenerator;
import creators.web.SubmenuGenerator;
import creators.web.LayoutGenerator;
import creators.web.UpdateGenerator;
import utils.VelocityInitializer;
import modules.concursos.beans.*;


import java.util.ArrayList;

public class Remocao {

    public Remocao() {}
    
    public static void geraEntidade(String beanPath){
        ActionGenerator ag = new ActionGenerator();
        ag.generate(beanPath);
        DaoGenerator dg = new DaoGenerator();
        dg.generate(beanPath);
        ValidatorGenerator vg = new ValidatorGenerator();
        vg.generate(beanPath);
        ExploreGenerator eg = new ExploreGenerator();
        eg.generate(beanPath);
        CreateGenerator cg = new CreateGenerator();
        cg.generate(beanPath);
        UpdateGenerator ug = new UpdateGenerator();
        ug.generate(beanPath);
        FormGenerator fm = new FormGenerator();
        fm.generate(beanPath);
    }

    public static void main(String[] args) {
        VelocityInitializer.getInstance().initializeVelocity();

        ArrayList<String> beanList = new ArrayList();
        beanList.add(RemocaoConcurso.class.getName());
        beanList.add(RemocaoInscricao.class.getName());
        beanList.add(RemocaoConcorrencia.class.getName());
        for (String bean : beanList) {
            geraEntidade(bean);
        }
        
        ModuleGenerator mg = new ModuleGenerator();
        mg.generate(beanList);
        MenuGenerator menu = new MenuGenerator();
        menu.generate(beanList);
        SubmenuGenerator submenu = new SubmenuGenerator();
        submenu.generate(beanList);
        FunctionsGenerator fg = new FunctionsGenerator();
        fg.generate(beanList);
        LayoutGenerator tg = new LayoutGenerator();
        tg.generate(beanList);
        RolesEnumGenerator re = new RolesEnumGenerator();
        re.generate(beanList);
        AuxiliarGenerator auxiliar = new AuxiliarGenerator();
        auxiliar.generate(beanList.get(0));
    }
}