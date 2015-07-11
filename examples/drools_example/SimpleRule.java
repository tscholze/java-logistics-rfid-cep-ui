/*
 * Visualisierung eines RFID-Scanners
 * Copyright (C) 2012  ss12rfid-Team
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package examples.drools_example;

import java.io.File;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

// You will habe to import all the libraries into your eclipse project
// You will also have to set where your rule file is depending on system:
/*
File accountRules = new File(
				"/location/ oder C:\\location ... 
*/

public class SimpleRule {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Create KnowledgeBase...
		KnowledgeBase knowledgeBase = createKnowledgeBase();
		// Create a statefull session
		StatefulKnowledgeSession session = knowledgeBase
				.newStatefulKnowledgeSession();

		// Create Facts - two ban accounts
		Account account = new Account();
		account.setBalance(10);
		account.setId("N1");

		Account account2 = new Account();
		account2.setBalance(120);
		account2.setId("N2");

		// Insert the bank account facts into a State full session
		session.insert(account);
		session.insert(account2);

		// Only now we will fire the rules which are already in the agenda
		session.fireAllRules();

		// A message of N1 is less than 100 will be printed to stdout.
	}

	/**
	 * Create new knowledge base
	 */
	private static KnowledgeBase createKnowledgeBase() {
		KnowledgeBuilder builder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		// Add drl file into builder
		File accountRules = new File(
				"BasicAccountRule.drl");
		builder.add(ResourceFactory.newFileResource(accountRules),
				ResourceType.DRL);
		if (builder.hasErrors()) {
			throw new RuntimeException(builder.getErrors().toString());
		}

		KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();

		// Add to Knowledge Base packages from the builder which are actually
		// the rules from the drl file.
		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());

		return knowledgeBase;
	}

}