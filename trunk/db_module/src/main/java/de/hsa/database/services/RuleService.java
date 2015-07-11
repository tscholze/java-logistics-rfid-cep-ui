package de.hsa.database.services;

import de.hsa.database.daos.BaseDao;
import de.hsa.database.daos.RuleDao;
import de.hsa.database.entities.Rule;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tobias Scholze
 * Date: 5/12/12
 * Time: 7:49 PM
 */
public class RuleService extends BaseDao<Rule> {
    private RuleDao dao = new RuleDao();

    public RuleService() {
        super(Rule.class);
    }

    public Rule loadByName(String name)
    {
        return dao.loadByName(name);
    }

    public Rule insert(Rule newRule) {
        if (checkIsUnique(newRule.getName()))
        {
            return dao.insert(newRule);    
        }
        else
        {
            System.out.println("Rule Name nicht einzugartig :(");
            System.out.println(listAll());
            System.exit(1);
        }
        return null;
    }
    
    public boolean checkIsUnique(String name)
    {
        Rule rule = loadByName(name);
        if(rule == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public List<Rule> listAll() {
        return dao.listAll();
    }

    public void dumpRuleList() {
        System.out.println("--- Dump Rule List ---");
        for (Rule rule: listAll()) {
            System.out.println(rule.toString());
            System.out.println("------------------");
        }
    }
}
