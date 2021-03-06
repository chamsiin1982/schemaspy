/*
 * This file is a part of the SchemaSpy project (http://schemaspy.sourceforge.net).
 * Copyright (C) 2011 John Currier
 *
 * SchemaSpy is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * SchemaSpy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.sourceforge.schemaspy.view;

import java.io.IOException;
import java.util.Collection;
import java.util.TreeSet;

import net.sourceforge.schemaspy.model.Database;
import net.sourceforge.schemaspy.model.Routine;
import net.sourceforge.schemaspy.util.LineWriter;

/**
 * The page that lists all of the routines (stored procedures and functions)
 * in the schema.
 *
 * @author John Currier
 */
public class HtmlRoutinesPage {
    private static HtmlRoutinesPage instance = new HtmlRoutinesPage();
    private TemplateService templateService;

    /**
     * Singleton: Don't allow instantiation
     */
    private HtmlRoutinesPage() {
    	templateService = TemplateService.getInstance();
    }

    /**
     * Singleton accessor
     *
     * @return the singleton instance
     */
    public static HtmlRoutinesPage getInstance() {
        return instance;
    }

    public void write(Database db, LineWriter html) throws IOException {
        Collection<Routine> routines = new TreeSet<Routine>(db.getRoutines());

        GlobalData globalData = new GlobalData();
		globalData.setDatabase(db);
        
        RoutinePageData data = new RoutinePageData();
        data.setGlobalData(globalData);
        setNumberOfFuncsAndProcs(data, routines);
        data.setRoutines(routines);
        
        html.writeln(templateService.renderTemplate("routines/routinesTemplate.ftl", data));
    }
    
    private void setNumberOfFuncsAndProcs(RoutinePageData data, Collection<Routine> routines){
    	int numProcs = 0;
        int numFuncs = 0;

        for (Routine routine : routines) {
            if (routine.isProcedur())
                ++numProcs;
            else if (routine.isProcedur())
                ++numFuncs;
        }
        data.setNumProcs(numProcs);
        data.setNumFuncs(numFuncs);
    }
}